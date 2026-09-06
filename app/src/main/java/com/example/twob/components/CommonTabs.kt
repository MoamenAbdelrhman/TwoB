package com.example.twob.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Orange = Color(0xFFFF6B2C)
private val TextDark = Color(0xFF2D3250)
private val CardBackground = Color(0xFFF4F4F4)
private val SelectedBackground = Color(0xFFFFF1EB)

@Composable
fun CommonTabs(
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    scrollable: Boolean = false
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (scrollable) {
                    Modifier
                        .horizontalScroll(scrollState)
                        .padding(horizontal = 20.dp)
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .clip(RoundedCornerShape(24.dp))
                .height(40.dp)
                .background(CardBackground),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->

                CommonTabItem(
                    text = item,
                    selected = index == selectedIndex,
                    onClick = {
                        onItemSelected(index)
                    }
                )
            }
        }
    }
}

@Composable
private fun CommonTabItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                if (selected) {
                    SelectedBackground
                } else {
                    Color.Transparent
                }
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) {
                Orange
            } else {
                TextDark
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
    }
}