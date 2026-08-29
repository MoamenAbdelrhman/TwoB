package com.example.twob.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResignationAssetsResponseDto(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: List<AssetDepartmentDto>,

    @SerializedName("message")
    val message: String,

    @SerializedName("statusCode")
    val statusCode: Int,

    @SerializedName("totalItems")
    val totalItems: Int?
)

data class AssetDepartmentDto(
    @SerializedName("departmentName")
    val departmentName: String,

    @SerializedName("assetOwners")
    val assetOwners: List<AssetOwnerDto>
)

data class AssetOwnerDto(
    @SerializedName("responsibleEmployeName")
    val responsibleEmployeeName: String?,

    @SerializedName("covenantName")
    val covenantName: String?,

    @SerializedName("departmentName")
    val departmentName: String?,

    @SerializedName("isReceived")
    val isReceived: Boolean,

    @SerializedName("employeeImageUrl")
    val employeeImageUrl: String?
)