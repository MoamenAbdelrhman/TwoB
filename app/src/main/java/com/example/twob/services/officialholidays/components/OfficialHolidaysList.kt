package com.example.twob.services.officialholidays.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.officialholidays.OfficialHoliday
import com.example.twob.ui.theme.secondaryColor

private val HolidaysGreen = Color(0xFF009B3A)

@Composable
fun OfficialHolidaysList(
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
                text = stringResource(R.string.upcoming),
                color = HolidaysGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OfficialHolidayCard(
                holiday = upcoming
            )

            if (laterOn.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                Text(
                    text = stringResource(R.string.later_on),
                    color = secondaryColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }

        items(
            items = laterOn
        ) { holiday ->

            OfficialHolidayCard(
                holiday = holiday
            )
        }
    }
}