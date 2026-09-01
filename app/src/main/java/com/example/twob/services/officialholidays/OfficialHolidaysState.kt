package com.example.twob.services.officialholidays

data class OfficialHolidaysState(
    val isLoading: Boolean = false,
    val upcoming: OfficialHoliday? = null,
    val laterOn: List<OfficialHoliday> = emptyList(),
    val errorMessage: String? = null
)