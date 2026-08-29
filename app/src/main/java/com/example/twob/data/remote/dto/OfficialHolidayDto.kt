package com.example.twob.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OfficialHolidaysResponseDto(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: List<OfficialHolidayDto> = emptyList(),

    @SerializedName("message")
    val message: String,

    @SerializedName("statusCode")
    val statusCode: Int,

    @SerializedName("totalItems")
    val totalItems: Int? = null
)

data class OfficialHolidayDto(
    @SerializedName("date")
    val date: String?,

    @SerializedName("reason")
    val reason: String?,

    @SerializedName("name")
    val name: String?
)