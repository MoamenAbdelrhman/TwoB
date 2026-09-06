package com.example.twob.services.hrletter.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.twob.R
import com.example.twob.services.hrletter.HRLetterRequestAction

@Composable
internal fun HRLetterErrorDialog(
    errorMessage: String,
    onAction: (HRLetterRequestAction) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onAction(
                HRLetterRequestAction.DismissError
            )
        },
        title = {
            Text(
                text = stringResource(
                    R.string.error
                )
            )
        },
        text = {
            Text(
                text = errorMessage
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAction(
                        HRLetterRequestAction.DismissError
                    )
                }
            ) {
                Text(
                    text = stringResource(
                        R.string.ok
                    )
                )
            }
        }
    )
}