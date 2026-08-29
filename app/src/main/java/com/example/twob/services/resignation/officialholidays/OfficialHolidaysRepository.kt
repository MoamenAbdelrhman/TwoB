package com.example.twob.services.resignation.officialholidays

import com.example.twob.data.remote.NetworkResult

data class OfficialHoliday(
    val name: String,
    val date: String
)
interface OfficialHolidaysRepository {

    suspend fun getOfficialHolidays():
            NetworkResult<List<OfficialHoliday>>
}