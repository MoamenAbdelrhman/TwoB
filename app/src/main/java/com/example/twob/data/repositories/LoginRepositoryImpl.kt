package com.example.twob.data.repositories

import android.util.Log
import com.example.twob.data.local.datastore.UserPreferencesRepository
import com.example.twob.data.remote.NetworkResult
import com.example.twob.data.remote.api.LoginApi
import com.example.twob.data.remote.dto.LoginResponseDto
import com.example.twob.data.remote.safeApiCall
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
class LoginRepositoryImpl(
    private val loginApi: LoginApi,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val employeeProfileRepository: EmployeeProfileRepository
) : LoginRepository {

    override suspend fun login(
        email: String,
        password: String
    ): NetworkResult<LoginResponseDto> {

        val textMediaType = "text/plain".toMediaType()

        return safeApiCall {

            val culture =
                userPreferencesRepository
                    .culture
                    .first()

            val response = loginApi.login(
                culture = culture,
                email = email.toRequestBody(textMediaType),
                password = password.toRequestBody(textMediaType),
//                firebaseToken = "".toRequestBody(textMediaType),
//                terminated = "".toRequestBody(textMediaType),
//                outOfService = "".toRequestBody(textMediaType)
            )

            if (response.success) {
                response.data?.let { data ->

                    val role = when {

                        data.roles.any {
                            it.equals("Manager", ignoreCase = true)
                        } -> "Manager"

                        data.roles.any {
                            it.equals("Employee", ignoreCase = true)
                        } -> "Employee"

                        else -> ""
                    }

                    userPreferencesRepository.saveToken(data.token)

                    userPreferencesRepository.saveUserProfile(
                        employeeId = data.employee.employeeId,
                        machineCode = data.employee.machineCode,
                        name = data.employee.name,
                        imageUrl = data.employee.imageUrl,
                        jobName = data.employee.jobName,
                        role = role,
                        shiftId = data.employee.shiftId
                    )
                }
            }

            response
        }
    }
}
