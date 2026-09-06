package com.example.twob.services.resignation.components.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.resignation.ResignationColors

@Composable
fun AcceptedContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            Modifier.height(65.dp)
        )

        Box(
            modifier = Modifier
                .size(92.dp)
                .background(
                    Color(0xFFF0F0F0),
                    RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = null,
                tint = ResignationColors.Green,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(
            Modifier.height(18.dp)
        )

        Text(
            text = stringResource(
                R.string.resignation_accepted
            ),
            color = ResignationColors.Green,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            Modifier.height(8.dp)
        )

        Text(
            text = stringResource(
                R.string.resignation_accepted_message
            ),
            color = ResignationColors.TextDark,
            fontSize = 11.sp,
            lineHeight = 19.sp,
            textAlign = TextAlign.Center
        )
    }
}