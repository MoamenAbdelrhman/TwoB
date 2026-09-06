package com.example.twob.services.internaljobs.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.internaljobs.InternalJob

@Composable
fun AvailableJobCard(
    job: InternalJob,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 5.dp
            )
            .height(76.dp)
            .background(
                InternalJobColors.CardBackground,
                RoundedCornerShape(8.dp)
            )
            .clickable(
                onClick = onClick
            )
            .padding(horizontal = 8.dp),
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    InternalJobColors.Navy,
                    RoundedCornerShape(8.dp)
                ),
            contentAlignment =
                Alignment.Center
        ) {
            Icon(
                painter =
                    painterResource(
                        R.drawable.logo_2b
                    ),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier =
                    Modifier.size(30.dp)
            )
        }

        Spacer(
            Modifier.width(8.dp)
        )

        Column(
            modifier =
                Modifier.weight(1f)
        ) {

            Text(
                text = job.title,
                color =
                    InternalJobColors.TextDark,
                fontSize = 13.sp
            )

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    imageVector =
                        Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    tint =
                        InternalJobColors.Muted,
                    modifier =
                        Modifier.size(13.dp)
                )

                Spacer(
                    Modifier.width(3.dp)
                )

                Text(
                    text = job.date,
                    color =
                        InternalJobColors.Muted,
                    fontSize = 10.sp
                )
            }
        }

        Text(
            text =
                stringResource(
                    R.string.internal_jobs_view_vacancy
                ),
            color =
                InternalJobColors.Orange,
            fontSize = 10.sp
        )
    }
}