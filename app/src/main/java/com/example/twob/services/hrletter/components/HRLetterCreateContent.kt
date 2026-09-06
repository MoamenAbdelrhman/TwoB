package com.example.twob.services.hrletter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.data.repositories.HRLetterLanguage
import com.example.twob.data.repositories.HRLetterReason
import com.example.twob.services.hrletter.HRLetterRequestAction
import com.example.twob.services.hrletter.HRLetterRequestState

private val Orange = Color(0xFFFF6B2C)
private val TextDark = Color(0xFF2D3250)

@Composable
internal fun HRLetterCreateContent(
    state: HRLetterRequestState,
    onAction: (HRLetterRequestAction) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 24.dp,
            vertical = 12.dp
        ),
        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        item {

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Image(
                painter = painterResource(
                    id = R.drawable.hr_request_icon
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit
            )
        }

        item {

            HRLetterDropdown(
                label = stringResource(
                    R.string.reason_for_request
                ),
                value = state.selectedReason?.let {
                    stringResource(it.titleRes)
                }.orEmpty(),
                placeholder = stringResource(
                    R.string.select_reason_for_hr_letter
                ),
                expanded = state.showReasonDialog,
                onClick = {
                    onAction(
                        HRLetterRequestAction.ReasonClicked
                    )
                },
                onDismiss = {
                    onAction(
                        HRLetterRequestAction.DismissReasonDialog
                    )
                },
                items = hrLetterReasons(),
                itemText = {
                    stringResource(it.titleRes)
                },
                onItemSelected = {
                    onAction(
                        HRLetterRequestAction.ReasonSelected(it)
                    )
                }
            )
        }

        item {

            HRLetterTextField(
                label = stringResource(
                    R.string.address_to
                ),
                value = state.addressTo,
                placeholder = stringResource(
                    R.string.write_recipient
                ),
                onValueChange = {
                    onAction(
                        HRLetterRequestAction.AddressChanged(it)
                    )
                }
            )
        }

        item {

            HRLetterDropdown(
                label = stringResource(
                    R.string.letter_language
                ),
                value = state.selectedLanguage?.let {
                    stringResource(it.titleRes)
                }.orEmpty(),
                placeholder = stringResource(
                    R.string.select_letter_language
                ),
                expanded = state.showLanguageDialog,
                onClick = {
                    onAction(
                        HRLetterRequestAction.LanguageClicked
                    )
                },
                onDismiss = {
                    onAction(
                        HRLetterRequestAction.DismissLanguageDialog
                    )
                },
                items = hrLetterLanguages(),
                itemText = {
                    stringResource(it.titleRes)
                },
                onItemSelected = {
                    onAction(
                        HRLetterRequestAction.LanguageSelected(it)
                    )
                }
            )
        }

        item {

            HRLetterTextField(
                label = stringResource(
                    R.string.note
                ),
                value = state.note,
                placeholder = stringResource(
                    R.string.add_note_here
                ),
                minHeight = 130.dp,
                onValueChange = {
                    onAction(
                        HRLetterRequestAction.NoteChanged(it)
                    )
                }
            )
        }

        item {

            Row(
                verticalAlignment = Alignment.Top
            ) {

                Text(
                    text = "ⓘ",
                    color = Color.Red,
                    fontSize = 14.sp
                )

                Spacer(
                    modifier = Modifier.width(5.dp)
                )

                Text(
                    text = stringResource(
                        R.string.hr_letter_note
                    ),
                    color = TextDark,
                    fontSize = 11.sp,
                    lineHeight = 18.sp
                )
            }
        }

        item {

            Button(
                onClick = {
                    onAction(
                        HRLetterRequestAction.SubmitClicked
                    )
                },
                enabled = !state.isSubmitting,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(
                    20.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange
                )
            ) {

                if (state.isSubmitting) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )

                } else {

                    Text(
                        text = stringResource(
                            R.string.request_letter
                        ),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

private fun hrLetterReasons(): List<HRLetterReason> {
    return listOf(
        HRLetterReason(
            id = 0,
            titleRes = R.string.employment_verification
        ),
        HRLetterReason(
            id = 1,
            titleRes = R.string.salary_certificate
        ),
        HRLetterReason(
            id = 2,
            titleRes = R.string.letter_for_bank
        ),
        HRLetterReason(
            id = 3,
            titleRes = R.string.visa_application
        ),
        HRLetterReason(
            id = 4,
            titleRes = R.string.proof_of_experience
        ),
        HRLetterReason(
            id = 5,
            titleRes = R.string.promotion_confirmation
        ),
        HRLetterReason(
            id = 6,
            titleRes = R.string.other
        )
    )
}

private fun hrLetterLanguages(): List<HRLetterLanguage> {
    return listOf(
        HRLetterLanguage(
            id = 0,
            titleRes = R.string.arabic
        ),
        HRLetterLanguage(
            id = 1,
            titleRes = R.string.english
        )
    )
}