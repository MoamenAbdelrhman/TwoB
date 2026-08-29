package com.example.twob.data.remote.dto

data class ResignationRequestDto(
    val employeeId: Int,
    val resignationReason: String,
    val lastWorkingDate: String,
    val resignationDate: String,
    val clearanceDate: String = "",
    val id: String = ""
)