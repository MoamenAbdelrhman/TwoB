package com.example.twob.services.hrletter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twob.data.local.datastore.UserPreferencesRepository
import com.example.twob.data.remote.ConnectivityObserver
import com.example.twob.data.repositories.HRLetterRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class HRLetterRequestViewModel(
    private val repository: HRLetterRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _state =
        MutableStateFlow(HRLetterRequestState())

    val state = _state.asStateFlow()

    init {

        viewModelScope.launch {
            loadStatuses()
            loadRequests(statusId = null, showFullScreenLoading = true)
        }

        observeConnectivity()
    }

    private suspend fun getEmployeeId(): Int? {
        return userPreferencesRepository.employeeId.first()
    }


    private fun observeConnectivity() {

        var wasDisconnected = false

        connectivityObserver.isConnected
            .distinctUntilChanged()
            .onEach { isConnected ->

                if (!isConnected) {
                    wasDisconnected = true
                }
            }
            .filter { it }
            .debounce(1000)
            .onEach {

                if (wasDisconnected) {
                    wasDisconnected = false
                    refreshAfterReconnect()
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun refreshAfterReconnect() {

        val currentState = _state.value

        // Data was already loaded successfully.
        // No need to reload just because connectivity returned.
        if (currentState.hasLoadedRequests) {
            return
        }

        loadStatuses()
        loadRequests(
            statusId = currentState.selectedStatus?.id,
            showFullScreenLoading = true
        )
    }
    // `statusId`: which status tab to fetch for (null = "All").
    // `showFullScreenLoading`: true for the very first fetch of the screen
    // (nothing on screen yet, so it's fine to show a full loader). false
    // for tab switches / background refreshes, where the tabs and any
    // existing list are already visible and must stay that way — only
    // `isTabLoading` toggles, `isLoading` is left untouched.
    private suspend fun loadRequests(
        statusId: Int? = _state.value.selectedStatus?.id,
        showFullScreenLoading: Boolean = true
    ) {

        _state.update {
            if (showFullScreenLoading) {
                it.copy(isLoading = true, errorMessage = null)
            } else {
                it.copy(isTabLoading = true, errorMessage = null)
            }
        }

        val employeeId = getEmployeeId()

        if (employeeId == null) {

            _state.update {
                it.copy(
                    isLoading = false,
                    isTabLoading = false,
                    requests = emptyList(),
                    errorMessage = "Employee ID is not available"
                )
            }

            return
        }

        repository
            .getHRLetters(
                employeeId = employeeId,
                statusId = statusId
            )
            .onSuccess { requests ->

                _state.update {
                    it.copy(
                        isLoading = false,
                        isTabLoading = false,
                        requests = requests,
                        hasLoadedRequests = true,
                        errorMessage = null
                    )
                }
            }
            .onFailure { error ->

                _state.update {
                    it.copy(
                        isLoading = false,
                        isTabLoading = false,
                        errorMessage = getUserFriendlyErrorMessage(error)
                    )
                }
            }
    }

    private suspend fun loadStatuses() {

        repository
            .getHRStatuses()
            .onSuccess { statuses ->

                _state.update {
                    it.copy(
                        statuses = statuses,
                        errorMessage = null
                    )
                }
            }
            .onFailure { error ->

                _state.update {
                    it.copy(
                        errorMessage =
                            getUserFriendlyErrorMessage(error)
                    )
                }
            }
    }

    fun onAction(
        action: HRLetterRequestAction
    ) {

        when (action) {

            HRLetterRequestAction.AddRequestClicked -> {

                _state.update {
                    it.copy(
                        screen = HRLetterScreen.CREATE,
                        errorMessage = null,
                        successMessage = null
                    )
                }
            }

            is HRLetterRequestAction.BackClicked -> {

                _state.update {
                    it.copy(
                        screen = HRLetterScreen.LIST
                    )
                }
            }

            HRLetterRequestAction.ReasonClicked -> {

                _state.update {
                    it.copy(
                        showReasonDialog = true
                    )
                }
            }

            HRLetterRequestAction.DismissReasonDialog -> {

                _state.update {
                    it.copy(
                        showReasonDialog = false
                    )
                }
            }

            is HRLetterRequestAction.ReasonSelected -> {

                _state.update {
                    it.copy(
                        selectedReason = action.reason,
                        showReasonDialog = false
                    )
                }
            }

            HRLetterRequestAction.LanguageClicked -> {

                _state.update {
                    it.copy(
                        showLanguageDialog = true
                    )
                }
            }

            HRLetterRequestAction.DismissLanguageDialog -> {

                _state.update {
                    it.copy(
                        showLanguageDialog = false
                    )
                }
            }

            is HRLetterRequestAction.LanguageSelected -> {

                _state.update {
                    it.copy(
                        selectedLanguage = action.language,
                        showLanguageDialog = false
                    )
                }
            }

            is HRLetterRequestAction.AddressChanged -> {

                _state.update {
                    it.copy(
                        addressTo = action.value
                    )
                }
            }

            is HRLetterRequestAction.NoteChanged -> {

                _state.update {
                    it.copy(
                        note = action.value
                    )
                }
            }

            HRLetterRequestAction.SubmitClicked -> {
                submitRequest()
            }

            HRLetterRequestAction.DismissError -> {

                _state.update {
                    it.copy(
                        errorMessage = null
                    )
                }
            }

            HRLetterRequestAction.DismissSuccess -> {

                _state.update {
                    it.copy(
                        successMessage = null
                    )
                }
            }

            is HRLetterRequestAction.StatusSelected -> {

                val currentStatusId =
                    _state.value.selectedStatus?.id

                val newStatusId =
                    action.status?.id

                if (currentStatusId == newStatusId) {
                    return
                }

                // Update the selection immediately so the tab highlights
                // right away, then fetch that status's letters from the
                // server. `showFullScreenLoading = false` is the key part:
                // it makes loadRequests() toggle `isTabLoading` instead of
                // `isLoading`, so only the content area below the tabs
                // shows a loader — the tabs themselves never unmount.
                _state.update {
                    it.copy(selectedStatus = action.status)
                }

                viewModelScope.launch {
                    loadRequests(
                        statusId = newStatusId,
                        showFullScreenLoading = false
                    )
                }
            }
        }
    }

    private fun submitRequest() {

        viewModelScope.launch {

            val currentState = state.value

            val reason = currentState.selectedReason
            val language = currentState.selectedLanguage

            if (reason == null) {

                _state.update {
                    it.copy(
                        errorMessage =
                            "Please select a reason"
                    )
                }

                return@launch
            }

            if (language == null) {

                _state.update {
                    it.copy(
                        errorMessage =
                            "Please select the letter language"
                    )
                }

                return@launch
            }

            if (currentState.addressTo.isBlank()) {

                _state.update {
                    it.copy(
                        errorMessage =
                            "Please enter the recipient"
                    )
                }

                return@launch
            }

            _state.update {
                it.copy(
                    isSubmitting = true,
                    errorMessage = null,
                    successMessage = null
                )
            }

            val employeeId = getEmployeeId()

            if (employeeId == null) {

                _state.update {
                    it.copy(
                        isSubmitting = false,
                        errorMessage =
                            "Employee ID is not available"
                    )
                }

                return@launch
            }

            repository.registerHRLetter(
                employeeId = employeeId,
                reasonForRequest = reason.id,
                addressTo = currentState.addressTo.trim(),
                languageOfRequest = language.id,
                note = currentState.note.trim()
            )
                .onSuccess { message ->

                    _state.update {
                        it.copy(
                            isSubmitting = false,
                            screen = HRLetterScreen.LIST,
                            successMessage = message
                        )
                    }

                    loadRequests(
                        statusId = currentState.selectedStatus?.id,
                        showFullScreenLoading = false
                    )
                }
                .onFailure { error ->

                    _state.update {
                        it.copy(
                            isSubmitting = false,
                            errorMessage =
                                getUserFriendlyErrorMessage(error)
                        )
                    }
                }
        }
    }

    private fun getUserFriendlyErrorMessage(
        error: Throwable
    ): String {

        return when (error) {

            is UnknownHostException -> {
                "No internet connection. Please check your connection and try again."
            }

            else -> {
                error.message
                    ?: "Something went wrong. Please try again."
            }
        }
    }
}