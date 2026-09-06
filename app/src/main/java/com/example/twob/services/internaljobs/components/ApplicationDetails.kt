package com.example.twob.services.internaljobs.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.internaljobs.InternalJobApplication

@Composable
fun ApplicationDetails(
    application: InternalJobApplication,
    onDelete: () -> Unit
) {

    Column(
        modifier =
            Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier =
                Modifier.weight(1f),

            contentPadding =
                androidx.compose.foundation.layout
                    .PaddingValues(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    ),

            verticalArrangement =
                Arrangement.spacedBy(14.dp)
        ) {

            item {

                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment =
                        Alignment.Center
                ) {

                    androidx.compose.material3.Icon(
                        painter =
                            androidx.compose.ui.res
                                .painterResource(
                                    R.drawable.hr_request_icon
                                ),
                        contentDescription = null,
                        tint =
                            Color.Unspecified,
                        modifier =
                            Modifier.height(140.dp)
                    )
                }
            }

            item {

                Column {

                    Text(
                        text =
                            stringResource(
                                R.string
                                    .internal_jobs_vacancy_title,
                                application.job.title
                            ),
                        color =
                            InternalJobColors.TextDark,
                        fontSize = 13.sp,
                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        Modifier.height(5.dp)
                    )

                    Row {

                        Text(
                            text =
                                stringResource(
                                    R.string
                                        .internal_jobs_applied_status
                                ),
                            color =
                                InternalJobColors.Muted,
                            fontSize = 10.sp
                        )

                        Spacer(
                            Modifier.height(1.dp)
                        )

                        Text(
                            text =
                                stringResource(
                                    R.string
                                        .internal_jobs_applied_date,
                                    application.appliedDate
                                ),
                            color =
                                InternalJobColors.Muted,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            item {

                ResumeRow(
                    resumeName =
                        application.resumeName
                )
            }

            item {

                ApplicationTextCard(
                    title =
                        stringResource(
                            R.string
                                .internal_jobs_your_skills
                        ),
                    text =
                        application.skills
                )
            }

            item {

                ApplicationTextCard(
                    title =
                        stringResource(
                            R.string.note
                        ),
                    text =
                        application.note
                )
            }
        }

        Button(
            onClick = onDelete,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 8.dp
                )
                .height(40.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor =
                        Color.Transparent,
                    contentColor =
                        InternalJobColors.Red
                ),
            border =
                BorderStroke(
                    1.dp,
                    InternalJobColors.Red
                ),
            shape =
                RoundedCornerShape(20.dp)
        ) {

            Text(
                text =
                    stringResource(
                        R.string
                            .internal_jobs_delete_application
                    ),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun ResumeRow(
    resumeName: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .border(
                1.dp,
                InternalJobColors.Border,
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp),
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(
            text =
                stringResource(
                    R.string
                        .internal_jobs_resume_downloaded
                ),
            color =
                InternalJobColors.TextDark,
            fontSize = 11.sp,
            modifier =
                Modifier.weight(1f)
        )

        Text(
            text =
                stringResource(
                    R.string
                        .internal_jobs_view
                ),
            color =
                InternalJobColors.Orange,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun ApplicationTextCard(
    title: String,
    text: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                InternalJobColors.Border,
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {

        Text(
            text = title,
            color =
                InternalJobColors.Orange,
            fontSize = 12.sp
        )

        Spacer(
            Modifier.height(8.dp)
        )

        Text(
            text = text,
            color =
                InternalJobColors.TextDark,
            fontSize = 10.sp,
            lineHeight = 18.sp
        )
    }
}