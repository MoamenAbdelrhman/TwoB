package com.example.twob.services.hrletter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

private val Orange = Color(0xFFFF6B2C)
private val TextDark = Color(0xFF2D3250)
private val Border = Color(0xFFD0D4DE)

@Composable
internal fun HRLetterTextField(
    label: String,
    value: String,
    placeholder: String,
    minHeight: Dp = 62.dp,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(
                min = minHeight
            ),
        label = {
            Text(
                text = label,
                color = Orange,
                fontSize = 14.sp
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                color = Color(0xFFC5C5C5),
                fontSize = 12.sp
            )
        },
        singleLine = minHeight <= 62.dp,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Border,
            unfocusedBorderColor = Border,
            focusedLabelColor = Orange,
            unfocusedLabelColor = Orange,
            cursorColor = Orange
        )
    )
}

@Composable
internal fun <T> HRLetterDropdown(
    label: String,
    value: String,
    placeholder: String,
    expanded: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    items: List<T>,
    itemText: @Composable (T) -> String,
    onItemSelected: (T) -> Unit
) {

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = Border,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable(
                    onClick = onClick
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
        ) {

            Column {

                Text(
                    text = label,
                    color = Orange,
                    fontSize = 14.sp
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = value.ifBlank {
                        placeholder
                    },
                    color = if (value.isBlank()) {
                        Color(0xFFC5C5C5)
                    } else {
                        TextDark
                    },
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector =
                    Icons.Outlined.ExpandMore,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(22.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            modifier = Modifier
                .width(350.dp)
                .background(Color.White)
        ) {

            items.forEach { item ->

                DropdownMenuItem(
                    text = {

                        Text(
                            text = itemText(item),
                            color = if (
                                value == itemText(item)
                            ) {
                                Orange
                            } else {
                                Color.Black
                            },
                            fontSize = 14.sp
                        )
                    },
                    onClick = {
                        onItemSelected(item)
                    }
                )
            }
        }
    }
}