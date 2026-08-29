package com.example.twob.data.repositories

import com.example.twob.data.local.datastore.UserPreferencesRepository
import com.example.twob.data.remote.NetworkResult
import com.example.twob.data.remote.api.EmployeeProfileApi
import com.example.twob.data.remote.dto.EmployeeProfileDataDto
import com.example.twob.data.remote.safeApiCall
import kotlinx.coroutines.flow.first

class EmployeeProfileRepositoryImpl(
    private val employeeProfileApi: EmployeeProfileApi,
    private val userPreferencesRepository: UserPreferencesRepository
) : EmployeeProfileRepository {

    override suspend fun getEmployeeProfileData():
            NetworkResult<EmployeeProfileDetails> {

        return fetchProfileData()
    }

    override suspend fun refreshProfile():
            NetworkResult<EmployeeProfileDetails> {

        return fetchProfileData()
    }

    private suspend fun fetchProfileData():
            NetworkResult<EmployeeProfileDetails> {

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
                employeeProfileApi.getEmployeeProfileData(
                    employeeId = employeeId,
                    culture = culture
                )

            if (!response.success || response.data == null) {
                error(
                    response.message.ifBlank {
                        "Failed to load employee profile data"
                    }
                )
            }

            val data = response.data

            val employee = data.employeeProfileData

            EmployeeProfileDetails(

                employeeId = employee?.id ?: employeeId,

                machineCode = employee?.machineCode.orEmpty(),

                name = employee?.name.orEmpty(),

                imageUrl = employee?.imageUrl,

                jobName = employee?.jobName.orEmpty(),

                departmentName =
                    employee?.departmentName.orEmpty(),

                partationName =
                    employee?.partationName.orEmpty(),

                managers =
                    data.employeeManagerList.map {
                        EmployeeManager(
                            name = it.managerName.orEmpty(),
                            jobName = it.jobName.orEmpty(),
                            imageUrl = it.imageUrl.orEmpty()
                        )
                    },

                employees =
                    data.managerEmployeesList.map {
                        EmployeeManager(
                            name = it.employeeName.orEmpty(),
                            jobName = it.jobName.orEmpty(),
                            imageUrl = it.imageUrl.orEmpty()
                        )
                    }
            )
        }
    }
}