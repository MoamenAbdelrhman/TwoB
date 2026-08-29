package com.example.twob.data.remote.dto

data class LoginDataDto(
    val token: String,
    val employee: EmployeeDto,
    val roles: List<String>
)