package com.example.twob.services.resignation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.twob.R
import com.example.twob.components.MainBottomNavigation
import com.example.twob.components.MainDestination
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import coil.compose.AsyncImage
import com.example.twob.components.AppHeader
import com.example.twob.components.AppPageHeader
import com.example.twob.components.HeaderViewModel
import com.example.twob.ui.theme.TwoBTheme
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.YearMonth
import kotlin.collections.map

private val Navy = Color(0xFF0D2E6B)
private val TextDark = Color(0xFF2D3250)
private val Muted = Color(0xFF8D8D8D)
private val Border = Color(0xFFD5D9E2)
private val LightCard = Color(0xFFF3F3F3)
private val Orange = Color(0xFFF36F28)
private val Error = Color(0xFFF04444)
private val Green = Color(0xFF0A9A45)

private const val PROFILE_IMAGE_BASE_URL =
    "https://shantafactory.com/HR/api/"
enum class ResignationStep {
    CREATE,
    SUBMISSION,
    PENDING,
    REJECTED,
    APPROVED,
    ASSET_OWNERS,
    ACCEPTED
}
private enum class DateField {
    RESIGNATION,
    LAST_WORKING_DAY
}

@Composable
fun RequestResignationScreen(
    onBack: () -> Unit,
    onDestinationSelected: (MainDestination) -> Unit,
    viewModel: ResignationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    RequestResignationScreenContent(
        state = state,
        onAction = viewModel::onAction,
        onBack = onBack,
        onDestinationSelected = onDestinationSelected
    )
}

@Composable
private fun RequestResignationScreenContent(
    state: ResignationState,
    onAction: (ResignationAction) -> Unit,
    onBack: () -> Unit,
    onDestinationSelected: (MainDestination) -> Unit
) {

    val headerViewModel: HeaderViewModel = koinViewModel()
    val imageUrl by headerViewModel.imageUrl.collectAsStateWithLifecycle()

    var dateField by remember {
        mutableStateOf<DateField?>(null)
    }

    var showContactDialog by remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        AppHeader(
            imageUrl = imageUrl
        )

        AppPageHeader(
            titleRes = R.string.request_resignation,
            onBack = onBack
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (state.isCheckingExistingRequest) {

                ResignationLoadingContent()

            } else {
                when (state.step) {

                    ResignationStep.CREATE -> {

                        CreateResignationContent(
                            resignationDate = state.resignationDate
                                .takeIf { it.isNotBlank() }
                                ?.substringBefore("T")
                                ?.let(LocalDate::parse),

                            lastWorkingDay = state.lastWorkingDate
                                .takeIf { it.isNotBlank() }
                                ?.substringBefore("T")
                                ?.let(LocalDate::parse),

                            reason = state.reason,


                            onReasonChanged = { reason ->

                                onAction(
                                    ResignationAction.ReasonChanged(
                                        reason
                                    )
                                )
                            },

                            onDateClick = {
                                dateField = it
                            },

                            onContactClick = {
                                showContactDialog = true
                            },

                            canProceed = state.canProceed,

                            onProceed = {

                                onAction(
                                    ResignationAction.ProceedToNextStep
                                )
                            }
                        )
                    }

                    ResignationStep.SUBMISSION -> {

                        SubmissionContent(
                            isSubmitting = state.isSubmitting,

                            onSubmit = {

                                onAction(
                                    ResignationAction.SubmitResignation
                                )
                            }
                        )
                    }

                    ResignationStep.PENDING -> {

                        StatusContent(
                            status = ResignationStatus.PENDING,
                            onPrimaryAction = null
                        )
                    }

                    ResignationStep.REJECTED -> {

                        StatusContent(
                            status = ResignationStatus.REJECTED,
                            onPrimaryAction = {

                                onAction(
                                    ResignationAction.ProceedToNextStep
                                )
                            }
                        )
                    }

                    ResignationStep.APPROVED -> {

                        ApprovedAssetsContent(
                            isLoading = state.isLoadingAssets,
                            errorMessage = state.assetsErrorMessage,
                            onOwnersClick = {
                                onAction(
                                    ResignationAction.SeeAssetOwners
                                )
                            }
                        )
                    }

                    ResignationStep.ASSET_OWNERS -> {

                        AssetOwnersContent(
                            departments = state.assets,
                            onBack = {
                                onAction(
                                    ResignationAction.BackToApproved
                                )
                            }
                        )
                    }

                    ResignationStep.ACCEPTED -> {

                        AcceptedContent()
                    }
                }

            }
        }

        MainBottomNavigation(
            selectedDestination = MainDestination.SERVICES,
            onDestinationSelected = onDestinationSelected
        )
    }

    /*
     * Date Picker
     */
    dateField?.let { field ->

        ResignationDatePicker(

            initialDate = when (field) {

                DateField.RESIGNATION -> {

                    state.resignationDate
                        .takeIf { it.isNotBlank() }
                        ?.substringBefore("T")
                        ?.let(LocalDate::parse)
                }

                DateField.LAST_WORKING_DAY -> {

                    state.lastWorkingDate
                        .takeIf { it.isNotBlank() }
                        ?.substringBefore("T")
                        ?.let(LocalDate::parse)
                }
            },

            onDismiss = {
                dateField = null
            },

            onApply = { selected ->

                val formattedDate =
                    "${selected}T00:00:00"

                when (field) {

                    DateField.RESIGNATION -> {

                        onAction(
                            ResignationAction.ResignationDateChanged(
                                formattedDate
                            )
                        )
                    }

                    DateField.LAST_WORKING_DAY -> {

                        onAction(
                            ResignationAction.LastWorkingDateChanged(
                                formattedDate
                            )
                        )
                    }
                }

                dateField = null
            }
        )
    }

    /*
     * Contact Dialog
     */
    if (showContactDialog) {

        ContactDialog(
            onDismiss = {
                showContactDialog = false
            }
        )
    }

    /*
     * API Error
     */
    state.errorMessage?.let { message ->

        ResignationErrorDialog(
            message = message,
            onDismiss = {
                onAction(
                    ResignationAction.DismissError
                )
            }
        )
    }
}


private enum class ResignationStatus {
    PENDING,
    REJECTED
}

@Composable
private fun ResignationHeader(
    onBack: () -> Unit
) {
    Column {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(
                    Navy,
                    RoundedCornerShape(
                        bottomStart = 36.dp,
                        bottomEnd = 36.dp
                    )
                )
        ) {

            Icon(
                painter = painterResource(R.drawable.logo_2b),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.BottomStart)
                    .padding(
                        start = 16.dp,
                        bottom = 10.dp
                    )
            )

            Icon(
                imageVector = Icons.Outlined.PersonOutline,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.BottomEnd)
                    .padding(
                        end = 16.dp,
                        bottom = 12.dp
                    )
            )
        }

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
                tint = Muted,
                modifier = Modifier
                    .size(17.dp)
                    .clickable(
                        onClick = onBack
                    )
            )

            Text(
                text = stringResource(
                    R.string.request_resignation
                ),

                color = TextDark,
                fontSize = 14.sp,

                modifier = Modifier.weight(1f),

                textAlign = TextAlign.Center
            )

            Spacer(
                Modifier.width(17.dp)
            )
        }
    }
}


@Composable
private fun ResignationLoadingContent() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            color = Orange
        )
    }
}


@Composable
private fun CreateResignationContent(
    resignationDate: LocalDate?,
    lastWorkingDay: LocalDate?,
    reason: String,
    onReasonChanged: (String) -> Unit,
    onDateClick: (DateField) -> Unit,
    onContactClick: () -> Unit,
    canProceed: Boolean,
    onProceed: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier.weight(1f),

            contentPadding =
                androidx.compose.foundation.layout.PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 6.dp,
                    bottom = 12.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            item {

                IntroCard(
                    onContactClick = onContactClick
                )
            }

            item {

                ResignationIllustration()
            }

            item {

                DateFieldCard(

                    title = stringResource(
                        R.string.resignation_date
                    ),

                    hint = stringResource(
                        R.string.select_resignation_date
                    ),

                    date = resignationDate,

                    onClick = {
                        onDateClick(
                            DateField.RESIGNATION
                        )
                    }
                )
            }

            item {

                DateFieldCard(

                    title = stringResource(
                        R.string.last_working_day
                    ),

                    hint = stringResource(
                        R.string.select_last_working_day
                    ),

                    date = lastWorkingDay,

                    onClick = {
                        onDateClick(
                            DateField.LAST_WORKING_DAY
                        )
                    }
                )
            }

            item {
                NoticeText()
            }

            item {

                ReasonCard(
                    value = reason,
                    onValueChange = onReasonChanged
                )
            }
        }

        Button(

            onClick = onProceed,

            enabled = canProceed,

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
                .height(44.dp),

            shape = RoundedCornerShape(12.dp),

            colors = ButtonDefaults.buttonColors(

                containerColor = Orange,

                disabledContainerColor =
                    Color(0xFFE8E8E8),

                contentColor = Color.White,

                disabledContentColor =
                    Color(0xFFCFCFCF)
            )
        ) {

            Text(
                text = stringResource(
                    R.string.proceed_to_next_step
                ),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun IntroCard(
    onContactClick: () -> Unit
) {

    val introPart1 =
        stringResource(
            R.string.resignation_intro_part_1
        )

    val contactLink =
        stringResource(
            R.string.resignation_contact_link
        )

    val introPart2 =
        stringResource(
            R.string.resignation_intro_part_2
        )

    val link = buildAnnotatedString {

        append(introPart1)
        append(" ")

        pushStringAnnotation(
            "contact",
            "contact"
        )

        addStyle(
            SpanStyle(
                color = Orange
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
            .clip(
                RoundedCornerShape(14.dp)
            )
            .background(LightCard)
            .padding(
                horizontal = 14.dp,
                vertical = 10.dp
            )
            .clickable(
                onClick = onContactClick
            )
    ) {

        Text(

            text = link,

            color = TextDark,

            fontSize = 12.sp,

            lineHeight = 16.sp
        )
    }
}


@Composable
private fun ResignationIllustration() {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),

        contentAlignment = Alignment.Center
    ) {

        Icon(

            painter = painterResource(
                id = R.drawable.cuate
            ),

            contentDescription = null,

            modifier = Modifier.size(150.dp),

            tint = Color.Unspecified
        )
    }
}

@Composable
private fun DateFieldCard(
    title: String,
    hint: String,
    date: LocalDate?,
    onClick: () -> Unit
) {

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(
                RoundedCornerShape(8.dp)
            )
            .border(
                1.dp,
                Border,
                RoundedCornerShape(8.dp)
            )
            .clickable(
                onClick = onClick
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
    ) {

        Row(

            modifier = Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Column(
                Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = Orange,
                    fontSize = 14.sp
                )

                Text(

                    text =
                        date?.let {
                            formatDate(it)
                        } ?: hint,

                    color =
                        if (date == null)
                            Color(0xFFB9B9B9)
                        else
                            TextDark,

                    fontSize = 12.sp
                )
            }

            Icon(

                imageVector =
                    Icons.Outlined.CalendarMonth,

                contentDescription = null,

                tint = Color(0xFF7D7D7D),

                modifier = Modifier.size(19.dp)
            )
        }
    }
}

@Composable
private fun NoticeText() {

    Row(

        modifier = Modifier.fillMaxWidth(),

        verticalAlignment =
            Alignment.Top
    ) {

        Icon(

            imageVector =
                Icons.Outlined.Cancel,

            contentDescription = null,

            tint = Error,

            modifier = Modifier.size(13.dp)
        )

        Spacer(
            Modifier.width(4.dp)
        )

        Text(

            text =
                stringResource(
                    R.string.resignation_notice_note
                ),

            color = TextDark,

            fontSize = 12.sp,

            lineHeight = 15.sp
        )
    }
}

@Composable
private fun ResignationErrorDialog(
    message: String,
    onDismiss: () -> Unit
) {

    AlertDialog(

        onDismissRequest = onDismiss,

        shape = RoundedCornerShape(20.dp),

        containerColor = Color.White,

        title = {

            Column(
                horizontalAlignment =
                    Alignment.CenterHorizontally,

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Box(

                    modifier = Modifier
                        .size(52.dp)
                        .clip(
                            RoundedCornerShape(16.dp)
                        )
                        .background(
                            Color(0xFFFFEEEE)
                        ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(

                        imageVector =
                            Icons.Outlined.Cancel,

                        contentDescription = null,

                        tint = Error,

                        modifier =
                            Modifier.size(28.dp)
                    )
                }

                Spacer(
                    Modifier.height(12.dp)
                )

                Text(

                    text =
                        stringResource(
                            R.string.error
                        ),

                    color = TextDark,

                    fontSize = 18.sp,

                    fontWeight =
                        FontWeight.Bold,

                    textAlign =
                        TextAlign.Center
                )
            }
        },

        text = {

            Text(

                text = message,

                color = Muted,

                fontSize = 13.sp,

                lineHeight = 19.sp,

                textAlign =
                    TextAlign.Center,

                modifier =
                    Modifier.fillMaxWidth()
            )
        },

        confirmButton = {

            Button(

                onClick = onDismiss,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(44.dp),

                shape =
                    RoundedCornerShape(12.dp),

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Orange
                    )
            ) {

                Text(
                    text =
                        stringResource(
                            R.string.ok
                        ),

                    fontSize = 13.sp
                )
            }
        }
    )
}

/*
@Composable
private fun AssetsCard(
    title: String,
    assets: List<CompanyAsset>
) {

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(8.dp)
            )
            .border(
                1.dp,
                Border,
                RoundedCornerShape(8.dp)
            )
            .padding(10.dp)
    ) {

        Text(
            text = title,
            color = Orange,
            fontSize = 14.sp
        )

        assets.forEachIndexed { index, asset ->

            Text(

                text =
                    "${index + 1}. ${asset.name}",

                color = TextDark,

                fontSize = 12.sp,

                modifier =
                    Modifier.padding(top = 5.dp)
            )
        }
    }
}
*/

@Composable
private fun ReasonCard(
    value: String,
    onValueChange: (String) -> Unit
) {

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(
                RoundedCornerShape(8.dp)
            )
            .border(
                1.dp,
                Border,
                RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 10.dp,
                vertical = 8.dp
            )
    ) {

        Text(

            text =
                stringResource(
                    R.string.why_do_you_want_to_leave
                ),

            color = Orange,

            fontSize = 14.sp
        )

        androidx.compose.foundation.text.BasicTextField(

            value = value,

            onValueChange = onValueChange,

            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),

            textStyle = TextStyle(
                color = TextDark,
                fontSize = 12.sp,
                lineHeight = 15.sp
            ),

            decorationBox = { innerTextField ->

                Box {

                    if (value.isBlank()) {

                        Text(

                            text =
                                stringResource(
                                    R.string.leave_reason_hint
                                ),

                            color =
                                Color(0xFFBDBDBD),

                            fontSize = 12.sp
                        )
                    }

                    innerTextField()
                }
            }
        )
    }
}

@Composable
private fun SubmissionContent(
    isSubmitting: Boolean,
    onSubmit: () -> Unit
) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        LazyColumn(

            modifier = Modifier.weight(1f),

            contentPadding =
                androidx.compose.foundation.layout.PaddingValues(
                    top = 8.dp,
                    bottom = 12.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(14.dp)
        ) {

            item {

                Text(

                    text =
                        stringResource(
                            R.string.post_resignation_submission
                        ),

                    color = TextDark,

                    fontSize = 14.sp,

                    fontWeight =
                        FontWeight.Medium
                )
            }

            item {
                BulletText(
                    stringResource(
                        R.string.submission_step_1
                    )
                )
            }

            item {
                BulletText(
                    stringResource(
                        R.string.submission_step_2
                    )
                )
            }

            item {
                BulletText(
                    stringResource(
                        R.string.submission_step_3
                    )
                )
            }

        }

        Button(

            onClick = onSubmit,

            enabled = !isSubmitting,

            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .padding(bottom = 6.dp),

            colors = ButtonDefaults.buttonColors(

                containerColor = Orange,

                disabledContainerColor =
                    Color(0xFFE0E0E0)
            ),

            shape =
                RoundedCornerShape(12.dp)
        ) {

            if (isSubmitting) {

                CircularProgressIndicator(

                    modifier =
                        Modifier.size(20.dp),

                    color = Color.White,

                    strokeWidth = 2.dp
                )

            } else {

                Text(

                    text =
                        stringResource(
                            R.string.submit_resignation
                        ),

                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun BulletText(
    text: String
) {

    Row(

        modifier = Modifier.fillMaxWidth(),

        verticalAlignment =
            Alignment.Top
    ) {

        Text(
            text = "▪",
            color = TextDark,
            fontSize = 13.sp
        )

        Spacer(
            Modifier.width(5.dp)
        )

        Text(

            text = text,

            color = TextDark,

            fontSize = 12.sp,

            lineHeight = 17.sp
        )
    }
}

@Composable
private fun StatusContent(
    status: ResignationStatus,
    onPrimaryAction: (() -> Unit)?
) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            )
    ) {

        when (status) {

            ResignationStatus.PENDING -> {

                StatusCard(

                    title =
                        stringResource(
                            R.string.pending
                        ),

                    rows = listOf(

                        Icons.Outlined.Schedule to
                                stringResource(
                                    R.string.waiting_hr_response
                                ),

                        Icons.Outlined.Schedule to
                                stringResource(
                                    R.string.waiting_manager_response
                                )
                    ),

                    iconColor = Muted
                )

                Spacer(
                    Modifier.height(14.dp)
                )

                Text(

                    text =
                        stringResource(
                            R.string.no_actions_yet
                        ),

                    color = Error,

                    fontSize = 10.sp
                )
            }

            ResignationStatus.REJECTED -> {

                StatusCard(

                    title =
                        stringResource(
                            R.string.rejected
                        ),

                    rows = listOf(

                        Icons.Outlined.CheckCircle to
                                stringResource(
                                    R.string.hr_approved_resignation
                                ),

                        Icons.Outlined.Cancel to
                                stringResource(
                                    R.string.manager_declined_resignation
                                )
                    ),

                    iconColor = Error
                )

                Spacer(
                    Modifier.height(14.dp)
                )

                Text(

                    text =
                        stringResource(
                            R.string.rejected_contact_hr
                        ),

                    color = TextDark,

                    fontSize = 10.sp,

                    lineHeight = 17.sp
                )

                onPrimaryAction?.let {

                    Spacer(
                        Modifier.weight(1f)
                    )

                    Button(

                        onClick = it,

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(46.dp),

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = Orange
                            ),

                        shape =
                            RoundedCornerShape(12.dp)
                    ) {

                        Text(
                            stringResource(
                                R.string.add_new_resignation
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusCard(
    title: String,
    rows: List<
            Pair<
                    androidx.compose.ui.graphics.vector.ImageVector,
                    String
                    >
            >,
    iconColor: Color
) {

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(8.dp)
            )
            .background(LightCard)
            .padding(12.dp)
    ) {

        Text(

            text = title,

            color = iconColor,

            fontSize = 10.sp
        )

        rows.forEach { (icon, text) ->

            Row(

                modifier =
                    Modifier.padding(top = 6.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(

                    imageVector = icon,

                    contentDescription = null,

                    tint = iconColor,

                    modifier =
                        Modifier.size(14.dp)
                )

                Spacer(
                    Modifier.width(5.dp)
                )

                Text(

                    text = text,

                    color = TextDark,

                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
private fun ApprovedAssetsContent(
    isLoading: Boolean,
    errorMessage: String?,
    onOwnersClick: () -> Unit
) {

    val temporaryAssets = listOf(
        "Laptop" to true,
        "Acumatica" to false,
        "Email" to false,
        "Uniform" to false,
        "Telephone Bills" to false,
        "Credit" to false
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            )
    ) {

        StatusCard(
            title = stringResource(R.string.approved),

            rows = listOf(

                Icons.Outlined.CheckCircle to
                        stringResource(
                            R.string.hr_approved_resignation
                        ),

                Icons.Outlined.CheckCircle to
                        stringResource(
                            R.string.manager_approved_resignation
                        )
            ),

            iconColor = Green
        )

        Spacer(
            Modifier.height(16.dp)
        )

        Text(
            text = stringResource(
                R.string.assets_will_be_checked
            ),

            color = TextDark,

            fontSize = 10.sp,

            lineHeight = 18.sp
        )

        Spacer(
            Modifier.height(10.dp)
        )

        // Temporary assets preview
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            temporaryAssets.forEach { (name, isReceived) ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = name,

                        color =
                            if (isReceived)
                                TextDark
                            else
                                Color(0xFFB8B8B8),

                        fontSize = 12.sp,

                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .border(
                                width = 1.dp,
                                color =
                                    if (isReceived)
                                        Orange
                                    else
                                        Border
                            )
                            .background(
                                color =
                                    if (isReceived)
                                        Orange
                                    else
                                        Color.Transparent
                            ),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        if (isReceived) {

                            Text(
                                text = "✓",

                                color = Color.White,

                                fontSize = 17.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(
            Modifier.weight(1f)
        )

        Button(
            onClick = onOwnersClick,

            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Orange
            ),

            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                Orange
            ),

            shape = RoundedCornerShape(12.dp)
        ) {

            Text(
                text = stringResource(
                    R.string.see_whos_responsible
                ),

                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun AssetOwnersContent(
    departments: List<ResignationAssetDepartment>,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 8.dp
            )
    ) {

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(
                items = departments
            ) { department ->

                Column {

                    DepartmentHeader(
                        departmentName = department.departmentName
                    )

                    department.assets.forEach { asset ->

                        AssetOwnerItem(
                            asset = asset
                        )
                    }
                }
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange
            ),
            shape = RoundedCornerShape(12.dp)
        ) {

            Text(
                text = stringResource(
                    R.string.back_to_assets
                ),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun DepartmentHeader(
    departmentName: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .clip(
                RoundedCornerShape(8.dp)
            )
            .background(
                Color(0xFFFFF0EA)
            ),
        contentAlignment = Alignment.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(R.drawable.request_errand),
                contentDescription = null,
                tint = Orange,
                modifier = Modifier.size(16.dp)
            )

            Spacer(
                Modifier.width(4.dp)
            )

            Text(
                text = departmentName,
                color = TextDark,
                fontSize = 11.sp
            )
        }
    }
}


@Composable
private fun AssetOwnerItem(
    asset: ResignationAsset
) {
    val imageUrl = asset.employeeImageUrl
        ?.takeIf { it.isNotBlank() }
        ?.let {
            if (it.startsWith("http")) {
                it
            } else {
                PROFILE_IMAGE_BASE_URL +
                        it.removePrefix("/")
            }
        }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    Color(0xFFD7EEF2)
                ),
            contentAlignment = Alignment.Center
        ) {

            if (imageUrl == null) {

                // Placeholder
                Icon(
                    imageVector =
                        Icons.Outlined.PersonOutline,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )

            } else {

                AsyncImage(
                    model = imageUrl,
                    contentDescription =
                        asset.responsibleEmployeeName,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(
            Modifier.width(9.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text =
                    asset.responsibleEmployeeName,
                color = TextDark,
                fontSize = 11.sp,
                maxLines = 1
            )

            Text(
                text = stringResource(
                    R.string.assigned_to,
                    asset.covenantName
                ),
                color = Muted,
                fontSize = 9.sp
            )
        }

        Box(
            modifier = Modifier
                .size(28.dp)
                .border(
                    width = 1.dp,
                    color =
                        if (asset.isReceived) {
                            Orange
                        } else {
                            Border
                        }
                )
                .background(
                    color =
                        if (asset.isReceived) {
                            Orange
                        } else {
                            Color.Transparent
                        }
                ),
            contentAlignment = Alignment.Center
        ) {

            if (asset.isReceived) {

                Text(
                    text = "✓",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun AcceptedContent() {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 24.dp
            ),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Spacer(
            Modifier.height(65.dp)
        )

        Box(

            modifier = Modifier
                .size(92.dp)
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .background(
                    Color(0xFFF0F0F0)
                ),

            contentAlignment =
                Alignment.Center
        ) {

            Icon(

                imageVector =
                    Icons.Outlined.CheckCircle,

                contentDescription = null,

                tint = Green,

                modifier =
                    Modifier.size(28.dp)
            )
        }

        Spacer(
            Modifier.height(18.dp)
        )

        Text(

            text =
                stringResource(
                    R.string.resignation_accepted
                ),

            color = Green,

            fontSize = 14.sp,

            fontWeight =
                FontWeight.Bold
        )

        Spacer(
            Modifier.height(8.dp)
        )

        Text(

            text =
                stringResource(
                    R.string.resignation_accepted_message
                ),

            color = TextDark,

            fontSize = 11.sp,

            lineHeight = 19.sp,

            textAlign =
                TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResignationDatePicker(
    initialDate: LocalDate?,
    onDismiss: () -> Unit,
    onApply: (LocalDate) -> Unit
) {

    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

    var month by remember {

        mutableStateOf(
            YearMonth.from(
                initialDate ?: LocalDate.now()
            )
        )
    }

    var selected by remember {

        mutableStateOf(
            initialDate ?: LocalDate.now()
        )
    }

    ModalBottomSheet(

        onDismissRequest = onDismiss,

        sheetState = sheetState,

        containerColor = Color.White,

        dragHandle = {

            Box(

                Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .width(70.dp)
                    .height(4.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(Color.Black)
            )
        }
    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom =
                        WindowInsets
                            .navigationBars
                            .asPaddingValues()
                            .calculateBottomPadding()
                )
        ) {

            Text(

                text =
                    stringResource(
                        R.string.select_a_date
                    ),

                color = TextDark,

                fontSize = 14.sp,

                fontWeight =
                    FontWeight.Bold,

                modifier =
                    Modifier.padding(
                        horizontal = 20.dp,
                        vertical = 8.dp
                    )
            )

            Divider()

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 18.dp
                    ),

                verticalAlignment =
                    Alignment.CenterVertically,

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Text(

                    text = "‹",

                    fontSize = 28.sp,

                    color = Muted,

                    modifier =
                        Modifier.clickable {

                            month =
                                month.minusMonths(1)
                        }
                )

                Text(

                    text =
                        month.month.name
                            .lowercase()
                            .replaceFirstChar {
                                it.uppercase()
                            },

                    color = TextDark,

                    fontSize = 16.sp
                )

                Text(

                    text = "›",

                    fontSize = 28.sp,

                    color = Muted,

                    modifier =
                        Modifier.clickable {

                            month =
                                month.plusMonths(1)
                        }
                )
            }

            CalendarGrid(

                month = month,

                selected = selected,

                onDateSelected = {
                    selected = it
                }
            )

            Divider()

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    ),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(

                    text =
                        stringResource(
                            R.string.cancel
                        ),

                    color = Orange,

                    fontSize = 14.sp,

                    modifier =
                        Modifier.clickable(
                            onClick = onDismiss
                        )
                )

                Button(

                    onClick = {
                        onApply(selected)
                    },

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Orange
                        ),

                    shape =
                        RoundedCornerShape(12.dp)
                ) {

                    Text(
                        stringResource(
                            R.string.apply
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarGrid(
    month: YearMonth,
    selected: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {

    val firstDay =
        month.atDay(1)

    val startOffset =
        firstDay.dayOfWeek.value % 7

    val totalDays =
        month.lengthOfMonth()

    Column(
        modifier =
            Modifier.padding(
                horizontal = 18.dp
            )
    ) {

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            listOf(

                R.string.sun,
                R.string.mon,
                R.string.tue,
                R.string.wed,
                R.string.thu,
                R.string.fri,
                R.string.sat

            ).forEach {

                Text(

                    text =
                        stringResource(it),

                    color = Muted,

                    fontSize = 9.sp,

                    modifier =
                        Modifier.width(38.dp),

                    textAlign =
                        TextAlign.Center
                )
            }
        }

        Spacer(
            Modifier.height(8.dp)
        )

        val cells =
            ArrayList<LocalDate?>()

        repeat(startOffset) {
            cells.add(null)
        }

        for (day in 1..totalDays) {
            cells.add(
                month.atDay(day)
            )
        }

        while (
            cells.size % 7 != 0
        ) {
            cells.add(null)
        }

        cells
            .chunked(7)
            .forEach { week ->

                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    week.forEach { date ->

                        Box(

                            modifier =
                                Modifier
                                    .size(38.dp)
                                    .clip(
                                        RoundedCornerShape(6.dp)
                                    )
                                    .then(

                                        if (
                                            date == selected
                                        ) {

                                            Modifier.background(
                                                Color(0xFFFFF0EA)
                                            )

                                        } else {

                                            Modifier
                                        }
                                    )
                                    .clickable(
                                        enabled =
                                            date != null
                                    ) {

                                        date?.let(
                                            onDateSelected
                                        )
                                    },

                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(

                                text =
                                    date
                                        ?.dayOfMonth
                                        ?.toString()
                                        ?: "",

                                color =
                                    if (
                                        date == selected
                                    )
                                        Orange
                                    else
                                        TextDark,

                                fontSize = 10.sp
                            )
                        }
                    }
                }

                Spacer(
                    Modifier.height(4.dp)
                )
            }
    }
}

@Composable
private fun ContactDialog(
    onDismiss: () -> Unit
) {

    androidx.compose.ui.window.Dialog(
        onDismissRequest = onDismiss
    ) {

        Surface(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(18.dp),

            color = Color.White
        ) {

            Column {

                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(
                            horizontal = 16.dp
                        ),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(

                        imageVector =
                            Icons.Outlined.Call,

                        contentDescription = null,

                        tint = Orange,

                        modifier =
                            Modifier.size(20.dp)
                    )

                    Spacer(
                        Modifier.width(7.dp)
                    )

                    Text(

                        text =
                            stringResource(
                                R.string.contact
                            ),

                        color = TextDark,

                        fontSize = 16.sp,

                        modifier =
                            Modifier.weight(1f)
                    )

                    Icon(

                        imageVector =
                            Icons.Outlined.Close,

                        contentDescription = null,

                        tint = Muted,

                        modifier =
                            Modifier
                                .size(22.dp)
                                .clickable(
                                    onClick = onDismiss
                                )
                    )
                }

                Divider()

                Text(

                    text =
                        stringResource(
                            R.string.contact_message
                        ),

                    color = TextDark,

                    fontSize = 10.sp,

                    lineHeight = 17.sp,

                    modifier =
                        Modifier.padding(14.dp)
                )

                Row(

                    modifier =
                        Modifier.padding(
                            start = 14.dp,
                            end = 14.dp,
                            bottom = 14.dp
                        ),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Box(

                        modifier = Modifier
                            .size(34.dp)
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .background(Navy),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(

                            text = "2B",

                            color = Color.White,

                            fontSize = 9.sp,

                            fontWeight =
                                FontWeight.Bold
                        )
                    }

                    Spacer(
                        Modifier.width(8.dp)
                    )

                    Text(

                        text =
                            stringResource(
                                R.string.email_address
                            ),

                        color = Orange,

                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

private fun formatDate(
    date: LocalDate
): String {

    return "%02d %s %04d".format(

        date.dayOfMonth,

        date.month.name
            .lowercase()
            .replaceFirstChar {
                it.uppercase()
            },

        date.year
    )
}

/*
 * Previews
 */

@Preview(showBackground = true)
@Composable
private fun RequestResignationScreenCreatePreview() {
    TwoBTheme {
        RequestResignationScreenContent(
            state = ResignationState(
                step = ResignationStep.CREATE,
                isCheckingExistingRequest = false
            ),
            onAction = {},
            onBack = {},
            onDestinationSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RequestResignationScreenSubmissionPreview() {
    TwoBTheme {
        RequestResignationScreenContent(
            state = ResignationState(
                step = ResignationStep.SUBMISSION,
                isCheckingExistingRequest = false,
                resignationDate = "2023-10-27T00:00:00",
                lastWorkingDate = "2023-11-27T00:00:00",
                reason = "Personal reasons"
            ),
            onAction = {},
            onBack = {},
            onDestinationSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RequestResignationScreenPendingPreview() {
    TwoBTheme {
        RequestResignationScreenContent(
            state = ResignationState(
                step = ResignationStep.PENDING,
                isCheckingExistingRequest = false
            ),
            onAction = {},
            onBack = {},
            onDestinationSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RequestResignationScreenRejectedPreview() {
    TwoBTheme {
        RequestResignationScreenContent(
            state = ResignationState(
                step = ResignationStep.REJECTED,
                isCheckingExistingRequest = false
            ),
            onAction = {},
            onBack = {},
            onDestinationSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RequestResignationScreenApprovedPreview() {
    TwoBTheme {
        RequestResignationScreenContent(
            state = ResignationState(
                step = ResignationStep.APPROVED,
                isCheckingExistingRequest = false
            ),
            onAction = {},
            onBack = {},
            onDestinationSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RequestResignationScreenAssetOwnersPreview() {
    TwoBTheme {
        RequestResignationScreenContent(
            state = ResignationState(
                step = ResignationStep.ASSET_OWNERS,
                isCheckingExistingRequest = false
            ),
            onAction = {},
            onBack = {},
            onDestinationSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RequestResignationScreenAcceptedPreview() {
    TwoBTheme {
        RequestResignationScreenContent(
            state = ResignationState(
                step = ResignationStep.ACCEPTED,
                isCheckingExistingRequest = false
            ),
            onAction = {},
            onBack = {},
            onDestinationSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResignationHeaderPreview() {
    TwoBTheme {
        ResignationHeader(onBack = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ResignationLoadingContentPreview() {
    TwoBTheme {
        ResignationLoadingContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun ResignationErrorDialogPreview() {
    TwoBTheme {
        ResignationErrorDialog(
            message = "Something went wrong. Please try again later.",
            onDismiss = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactDialogPreview() {
    TwoBTheme {
        ContactDialog(onDismiss = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ResignationDatePickerPreview() {
    TwoBTheme {
        ResignationDatePicker(
            initialDate = LocalDate.now(),
            onDismiss = {},
            onApply = {}
        )
    }
}


