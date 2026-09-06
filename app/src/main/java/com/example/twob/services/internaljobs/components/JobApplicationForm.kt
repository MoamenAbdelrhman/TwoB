package com.example.twob.services.internaljobs.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.internaljobs.InternalJob
import com.example.twob.ui.theme.TwoBTheme

@Composable
fun JobApplicationForm(
    job: InternalJob,
    resumeName: String?,
    skills: String,
    note: String,
    canApply: Boolean,
    isApplying: Boolean,
    onSelectResume: () -> Unit,
    onSkillsChanged: (String) -> Unit,
    onNoteChanged: (String) -> Unit,
    onApply: () -> Unit
) {

    Column(
        modifier =
            Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier =
                Modifier.weight(1f),

            contentPadding =
                PaddingValues(
                    horizontal = 24.dp,
                    vertical = 12.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(14.dp)
        ) {

            item {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(145.dp),
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        painter =
                            painterResource(
                                R.drawable.hr_request_icon
                            ),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier =
                            Modifier.height(150.dp)
                    )
                }
            }

            item {

                ResumeUploadCard(
                    resumeName = resumeName,
                    onSelectResume =
                        onSelectResume
                )
            }

            item {

                InputCard(
                    title =
                        stringResource(
                            R.string
                                .internal_jobs_your_skills
                        ),
                    hint =
                        stringResource(
                            R.string
                                .internal_jobs_skills_hint
                        ),
                    value = skills,
                    onValueChange =
                        onSkillsChanged
                )
            }

            item {

                InputCard(
                    title =
                        stringResource(
                            R.string.note
                        ),
                    hint =
                        stringResource(
                            R.string
                                .internal_jobs_note_hint
                        ),
                    value = note,
                    onValueChange =
                        onNoteChanged
                )
            }
        }

        Button(
            onClick = onApply,
            enabled =
                canApply && !isApplying,
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
                        InternalJobColors.Orange,

                    disabledContainerColor =
                        Color(0xFFE8E8E8),

                    disabledContentColor =
                        Color(0xFFCFCFCF)
                ),
            shape =
                RoundedCornerShape(20.dp)
        ) {

            if (isApplying) {

                CircularProgressIndicator(
                    modifier =
                        Modifier.height(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )

            } else {

                Text(
                    text =
                        stringResource(
                            R.string.apply
                        ),
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
private fun ResumeUploadCard(
    resumeName: String?,
    onSelectResume: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(136.dp)
            .border(
                BorderStroke(
                    1.dp,
                    InternalJobColors.Border
                ),
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
            ,horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text =
                stringResource(
                    R.string
                        .internal_jobs_upload_resume
                ),
            color =
                InternalJobColors.Orange,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(
            Modifier.height(6.dp)
        )

        Icon(
            painter = painterResource(R.drawable.download),
            contentDescription = null,
            tint =
                InternalJobColors.Muted,
            modifier = Modifier
                .size(16.dp)
        )

        Text(
            text =
                resumeName
                    ?: stringResource(
                        R.string
                            .internal_jobs_max_file_size
                    ),
            color =
                if (resumeName == null) {
                    Color(0xFFBDBDBD)
                } else {
                    InternalJobColors.TextDark
                },
            fontSize = 10.sp,
            modifier =
                Modifier
                    .padding(top = 3.dp)
        )

        Spacer(
            Modifier.height(7.dp)
        )

        Button(
            onClick = onSelectResume,
            modifier = Modifier
                .size(height = 27.dp, width = 150.dp),
            contentPadding =
                PaddingValues(0.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                ),
            border =
                BorderStroke(
                    1.dp,
                    InternalJobColors.Orange
                ),
            shape =
                RoundedCornerShape(20.dp)
        ) {

            Text(
                text =
                    stringResource(
                        R.string
                            .internal_jobs_select_file
                    ),
                color = Color(0xFFF36F28),
                fontSize = 14.sp,

            )
        }
    }
}

@Composable
private fun InputCard(
    title: String,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(118.dp)
            .border(
                BorderStroke(
                    1.dp,
                    InternalJobColors.Border
                ),
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {

        Text(
            text = title,
            color =
                InternalJobColors.Orange,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            Modifier.height(6.dp)
        )

        BasicTextField(
            value = value,
            onValueChange =
                onValueChange,
            modifier =
                Modifier.fillMaxSize(),
            singleLine = false,
            decorationBox = {
                    innerTextField ->

                Box {

                    if (value.isBlank()) {

                        Text(
                            text = hint,
                            color =
                                Color(0xFFBDBDBD),
                            fontSize = 10.sp
                        )
                    }

                    innerTextField()
                }
            }
        )
    }
}


private val previewJob =
    InternalJob(
        id = 1,
        title = "Finance",
        date = "1 Aug 2024",
        about =
            "Lorem ipsum dolor sit amet consectetur. Sit pellentesque at nec et in sit ac.",
        requirements =
            listOf(
                "Lorem ipsum dolor sit amet consectetur.",
                "Lorem ipsum dolor sit amet consectetur.",
                "Lorem ipsum dolor sit amet consectetur."
            ),
        note =
            "Lorem ipsum dolor sit amet consectetur."
    )

@Preview(showBackground = true)
@Composable
private fun JobApplicationFormPreview() {

    TwoBTheme {

        JobApplicationForm(
            job = previewJob,
            resumeName = null,
            skills = "",
            note = "",
            canApply = false,
            isApplying = false,
            onSelectResume = {},
            onSkillsChanged = {},
            onNoteChanged = {},
            onApply = {}
        )
    }
}