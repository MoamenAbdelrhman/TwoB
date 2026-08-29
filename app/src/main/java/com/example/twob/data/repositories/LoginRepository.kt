package com.example.twob.data.repositories

import com.example.twob.data.remote.NetworkResult
import com.example.twob.data.remote.dto.LoginResponseDto

interface LoginRepository {

    suspend fun login(
        email: String,
        password: String
    ): NetworkResult<LoginResponseDto>
}