package com.example.twob.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HRLetterResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: List<HRLetterDto>?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("statusCode")
    val statusCode: Int?,

    @SerializedName("totalItems")
    val totalItems: Int?
)

data class HRStatusResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: List<HRStatusDto>?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("statusCode")
    val statusCode: Int?
)

data class HRStatusDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?
)

data class HRLetterDto(

    @SerializedName("employeeId")
    val employeeId: Int?,

    @SerializedName("statusValue")
    val statusValue: String?,

    @SerializedName("languageValue")
    val languageValue: String?,

    @SerializedName("hrStatusValue")
    val hrStatusValue: String?,

    @SerializedName("status")
    val status: Int?,

    @SerializedName("reasonFrRequest")
    val reasonFrRequest: Int?,

    @SerializedName("addressTo")
    val addressTo: String?,

    @SerializedName("languageOfRequest")
    val languageOfRequest: Int?,

    @SerializedName("note")
    val note: String?,

    @SerializedName("employeeName")
    val employeeName: String?,

    @SerializedName("employeeCode")
    val employeeCode: String?,

    @SerializedName("creationTime")
    val creationTime: String?,

    @SerializedName("lastModificationTime")
    val lastModificationTime: String?,

    @SerializedName("creatorName")
    val creatorName: String?,

    @SerializedName("lastModifierName")
    val lastModifierName: String?,

    @SerializedName("id")
    val id: Int?
)