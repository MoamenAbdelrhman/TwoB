package com.example.twob.data.remote.api

import com.example.twob.data.remote.dto.InternalJobApplicationListResponseDto
import com.example.twob.data.remote.dto.InternalJobApplicationResponseDto
import com.example.twob.data.remote.dto.InternalJobDto
import com.example.twob.data.remote.dto.InternalJobRegisterResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface InternalJobsApi {

    @GET("ApplyingInternalJob/availableJobs")
    suspend fun getAvailableJobs():
            Response<List<InternalJobDto>>

    @Multipart
    @POST("ApplyingInternalJob")
    suspend fun getApplications(
        @Part("ApplicationState")
        applicationState: RequestBody? = null,

        @Part("ManagerId")
        managerId: RequestBody? = null,

        @Part("EmployeeId")
        employeeId: RequestBody,

        @Part("PageSize")
        pageSize: RequestBody? = null,

        @Part("FilterType")
        filterType: RequestBody? = null,

        @Part("SortType")
        sortType: RequestBody? = null,

        @Part("PageNumber")
        pageNumber: RequestBody? = null,

        @Part("FilterValue")
        filterValue: RequestBody? = null,

        @Part("EmployeeName")
        employeeName: RequestBody? = null
    ): Response<InternalJobApplicationListResponseDto>

    @GET("ApplyingInternalJob/{id}")
    suspend fun getApplicationById(
        @Path("id") id: Int
    ): Response<InternalJobApplicationResponseDto>

    @Multipart
    @POST("ApplyingInternalJob/register")
    suspend fun registerApplication(
        @Part("Id")
        id: RequestBody? = null,

        @Part("EmployeeId")
        employeeId: RequestBody,

        @Part("InternalJobId")
        internalJobId: RequestBody,

        @Part("Notes")
        notes: RequestBody,

        @Part("Skills")
        skills: RequestBody,

        @Part uploadCV: MultipartBody.Part,

        @Part("ApplicationState")
        applicationState: RequestBody? = null
    ): Response<InternalJobRegisterResponseDto>

    @DELETE("ApplyingInternalJob/DeleteSoftById/{id}")
    suspend fun deleteApplication(
        @Path("id") id: Int
    ): Response<InternalJobRegisterResponseDto>
}