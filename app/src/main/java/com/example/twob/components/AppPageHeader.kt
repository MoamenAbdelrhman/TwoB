package com.example.twob.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.ui.theme.TwoBTheme

private val PageHeaderText = Color(0xFF2D3250)
private val PageHeaderMuted = Color(0xFF8D8D8D)

@Composable
fun AppPageHeader(
    titleRes: Int,
    onBack: () -> Unit,
    trailingIcon: ImageVector? = null,
    onTrailingClick: (() -> Unit)? = null
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Outlined.ArrowBackIosNew,
            contentDescription = null,
            tint = PageHeaderMuted,
            modifier = Modifier
                .size(17.dp)
                .clickable(
                    onClick = onBack
                )
        )

        Text(
            text = stringResource(titleRes),
            color = PageHeaderText,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        if (trailingIcon != null) {

            Icon(
                imageVector = trailingIcon,
                contentDescription = null,
                tint = PageHeaderMuted,
                modifier = Modifier
                    .size(22.dp)
                    .clickable(
                        enabled = onTrailingClick != null,
                        onClick = {
                            onTrailingClick?.invoke()
                        }
                    )
            )

        } else {

            Spacer(
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppPageHeaderPreview() {
    TwoBTheme {
        AppPageHeader(
            titleRes = R.string.official_holidays,
            onBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppPageHeaderWithTrailingIconPreview() {
    TwoBTheme {
        AppPageHeader(
            titleRes = R.string.official_holidays,
            onBack = {},
            trailingIcon = Icons.Outlined.Search,
            onTrailingClick = {}
        )
    }
}
