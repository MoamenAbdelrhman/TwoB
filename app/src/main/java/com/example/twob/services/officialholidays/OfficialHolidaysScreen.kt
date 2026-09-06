package com.example.twob.services.officialholidays

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.twob.R
import com.example.twob.components.AppHeader
import com.example.twob.components.AppPageHeader
import com.example.twob.components.HeaderViewModel
import com.example.twob.components.MainBottomNavigation
import com.example.twob.components.MainDestination
import com.example.twob.services.officialholidays.components.EmptyOfficialHolidaysState
import com.example.twob.services.officialholidays.components.OfficialHolidaysList
import org.koin.androidx.compose.koinViewModel

private val HolidaysOrange = Color(0xFFFF6B2C)

@Composable
fun OfficialHolidaysScreen(
    onBackClick: () -> Unit = {},
    onDestinationSelected: (MainDestination) -> Unit,
    viewModel: OfficialHolidaysViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val headerViewModel: HeaderViewModel = koinViewModel()
    val imageUrl by headerViewModel.imageUrl.collectAsStateWithLifecycle()

    val upcoming = state.upcoming

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        AppHeader(
            imageUrl = imageUrl
        )

        AppPageHeader(
            titleRes = R.string.official_holidays,
            onBack = onBackClick
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            when {

                state.isLoading -> {

                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = HolidaysOrange
                    )
                }

                state.errorMessage != null -> {

                    Text(
                        text = state.errorMessage.orEmpty(),
                        color = Color.Red,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 24.dp)
                    )
                }

               upcoming == null -> {

                    EmptyOfficialHolidaysState()
                }

                else -> {

                    OfficialHolidaysList(
                        upcoming = upcoming,
                        laterOn = state.laterOn
                    )
                }
            }
        }

        MainBottomNavigation(
            selectedDestination = MainDestination.SERVICES,
            onDestinationSelected = onDestinationSelected
        )
    }
}