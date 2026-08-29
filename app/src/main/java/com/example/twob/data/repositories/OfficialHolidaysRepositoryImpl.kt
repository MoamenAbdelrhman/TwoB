package com.example.twob.data.repositories

import com.example.twob.data.local.datastore.UserPreferencesRepository
import com.example.twob.data.remote.NetworkResult
import com.example.twob.data.remote.api.OfficialHolidaysApi
import com.example.twob.data.remote.dto.OfficialHolidayDto
import com.example.twob.data.remote.safeApiCall
import com.example.twob.services.resignation.officialholidays.OfficialHoliday
import com.example.twob.services.resignation.officialholidays.OfficialHolidaysRepository
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class OfficialHolidaysRepositoryImpl(
    private val api: OfficialHolidaysApi,
    private val preferences: UserPreferencesRepository
) : OfficialHolidaysRepository {

    override suspend fun getOfficialHolidays():
            NetworkResult<List<OfficialHoliday>> {

        return safeApiCall {

            val employeeId =
                preferences.employeeId.first()
                    ?: error("Employee ID is not available")

            val shiftId =
                preferences.shiftId.first()
                    ?: error("Shift ID is not available")

            val textType =
                "text/plain".toMediaType()

            fun String.toPart() =
                toRequestBody(textType)

            val culture =
                preferences.culture.first()

            val response =
                api.getOfficialHolidays(
                    culture = culture,

                    month = "".toPart(),

                    employeeId =
                        employeeId.toString().toPart(),

                    pageSize = "".toPart(),

                    filterType = "".toPart(),

                    sortType = "".toPart(),

                    pageNumber = "".toPart(),

                    filterValue = "".toPart(),

                    year = "".toPart(),

                    shiftId =
                        shiftId.toString().toPart()
                )

            if (!response.success) {
                error(
                    response.message.ifBlank {
                        "Failed to load official holidays"
                    }
                )
            }

            response.data.mapNotNull {
                it.toDomain()
            }
        }
    }
}

private fun OfficialHolidayDto.toDomain():
        OfficialHoliday? {

    val name =
        name?.takeIf { it.isNotBlank() }
            ?: reason?.takeIf { it.isNotBlank() }
            ?: return null

    val date =
        date?.takeIf { it.isNotBlank() }
            ?: return null

    return OfficialHoliday(
        name = name,
        date = date
    )
}