package com.example.twob.data.remote.dto

data class ResignationDetailsResponseDto(
    val success: Boolean,
    val data: ResignationDetailsDto?,
    val message: String,
    val statusCode: Int
)

data class ResignationDetailsDto(
    val employeeName: String?,
    val employeeCode: String?,
    val employeeId: Int,
    val resignationReason: String?,
    val lastWorkingDate: String?,
    val hrNotes: String?,
    val managerNotes: String?,
    val status: Int,
    val statusValue: String?,
    val processedByManagerId: Int?,
    val processedDate: String?,
    val assignedCovenants: List<Any>?,
    val jobName: String?,
    val isApprovedByManager: Boolean?,
    val isApprovedByManagerValue: String?,
    val isApprovedByHR: Boolean?,
    val isApprovedByHRValue: String?,
    val imageUrl: String?,
    val resignationDate: String?,
    val clearanceDate: String?,
    val creationTime: String?,
    val lastModificationTime: String?,
    val creatorName: String?,
    val lastModifierName: String?,
    val id: Int
)
