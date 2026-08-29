package com.example.twob.data.remote.dto

import com.google.gson.JsonElement

data class ResignationResponseDto(
    val success: Boolean,
    val data: JsonElement?,
    val message: String,
    val statusCode: Int
)