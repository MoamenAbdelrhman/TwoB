package com.example.twob.data.remote.dto

import com.google.gson.annotations.SerializedName

data class InternalJobRegisterResponseDto(

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: Any? = null,

    @SerializedName("message")
    val message: String = "",

    @SerializedName("statusCode")
    val statusCode: Int = 0
)