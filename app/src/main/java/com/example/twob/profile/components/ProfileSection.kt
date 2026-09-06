package com.example.twob.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.ui.theme.secondaryColor

private val DividerColor = Color(0xFFD7D7D7)

@Composable
internal fun ProfileSectionContainer(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFF7F7F7)
            )
            .padding(
                start = 32.dp,
                end = 18.dp,
                top = 18.dp,
                bottom = 18.dp
            ),
        content = content
    )
}

@Composable
internal fun ProfileSectionTitle(
    title: String
) {
    Text(
        text = title,
        color = secondaryColor,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    )
}


@Composable
internal fun ProfileActionRow(
    icon: ImageVector,
    title: String,
    iconTint: Color = Color(0xFFE9834D),
    textColor: Color = secondaryColor,
    onClick: (() -> Unit)?
) {
    val rowModifier = Modifier
        .fillMaxWidth()
        .height(54.dp)
        .then(
            if (onClick != null) {
                Modifier.clickable(
                    onClick = onClick
                )
            } else {
                Modifier
            }
        )

    Row(
        modifier = rowModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(22.dp)
        )

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Text(
            text = title,
            color = textColor,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Outlined.ArrowForwardIos,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
internal fun ProfileDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(DividerColor)
    )
}