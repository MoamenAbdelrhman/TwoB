package com.example.twob.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EmployeeProfileResponseDto(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: EmployeeProfileDataWrapperDto?,

    @SerializedName("message")
    val message: String,

    @SerializedName("statusCode")
    val statusCode: Int
)