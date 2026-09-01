package com.example.twob.data.remote.api

import com.example.twob.data.remote.dto.HRLetterResponse
import com.example.twob.data.remote.dto.HRStatusResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface HRLetterApi {

    @Multipart
    @POST("HrLetter")
    suspend fun getHRLetterRequests(
        @Part("LanguageOfRequest") languageOfRequest: RequestBody,
        @Part("EmployeeId") employeeId: RequestBody,
        @Part("PageSize") pageSize: RequestBody,
        @Part("Status") status: RequestBody,
        @Part("FilterType") filterType: RequestBody,
        @Part("SortType") sortType: RequestBody,
        @Part("PageNumber") pageNumber: RequestBody,
        @Part("FilterValue") filterValue: RequestBody
    ): Response<HRLetterResponse>

    @GET("Enums/HRStatus")
    suspend fun getHRStatuses(
        @Query("culture") culture: String
    ): Response<HRStatusResponse>

    @Multipart
    @POST("HrLetter/register")
    suspend fun registerHRLetter(
        @Part employeeId: MultipartBody.Part,
        @Part reasonForRequest: MultipartBody.Part,
        @Part addressTo: MultipartBody.Part,
        @Part languageOfRequest: MultipartBody.Part,
        @Part note: MultipartBody.Part,
        @Part id: MultipartBody.Part? = null
    ): Response<HRLetterResponse>
}