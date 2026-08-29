package com.example.twob.services.resignation.officialholidays

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.twob.ui.theme.secondaryColor
import org.koin.androidx.compose.koinViewModel

private val HolidaysOrange = Color(0xFFFF6B2C)
private val HolidaysGreen = Color(0xFF009B3A)
private val HolidaysBlue = Color(0xFF0D2E6B)
private val HolidayCardColor = Color(0xFFF5F5F5)

@Composable
fun OfficialHolidaysScreen(
    onBackClick: () -> Unit = {},
    onDestinationSelected: (MainDestination) -> Unit,
    viewModel: OfficialHolidaysViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val upcoming = state.upcoming

    val headerViewModel: HeaderViewModel = koinViewModel()
    val imageUrl by headerViewModel.imageUrl.collectAsStateWithLifecycle()

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

                    EmptyHolidaysContent()
                }

                else -> {

                    HolidaysList(
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


@Composable
private fun OfficialHolidaysHeader(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(118.dp)
            .background(
                color = HolidaysBlue,
                shape = RoundedCornerShape(
                    bottomStart = 38.dp,
                    bottomEnd = 38.dp
                )
            )
    ) {

        Icon(
            imageVector = Icons.Outlined.ArrowBackIosNew,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(
                    start = 18.dp,
                    bottom = 10.dp
                )
                .size(20.dp)
                .align(Alignment.BottomStart)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Outlined.ArrowBackIosNew,
            contentDescription = null,
            tint = Color(0xFF8E8E8E),
            modifier = Modifier
                .size(18.dp)
                .align(Alignment.CenterVertically)
        )

        Text(
            text = stringResource(
                R.string.official_holidays
            ),
            modifier = Modifier.weight(1f),
            color = secondaryColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.width(18.dp)
        )
    }
}
@Composable
private fun HolidaysList(
    upcoming: OfficialHoliday,
    laterOn: List<OfficialHoliday>
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),

        contentPadding = PaddingValues(
            start = 26.dp,
            end = 26.dp,
            top = 8.dp,
            bottom = 20.dp
        ),

        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {

            Text(
                text = stringResource(
                    R.string.upcoming
                ),

                color = HolidaysGreen,

                fontSize = 14.sp,

                fontWeight = FontWeight.Bold,

                modifier = Modifier.padding(
                    bottom = 8.dp
                )
            )

            HolidayCard(
                holiday = upcoming
            )

            if (laterOn.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                Text(
                    text = stringResource(
                        R.string.later_on
                    ),

                    color = secondaryColor,

                    fontSize = 14.sp,

                    fontWeight = FontWeight.Bold,

                    modifier = Modifier.padding(
                        bottom = 8.dp
                    )
                )
            }
        }

        items(
            items = laterOn
        ) { holiday ->

            HolidayCard(
                holiday = holiday
            )
        }
    }
}


private fun formatHolidayDate(
    value: String
): String {

    return try {

        java.time.LocalDateTime
            .parse(
                value,
                java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME
            )
            .format(
                java.time.format.DateTimeFormatter.ofPattern(
                    "d MMM yyyy"
                )
            )

    } catch (_: Exception) {

        value
    }
}

@Composable
private fun EmptyHolidaysContent() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(125.dp)
        )

        Image(
            painter = painterResource(
                id = R.drawable.request_errand
            ),

            contentDescription = null,

            modifier = Modifier.size(
                width = 160.dp,
                height = 145.dp
            ),

            contentScale = ContentScale.Fit
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = stringResource(
                R.string.no_vacations_yet
            ),
            color = secondaryColor,
            fontSize = 12.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = stringResource(
                R.string.no_holidays_scheduled
            ),
            color = secondaryColor,
            fontSize = 12.sp
        )

        Text(
            text = stringResource(
                R.string.check_back_later
            ),
            color = secondaryColor,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun HolidayCard(
    holiday: OfficialHoliday
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(
                color = HolidayCardColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 14.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Outlined.Event,
            contentDescription = null,
            tint = HolidaysOrange,
            modifier = Modifier.size(19.dp)
        )

        Spacer(
            modifier = Modifier.width(6.dp)
        )

        Text(
            text = holiday.name,
            color = secondaryColor,
            fontSize = 13.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = formatHolidayDate(holiday.date),
            color = secondaryColor,
            fontSize = 11.sp
        )
    }
}
