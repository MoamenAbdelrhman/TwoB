package com.example.twob.services.internaljobs

import android.content.Context
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.twob.R
import com.example.twob.components.AppHeader
import com.example.twob.components.AppPageHeader
import com.example.twob.components.HeaderViewModel
import com.example.twob.components.MainBottomNavigation
import com.example.twob.components.MainDestination
import com.example.twob.services.internaljobs.components.ApplicationDetails
import com.example.twob.services.internaljobs.components.AppliedJobCard
import com.example.twob.services.internaljobs.components.AvailableJobCard
import com.example.twob.services.internaljobs.components.DeleteApplicationDialog
import com.example.twob.services.internaljobs.components.InternalJobColors
import com.example.twob.services.internaljobs.components.InternalJobDetails
import com.example.twob.services.internaljobs.components.InternalJobTabs
import com.example.twob.services.internaljobs.components.JobApplicationForm
import org.koin.androidx.compose.koinViewModel

@Composable
fun InternalJobsScreen(
    onBack: () -> Unit,
    onDestinationSelected:
        (MainDestination) -> Unit,
    viewModel: InternalJobsViewModel =
        koinViewModel()
) {

    val state by
    viewModel.state
        .collectAsStateWithLifecycle()

    BackHandler(
        enabled =
            state.screen !=
                    InternalJobsScreen.LIST
    ) {
        viewModel.onAction(
            InternalJobsAction.Back
        )
    }

    InternalJobsScreenContent(
        state = state,
        onAction = viewModel::onAction,
        onBack = onBack,
        onDestinationSelected =
            onDestinationSelected
    )
}

@Composable
private fun InternalJobsScreenContent(
    state: InternalJobsState,
    onAction:
        (InternalJobsAction) -> Unit,
    onBack: () -> Unit,
    onDestinationSelected:
        (MainDestination) -> Unit
) {

    val headerViewModel:
            HeaderViewModel =
        koinViewModel()

    val imageUrl by
    headerViewModel.imageUrl
        .collectAsStateWithLifecycle()

    val context = LocalContext.current

    val filePicker =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->

            uri ?: return@rememberLauncherForActivityResult

            val fileName =
                getFileName(
                    context = context,
                    uri = uri
                )

            onAction(
                InternalJobsAction.ResumeSelected(
                    fileName = fileName,
                    uri = uri.toString()
                )
            )
        }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White)
    ) {

        AppHeader(
            imageUrl = imageUrl
        )

        AppPageHeader(
            titleRes =
                when (state.screen) {

                    InternalJobsScreen.LIST,
                    InternalJobsScreen.JOB_DETAILS ->
                        R.string.internal_jobs

                    InternalJobsScreen.APPLICATION ->
                        R.string.internal_jobs_vacancy

                    InternalJobsScreen
                        .APPLICATION_DETAILS ->
                        R.string
                            .internal_jobs_your_application
                },

            onBack = {

                if (
                    state.screen ==
                    InternalJobsScreen.LIST
                ) {
                    onBack()
                } else {
                    onAction(
                        InternalJobsAction.Back
                    )
                }
            }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {

            when (state.screen) {

                InternalJobsScreen.LIST -> {

                    InternalJobsListContent(
                        state = state,
                        onAction = onAction
                    )
                }

                InternalJobsScreen.JOB_DETAILS -> {

                    state.selectedJob?.let { job ->

                        InternalJobDetails(
                            job = job,
                            onApplyNow = {
                                onAction(
                                    InternalJobsAction.ApplyNow
                                )
                            }
                        )
                    }
                }

                InternalJobsScreen.APPLICATION -> {

                    state.selectedJob?.let { job ->

                        JobApplicationForm(
                            job = job,
                            resumeName =
                                state.selectedResumeName,
                            skills =
                                state.skills,
                            note =
                                state.note,
                            canApply =
                                state.canApply,
                            isApplying =
                                state.isApplying,

                            onSelectResume = {

                                filePicker.launch(
                                    arrayOf(
                                        "application/pdf",
                                        "application/msword",
                                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                                    )
                                )
                            },

                            onSkillsChanged = {
                                onAction(
                                    InternalJobsAction
                                        .SkillsChanged(it)
                                )
                            },

                            onNoteChanged = {
                                onAction(
                                    InternalJobsAction
                                        .NoteChanged(it)
                                )
                            },

                            onApply = {
                                onAction(
                                    InternalJobsAction
                                        .SubmitApplication
                                )
                            }
                        )
                    }
                }

                InternalJobsScreen.APPLICATION_DETAILS -> {

                    state.selectedApplication?.let { application ->

                        ApplicationDetails(
                            application = application,

                            onDelete = {
                                onAction(
                                    InternalJobsAction
                                        .DeleteApplication
                                )
                            }
                        )
                    }
                }
            }
        }

        MainBottomNavigation(
            selectedDestination =
                MainDestination.SERVICES,
            onDestinationSelected =
                onDestinationSelected
        )
    }

    if (state.showDeleteDialog) {

        DeleteApplicationDialog(
            onConfirm = {
                onAction(
                    InternalJobsAction
                        .ConfirmDelete
                )
            },

            onDismiss = {
                onAction(
                    InternalJobsAction
                        .DismissDeleteDialog
                )
            }
        )
    }
}

@Composable
private fun InternalJobsListContent(
    state: InternalJobsState,
    onAction: (InternalJobsAction) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {


        InternalJobTabs(
            selectedTab = state.selectedTab,

            onTabSelected = {
                onAction(
                    InternalJobsAction.SelectTab(it)
                )
            }
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            when {

                state.isLoading -> {

                    androidx.compose.material3.CircularProgressIndicator(
                        modifier =
                            Modifier.align(
                                Alignment.Center
                            ),

                        color =
                            InternalJobColors.Orange
                    )
                }

                else -> {

                    when (state.selectedTab) {

                        InternalJobsTab.AVAILABLE -> {

                            LazyColumn(
                                modifier =
                                    Modifier.fillMaxSize()
                            ) {

                                items(
                                    items = state.jobs,
                                    key = { it.id }
                                ) { job ->

                                    AvailableJobCard(
                                        job = job,

                                        onClick = {
                                            onAction(
                                                InternalJobsAction
                                                    .OpenJob(job)
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        InternalJobsTab.APPLIED -> {

                            LazyColumn(
                                modifier =
                                    Modifier.fillMaxSize()
                            ) {

                                items(
                                    items =
                                        state.applications,

                                    key = {
                                        it.id
                                    }
                                ) { application ->

                                    AppliedJobCard(
                                        application =
                                            application,

                                        onClick = {

                                            onAction(
                                                InternalJobsAction
                                                    .OpenApplication(
                                                        application
                                                    )
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getFileName(
    context: Context,
    uri: Uri
): String {

    val projection =
        arrayOf(
            android.provider
                .OpenableColumns
                .DISPLAY_NAME
        )

    context.contentResolver
        .query(
            uri,
            projection,
            null,
            null,
            null
        )
        ?.use { cursor ->

            val nameIndex =
                cursor.getColumnIndex(
                    android.provider
                        .OpenableColumns
                        .DISPLAY_NAME
                )

            if (
                cursor.moveToFirst() &&
                nameIndex >= 0
            ) {
                return cursor.getString(
                    nameIndex
                )
            }
        }

    return "Resume.pdf"
}