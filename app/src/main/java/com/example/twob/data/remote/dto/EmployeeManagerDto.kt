package com.example.twob.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EmployeeManagerDto(

    @SerializedName("mangerName")
    val managerName: String?,

    @SerializedName("jobName")
    val jobName: String?,

    @SerializedName("imageUrl")
    val imageUrl: String?
)