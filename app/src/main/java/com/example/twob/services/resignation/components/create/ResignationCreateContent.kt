package com.example.twob.services.resignation.components.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.resignation.ResignationColors
import java.time.LocalDate

@Composable
fun CreateResignationContent(
    resignationDate: LocalDate?,
    lastWorkingDay: LocalDate?,
    reason: String,
    onReasonChanged: (String) -> Unit,
    onDateClick: (DateField) -> Unit,
    onContactClick: () -> Unit,
    canProceed: Boolean,
    onProceed: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 6.dp,
                bottom = 12.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                IntroCard(
                    onContactClick = onContactClick
                )
            }

            item {
                ResignationIllustration()
            }

            item {
                DateFieldCard(
                    title = stringResource(
                        R.string.resignation_date
                    ),
                    hint = stringResource(
                        R.string.select_resignation_date
                    ),
                    date = resignationDate,
                    onClick = {
                        onDateClick(
                            DateField.RESIGNATION
                        )
                    }
                )
            }

            item {
                DateFieldCard(
                    title = stringResource(
                        R.string.last_working_day
                    ),
                    hint = stringResource(
                        R.string.select_last_working_day
                    ),
                    date = lastWorkingDay,
                    onClick = {
                        onDateClick(
                            DateField.LAST_WORKING_DAY
                        )
                    }
                )
            }

            item {
                NoticeText()
            }

            item {
                ReasonCard(
                    value = reason,
                    onValueChange = onReasonChanged
                )
            }
        }

        Button(
            onClick = onProceed,
            enabled = canProceed,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
                .height(44.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ResignationColors.Orange,
                disabledContainerColor =
                    Color(0xFFE8E8E8),
                contentColor = Color.White,
                disabledContentColor =
                    Color(0xFFCFCFCF)
            )
        ) {
            Text(
                text = stringResource(
                    R.string.proceed_to_next_step
                ),
                fontSize = 14.sp
            )
        }
    }
}

enum class DateField {
    RESIGNATION,
    LAST_WORKING_DAY
}