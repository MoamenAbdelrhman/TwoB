package com.example.twob.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EmployeeProfileDataWrapperDto(

    @SerializedName("employeeProfileData")
    val employeeProfileData: EmployeeProfileDataDto?,

    @SerializedName("employeeMangerList")
    val employeeManagerList: List<EmployeeManagerDto>,

    @SerializedName("mangerEmployeesList")
    val managerEmployeesList: List<ManagerEmployeeDto>,

    @SerializedName("roles")
    val roles: List<EmployeeRoleDto>
)