package com.example.twob.services.officialholidays.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.ui.theme.secondaryColor

@Composable
fun EmptyOfficialHolidaysState() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(125.dp)
        )

        Image(
            painter = painterResource(
                id = R.drawable.emptyicon
            ),

            contentDescription = null,

            modifier = Modifier.size(
                width = 160.dp,
                height = 145.dp
            ),

            contentScale = ContentScale.Fit
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = stringResource(
                R.string.no_vacations_yet
            ),
            color = secondaryColor,
            fontSize = 12.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = stringResource(
                R.string.no_holidays_scheduled
            ),
            color = secondaryColor,
            fontSize = 12.sp
        )

        Text(
            text = stringResource(
                R.string.check_back_later
            ),
            color = secondaryColor,
            fontSize = 12.sp
        )
    }
}