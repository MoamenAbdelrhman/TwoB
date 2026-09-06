package com.example.twob.services.resignation.components.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.services.resignation.ResignationColors

@Composable
fun IntroCard(
    onContactClick: () -> Unit
) {
    val introPart1 =
        stringResource(R.string.resignation_intro_part_1)

    val contactLink =
        stringResource(R.string.resignation_contact_link)

    val introPart2 =
        stringResource(R.string.resignation_intro_part_2)

    val link = buildAnnotatedString {
        append(introPart1)
        append(" ")

        pushStringAnnotation(
            "contact",
            "contact"
        )

        addStyle(
            SpanStyle(
                color = ResignationColors.Orange
            ),
            start = length,
            end = length + contactLink.length
        )

        append(contactLink)
        pop()

        append(" ")
        append(introPart2)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(ResignationColors.LightCard)
            .padding(
                horizontal = 14.dp,
                vertical = 10.dp
            )
            .clickable(onClick = onContactClick)
    ) {
        Text(
            text = link,
            color = ResignationColors.TextDark,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    }
}