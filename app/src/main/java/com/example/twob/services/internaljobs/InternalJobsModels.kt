package com.example.twob.services.internaljobs

enum class InternalJobsTab {
    AVAILABLE,
    APPLIED
}

enum class InternalJobsScreen {
    LIST,
    JOB_DETAILS,
    APPLICATION,
    APPLICATION_DETAILS
}

enum class InternalJobApplicationStatus {
    IN_CONSIDERATION,
    HIRED,
    APPLIED,
    NOT_SELECTED
}

data class InternalJob(
    val id: Int,
    val title: String,
    val date: String,
    val about: String,
    val requirements: List<String>,
    val note: String
)

data class InternalJobApplication(
    val id: Int,
    val job: InternalJob,
    val appliedDate: String,
    val status: InternalJobApplicationStatus,
    val resumeName: String,
    val resumeUri: String?,
    val skills: String,
    val note: String
)