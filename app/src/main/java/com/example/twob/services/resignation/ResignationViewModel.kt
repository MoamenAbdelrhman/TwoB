package com.example.twob.services.resignation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twob.data.remote.NetworkResult
import com.example.twob.data.remote.dto.ResignationDetailsDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResignationViewModel(
    private val resignationRepository: ResignationRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow(
            ResignationState()
        )

    val state: StateFlow<ResignationState> =
        _state.asStateFlow()

    init {
        checkExistingResignation()
    }

    fun onAction(action: ResignationAction) {

        when (action) {

            is ResignationAction.ResignationDateChanged -> {
                _state.update {
                    it.copy(
                        resignationDate = action.date,
                        errorMessage = null
                    )
                }
            }

            is ResignationAction.LastWorkingDateChanged -> {
                _state.update {
                    it.copy(
                        lastWorkingDate = action.date,
                        errorMessage = null
                    )
                }
            }

            is ResignationAction.ReasonChanged -> {
                _state.update {
                    it.copy(
                        reason = action.reason,
                        errorMessage = null
                    )
                }
            }

            ResignationAction.ProceedToNextStep -> {
                if (_state.value.canProceed) {
                    _state.update {
                        it.copy(
                            step = ResignationStep.SUBMISSION
                        )
                    }
                }
            }

            ResignationAction.SubmitResignation -> {
                submitResignation()
            }

            ResignationAction.DismissError -> {
                _state.update {
                    it.copy(
                        errorMessage = null
                    )
                }
            }

            ResignationAction.SeeAssetOwners -> {

                if (_state.value.assets.isNotEmpty()) {

                    _state.update {
                        it.copy(
                            step = ResignationStep.ASSET_OWNERS
                        )
                    }

                } else {

                    loadAssets(
                    )
                }
            }

            ResignationAction.BackToApproved -> {
                _state.update {
                    it.copy(
                        step = ResignationStep.APPROVED,
                        assetsErrorMessage = null
                    )
                }
            }
        }
    }

    private fun loadAssets() {

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoadingAssets = true,
                    assetsErrorMessage = null
                )
            }

            when (
                val result =
                    resignationRepository
                        .getAssetCoordinatorsForEmployee()
            ) {

                is NetworkResult.Success -> {

                    _state.update {
                        it.copy(
                            assets = result.data,
                            isLoadingAssets = false,
                            assetsErrorMessage = null,
                            step = ResignationStep.ASSET_OWNERS
                        )
                    }
                }

                is NetworkResult.Error -> {

                    _state.update {
                        it.copy(
                            isLoadingAssets = false,
                            assetsErrorMessage = result.message
                        )
                    }
                }

                NetworkResult.Loading -> Unit
            }
        }
    }
    private fun checkExistingResignation() {

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isCheckingExistingRequest = true,
                    errorMessage = null
                )
            }

            when (
                val result =
                    resignationRepository
                        .getExistingResignation()
            ) {

                is NetworkResult.Success -> {

                    val resignation = result.data

                    if (resignation == null) {

                        // No existing resignation request.
                        _state.update {
                            it.copy(
                                isCheckingExistingRequest = false,
                                step = ResignationStep.CREATE
                            )
                        }

                    } else {

                        val step = resignation.toStep()

                        _state.update {
                            it.copy(
                                isCheckingExistingRequest = false,
                                step = step,

                                resignationDate =
                                    resignation.resignationDate.orEmpty(),

                                lastWorkingDate =
                                    resignation.lastWorkingDate.orEmpty(),

                                reason =
                                    resignation.resignationReason.orEmpty()
                            )
                        }
                    }
                }

                is NetworkResult.Error -> {

                    _state.update {
                        it.copy(
                            isCheckingExistingRequest = false,
                            step = ResignationStep.CREATE,
                            errorMessage = result.message
                        )
                    }
                }

                NetworkResult.Loading -> Unit
            }
        }
    }

    private fun submitResignation() {

        val currentState = _state.value

        if (
            currentState.isSubmitting ||
            !currentState.canProceed
        ) {
            return
        }

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isSubmitting = true,
                    errorMessage = null
                )
            }

            when (
                val result =
                    resignationRepository.registerResignation(
                        resignationDate =
                            currentState.resignationDate,

                        lastWorkingDate =
                            currentState.lastWorkingDate,

                        reason =
                            currentState.reason
                    )
            ) {

                is NetworkResult.Success -> {

                    _state.update {
                        it.copy(
                            isSubmitting = false,
                            step = ResignationStep.PENDING
                        )
                    }
                }

                is NetworkResult.Error -> {

                    _state.update {
                        it.copy(
                            isSubmitting = false,
                            errorMessage = result.message
                        )
                    }
                }

                NetworkResult.Loading -> Unit
            }
        }
    }


}

private fun ResignationDetailsDto.toStep(): ResignationStep {
    return ResignationStep.APPROVED
}

