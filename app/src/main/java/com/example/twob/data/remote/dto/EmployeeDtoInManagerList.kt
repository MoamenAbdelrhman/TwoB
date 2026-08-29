package com.example.twob.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ManagerEmployeeDto(

    @SerializedName("employeeId")
    val employeeId: Int,

    @SerializedName("employeeName")
    val employeeName: String?,

    @SerializedName("jobName")
    val jobName: String?,

    @SerializedName("imageUrl")
    val imageUrl: String?
)