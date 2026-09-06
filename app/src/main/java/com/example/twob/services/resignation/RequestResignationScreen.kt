package com.example.twob.services.resignation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.twob.R
import com.example.twob.components.AppHeader
import com.example.twob.components.AppPageHeader
import com.example.twob.components.HeaderViewModel
import com.example.twob.components.MainBottomNavigation
import com.example.twob.components.MainDestination
import com.example.twob.services.resignation.components.assets.AcceptedContent
import com.example.twob.services.resignation.components.assets.AssetOwnersContent
import com.example.twob.services.resignation.components.assets.ApprovedAssetsContent
import com.example.twob.services.resignation.components.common.ContactDialog
import com.example.twob.services.resignation.components.create.CreateResignationContent
import com.example.twob.services.resignation.components.create.DateField
import com.example.twob.services.resignation.components.common.ResignationErrorDialog
import com.example.twob.services.resignation.components.common.ResignationLoadingContent
import com.example.twob.services.resignation.components.common.ResignationDatePicker
import com.example.twob.services.resignation.components.submission.SubmissionContent
import com.example.twob.services.resignation.components.status.ResignationStatus
import com.example.twob.services.resignation.components.status.StatusContent
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun RequestResignationScreen(
    onBack: () -> Unit,
    onDestinationSelected: (MainDestination) -> Unit,
    viewModel: ResignationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RequestResignationScreenContent(
        state = state,
        onAction = viewModel::onAction,
        onBack = onBack,
        onDestinationSelected = onDestinationSelected
    )
}

@Composable
private fun RequestResignationScreenContent(
    state: ResignationState,
    onAction: (ResignationAction) -> Unit,
    onBack: () -> Unit,
    onDestinationSelected: (MainDestination) -> Unit
) {
    val headerViewModel: HeaderViewModel = koinViewModel()
    val imageUrl by headerViewModel.imageUrl.collectAsStateWithLifecycle()

    var dateField by remember {
        mutableStateOf<DateField?>(null)
    }

    var showContactDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.White)
    ) {
        AppHeader(
            imageUrl = imageUrl
        )

        AppPageHeader(
            titleRes = R.string.request_resignation,
            onBack = onBack
        )

        Box(
            modifier = androidx.compose.ui.Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (state.isCheckingExistingRequest) {

                ResignationLoadingContent()

            } else {
                when (state.step) {

                    ResignationStep.CREATE -> {
                        CreateResignationContent(
                            resignationDate = state.resignationDate
                                .takeIf { it.isNotBlank() }
                                ?.substringBefore("T")
                                ?.let(LocalDate::parse),

                            lastWorkingDay = state.lastWorkingDate
                                .takeIf { it.isNotBlank() }
                                ?.substringBefore("T")
                                ?.let(LocalDate::parse),

                            reason = state.reason,

                            onReasonChanged = { reason ->
                                onAction(
                                    ResignationAction.ReasonChanged(reason)
                                )
                            },

                            onDateClick = {
                                dateField = it
                            },

                            onContactClick = {
                                showContactDialog = true
                            },

                            canProceed = state.canProceed,

                            onProceed = {
                                onAction(
                                    ResignationAction.ProceedToNextStep
                                )
                            }
                        )
                    }

                    ResignationStep.SUBMISSION -> {
                        SubmissionContent(
                            isSubmitting = state.isSubmitting,
                            onSubmit = {
                                onAction(
                                    ResignationAction.SubmitResignation
                                )
                            }
                        )
                    }

                    ResignationStep.PENDING -> {
                        StatusContent(
                            status = ResignationStatus.PENDING,
                            onPrimaryAction = null
                        )
                    }

                    ResignationStep.REJECTED -> {
                        StatusContent(
                            status = ResignationStatus.REJECTED,
                            onPrimaryAction = {
                                onAction(
                                    ResignationAction.ProceedToNextStep
                                )
                            }
                        )
                    }

                    ResignationStep.APPROVED -> {
                        ApprovedAssetsContent(
                            isLoading = state.isLoadingAssets,
                            errorMessage = state.assetsErrorMessage,
                            onOwnersClick = {
                                onAction(
                                    ResignationAction.SeeAssetOwners
                                )
                            }
                        )
                    }

                    ResignationStep.ASSET_OWNERS -> {
                        AssetOwnersContent(
                            departments = state.assets,
                            onBack = {
                                onAction(
                                    ResignationAction.BackToApproved
                                )
                            }
                        )
                    }

                    ResignationStep.ACCEPTED -> {
                        AcceptedContent()
                    }
                }
            }
        }

        MainBottomNavigation(
            selectedDestination = MainDestination.SERVICES,
            onDestinationSelected = onDestinationSelected
        )
    }

    dateField?.let { field ->

        ResignationDatePicker(
            initialDate = when (field) {

                DateField.RESIGNATION -> {
                    state.resignationDate
                        .takeIf { it.isNotBlank() }
                        ?.substringBefore("T")
                        ?.let(LocalDate::parse)
                }

                DateField.LAST_WORKING_DAY -> {
                    state.lastWorkingDate
                        .takeIf { it.isNotBlank() }
                        ?.substringBefore("T")
                        ?.let(LocalDate::parse)
                }
            },

            onDismiss = {
                dateField = null
            },

            onApply = { selected ->

                val formattedDate =
                    "${selected}T00:00:00"

                when (field) {

                    DateField.RESIGNATION -> {
                        onAction(
                            ResignationAction.ResignationDateChanged(
                                formattedDate
                            )
                        )
                    }

                    DateField.LAST_WORKING_DAY -> {
                        onAction(
                            ResignationAction.LastWorkingDateChanged(
                                formattedDate
                            )
                        )
                    }
                }

                dateField = null
            }
        )
    }

    if (showContactDialog) {
        ContactDialog(
            onDismiss = {
                showContactDialog = false
            }
        )
    }

    state.errorMessage?.let { message ->
        ResignationErrorDialog(
            message = message,
            onDismiss = {
                onAction(
                    ResignationAction.DismissError
                )
            }
        )
    }
}