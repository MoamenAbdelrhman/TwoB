package com.example.twob.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twob.R
import com.example.twob.data.local.datastore.UserPreferencesRepository
import com.example.twob.data.remote.NetworkResult
import com.example.twob.data.repositories.EmployeeProfileRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val employeeProfileRepository: EmployeeProfileRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel()
{


    private val _state = MutableStateFlow(ProfileState())

    val state: StateFlow<ProfileState> =
        _state.asStateFlow()

    private val _effect =
        MutableSharedFlow<ProfileEffect>(extraBufferCapacity = 1)

    val effect: SharedFlow<ProfileEffect> =
        _effect.asSharedFlow()

    init {
        observeProfile()
        observeCulture()
        observeRole()
        loadProfileDetails()
    }

    private fun observeCulture() {
        viewModelScope.launch {

            userPreferencesRepository.culture
                .collectLatest { culture ->

                    _state.update {
                        it.copy(
                            culture = culture
                        )
                    }
                }
        }
    }

    fun onAction(action: ProfileAction) {

        when (action) {

            ProfileAction.ManagerClicked -> {
                openManagerDialog()
            }

            ProfileAction.EmployeesClicked -> {
                openEmployeesDialog()
            }

            ProfileAction.DepartmentClicked -> {
                openDepartmentDialog()
            }

            ProfileAction.LanguageClicked -> {
                // LanguageSelector handles selection directly
            }

            is ProfileAction.LanguageSelected -> {
                changeLanguage(action.culture)
            }

            ProfileAction.DismissDialog -> {
                dismissDialog()
            }

            ProfileAction.LogoutClicked -> {
                logout()
            }
        }
    }

    private fun changeLanguage(culture: String) {

        if (_state.value.culture == culture) return

        viewModelScope.launch {

            userPreferencesRepository.saveCulture(culture)

            when (
                val result =
                    employeeProfileRepository.refreshProfile()
            ) {

                is NetworkResult.Success -> {

                    val profile = result.data

                    val shiftId =
                        userPreferencesRepository
                            .shiftId
                            .first()
                            ?: error("Shift ID is not available")

                    userPreferencesRepository.saveUserProfile(
                        employeeId = profile.employeeId,
                        machineCode = profile.machineCode,
                        name = profile.name,
                        imageUrl = profile.imageUrl,
                        jobName = profile.jobName,
                        role = userPreferencesRepository.role.first().orEmpty(),
                        shiftId = shiftId
                    )

                    _state.update {
                        it.copy(
                            culture = culture,
                            managers = profile.managers,
                            employees = profile.employees,
                            departmentName = profile.departmentName,
                            partationName = profile.partationName,
                            hasManager = profile.managers.isNotEmpty(),
                            dialogErrorRes = null
                        )
                    }
                }

                is NetworkResult.Error -> {

                    _state.update {
                        it.copy(
                            dialogErrorRes = R.string.profile_dialog_generic_error
                        )
                    }
                }

                NetworkResult.Loading -> Unit
            }
        }
    }

    private fun observeProfile() {

        viewModelScope.launch {

            combine(
                userPreferencesRepository.employeeId,
                userPreferencesRepository.machineCode,
                userPreferencesRepository.employeeName,
                userPreferencesRepository.imageUrl,
                userPreferencesRepository.jobName
            ) { employeeId, machineCode, name, imageUrl, jobName ->

                ProfileLocalData(
                    employeeId = employeeId,
                    machineCode = machineCode,
                    name = name,
                    imageUrl = imageUrl,
                    jobName = jobName
                )

            }.collectLatest { profile ->

                _state.update { currentState ->

                    currentState.copy(
                        name = profile.name.orEmpty(),
                        machineCode = profile.machineCode.orEmpty(),
                        jobTitle = profile.jobName.orEmpty(),
                        imageUrl = profile.imageUrl,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadProfileDetails() {

        viewModelScope.launch {

            when (
                val result =
                    employeeProfileRepository.getEmployeeProfileData()
            ) {

                is NetworkResult.Success -> {

                    val profile = result.data

                    _state.update {
                        it.copy(
                            managers = profile.managers,
                            employees = profile.employees,
                            departmentName = profile.departmentName,
                            partationName = profile.partationName,
                            hasManager = profile.managers.isNotEmpty()
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _state.update {
                        it.copy(
                            dialogErrorRes = R.string.profile_dialog_generic_error
                        )
                    }
                }

                NetworkResult.Loading -> Unit
            }
        }
    }

    private fun logout() {

        viewModelScope.launch {

            _effect.emit(
                ProfileEffect.NavigateToLogin
            )

            userPreferencesRepository.clearUserData()
        }
    }

    private fun openManagerDialog() {

        _state.update {
            it.copy(
                activeDialog = ProfileDialog.MANAGER,
                dialogErrorRes = null
            )
        }
    }

    private fun openEmployeesDialog() {

        _state.update {
            it.copy(
                activeDialog = ProfileDialog.EMPLOYEES,
                dialogErrorRes = null
            )
        }
    }

    private fun openDepartmentDialog() {

        _state.update {
            it.copy(
                activeDialog = ProfileDialog.DEPARTMENT,
                dialogErrorRes = null
            )
        }
    }

    private fun dismissDialog() {

        _state.update {
            it.copy(
                activeDialog = null,
                dialogErrorRes = null
            )
        }
    }


    private fun observeRole() {
        viewModelScope.launch {
            userPreferencesRepository.role
                .collectLatest { role ->
                    _state.update {
                        it.copy(
                            isManager = role.equals(
                                "Manager",
                                ignoreCase = true
                            )
                        )
                    }
                }
        }
    }
}

private data class ProfileLocalData(
    val employeeId: Int?,
    val machineCode: String?,
    val name: String?,
    val imageUrl: String?,
    val jobName: String?
)