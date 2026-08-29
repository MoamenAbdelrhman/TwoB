package com.example.twob.data.remote.api

import com.example.twob.data.remote.dto.EmployeeProfileResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface EmployeeProfileApi {

    @GET("Employee/GetEmployeeProfileData")
    suspend fun getEmployeeProfileData(
        @Query("EmployeeId") employeeId: Int,
        @Query("culture") culture: String
    ): EmployeeProfileResponseDto
}