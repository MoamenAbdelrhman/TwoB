package com.example.twob.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EmployeeRoleDto(

    @SerializedName("name")
    val name: String?,

    @SerializedName("id")
    val id: Int
)