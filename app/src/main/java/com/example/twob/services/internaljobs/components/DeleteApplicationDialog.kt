package com.example.twob.services.internaljobs.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.twob.R

@Composable
fun DeleteApplicationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    Dialog(
        onDismissRequest = onDismiss
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White,
                    RoundedCornerShape(22.dp)
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 26.dp
                ),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector =
                    Icons.Outlined.ErrorOutline,
                contentDescription = null,
                tint =
                    InternalJobColors.Red,
                modifier =
                    Modifier.size(28.dp)
            )

            Spacer(
                Modifier.height(14.dp)
            )

            Text(
                text =
                    stringResource(
                        R.string
                            .internal_jobs_delete_confirmation
                    ),
                color =
                    InternalJobColors.TextDark,
                fontSize = 13.sp,
                textAlign =
                    TextAlign.Center
            )

            Spacer(
                Modifier.height(8.dp)
            )

            Text(
                text =
                    stringResource(
                        R.string
                            .internal_jobs_delete_message
                    ),
                color =
                    InternalJobColors.Muted,
                fontSize = 10.sp,
                textAlign =
                    TextAlign.Center
            )

            Spacer(
                Modifier.height(22.dp)
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(14.dp)
            ) {

                Button(
                    onClick = onConfirm,
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color.White,
                            contentColor =
                                InternalJobColors.Orange
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
                                    .internal_jobs_yes_delete
                            ),
                        fontSize = 10.sp
                    )
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
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
                                    .internal_jobs_no_keep
                            ),
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}