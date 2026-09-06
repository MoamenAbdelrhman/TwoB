package com.example.twob.services.officialholidays.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.services.officialholidays.OfficialHoliday
import com.example.twob.ui.theme.secondaryColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val HolidaysOrange = Color(0xFFFF6B2C)
private val HolidayCardColor = Color(0xFFF5F5F5)

@Composable
fun OfficialHolidayCard(
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

private fun formatHolidayDate(
    value: String
): String {

    return try {

        LocalDateTime
            .parse(
                value,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
            )
            .format(
                DateTimeFormatter.ofPattern(
                    "d MMM yyyy"
                )
            )

    } catch (_: Exception) {

        value
    }
}