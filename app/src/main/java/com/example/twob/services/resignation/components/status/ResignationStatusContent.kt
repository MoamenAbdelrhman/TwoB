package com.example.twob.services.resignation.components.status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.resignation.ResignationColors

enum class ResignationStatus {
    PENDING,
    REJECTED
}

@Composable
fun StatusContent(
    status: ResignationStatus,
    onPrimaryAction: (() -> Unit)?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            )
    ) {
        when (status) {

            ResignationStatus.PENDING -> {
                StatusCard(
                    title = stringResource(
                        R.string.pending
                    ),
                    rows = listOf(
                        Icons.Outlined.Schedule to
                                stringResource(
                                    R.string.waiting_hr_response
                                ),
                        Icons.Outlined.Schedule to
                                stringResource(
                                    R.string.waiting_manager_response
                                )
                    ),
                    iconColor = ResignationColors.Muted
                )

                Spacer(
                    Modifier.height(14.dp)
                )

                Text(
                    text = stringResource(
                        R.string.no_actions_yet
                    ),
                    color = ResignationColors.Error,
                    fontSize = 10.sp
                )
            }

            ResignationStatus.REJECTED -> {
                StatusCard(
                    title = stringResource(
                        R.string.rejected
                    ),
                    rows = listOf(
                        Icons.Outlined.CheckCircle to
                                stringResource(
                                    R.string.hr_approved_resignation
                                ),
                        Icons.Outlined.Cancel to
                                stringResource(
                                    R.string.manager_declined_resignation
                                )
                    ),
                    iconColor = ResignationColors.Error
                )

                Spacer(
                    Modifier.height(14.dp)
                )

                Text(
                    text = stringResource(
                        R.string.rejected_contact_hr
                    ),
                    color = ResignationColors.TextDark,
                    fontSize = 10.sp,
                    lineHeight = 17.sp
                )

                onPrimaryAction?.let {
                    Spacer(
                        Modifier.weight(1f)
                    )

                    Button(
                        onClick = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                ResignationColors.Orange
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            stringResource(
                                R.string.add_new_resignation
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatusCard(
    title: String,
    rows: List<Pair<ImageVector, String>>,
    iconColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                ResignationColors.LightCard,
                RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {
        Text(
            text = title,
            color = iconColor,
            fontSize = 10.sp
        )

        rows.forEach { (icon, text) ->
            Row(
                modifier = Modifier.padding(top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(14.dp)
                )

                Spacer(
                    Modifier.width(5.dp)
                )

                Text(
                    text = text,
                    color = ResignationColors.TextDark,
                    fontSize = 10.sp
                )
            }
        }
    }
}