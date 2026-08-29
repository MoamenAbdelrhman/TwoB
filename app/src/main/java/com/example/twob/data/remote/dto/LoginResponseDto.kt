package com.example.twob.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: LoginDataDto?,

    @SerializedName("message")
    val message: String,

    @SerializedName("statusCode")
    val statusCode: Int
)