package com.example.twob.services.resignation.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.resignation.ResignationColors
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResignationDatePicker(
    initialDate: LocalDate?,
    onDismiss: () -> Unit,
    onApply: (LocalDate) -> Unit
) {
    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

    var month by remember {
        mutableStateOf(
            YearMonth.from(
                initialDate ?: LocalDate.now()
            )
        )
    }

    var selected by remember {
        mutableStateOf(
            initialDate ?: LocalDate.now()
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        dragHandle = {
            Box(
                Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .width(70.dp)
                    .height(4.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(
                        Color.Black
                    )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = WindowInsets
                        .navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                )
        ) {
            Text(
                text = stringResource(
                    R.string.select_a_date
                ),
                color = ResignationColors.TextDark,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 8.dp
                )
            )

            Divider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 18.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "‹",
                    fontSize = 28.sp,
                    color = ResignationColors.Muted,
                    modifier = Modifier.clickable {
                        month = month.minusMonths(1)
                    }
                )

                Text(
                    text = month.month.name
                        .lowercase()
                        .replaceFirstChar {
                            it.uppercase()
                        },
                    color = ResignationColors.TextDark,
                    fontSize = 16.sp
                )

                Text(
                    text = "›",
                    fontSize = 28.sp,
                    color = ResignationColors.Muted,
                    modifier = Modifier.clickable {
                        month = month.plusMonths(1)
                    }
                )
            }

            CalendarGrid(
                month = month,
                selected = selected,
                onDateSelected = {
                    selected = it
                }
            )

            Divider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        R.string.cancel
                    ),
                    color = ResignationColors.Orange,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable(
                        onClick = onDismiss
                    )
                )

                Button(
                    onClick = {
                        onApply(selected)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            ResignationColors.Orange
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        stringResource(
                            R.string.apply
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarGrid(
    month: YearMonth,
    selected: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDay = month.atDay(1)
    val startOffset = firstDay.dayOfWeek.value % 7
    val totalDays = month.lengthOfMonth()

    Column(
        modifier = Modifier.padding(
            horizontal = 18.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf(
                R.string.sun,
                R.string.mon,
                R.string.tue,
                R.string.wed,
                R.string.thu,
                R.string.fri,
                R.string.sat
            ).forEach {
                Text(
                    text = stringResource(it),
                    color = ResignationColors.Muted,
                    fontSize = 9.sp,
                    modifier = Modifier.width(38.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(
            Modifier.height(8.dp)
        )

        val cells = ArrayList<LocalDate?>()

        repeat(startOffset) {
            cells.add(null)
        }

        for (day in 1..totalDays) {
            cells.add(
                month.atDay(day)
            )
        }

        while (cells.size % 7 != 0) {
            cells.add(null)
        }

        cells.chunked(7).forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                week.forEach { date ->
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(
                                RoundedCornerShape(6.dp)
                            )
                            .then(
                                if (date == selected) {
                                    Modifier.background(
                                        Color(
                                            0xFFFFF0EA
                                        )
                                    )
                                } else {
                                    Modifier
                                }
                            )
                            .clickable(
                                enabled = date != null
                            ) {
                                date?.let(
                                    onDateSelected
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = date
                                ?.dayOfMonth
                                ?.toString()
                                ?: "",
                            color =
                                if (date == selected) {
                                    ResignationColors.Orange
                                } else {
                                    ResignationColors.TextDark
                                },
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(
                Modifier.height(4.dp)
            )
        }
    }
}