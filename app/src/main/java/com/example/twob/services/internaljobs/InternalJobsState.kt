package com.example.twob.services.internaljobs

data class InternalJobsState(
    val screen: InternalJobsScreen =
        InternalJobsScreen.LIST,

    val selectedTab: InternalJobsTab =
        InternalJobsTab.AVAILABLE,

    val jobs: List<InternalJob> =
        emptyList(),

    val applications:
    List<InternalJobApplication> =
        emptyList(),

    val selectedJob: InternalJob? =
        null,

    val selectedApplication:
    InternalJobApplication? =
        null,

    val selectedResumeName: String? =
        null,

    val selectedResumeUri: String? =
        null,

    val skills: String = "",

    val note: String = "",

    val showDeleteDialog: Boolean =
        false,

    val isLoading: Boolean =
        false,

    val isApplying: Boolean =
        false,

    val isDeleting: Boolean =
        false,

    val errorMessage: String? =
        null
) {

    val canApply: Boolean
        get() =
            !selectedResumeName.isNullOrBlank() &&
                    !selectedResumeUri.isNullOrBlank() &&
                    skills.isNotBlank() &&
                    note.isNotBlank()
}