package com.example.twob.services.hrletter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

private val TextDark = Color(0xFF2D3250)

@Composable
internal fun EmptyHRLetterState() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 24.dp
            ),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(115.dp)
        )

        Icon(
            painter = painterResource(
                R.drawable.emptyicon
            ),
            contentDescription = null,
            modifier = Modifier.size(180.dp),
            tint = Color.Unspecified
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = stringResource(
                R.string.no_requests_yet
            ),
            color = TextDark,
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = stringResource(
                R.string.no_hr_requests_message
            ),
            color = TextDark,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = stringResource(
                R.string.start_hr_request_message
            ),
            color = TextDark,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}