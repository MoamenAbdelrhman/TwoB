package com.example.twob.data.remote.dto

import com.google.gson.annotations.SerializedName

data class InternalJobDto(

    @SerializedName("nameOfJob")
    val nameOfJob: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("status")
    val status: Int?,

    @SerializedName("hrStatusValue")
    val hrStatusValue: String?,

    @SerializedName("notes")
    val notes: String?,

    @SerializedName("date")
    val date: String?,

    @SerializedName("jobRequirements")
    val jobRequirements: List<String>?,

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