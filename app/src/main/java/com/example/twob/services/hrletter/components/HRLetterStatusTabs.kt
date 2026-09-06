package com.example.twob.services.hrletter.components


import androidx.compose.runtime.Composable
import com.example.twob.components.CommonTabs
import com.example.twob.data.repositories.HRLetterStatus

@Composable
internal fun HRLetterStatusTabs(
    statuses: List<HRLetterStatus>,
    selectedStatus: HRLetterStatus?,
    onStatusSelected: (HRLetterStatus?) -> Unit
) {

    CommonTabs(
        items = statuses.map { it.name },

        selectedIndex = statuses.indexOfFirst {
            it.id == selectedStatus?.id
        },

        onItemSelected = { index ->
            onStatusSelected(statuses[index])
        },

        scrollable = true
    )

    /*val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
            .horizontalScroll(scrollState)
            .padding(end = 20.dp)
            .clip(
                RoundedCornerShape(24.dp)
            )
            .height(40.dp)
            .background(CardBackground)
    ) {

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(2.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            statuses.forEach { status ->

                HRLetterStatusTab(
                    text = status.name,
                    selected =
                        status.id ==
                                selectedStatus?.id,
                    onClick = {
                        onStatusSelected(status)
                    }
                )
            }
        }
    }*/
}

/*
@Composable
private fun HRLetterStatusTab(
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
}*/
