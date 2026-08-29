package com.example.twob.data.remote.api

import com.example.twob.data.remote.dto.OfficialHolidaysResponseDto
import com.example.twob.data.remote.dto.ResignationAssetsResponseDto
import com.example.twob.data.remote.dto.ResignationDetailsResponseDto
import com.example.twob.data.remote.dto.ResignationResponseDto
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ResignationApi {

    @Multipart
    @POST("Resignation/register")
    suspend fun registerResignation(
        @Part("EmployeeId") employeeId: RequestBody,
        @Part("ResignationReason") resignationReason: RequestBody,
        @Part("LastWorkingDate") lastWorkingDate: RequestBody,
        @Part("ResignationDate") resignationDate: RequestBody,
        @Part("ClearanceDate") clearanceDate: RequestBody,
        @Part("Id") id: RequestBody
    ): ResignationResponseDto

    @GET("Resignation/GetByEmployeeId/{employeeId}")
    suspend fun getResignationByEmployeeId(
        @Path("employeeId") employeeId: Int
    ): ResignationDetailsResponseDto

    @GET("Resignation/GetAssetCoordinatorsForEmployee/{employeeId}")
    suspend fun getAssetCoordinatorsForEmployee(
        @Path("employeeId") employeeId: Int,
        @Query("culture") culture: String
    ): ResignationAssetsResponseDto
}