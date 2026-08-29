package com.example.twob.services.resignation

import com.example.twob.data.remote.NetworkResult
import com.example.twob.data.remote.dto.ResignationDetailsDto

interface ResignationRepository {

    suspend fun registerResignation(
        resignationDate: String,
        lastWorkingDate: String,
        reason: String
    ): NetworkResult<Unit>

    suspend fun getExistingResignation(): NetworkResult<ResignationDetailsDto?>

    suspend fun getAssetCoordinatorsForEmployee():
            NetworkResult<List<ResignationAssetDepartment>>

}