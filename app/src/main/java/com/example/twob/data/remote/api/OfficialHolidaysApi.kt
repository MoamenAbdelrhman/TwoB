package com.example.twob.data.remote.api

import com.example.twob.data.remote.dto.OfficialHolidaysResponseDto
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface OfficialHolidaysApi {

    @Multipart
    @POST("PublicVacation/GetMobile")
    suspend fun getOfficialHolidays(
        @Query("culture") culture: String,

        @Part("Month") month: RequestBody,
        @Part("EmployeeId") employeeId: RequestBody,
        @Part("PageSize") pageSize: RequestBody,
        @Part("FilterType") filterType: RequestBody,
        @Part("SortType") sortType: RequestBody,
        @Part("PageNumber") pageNumber: RequestBody,
        @Part("FilterValue") filterValue: RequestBody,
        @Part("Year") year: RequestBody,
        @Part("ShiftId") shiftId: RequestBody
    ): OfficialHolidaysResponseDto
}