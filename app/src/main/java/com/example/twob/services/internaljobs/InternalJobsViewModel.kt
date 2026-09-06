package com.example.twob.services.internaljobs

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twob.data.remote.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InternalJobsViewModel(
    private val repository: InternalJobsRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow(
            InternalJobsState()
        )

    val state: StateFlow<InternalJobsState> =
        _state.asStateFlow()

    init {
        loadAvailableJobs()
    }

    fun onAction(
        action: InternalJobsAction
    ) {
        when (action) {

            is InternalJobsAction.SelectTab -> {

                if (action.tab == _state.value.selectedTab) {
                    return
                }

                _state.update {
                    it.copy(
                        selectedTab = action.tab,
                        screen = InternalJobsScreen.LIST,
                        errorMessage = null
                    )
                }

                when (action.tab) {

                    InternalJobsTab.AVAILABLE ->
                        loadAvailableJobs()

                    InternalJobsTab.APPLIED ->
                        loadApplications()
                }
            }

            is InternalJobsAction.OpenJob -> {

                _state.update {
                    it.copy(
                        selectedJob = action.job,
                        screen =
                            InternalJobsScreen.JOB_DETAILS,
                        errorMessage = null
                    )
                }
            }

            InternalJobsAction.Back -> {
                handleBack()
            }

            InternalJobsAction.ApplyNow -> {

                _state.update {
                    it.copy(
                        screen =
                            InternalJobsScreen.APPLICATION,

                        selectedResumeName = null,

                        selectedResumeUri = null,

                        skills = "",

                        note = "",

                        errorMessage = null
                    )
                }
            }

            is InternalJobsAction.ResumeSelected -> {

                _state.update {
                    it.copy(
                        selectedResumeName =
                            action.fileName,

                        selectedResumeUri =
                            action.uri,

                        errorMessage = null
                    )
                }
            }

            is InternalJobsAction.SkillsChanged -> {

                _state.update {
                    it.copy(
                        skills = action.value,
                        errorMessage = null
                    )
                }
            }

            is InternalJobsAction.NoteChanged -> {

                _state.update {
                    it.copy(
                        note = action.value,
                        errorMessage = null
                    )
                }
            }

            InternalJobsAction.SubmitApplication -> {
                submitApplication()
            }

            is InternalJobsAction.OpenApplication -> {

                /*
                 * نفتح التفاصيل مبدئيًا بالبيانات الموجودة،
                 * وبعدها نطلب التفاصيل الكاملة من الـ API.
                 */
                _state.update {
                    it.copy(
                        selectedApplication =
                            action.application,

                        screen =
                            InternalJobsScreen
                                .APPLICATION_DETAILS,

                        errorMessage = null
                    )
                }

                loadApplicationById(
                    id = action.application.id
                )
            }

            InternalJobsAction.DeleteApplication -> {

                _state.update {
                    it.copy(
                        showDeleteDialog = true,
                        errorMessage = null
                    )
                }
            }

            InternalJobsAction.ConfirmDelete -> {
                deleteApplication()
            }

            InternalJobsAction.DismissDeleteDialog -> {

                _state.update {
                    it.copy(
                        showDeleteDialog = false
                    )
                }
            }

            InternalJobsAction.DismissError -> {

                _state.update {
                    it.copy(
                        errorMessage = null
                    )
                }
            }
        }
    }

    private fun loadAvailableJobs() {

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            when (
                val result =
                    repository.getAvailableJobs()
            ) {

                is NetworkResult.Success -> {

                    _state.update {
                        it.copy(
                            jobs = result.data,
                            isLoading = false
                        )
                    }
                }

                is NetworkResult.Error -> {

                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage =
                                result.message
                        )
                    }
                }

                NetworkResult.Loading -> Unit
            }
        }
    }

    private fun loadApplications() {

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            when (
                val result =
                    repository.getApplications()
            ) {

                is NetworkResult.Success -> {

                    _state.update {
                        it.copy(
                            applications =
                                result.data,
                            isLoading = false
                        )
                    }
                }

                is NetworkResult.Error -> {

                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage =
                                result.message
                        )
                    }
                }

                NetworkResult.Loading -> Unit
            }
        }
    }

    private fun loadApplicationById(
        id: Int
    ) {

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            when (
                val result =
                    repository.getApplicationById(id)
            ) {

                is NetworkResult.Success -> {

                    _state.update {
                        it.copy(
                            selectedApplication =
                                result.data,

                            isLoading = false
                        )
                    }
                }

                is NetworkResult.Error -> {

                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage =
                                result.message
                        )
                    }
                }

                NetworkResult.Loading -> Unit
            }
        }
    }

    private fun submitApplication() {

        val currentState =
            _state.value

        if (!currentState.canApply) {
            return
        }

        val job =
            currentState.selectedJob
                ?: return

        val resumeUri =
            currentState.selectedResumeUri
                ?: return

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isApplying = true,
                    errorMessage = null
                )
            }

            when (
                val result =
                    repository.registerApplication(
                        jobId = job.id,
                        notes = currentState.note,
                        skills = currentState.skills,
                        resumeUri = Uri.parse(resumeUri)
                    )
            ) {

                is NetworkResult.Success -> {

                    _state.update {
                        it.copy(
                            isApplying = false
                        )
                    }
                    loadApplicationsAfterSubmit()
                }

                is NetworkResult.Error -> {

                    _state.update {
                        it.copy(
                            isApplying = false,
                            errorMessage =
                                result.message
                        )
                    }
                }

                NetworkResult.Loading -> Unit
            }
        }
    }

    private fun loadApplicationsAfterSubmit() {

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            when (
                val result =
                    repository.getApplications()
            ) {

                is NetworkResult.Success -> {

                    val selectedApplication =
                        result.data
                            .firstOrNull {
                                it.job.id ==
                                        _state.value
                                            .selectedJob
                                            ?.id
                            }

                    _state.update {
                        it.copy(
                            applications =
                                result.data,

                            selectedApplication =
                                selectedApplication,

                            selectedTab =
                                InternalJobsTab.APPLIED,

                            screen =
                                if (
                                    selectedApplication != null
                                ) {
                                    InternalJobsScreen
                                        .APPLICATION_DETAILS
                                } else {
                                    InternalJobsScreen.LIST
                                },

                            isLoading = false
                        )
                    }
                }

                is NetworkResult.Error -> {

                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage =
                                result.message,

                            selectedTab =
                                InternalJobsTab.APPLIED,

                            screen =
                                InternalJobsScreen.LIST
                        )
                    }
                }

                NetworkResult.Loading -> Unit
            }
        }
    }

    private fun deleteApplication() {

        val application =
            _state.value.selectedApplication
                ?: return

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isDeleting = true,
                    errorMessage = null
                )
            }

            when (
                val result =
                    repository.deleteApplication(
                        id = application.id
                    )
            ) {

                is NetworkResult.Success -> {

                    _state.update {
                        it.copy(
                            isDeleting = false,
                            showDeleteDialog = false,
                            selectedApplication = null,
                            selectedTab =
                                InternalJobsTab.APPLIED,
                            screen =
                                InternalJobsScreen.LIST
                        )
                    }

                    loadApplications()
                }

                is NetworkResult.Error -> {

                    _state.update {
                        it.copy(
                            isDeleting = false,
                            errorMessage =
                                result.message
                        )
                    }
                }

                NetworkResult.Loading -> Unit
            }
        }
    }

    private fun handleBack() {

        when (
            _state.value.screen
        ) {

            InternalJobsScreen.LIST -> Unit

            InternalJobsScreen.JOB_DETAILS -> {

                _state.update {
                    it.copy(
                        screen =
                            InternalJobsScreen.LIST,

                        selectedJob = null
                    )
                }
            }

            InternalJobsScreen.APPLICATION -> {

                _state.update {
                    it.copy(
                        screen =
                            InternalJobsScreen.JOB_DETAILS
                    )
                }
            }

            InternalJobsScreen.APPLICATION_DETAILS -> {

                _state.update {
                    it.copy(
                        screen =
                            InternalJobsScreen.LIST,

                        selectedApplication = null,

                        selectedTab =
                            InternalJobsTab.APPLIED
                    )
                }

                loadApplications()
            }
        }
    }
}