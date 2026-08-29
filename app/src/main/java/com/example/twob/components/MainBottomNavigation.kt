package com.example.twob.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.ui.theme.TwoBTheme
import com.example.twob.ui.theme.thirdColor

enum class MainDestination {
    FINGERPRINT,
    SERVICES,
    ASK_2B,
    NOTIFICATION,
    PROFILE
}

@Composable
fun MainBottomNavigation(
    selectedDestination: MainDestination,
    onDestinationSelected: (MainDestination) -> Unit
) {

    val navigationBarPadding =
        WindowInsets.navigationBars
            .asPaddingValues()
            .calculateBottomPadding()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 18.dp,
                    topEnd = 18.dp
                )
            )
            .height(86.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(
                    topStart = 18.dp,
                    topEnd = 18.dp
                ),
                clip = true
            )
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 12.dp,
                    bottom = navigationBarPadding
                ),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {

            MainBottomNavigationItem(
                destination = MainDestination.FINGERPRINT,
                icon = R.drawable.fingerprint,
                label = R.string.fingerprint,
                selectedDestination = selectedDestination,
                onClick = onDestinationSelected
            )

            MainBottomNavigationItem(
                destination = MainDestination.SERVICES,
                icon = R.drawable.services,
                label = R.string.services,
                selectedDestination = selectedDestination,
                onClick = onDestinationSelected
            )

            MainBottomNavigationItem(
                destination = MainDestination.ASK_2B,
                icon = R.drawable.ask2b,
                label = R.string.ask_2b,
                selectedDestination = selectedDestination,
                onClick = onDestinationSelected
            )

            MainBottomNavigationItem(
                destination = MainDestination.NOTIFICATION,
                icon = R.drawable.notification,
                label = R.string.notification,
                selectedDestination = selectedDestination,
                onClick = onDestinationSelected
            )

            MainBottomNavigationItem(
                destination = MainDestination.PROFILE,
                icon = R.drawable.profile,
                label = R.string.profile,
                selectedDestination = selectedDestination,
                onClick = onDestinationSelected
            )
        }
    }
}

@Composable
private fun MainBottomNavigationItem(
    destination: MainDestination,
    icon: Int,
    label: Int,
    selectedDestination: MainDestination,
    onClick: (MainDestination) -> Unit
) {

    val selected =
        destination == selectedDestination

    Column(
        modifier = Modifier
            .width(68.dp)
            .clickable {
                onClick(destination)
            }
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            painter = painterResource(icon),
            contentDescription = stringResource(label),
            tint = if (selected) {
                thirdColor
            } else {
                Color(0xFF9E9E9E)
            },
            modifier = Modifier.size(22.dp)
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = stringResource(label),
            color = if (selected) {
                thirdColor
            } else {
                Color(0xFF9E9E9E)
            },
            fontSize = 12.sp,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainBottomNavigationPreview() {
    TwoBTheme {
        MainBottomNavigation(
            selectedDestination = MainDestination.FINGERPRINT,
            onDestinationSelected = {}
        )
    }
}