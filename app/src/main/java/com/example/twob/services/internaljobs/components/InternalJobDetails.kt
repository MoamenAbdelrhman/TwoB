package com.example.twob.services.internaljobs.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.internaljobs.InternalJob

@Composable
fun InternalJobDetails(
    job: InternalJob,
    onApplyNow: () -> Unit
) {

    LazyColumn(
        modifier =
            Modifier.fillMaxSize()
    ) {

        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    ),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(54.dp)
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
                            Modifier.size(38.dp)
                    )
                }

                Spacer(
                    Modifier.width(9.dp)
                )

                Column {

                    Text(
                        text = job.title,
                        color =
                            InternalJobColors.TextDark,
                        fontSize = 16.sp,
                        fontWeight =
                            FontWeight.Bold
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
            }
        }

        item {

            Button(
                onClick = onApplyNow,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 40.dp
                    )
                    .height(40.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            InternalJobColors.Orange
                    ),
                shape =
                    RoundedCornerShape(20.dp)
            ) {

                Text(
                    text =
                        stringResource(
                            R.string
                                .internal_jobs_apply_now
                        ),
                    fontSize = 12.sp
                )
            }

            Spacer(
                Modifier.height(20.dp)
            )
        }

        item {

            JobDescriptionSection(
                title =
                    stringResource(
                        R.string.internal_jobs_about
                    ),
                icon =
                    Icons.Outlined.BusinessCenter,
                text = job.about
            )
        }

        item {

            JobRequirementsSection(
                requirements =
                    job.requirements
            )
        }

        item {

            JobDescriptionSection(
                title =
                    stringResource(
                        R.string.internal_jobs_job_note
                    ),
                icon =
                    Icons.Outlined.BusinessCenter,
                text = job.note
            )
        }
    }
}

@Composable
private fun JobDescriptionSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                InternalJobColors.CardBackground
            )
            .padding(
                horizontal = 24.dp,
                vertical = 18.dp
            )
    ) {

        Row(
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint =
                    InternalJobColors.Orange,
                modifier =
                    Modifier.size(16.dp)
            )

            Spacer(
                Modifier.width(6.dp)
            )

            Text(
                text = title,
                color =
                    InternalJobColors.TextDark,
                fontSize = 14.sp,
                fontWeight =
                    FontWeight.Bold
            )
        }

        Spacer(
            Modifier.height(12.dp)
        )

        Text(
            text = text,
            color =
                InternalJobColors.TextDark,
            fontSize = 10.sp,
            lineHeight = 20.sp
        )
    }

    Spacer(
        Modifier.height(8.dp)
    )
}

@Composable
private fun JobRequirementsSection(
    requirements: List<String>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                InternalJobColors.CardBackground
            )
            .padding(
                horizontal = 24.dp,
                vertical = 18.dp
            )
    ) {

        Row(
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(
                imageVector =
                    Icons.Outlined.List,
                contentDescription = null,
                tint =
                    InternalJobColors.Orange,
                modifier =
                    Modifier.size(16.dp)
            )

            Spacer(
                Modifier.width(6.dp)
            )

            Text(
                text =
                    stringResource(
                        R.string
                            .internal_jobs_requirements
                    ),
                color =
                    InternalJobColors.TextDark,
                fontSize = 14.sp,
                fontWeight =
                    FontWeight.Bold
            )
        }

        Spacer(
            Modifier.height(10.dp)
        )

        requirements.forEach { requirement ->

            Row(
                modifier =
                    Modifier.padding(
                        vertical = 2.dp
                    )
            ) {

                Text(
                    text = "▪",
                    color =
                        InternalJobColors.TextDark,
                    fontSize = 10.sp
                )

                Spacer(
                    Modifier.width(6.dp)
                )

                Text(
                    text = requirement,
                    color =
                        InternalJobColors.TextDark,
                    fontSize = 10.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }

    Spacer(
        Modifier.height(8.dp)
    )
}