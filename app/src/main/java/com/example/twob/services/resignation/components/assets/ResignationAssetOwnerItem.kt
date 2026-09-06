package com.example.twob.services.resignation.components.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.twob.R
import com.example.twob.services.resignation.ResignationAsset
import com.example.twob.services.resignation.ResignationColors

private const val PROFILE_IMAGE_BASE_URL =
    "https://shantafactory.com/HR/api/"

@Composable
fun AssetOwnerItem(
    asset: ResignationAsset
) {
    val imageUrl = asset.employeeImageUrl
        ?.takeIf { it.isNotBlank() }
        ?.let {
            if (it.startsWith("http")) {
                it
            } else {
                PROFILE_IMAGE_BASE_URL +
                        it.removePrefix("/")
            }
        }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFD7EEF2)),
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl == null) {
                Icon(
                    imageVector =
                        Icons.Outlined.PersonOutline,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                AsyncImage(
                    model = imageUrl,
                    contentDescription =
                        asset.responsibleEmployeeName,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(
            Modifier.width(9.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = asset.responsibleEmployeeName,
                color = ResignationColors.TextDark,
                fontSize = 11.sp,
                maxLines = 1
            )

            Text(
                text = stringResource(
                    R.string.assigned_to,
                    asset.covenantName
                ),
                color = ResignationColors.Muted,
                fontSize = 9.sp
            )
        }

        Box(
            modifier = Modifier
                .size(28.dp)
                .border(
                    width = 1.dp,
                    color =
                        if (asset.isReceived) {
                            ResignationColors.Orange
                        } else {
                            ResignationColors.Border
                        }
                )
                .background(
                    color =
                        if (asset.isReceived) {
                            ResignationColors.Orange
                        } else {
                            Color.Transparent
                        }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (asset.isReceived) {
                Text(
                    text = "✓",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}