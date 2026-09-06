package com.example.twob.services.resignation.components.assets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.resignation.ResignationColors
import com.example.twob.services.resignation.components.status.StatusCard

@Composable
fun ApprovedAssetsContent(
    isLoading: Boolean,
    errorMessage: String?,
    onOwnersClick: () -> Unit
) {
    val temporaryAssets = listOf(
        "Laptop" to true,
        "Acumatica" to false,
        "Email" to false,
        "Uniform" to false,
        "Telephone Bills" to false,
        "Credit" to false
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            )
    ) {
        StatusCard(
            title = stringResource(
                R.string.approved
            ),
            rows = listOf(
                Icons.Outlined.CheckCircle to
                        stringResource(
                            R.string.hr_approved_resignation
                        ),
                Icons.Outlined.CheckCircle to
                        stringResource(
                            R.string.manager_approved_resignation
                        )
            ),
            iconColor = ResignationColors.Green
        )

        Spacer(
            Modifier.height(16.dp)
        )

        Text(
            text = stringResource(
                R.string.assets_will_be_checked
            ),
            color = ResignationColors.TextDark,
            fontSize = 10.sp,
            lineHeight = 18.sp
        )

        Spacer(
            Modifier.height(10.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            temporaryAssets.forEach { (name, isReceived) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        color =
                            if (isReceived) {
                                ResignationColors.TextDark
                            } else {
                                Color(0xFFB8B8B8)
                            },
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .border(
                                width = 1.dp,
                                color =
                                    if (isReceived) {
                                        ResignationColors.Orange
                                    } else {
                                        ResignationColors.Border
                                    }
                            )
                            .background(
                                color =
                                    if (isReceived) {
                                        ResignationColors.Orange
                                    } else {
                                        Color.Transparent
                                    }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isReceived) {
                            Text(
                                text = "✓",
                                color = Color.White,
                                fontSize = 17.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(
            Modifier.weight(1f)
        )

        Button(
            onClick = onOwnersClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = ResignationColors.Orange
            ),
            border = BorderStroke(
                1.dp,
                ResignationColors.Orange
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.see_whos_responsible
                ),
                fontSize = 12.sp
            )
        }
    }
}

/*
@Composable
private fun AssetsCard(
    title: String,
    assets: List<CompanyAsset>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(8.dp)
            )
            .border(
                1.dp,
                Border,
                RoundedCornerShape(8.dp)
            )
            .padding(10.dp)
    ) {

        Text(
            text = title,
            color = Orange,
            fontSize = 14.sp
        )

        assets.forEachIndexed { index, asset ->

            Text(
                text =
                    "${index + 1}. ${asset.name}",
                color = TextDark,
                fontSize = 12.sp,
                modifier =
                    Modifier.padding(top = 5.dp)
            )
        }
    }
}
*/