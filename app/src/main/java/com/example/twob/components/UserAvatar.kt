package com.example.twob.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.twob.R
import com.example.twob.ui.theme.TwoBTheme

private const val PROFILE_IMAGE_BASE_URL =
    "https://shantafactory.com/HR/api/"

@Composable
fun UserAvatar(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {

    val fullUrl = when {

        imageUrl.isNullOrBlank() -> null

        imageUrl.startsWith("http", ignoreCase = true) -> {
            imageUrl
        }

        else -> {
            PROFILE_IMAGE_BASE_URL +
                    imageUrl.removePrefix("/")
        }
    }

    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Color(0xFFD7EEF2))
            .border(
                width = 1.dp,
                color = Color.White,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        if (fullUrl.isNullOrBlank()) {

            AsyncImage(
                model = R.drawable.user_img,
                contentDescription = "User profile picture",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

        } else {

            AsyncImage(
                model = fullUrl,
                contentDescription = "User profile picture",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(
                    R.drawable.user_img
                ),
                error = painterResource(
                    R.drawable.user_img
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserAvatarPreview() {
    TwoBTheme {
        UserAvatar(
            imageUrl = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserAvatarWithImagePreview() {
    TwoBTheme {
        UserAvatar(
            imageUrl = "https://shantafactory.com/HR/api/user.png"
        )
    }
}
