package com.example.twob.data.repositories

import com.example.twob.data.remote.NetworkResult

data class EmployeeManager(
    val name: String,
    val jobName: String,
    val imageUrl: String
)

data class EmployeeProfileDetails(
    val employeeId: Int,
    val machineCode: String,
    val name: String,
    val imageUrl: String?,
    val jobName: String,
    val departmentName: String,
    val partationName: String,
    val managers: List<EmployeeManager>,
    val employees: List<EmployeeManager>
)
interface EmployeeProfileRepository {

    suspend fun getEmployeeProfileData():
            NetworkResult<EmployeeProfileDetails>

    suspend fun refreshProfile():
            NetworkResult<EmployeeProfileDetails>}