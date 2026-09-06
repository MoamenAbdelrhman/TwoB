package com.example.twob.services.hrletter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.data.repositories.HRLetterRequest
import com.example.twob.data.repositories.HRLetterStatus

private val Orange = Color(0xFFFF6B2C)
private val TextDark = Color(0xFF2D3250)
private val Muted = Color(0xFFB7B7B7)
private val CardBackground = Color(0xFFF4F4F4)

@Composable
internal fun HRLetterRequestCard(
    request: HRLetterRequest,
    statuses: List<HRLetterStatus>
) {

    val statusText = statuses
        .firstOrNull {
            it.id == request.status?.id
        }
        ?.name
        .orEmpty()

    val statusColor = when (request.status?.id) {

        0 -> Muted

        1 -> Color(0xFF009B45)

        2 -> Color.Red

        3 -> Color(0xFF3874E8)

        else -> Muted
    }

    val reasonText = when (request.reasonId) {

        0 -> stringResource(
            R.string.employment_verification
        )

        1 -> stringResource(
            R.string.salary_certificate
        )

        2 -> stringResource(
            R.string.letter_for_bank
        )

        3 -> stringResource(
            R.string.visa_application
        )

        4 -> stringResource(
            R.string.proof_of_experience
        )

        5 -> stringResource(
            R.string.promotion_confirmation
        )

        6 -> stringResource(
            R.string.other
        )

        else -> request.reasonText
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(9.dp)
            )
            .background(CardBackground)
            .padding(
                horizontal = 10.dp,
                vertical = 10.dp
            )
    ) {

        Text(
            text = statusText,
            color = statusColor,
            fontSize = 12.sp
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector =
                    Icons.Outlined.Description,
                contentDescription = null,
                tint = Orange,
                modifier = Modifier.size(20.dp)
            )

            Spacer(
                modifier = Modifier.width(6.dp)
            )

            Text(
                text = reasonText,
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text = formatHRLetterDate(
                    request.creationTime
                ),
                color = TextDark,
                fontSize = 11.sp
            )
        }
    }
}

private fun formatHRLetterDate(
    value: String
): String {

    return try {

        val inputFormatter =
            java.time.format.DateTimeFormatterBuilder()
                .appendPattern(
                    "yyyy-MM-dd'T'HH:mm:ss"
                )
                .optionalStart()
                .appendFraction(
                    java.time.temporal.ChronoField.NANO_OF_SECOND,
                    0,
                    9,
                    true
                )
                .optionalEnd()
                .toFormatter()

        val dateTime =
            java.time.LocalDateTime.parse(
                value,
                inputFormatter
            )

        dateTime.format(
            java.time.format.DateTimeFormatter.ofPattern(
                "dd MMM yyyy",
                java.util.Locale.ENGLISH
            )
        )

    } catch (e: Exception) {

        value
    }
}