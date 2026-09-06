package com.example.twob.services.resignation.components.create

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.services.resignation.ResignationColors
import java.time.LocalDate

@Composable
fun DateFieldCard(
    title: String,
    hint: String,
    date: LocalDate?,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = onClick)
            .border(
                1.dp,
                ResignationColors.Border,
                RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = ResignationColors.Orange,
                    fontSize = 14.sp
                )

                Text(
                    text = date?.let(::formatDate) ?: hint,
                    color =
                        if (date == null) {
                            Color(0xFFB9B9B9)
                        } else {
                            ResignationColors.TextDark
                        },
                    fontSize = 12.sp
                )
            }

            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = null,
                tint = Color(0xFF7D7D7D),
                modifier = Modifier.size(19.dp)
            )
        }
    }
}

private fun formatDate(
    date: LocalDate
): String {
    return "%02d %s %04d".format(
        date.dayOfMonth,
        date.month.name
            .lowercase()
            .replaceFirstChar {
                it.uppercase()
            },
        date.year
    )
}