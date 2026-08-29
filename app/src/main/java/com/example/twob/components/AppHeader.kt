package com.example.twob.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.twob.R
import com.example.twob.ui.theme.TwoBTheme

private val AppHeaderBlue = Color(0xFF0D2E6B)

@Composable
fun AppHeader(
    imageUrl: String? = null,
    showUserImage: Boolean = true,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                color = AppHeaderBlue,
                shape = RoundedCornerShape(
                    bottomStart = 36.dp,
                    bottomEnd = 36.dp
                )
            )
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.logo_2b
            ),
            contentDescription = "2B logo",
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.BottomStart)
                .padding(
                    start = 16.dp,
                    bottom = 10.dp
                ),
            contentScale = ContentScale.Fit
        )

        if (showUserImage) {

            UserAvatar(
                imageUrl = imageUrl,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        end = 20.dp,
                        bottom = 20.dp
                    )
            )
        }
    }
}

@Preview
@Composable
fun AppHeaderPreview() {
    TwoBTheme {
        AppHeader(
            imageUrl = "https://example.com/avatar.png",
            showUserImage = true
        )
    }
}

@Preview
@Composable
fun AppHeaderNoAvatarPreview() {
    TwoBTheme {
        AppHeader(
            showUserImage = false
        )
    }
}

