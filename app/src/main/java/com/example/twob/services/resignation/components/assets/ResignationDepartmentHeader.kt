package com.example.twob.services.resignation.components.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.resignation.ResignationColors

@Composable
fun DepartmentHeader(
    departmentName: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(
                ResignationColors.Orange,
                RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(
                    R.drawable.request_errand
                ),
                contentDescription = null,
                tint = ResignationColors.Orange,
                modifier = Modifier.size(16.dp)
            )

            Spacer(
                Modifier.width(4.dp)
            )

            Text(
                text = departmentName,
                color = ResignationColors.TextDark,
                fontSize = 11.sp
            )
        }
    }
}