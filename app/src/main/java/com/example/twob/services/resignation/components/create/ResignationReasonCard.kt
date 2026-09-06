package com.example.twob.services.resignation.components.create

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.resignation.ResignationColors

@Composable
fun ReasonCard(
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(
                1.dp,
                ResignationColors.Border,
                RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 10.dp,
                vertical = 8.dp
            )
    ) {
        Text(
            text = stringResource(
                R.string.why_do_you_want_to_leave
            ),
            color = ResignationColors.Orange,
            fontSize = 14.sp
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            textStyle = TextStyle(
                color = ResignationColors.TextDark,
                fontSize = 12.sp,
                lineHeight = 15.sp
            ),
            decorationBox = { innerTextField ->
                Box {
                    if (value.isBlank()) {
                        Text(
                            text = stringResource(
                                R.string.leave_reason_hint
                            ),
                            color = Color(0xFFBDBDBD),
                            fontSize = 12.sp
                        )
                    }

                    innerTextField()
                }
            }
        )
    }
}