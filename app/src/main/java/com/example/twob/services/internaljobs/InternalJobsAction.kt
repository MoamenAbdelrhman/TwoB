package com.example.twob.services.internaljobs

sealed interface InternalJobsAction {

    data class SelectTab(
        val tab: InternalJobsTab
    ) : InternalJobsAction

    data class OpenJob(
        val job: InternalJob
    ) : InternalJobsAction

    data object Back : InternalJobsAction

    data object ApplyNow : InternalJobsAction

    data class ResumeSelected(
        val fileName: String,
        val uri: String
    ) : InternalJobsAction

    data class SkillsChanged(
        val value: String
    ) : InternalJobsAction

    data class NoteChanged(
        val value: String
    ) : InternalJobsAction

    data object SubmitApplication : InternalJobsAction

    data class OpenApplication(
        val application: InternalJobApplication
    ) : InternalJobsAction

    data object DeleteApplication : InternalJobsAction

    data object ConfirmDelete : InternalJobsAction

    data object DismissDeleteDialog : InternalJobsAction

    data object DismissError : InternalJobsAction
}