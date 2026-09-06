package com.example.twob.services.resignation.components.create

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.resignation.ResignationColors

@Composable
fun NoticeText() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Outlined.Cancel,
            contentDescription = null,
            tint = ResignationColors.Error,
            modifier = Modifier.size(13.dp)
        )

        Spacer(
            Modifier.width(4.dp)
        )

        Text(
            text = stringResource(
                R.string.resignation_notice_note
            ),
            color = ResignationColors.TextDark,
            fontSize = 12.sp,
            lineHeight = 15.sp
        )
    }
}