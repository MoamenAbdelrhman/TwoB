package com.example.twob.services.internaljobs.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twob.R
import com.example.twob.components.CommonTabs
import com.example.twob.services.internaljobs.InternalJobsTab
import com.example.twob.ui.theme.TwoBTheme

@Composable
fun InternalJobTabs(
    selectedTab: InternalJobsTab,
    onTabSelected: (InternalJobsTab) -> Unit
) {
    val items = listOf(
        stringResource(R.string.internal_jobs_available),
        stringResource(R.string.internal_jobs_applied)
    )

    CommonTabs(
        items = items,
        selectedIndex = when (selectedTab) {
            InternalJobsTab.AVAILABLE -> 0
            InternalJobsTab.APPLIED -> 1
        },
        onItemSelected = { index ->
            val tab = when (index) {
                0 -> InternalJobsTab.AVAILABLE
                else -> InternalJobsTab.APPLIED
            }

            onTabSelected(tab)
        },
        modifier = Modifier.padding(vertical = 10.dp)
    )
}

/*
@Composable
private fun InternalJobTabItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .width(100.dp)
            .height(40.dp)
            .clip(
                RoundedCornerShape(24.dp)
            )
            .background(
                if (selected) {
                    Color(0xFFFFF1EB)
                } else {
                    Color.Transparent
                }
            )
            .clickable(
                onClick = onClick
            ),

        contentAlignment =
            Alignment.Center
    ) {

        Text(
            text = text,

            color =
                if (selected) {
                    InternalJobColors.Orange
                } else {
                    InternalJobColors.TextDark
                },

            fontSize = 12.sp,

            fontWeight =
                FontWeight.Normal
        )
    }
}
*/

@Preview(showBackground = true)
@Composable
private fun InternalJobTabsPreview() {

    TwoBTheme {

        InternalJobTabs(
            selectedTab =
                InternalJobsTab.AVAILABLE,

            onTabSelected = {}
        )
    }
}