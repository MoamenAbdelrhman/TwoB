package com.example.twob.services.resignation.components.submission

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.twob.services.resignation.ResignationColors

@Composable
fun SubmissionContent(
    isSubmitting: Boolean,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(
                top = 8.dp,
                bottom = 12.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(
                    text = stringResource(
                        R.string.post_resignation_submission
                    ),
                    color = ResignationColors.TextDark,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            item {
                BulletText(
                    stringResource(
                        R.string.submission_step_1
                    )
                )
            }

            item {
                BulletText(
                    stringResource(
                        R.string.submission_step_2
                    )
                )
            }

            item {
                BulletText(
                    stringResource(
                        R.string.submission_step_3
                    )
                )
            }
        }

        Button(
            onClick = onSubmit,
            enabled = !isSubmitting,
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .padding(bottom = 6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ResignationColors.Orange,
                disabledContainerColor =
                    Color(
                        0xFFE0E0E0
                    )
            ),
            shape = RoundedCornerShape(
                12.dp
            )
        ) {
            if (isSubmitting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = stringResource(
                        R.string.submit_resignation
                    ),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun BulletText(
    text: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "▪",
            color = ResignationColors.TextDark,
            fontSize = 13.sp
        )

        Spacer(
            Modifier.width(5.dp)
        )

        Text(
            text = text,
            color = ResignationColors.TextDark,
            fontSize = 12.sp,
            lineHeight = 17.sp
        )
    }
}
