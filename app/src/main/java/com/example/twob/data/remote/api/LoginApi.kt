package com.example.twob.data.remote.api

import com.example.twob.data.remote.dto.LoginResponseDto
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface LoginApi {

    @Multipart
    @POST("Auth/Mobilelogin")
    suspend fun login(
        @Query("culture") culture: String,
        @Part("Email") email: RequestBody,
        @Part("Password") password: RequestBody,
//        @Part("FireBaseToken") firebaseToken: RequestBody,
//        @Part("Terminated") terminated: RequestBody,
//        @Part("OutOfService") outOfService: RequestBody
    ): LoginResponseDto
}