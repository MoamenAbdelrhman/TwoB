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
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.time.debounce
import java.net.UnknownHostException
import kotlin.time.Duration.Companion.milliseconds

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
            loadRequests()
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
        loadRequests()
    }
    private suspend fun loadRequests() {

        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        val employeeId = getEmployeeId()

        if (employeeId == null) {

            _state.update {
                it.copy(
                    isLoading = false,
                    requests = emptyList(),
                    errorMessage = "Employee ID is not available"
                )
            }

            return
        }

        repository
            .getHRLetters(
                employeeId = employeeId
            )
            .onSuccess { requests ->

                _state.update {
                    it.copy(
                        isLoading = false,
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

                // Same tab → do nothing
                if (currentStatusId == newStatusId) {
                    return
                }

                _state.update {
                    it.copy(
                        selectedStatus = action.status
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

                    loadRequests()
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