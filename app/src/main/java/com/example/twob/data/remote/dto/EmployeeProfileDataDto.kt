package com.example.twob.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EmployeeProfileDataDto(

    @SerializedName("name")
    val name: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("birthDate")
    val birthDate: String?,

    @SerializedName("nationalId")
    val nationalId: String?,

    @SerializedName("imageUrl")
    val imageUrl: String?,

    @SerializedName("joininDate")
    val joininDate: String?,

    @SerializedName("hirDate")
    val hirDate: String?,

    @SerializedName("resignationDate")
    val resignationDate: String?,

    @SerializedName("ismanger")
    val isManager: Boolean?,

    @SerializedName("discription")
    val description: String?,

    @SerializedName("isInsured")
    val isInsured: Boolean?,

    @SerializedName("staticVacation")
    val staticVacation: Boolean?,

    @SerializedName("staticShift")
    val staticShift: Boolean?,

    @SerializedName("genderName")
    val genderName: String?,

    @SerializedName("maritalStatusName")
    val maritalStatusName: String?,

    @SerializedName("religionName")
    val religionName: String?,

    @SerializedName("bloodTypesName")
    val bloodTypesName: String?,

    @SerializedName("bankName")
    val bankName: String?,

    @SerializedName("departmentName")
    val departmentName: String?,

    @SerializedName("partationName")
    val partationName: String?,

    @SerializedName("shiftName")
    val shiftName: String?,

    @SerializedName("gradeName")
    val gradeName: String?,

    @SerializedName("governmentName")
    val governmentName: String?,

    @SerializedName("qualificationName")
    val qualificationName: String?,

    @SerializedName("jobName")
    val jobName: String?,

    @SerializedName("jobNatureName")
    val jobNatureName: String?,

    @SerializedName("recuritmentSourceName")
    val recruitmentSourceName: String?,

    @SerializedName("contractTypeName")
    val contractTypeName: String?,

    @SerializedName("machineCode")
    val machineCode: String?,

    @SerializedName("id")
    val id: Int
)