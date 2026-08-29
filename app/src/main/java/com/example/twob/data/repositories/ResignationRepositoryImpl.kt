package com.example.twob.data.repositories

import com.example.twob.data.local.datastore.UserPreferencesRepository
import com.example.twob.data.remote.NetworkResult
import com.example.twob.data.remote.api.ResignationApi
import com.example.twob.data.remote.dto.ResignationDetailsDto
import com.example.twob.data.remote.dto.ResignationRequestDto
import com.example.twob.data.remote.safeApiCall
import com.example.twob.services.resignation.ResignationAsset
import com.example.twob.services.resignation.ResignationAssetDepartment
import com.example.twob.services.resignation.ResignationRepository
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class ResignationRepositoryImpl(
    private val resignationApi: ResignationApi,
    private val userPreferencesRepository: UserPreferencesRepository
) : ResignationRepository {

    override suspend fun registerResignation(
        resignationDate: String,
        lastWorkingDate: String,
        reason: String
    ): NetworkResult<Unit> {

        return safeApiCall {

            val employeeId =
                userPreferencesRepository
                    .employeeId
                    .first()
                    ?: error("Employee ID is not available")

            val request = ResignationRequestDto(
                employeeId = employeeId,
                resignationReason = reason,
                lastWorkingDate = lastWorkingDate,
                resignationDate = resignationDate,
                clearanceDate = "",
                id = ""
            )

            val textMediaType =
                "text/plain".toMediaType()

            val response =
                resignationApi.registerResignation(

                    employeeId =
                        request.employeeId
                            .toString()
                            .toRequestBody(textMediaType),

                    resignationReason =
                        request.resignationReason
                            .toRequestBody(textMediaType),

                    lastWorkingDate =
                        request.lastWorkingDate
                            .toRequestBody(textMediaType),

                    resignationDate =
                        request.resignationDate
                            .toRequestBody(textMediaType),

                    clearanceDate =
                        request.clearanceDate
                            .toRequestBody(textMediaType),

                    id =
                        request.id
                            .toRequestBody(textMediaType)
                )

            if (!response.success) {
                error(
                    response.message.ifBlank {
                        "Failed to submit resignation request"
                    }
                )
            }

            Unit
        }
    }

    override suspend fun getExistingResignation():
            NetworkResult<ResignationDetailsDto?> {

        return safeApiCall {

            val employeeId =
                userPreferencesRepository
                    .employeeId
                    .first()
                    ?: error("Employee ID is not available")

            val response =
                resignationApi.getResignationByEmployeeId(
                    employeeId = employeeId
                )

            // The API uses 400 + "Not found."
            // when the employee has no resignation request.
            if (
                !response.success &&
                response.statusCode == 400 &&
                response.data == null &&
                response.message.equals(
                    "Not found.",
                    ignoreCase = true
                )
            ) {
                return@safeApiCall null
            }

            if (!response.success) {
                error(
                    response.message.ifBlank {
                        "Failed to load resignation request"
                    }
                )
            }

            response.data
        }
    }

    override suspend fun getAssetCoordinatorsForEmployee():
            NetworkResult<List<ResignationAssetDepartment>> {

        return safeApiCall {

            val employeeId =
                userPreferencesRepository
                    .employeeId
                    .first()
                    ?: error("Employee ID is not available")

            val culture =
                userPreferencesRepository
                    .culture
                    .first()

            val response =
                resignationApi.getAssetCoordinatorsForEmployee(
                    employeeId = employeeId,
                    culture = culture
                )

            if (!response.success) {
                error(
                    response.message.ifBlank {
                        "Failed to load company assets"
                    }
                )
            }

            response.data.map { department ->

                ResignationAssetDepartment(
                    departmentName =
                        department.departmentName,

                    assets =
                        department.assetOwners.map { owner ->

                            ResignationAsset(
                                responsibleEmployeeName =
                                    owner.responsibleEmployeeName
                                        .orEmpty(),

                                covenantName =
                                    owner.covenantName
                                        .orEmpty(),

                                isReceived =
                                    owner.isReceived,

                                employeeImageUrl =
                                    owner.employeeImageUrl
                            )
                        }
                )
            }
        }
    }
}