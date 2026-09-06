package com.example.twob.services.resignation.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.twob.R
import com.example.twob.services.resignation.ResignationColors

@Composable
fun ContactDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = Color.White
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Call,
                        contentDescription = null,
                        tint = ResignationColors.Orange,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(
                        Modifier.width(7.dp)
                    )

                    Text(
                        text = stringResource(
                            R.string.contact
                        ),
                        color = ResignationColors.TextDark,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        tint = ResignationColors.Muted,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable(
                                onClick = onDismiss
                            )
                    )
                }

                Divider()

                Text(
                    text = stringResource(
                        R.string.contact_message
                    ),
                    color = ResignationColors.TextDark,
                    fontSize = 10.sp,
                    lineHeight = 17.sp,
                    modifier = Modifier.padding(14.dp)
                )

                Row(
                    modifier = Modifier.padding(
                        start = 14.dp,
                        end = 14.dp,
                        bottom = 14.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .background(
                                ResignationColors.Navy,
                                RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "2B",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(
                        Modifier.width(8.dp)
                    )

                    Text(
                        text = stringResource(
                            R.string.email_address
                        ),
                        color = ResignationColors.Orange,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}