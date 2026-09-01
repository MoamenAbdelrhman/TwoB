package com.example.twob.services.hrletter

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.twob.R
import com.example.twob.components.AppHeader
import com.example.twob.components.AppPageHeader
import com.example.twob.components.HeaderViewModel
import com.example.twob.components.MainBottomNavigation
import com.example.twob.components.MainDestination
import com.example.twob.data.repositories.HRLetterLanguage
import com.example.twob.data.repositories.HRLetterReason
import com.example.twob.data.repositories.HRLetterRequest
import com.example.twob.ui.theme.TwoBTheme
import org.koin.androidx.compose.koinViewModel
import com.example.twob.data.repositories.HRLetterStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

private val Navy = Color(0xFF0D2E6B)
private val Orange = Color(0xFFFF6B2C)
private val TextDark = Color(0xFF2D3250)
private val Muted = Color(0xFFB7B7B7)
private val CardBackground = Color(0xFFF4F4F4)
private val Border = Color(0xFFD0D4DE)

@Composable
fun HRLetterRequestScreen(
    onBack: () -> Unit,
    onDestinationSelected: (MainDestination) -> Unit,
    viewModel: HRLetterRequestViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler(
        enabled = state.screen == HRLetterScreen.CREATE
    ) {
        viewModel.onAction(
            HRLetterRequestAction.BackClicked
        )
    }

    val headerViewModel: HeaderViewModel = koinViewModel()

    val imageUrl by
    headerViewModel.imageUrl.collectAsStateWithLifecycle()

    HRLetterRequestContent(
        state = state,
        imageUrl = imageUrl,
        onBack = onBack,
        onDestinationSelected = onDestinationSelected,
        onAction = viewModel::onAction
    )
}

@Composable
private fun HRLetterRequestContent(
    state: HRLetterRequestState,
    imageUrl: String?,
    onBack: () -> Unit,
    onDestinationSelected: (MainDestination) -> Unit,
    onAction: (HRLetterRequestAction) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        AppHeader(
            imageUrl = imageUrl
        )

        AppPageHeader(
            titleRes = R.string.hr_letter_request,
            onBack = {
                if (state.screen == HRLetterScreen.CREATE) {
                    onAction(
                        HRLetterRequestAction.BackClicked
                    )
                } else {
                    onBack()
                }
            }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {

            when (state.screen) {

                HRLetterScreen.LIST -> {

                    HRLetterListContent(
                        state = state,
                        onAction = onAction
                    )
                }

                HRLetterScreen.CREATE -> {

                    HRLetterCreateContent(
                        state = state,
                        onAction = onAction
                    )
                }
            }
        }

        MainBottomNavigation(
            selectedDestination = MainDestination.SERVICES,
            onDestinationSelected = onDestinationSelected
        )

        if (state.errorMessage != null) {

            AlertDialog(
                onDismissRequest = {
                    onAction(
                        HRLetterRequestAction.DismissError
                    )
                },
                title = {
                    Text(
                        text = stringResource(
                            R.string.error
                        )
                    )
                },
                text = {
                    Text(
                        text = state.errorMessage.orEmpty()
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onAction(
                                HRLetterRequestAction.DismissError
                            )
                        }
                    ) {
                        Text(
                            text = stringResource(
                                R.string.ok
                            )
                        )
                    }
                }
            )
        }
    }

}

@Composable
private fun EmptyHRLetterState() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(115.dp)
        )

        Icon(
            painter = painterResource(
                R.drawable.emptyicon
            ),
            contentDescription = null,
            modifier = Modifier.size(180.dp),
            tint = Color.Unspecified
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = stringResource(
                R.string.no_requests_yet
            ),
            color = TextDark,
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = stringResource(
                R.string.no_hr_requests_message
            ),
            color = TextDark,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = stringResource(
                R.string.start_hr_request_message
            ),
            color = TextDark,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun HRLetterListContent(
    state: HRLetterRequestState,
    onAction: (HRLetterRequestAction) -> Unit
) {

    val allStatuses = listOf(
        HRLetterStatus(
            id = null,
            name = stringResource(R.string.all)
        )
    ) + state.statuses

    val filteredRequests =
        state.requests.filter { request ->
            state.selectedStatus?.id == null ||
                    request.status?.id == state.selectedStatus.id
        }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        when {

            // Initial load / refresh
            state.isLoading -> {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Orange
                    )
                }
            }

            // No request has been loaded yet
            !state.hasLoadedRequests -> {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Orange
                    )
                }
            }

            // Requests were successfully loaded but there are no requests
            state.requests.isEmpty() -> {

                EmptyHRLetterState()
            }

            else -> {

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    StatusTabs(
                        statuses = allStatuses,
                        selectedStatus = state.selectedStatus,
                        onStatusSelected = { status ->

                            onAction(
                                HRLetterRequestAction.StatusSelected(status)
                            )
                        }
                    )

                    if (filteredRequests.isEmpty()) {

                        EmptyHRLetterState()

                    } else {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                start = 20.dp,
                                end = 20.dp,
                                top = 18.dp,
                                bottom = 100.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            items(
                                items = filteredRequests,
                                key = { it.id }
                            ) { request ->

                                HRLetterRequestCard(
                                    request = request,
                                    statuses = state.statuses
                                )
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                onAction(
                    HRLetterRequestAction.AddRequestClicked
                )
            },
            containerColor = Orange,
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 24.dp,
                    bottom = 24.dp
                )
                .size(56.dp)
        ) {

            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(
                    R.string.add_hr_letter_request
                )
            )
        }
    }
}

@Composable
private fun StatusTabs(
    statuses: List<HRLetterStatus>,
    selectedStatus: HRLetterStatus?,
    onStatusSelected: (HRLetterStatus?) -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
            .horizontalScroll(scrollState)
            .padding(end = 20.dp)
            .clip(RoundedCornerShape(24.dp))
            .height(40.dp)
            .background(CardBackground),
    ) {

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            statuses.forEach { status ->

                StatusTab(
                    text = status.name,
                    selected = status.id == selectedStatus?.id,
                    onClick = {
                        onStatusSelected(status)
                    }
                )
            }
        }
    }
}


@Composable
private fun StatusTab(
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
            .then(
                if (selected) {
                    Modifier.border(
                        width = 1.dp,
                        color = Orange,
                        shape = RoundedCornerShape(24.dp)
                    )
                } else {
                    Modifier
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

@Composable
private fun HRLetterRequestCard(
    request: HRLetterRequest,
    statuses: List<HRLetterStatus>
) {

    val statusText = statuses
        .firstOrNull { it.id == request.status?.id }
        ?.name
        .orEmpty()

    val statusColor = when (request.status?.id) {

        0 -> Muted

        1 -> Color(0xFF009B45)

        2 -> Color.Red

        3 -> Color(0xFF3874E8)

        else -> Muted
    }

    val reasonText = when (request.reasonId) {

        0 -> stringResource(
            R.string.employment_verification
        )

        1 -> stringResource(
            R.string.salary_certificate
        )

        2 -> stringResource(
            R.string.letter_for_bank
        )

        3 -> stringResource(
            R.string.visa_application
        )

        4 -> stringResource(
            R.string.proof_of_experience
        )

        5 -> stringResource(
            R.string.promotion_confirmation
        )

        6 -> stringResource(
            R.string.other
        )

        else -> request.reasonText
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(9.dp)
            )
            .background(CardBackground)
            .padding(
                horizontal = 10.dp,
                vertical = 10.dp
            )
    ) {

        Text(
            text = statusText,
            color = statusColor,
            fontSize = 12.sp
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Outlined.Description,
                contentDescription = null,
                tint = Orange,
                modifier = Modifier.size(20.dp)
            )

            Spacer(
                modifier = Modifier.width(6.dp)
            )

            Text(
                text = reasonText,
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text = formatHRLetterDate(
                    request.creationTime
                ),
                color = TextDark,
                fontSize = 11.sp
            )
        }
    }
}


@Composable
private fun HRLetterCreateContent(
    state: HRLetterRequestState,
    onAction: (HRLetterRequestAction) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                horizontal = 24.dp,
                vertical = 12.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                // Temporary until the final HR letter illustration
                // is added to drawable resources.
                Image(
                    painter = painterResource(
                        id = R.drawable.hr_request_icon
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Fit
                )
            }

            item {

                HRLetterDropdown(
                    label = stringResource(
                        R.string.reason_for_request
                    ),
                    value = state.selectedReason?.let {
                        stringResource(it.titleRes)
                    }.orEmpty(),
                    placeholder = stringResource(
                        R.string.select_reason_for_hr_letter
                    ),
                    expanded = state.showReasonDialog,
                    onClick = {
                        onAction(
                            HRLetterRequestAction.ReasonClicked
                        )
                    },
                    onDismiss = {
                        onAction(
                            HRLetterRequestAction.DismissReasonDialog
                        )
                    },
                    items = hrLetterReasons(),
                    itemText = {
                        stringResource(it.titleRes)
                    },
                    onItemSelected = {
                        onAction(
                            HRLetterRequestAction.ReasonSelected(it)
                        )
                    }
                )
            }

            item {

                HRLetterTextField(
                    label = stringResource(
                        R.string.address_to
                    ),
                    value = state.addressTo,
                    placeholder = stringResource(
                        R.string.write_recipient
                    ),
                    onValueChange = {
                        onAction(
                            HRLetterRequestAction.AddressChanged(it)
                        )
                    }
                )
            }

            item {

                HRLetterDropdown(
                    label = stringResource(
                        R.string.letter_language
                    ),
                    value = state.selectedLanguage?.let {
                        stringResource(it.titleRes)
                    }.orEmpty(),
                    placeholder = stringResource(
                        R.string.select_letter_language
                    ),
                    expanded = state.showLanguageDialog,
                    onClick = {
                        onAction(
                            HRLetterRequestAction.LanguageClicked
                        )
                    },
                    onDismiss = {
                        onAction(
                            HRLetterRequestAction.DismissLanguageDialog
                        )
                    },
                    items = hrLetterLanguages(),
                    itemText = {
                        stringResource(it.titleRes)
                    },
                    onItemSelected = {
                        onAction(
                            HRLetterRequestAction.LanguageSelected(it)
                        )
                    }
                )
            }

            item {

                HRLetterTextField(
                    label = stringResource(
                        R.string.note
                    ),
                    value = state.note,
                    placeholder = stringResource(
                        R.string.add_note_here
                    ),
                    minHeight = 130.dp,
                    onValueChange = {
                        onAction(
                            HRLetterRequestAction.NoteChanged(it)
                        )
                    }
                )
            }

            item {

                Row(
                    verticalAlignment = Alignment.Top
                ) {

                    Text(
                        text = "ⓘ",
                        color = Color.Red,
                        fontSize = 14.sp
                    )

                    Spacer(
                        modifier = Modifier.width(5.dp)
                    )

                    Text(
                        text = stringResource(
                            R.string.hr_letter_note
                        ),
                        color = TextDark,
                        fontSize = 11.sp,
                        lineHeight = 18.sp
                    )
                }
            }

            item {

                Button(
                    onClick = {
                        onAction(
                            HRLetterRequestAction.SubmitClicked
                        )
                    },
                    enabled = !state.isSubmitting,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange
                    )
                ) {

                    if (state.isSubmitting) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )

                    } else {

                        Text(
                            text = stringResource(
                                R.string.request_letter
                            ),
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HRLetterTextField(
    label: String,
    value: String,
    placeholder: String,
    minHeight: androidx.compose.ui.unit.Dp = 62.dp,
    onValueChange: (String) -> Unit
) {

    Column {

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
}


@Composable
private fun <T> HRLetterDropdown(
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
                    fontSize = 12.sp
                )
            }

            Icon(
                imageVector = Icons.Outlined.ExpandMore,
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


private fun formatHRLetterDate(
    value: String
): String {

    return try {

        val inputFormatter =
            DateTimeFormatterBuilder()
                .appendPattern(
                    "yyyy-MM-dd'T'HH:mm:ss"
                )
                .optionalStart()
                .appendFraction(
                    java.time.temporal.ChronoField.NANO_OF_SECOND,
                    0,
                    9,
                    true
                )
                .optionalEnd()
                .toFormatter()

        val dateTime =
            LocalDateTime.parse(
                value,
                inputFormatter
            )

        dateTime.format(
            DateTimeFormatter.ofPattern(
                "dd MMM yyyy",
                java.util.Locale.ENGLISH
            )
        )

    } catch (e: Exception) {

        value
    }
}


private fun hrLetterReasons(): List<HRLetterReason> {
    return listOf(
        HRLetterReason(
            id = 0,
            titleRes = R.string.employment_verification
        ),
        HRLetterReason(
            id = 1,
            titleRes = R.string.salary_certificate
        ),
        HRLetterReason(
            id = 2,
            titleRes = R.string.letter_for_bank
        ),
        HRLetterReason(
            id = 3,
            titleRes = R.string.visa_application
        ),
        HRLetterReason(
            id = 4,
            titleRes = R.string.proof_of_experience
        ),
        HRLetterReason(
            id = 5,
            titleRes = R.string.promotion_confirmation
        ),
        HRLetterReason(
            id = 6,
            titleRes = R.string.other
        )
    )
}

private fun hrLetterLanguages(): List<HRLetterLanguage> {
    return listOf(
        HRLetterLanguage(
            id = 0,
            titleRes = R.string.arabic
        ),
        HRLetterLanguage(
            id = 1,
            titleRes = R.string.english
        )
    )
}


@Preview(showBackground = true)
@Composable
private fun HRLetterRequestScreenCreatePreview() {
    TwoBTheme {
        HRLetterRequestContent(
            state = HRLetterRequestState(
                screen = HRLetterScreen.CREATE,
                selectedReason = HRLetterReason(
                    id = 0,
                    titleRes = R.string.employment_verification
                ),
                addressTo = "To Whom It May Concern",
                selectedLanguage = HRLetterLanguage(
                    id = 0,
                    titleRes = R.string.english
                ),
                note = "Sample Note"
            ),
            imageUrl = null,
            onBack = {},
            onDestinationSelected = {},
            onAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HRLetterRequestScreenListPreview() {
    TwoBTheme {
        HRLetterRequestContent(
            state = HRLetterRequestState(
                screen = HRLetterScreen.LIST,
                isLoading = false,
                statuses = listOf(
                    HRLetterStatus(id = 0, name = "Pending"),
                    HRLetterStatus(id = 1, name = "Approved"),
                    HRLetterStatus(id = 2, name = "Rejected")
                ),
                selectedStatus = null,
                requests = listOf(
                    HRLetterRequest(
                        id = 1,
                        status = HRLetterStatus(id = 1, name = "Approved"),
                        statusText = "Approved",
                        reasonId = 0,
                        reasonText = "Employment Verification",
                        languageId = 1,
                        languageText = "English",
                        addressTo = "Embassy",
                        note = "For visa application",
                        creationTime = "2026-08-15T10:30:00"
                    ),
                    HRLetterRequest(
                        id = 2,
                        status = HRLetterStatus(id = 0, name = "Pending"),
                        statusText = "Pending",
                        reasonId = 1,
                        reasonText = "Salary Certificate",
                        languageId = 1,
                        languageText = "English",
                        addressTo = "Bank",
                        note = "For loan",
                        creationTime = "2026-08-20T14:00:00"
                    )
                )
            ),
            imageUrl = null,
            onBack = {},
            onDestinationSelected = {},
            onAction = {}
        )
    }
}

