package com.example.twob.data.remote.dto

import com.google.gson.annotations.SerializedName

data class InternalJobApplicationResponseDto(

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: InternalJobApplicationDataDto? = null,

    @SerializedName("message")
    val message: String = "",

    @SerializedName("statusCode")
    val statusCode: Int = 0,

    @SerializedName("totalItems")
    val totalItems: Int? = null,

    @SerializedName("columnPreferences")
    val columnPreferences: InternalJobColumnPreferencesDto? = null
)

data class InternalJobApplicationListResponseDto(

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: List<InternalJobApplicationDataDto> = emptyList(),

    @SerializedName("message")
    val message: String = "",

    @SerializedName("statusCode")
    val statusCode: Int = 0,

    @SerializedName("totalItems")
    val totalItems: Int? = null,

    @SerializedName("columnPreferences")
    val columnPreferences: InternalJobColumnPreferencesDto? = null
)

data class InternalJobApplicationDataDto(

    @SerializedName("nameOfJob")
    val nameOfJob: String?,

    @SerializedName("employeeId")
    val employeeId: Int?,

    @SerializedName("internalJobId")
    val internalJobId: Int?,

    @SerializedName("notes")
    val notes: String?,

    @SerializedName("skills")
    val skills: String?,

    @SerializedName("uploadCV")
    val uploadCV: String?,

    @SerializedName("status")
    val status: Int?,

    @SerializedName("internalJobStatusValue")
    val internalJobStatusValue: String?,

    @SerializedName("applicationState")
    val applicationState: Int?,

    @SerializedName("internalJobApplicationStateValue")
    val internalJobApplicationStateValue: String?,

    @SerializedName("imageUrl")
    val imageUrl: String?,

    @SerializedName("employeeName")
    val employeeName: String?,

    @SerializedName("jobTitle")
    val jobTitle: String?,

    @SerializedName("creationTime")
    val creationTime: String?,

    @SerializedName("lastModificationTime")
    val lastModificationTime: String?,

    @SerializedName("creatorName")
    val creatorName: String?,

    @SerializedName("lastModifierName")
    val lastModifierName: String?,

    @SerializedName("id")
    val id: Int
)

data class InternalJobColumnPreferencesDto(

    @SerializedName("columnVisibilityScope")
    val columnVisibilityScope: String?,

    @SerializedName("columnVisibility")
    val columnVisibility: Any?
)