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
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.resignation.ResignationColors

@Composable
fun ResignationHeader(
    onBack: () -> Unit
) {
    Column {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(
                    ResignationColors.Navy,
                    RoundedCornerShape(
                        bottomStart = 36.dp,
                        bottomEnd = 36.dp
                    )
                )
        ) {
            Icon(
                painter = painterResource(R.drawable.logo_2b),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.BottomStart)
                    .padding(
                        start = 16.dp,
                        bottom = 10.dp
                    )
            )

            Icon(
                imageVector = Icons.Outlined.PersonOutline,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.BottomEnd)
                    .padding(
                        end = 16.dp,
                        bottom = 12.dp
                    )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBackIosNew,
                contentDescription = null,
                tint = ResignationColors.Muted,
                modifier = Modifier
                    .size(17.dp)
                    .clickable(onClick = onBack)
            )

            Text(
                text = stringResource(
                    R.string.request_resignation
                ),
                color = ResignationColors.TextDark,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(
                Modifier.width(17.dp)
            )
        }
    }
}