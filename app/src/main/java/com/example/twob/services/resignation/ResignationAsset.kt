package com.example.twob.services.resignation

data class ResignationAsset(
    val responsibleEmployeeName: String,
    val covenantName: String,
    val isReceived: Boolean,
    val employeeImageUrl: String?
)

data class ResignationAssetDepartment(
    val departmentName: String,
    val assets: List<ResignationAsset>
)