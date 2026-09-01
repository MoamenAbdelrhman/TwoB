package com.example.twob.services.officialholidays

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twob.data.remote.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OfficialHolidaysViewModel(
    private val repository: OfficialHolidaysRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow(
            OfficialHolidaysState()
        )

    val state: StateFlow<OfficialHolidaysState> =
        _state.asStateFlow()

    init {
        loadOfficialHolidays()
    }

    private fun loadOfficialHolidays() {

        viewModelScope.launch {

            _state.value =
                OfficialHolidaysState(
                    isLoading = true
                )

            when (
                val result =
                    repository.getOfficialHolidays()
            ) {

                is NetworkResult.Success -> {

                    val today =
                        LocalDate.now()

                    val sorted =
                        result.data
                            .mapNotNull { holiday ->
                                holiday.parseDate()
                                    ?.let { date ->
                                        date to holiday
                                    }
                            }
                            .filter {
                                !it.first.isBefore(today)
                            }
                            .sortedBy {
                                it.first
                            }

                    _state.value =
                        OfficialHolidaysState(
                            isLoading = false,
                            upcoming =
                                sorted
                                    .firstOrNull()
                                    ?.second,

                            laterOn =
                                sorted
                                    .drop(1)
                                    .map { it.second }
                        )
                }

                is NetworkResult.Error -> {

                    _state.value =
                        OfficialHolidaysState(
                            isLoading = false,
                            errorMessage =
                                result.message
                        )
                }

                NetworkResult.Loading -> Unit
            }
        }
    }
}

private fun OfficialHoliday.parseDate():
        LocalDate? {

    return try {

        LocalDateTime
            .parse(
                date,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
            )
            .toLocalDate()

    } catch (_: Exception) {

        null
    }
}