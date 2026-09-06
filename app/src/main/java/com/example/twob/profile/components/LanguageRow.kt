package com.example.twob.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.ui.theme.secondaryColor
import com.example.twob.ui.theme.thirdColor

@Composable
internal fun LanguageRow(
    culture: String,
    onLanguageSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Outlined.Language,
            contentDescription = "Language",
            tint = thirdColor,
            modifier = Modifier.size(20.dp)
        )

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Text(
            text = stringResource(R.string.language),
            color = secondaryColor,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )

        LanguageSelector(
            culture = culture,
            onLanguageSelected = onLanguageSelected
        )
    }
}

@Composable
private fun LanguageSelector(
    culture: String,
    onLanguageSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .width(150.dp)
            .height(28.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(28.dp)
            )
            .clip(
                RoundedCornerShape(28.dp)
            )
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {

        LanguageOption(
            text = stringResource(R.string.english),
            selected = culture == "en",
            onClick = {
                onLanguageSelected("en")
            },
            modifier = Modifier.weight(1f)
        )

        LanguageOption(
            text = stringResource(R.string.arabic),
            selected = culture == "ar",
            onClick = {
                onLanguageSelected("ar")
            },
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
private fun LanguageOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(
                RoundedCornerShape(16.dp)
            )
            .then(
                if (selected) {
                    Modifier.background(
                        Color(0xFFFFF0EA)
                    )
                } else {
                    Modifier
                }
            )
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = text,
            color = if (selected) {
                thirdColor
            } else {
                secondaryColor
            },
            fontSize = 10.sp
        )
    }
}

