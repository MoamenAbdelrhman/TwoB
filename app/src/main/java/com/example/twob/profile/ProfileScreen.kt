package com.example.twob.profile

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.twob.R
import com.example.twob.data.repositories.EmployeeManager
import com.example.twob.ui.theme.TwoBTheme
import com.example.twob.ui.theme.secondaryColor
import com.example.twob.ui.theme.thirdColor
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import com.example.twob.components.AppHeader
import com.example.twob.components.MainBottomNavigation
import com.example.twob.components.MainDestination

private val ProfileBlue = Color(0xFF0D2E6B)
private val CardBackground = Color(0xFFF4F4F4)
private val TextDark = Color(0xFF28335A)
private val DividerColor = Color(0xFFD7D7D7)
private val ErrorRed = Color(0xFFEF4444)

private const val PROFILE_IMAGE_BASE_URL =
    "https://shantafactory.com/HR/api/"

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onDestinationSelected: (MainDestination) -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
    ) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onAction: (ProfileAction) -> Unit =
        viewModel::onAction

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ProfileEffect.NavigateToLogin -> onLogout()
            }
        }
    }

    ProfileContent(
        state = state,
        onAction = onAction,
        onDestinationSelected = onDestinationSelected
    )

    if (state.activeDialog == ProfileDialog.MANAGER) {

        ManagerDialog(
            managers = state.managers,
            isLoading = state.isDialogLoading,
            errorRes = state.dialogErrorRes,
            onDismiss = { onAction(ProfileAction.DismissDialog) }
        )
    }

    if (state.activeDialog == ProfileDialog.EMPLOYEES) {

        EmployeesDialog(
            employees = state.employees,
            isLoading = state.isDialogLoading,
            errorRes = state.dialogErrorRes,
            onDismiss = {
                onAction(ProfileAction.DismissDialog)
            }
        )
    }

    if (state.activeDialog == ProfileDialog.DEPARTMENT) {

        DepartmentDialog(
            departmentName = state.departmentName,
            partationName = state.partationName,
            isLoading = state.isDialogLoading,
            errorRes = state.dialogErrorRes,
            onDismiss = {
                onAction(
                    ProfileAction.DismissDialog
                )
            }
        )
    }
}

@Composable
private fun ProfileContent(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    onDestinationSelected: (MainDestination) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            AppHeader(
                showUserImage = false
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            if (state.isLoading) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = thirdColor
                    )
                }

            } else {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {


                    EmployeeInfoCard(
                        state = state,
                        onManagerClick = {
                            onAction(ProfileAction.ManagerClicked)
                        },
                        onEmployeesClick = {
                            onAction(ProfileAction.EmployeesClicked)
                        },
                        onDepartmentClick = {
                            onAction(ProfileAction.DepartmentClicked)
                        }
                    )


                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    ProfileSectionContainer {

                        ProfileSectionTitle(
                            title = stringResource(R.string.personal_report)
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        ProfileActionRow(
                            icon = Icons.Outlined.AccessTime,
                            title = stringResource(R.string.shift_and_time_off),
                            onClick = null
                        )

                        ProfileDivider()
                    }

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )


                    ProfileSectionContainer {

                        ProfileSectionTitle(
                            title = stringResource(R.string.settings)
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        LanguageRow(
                            culture = state.culture,
                            onLanguageSelected = { culture ->
                                onAction(
                                    ProfileAction.LanguageSelected(culture)
                                )
                            }
                        )

                        ProfileDivider()

                        ProfileActionRow(
                            icon = Icons.Outlined.Lock,
                            title = stringResource(R.string.change_password),
                            onClick = null
                        )

                        ProfileDivider()

                        ProfileActionRow(
                            icon = Icons.Outlined.Logout,
                            title = stringResource(R.string.log_out),
                            iconTint = ErrorRed,
                            textColor = ErrorRed,
                            onClick = {
                                onAction(
                                    ProfileAction.LogoutClicked
                                )
                            }
                        )

                        ProfileDivider()
                    }

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                }
            }

//            ProfileBottomNavigation()
            MainBottomNavigation(
                selectedDestination = MainDestination.PROFILE,
                onDestinationSelected = onDestinationSelected
            )
        }
    }
}

@Composable
private fun ProfileSectionContainer(
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
private fun EmployeeInfoCard(
    state: ProfileState,
    onManagerClick: () -> Unit,
    onDepartmentClick: () -> Unit,
    onEmployeesClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(
                horizontal = 18.dp
            )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(205.dp)
                .align(Alignment.BottomCenter)
                .clip(
                    RoundedCornerShape(14.dp)
                )
                .background(CardBackground)
        ) {

            RoleDepartment(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = 12.dp,
                        end = 18.dp
                    ),
                isManager = state.isManager,
                hasManager = state.hasManager,
                onManagerClick = onManagerClick,
                onDepartmentClick = onDepartmentClick,
                onEmployeesClick = onEmployeesClick
            )

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        start = 12.dp,
                        top = 75.dp
                    )
            ) {

                Text(
                    text = state.name,
                    color = secondaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = state.machineCode,
                    color = secondaryColor,
                    fontSize = 12.sp
                )
                Spacer(
                    modifier = Modifier.height(5.dp)
                )
                ChangeProfilePictureButton()
            }
        }


        ProfileImage(
            imageUrl = state.imageUrl,
            modifier = Modifier
                .padding(
                    start = 4.dp
                )
                .align(Alignment.TopStart)
                .size(90.dp)
        )

        if (state.jobTitle.isNotBlank()) {

            JobTitle(
                title = state.jobTitle,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        start = 10.dp,
                        top = 80.dp
                    )
            )
        }
    }
}

@Composable
private fun RoleDepartment(
    modifier: Modifier = Modifier,
    isManager: Boolean,
    hasManager: Boolean,
    onManagerClick: () -> Unit,
    onDepartmentClick: () -> Unit,
    onEmployeesClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (hasManager) {

            Text(
                text = stringResource(R.string.manager),
                color = thirdColor,
                fontSize = 12.sp,
                modifier = Modifier.clickable(
                    onClick = onManagerClick
                )
            )

            if (isManager) {

                Text(
                    text = " • ",
                    color = thirdColor,
                    fontSize = 12.sp
                )

                Text(
                    text = stringResource(R.string.employees),
                    color = thirdColor,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable(
                        onClick = onEmployeesClick
                    )
                )
            }

            Text(
                text = " • ",
                color = thirdColor,
                fontSize = 12.sp
            )
        }

        Text(
            text = stringResource(R.string.department),
            color = thirdColor,
            fontSize = 12.sp,
            modifier = Modifier.clickable(
                onClick = onDepartmentClick
            )
        )
    }
}

@Composable
private fun JobTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(24.dp)
            )
            .background(Color.White)
            .border(
                width = 1.dp,
                color = ProfileBlue,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(
                horizontal = 6.dp
            )
    ) {

        Text(
            text = title,
            color = secondaryColor,
            fontSize = 10.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ProfileImage(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    val fullUrl = when {
        imageUrl?.startsWith("http") == true -> {
            imageUrl
        }

        imageUrl.isNullOrBlank() -> {
            null
        }

        else -> {
            PROFILE_IMAGE_BASE_URL +
                    imageUrl.removePrefix("/")
        }
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.White)
            .border(
                width = 2.dp,
                color = ProfileBlue,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        if (fullUrl == null) {

            Icon(
                imageVector = Icons.Outlined.PersonOutline,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.size(50.dp)
            )

        } else {

            AsyncImage(
                model = fullUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun ChangeProfilePictureButton(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(190.dp)
            .height(40.dp)
            .clip(
                RoundedCornerShape(8.dp)
            )
            .background(thirdColor)
            .clickable {
                // Profile picture flow will be implemented later.
            },
        contentAlignment = Alignment.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = "Change profile picture",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )

            Spacer(
                modifier = Modifier.width(6.dp)
            )

            Text(
                text = stringResource(R.string.change_my_profile_picture),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Composable
private fun ProfileSectionTitle(
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
private fun ProfileActionRow(
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
private fun LanguageRow(
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
@Composable
private fun ProfileDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(DividerColor)
    )
}

@Composable
private fun ManagerDialog(
    managers: List<EmployeeManager>,
    isLoading: Boolean,
    errorRes: Int?,
    onDismiss: () -> Unit
) {

    val localizedContext = LocalContext.current
    val localizedLayoutDirection = LocalLayoutDirection.current

    Dialog(
        onDismissRequest = onDismiss
    ) {
        CompositionLocalProvider(
            LocalContext provides localizedContext,
            LocalLayoutDirection provides localizedLayoutDirection
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(18.dp),
                color = Color.White
            ) {

                Box {

                    Column {

                        DialogHeader(
                            icon = Icons.Outlined.People,
                            title = stringResource(R.string.your_manager),
                            onDismiss = onDismiss
                        )

                        ProfileDivider()

                        when {

                            isLoading -> {

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(130.dp),
                                    contentAlignment =
                                        Alignment.Center
                                ) {

                                    CircularProgressIndicator(
                                        color = thirdColor
                                    )
                                }
                            }

                            errorRes != null -> {
                                Text(
                                    text = stringResource(errorRes),
                                    color = ErrorRed,
                                    fontSize = 12.sp,
                                    modifier = Modifier.fillMaxWidth().padding(24.dp)
                                )
                            }

                            managers.isEmpty() -> {

                                Text(
                                    text = stringResource(R.string.no_manager_found),
                                    color = secondaryColor,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp)
                                )
                            }

                            else -> {

                                Column(
                                    modifier = Modifier.padding(
                                        vertical = 8.dp
                                    )
                                ) {

                                    managers.forEach { manager ->

                                        ManagerItem(
                                            manager = manager
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}


@Composable
private fun DialogHeader(
    icon: ImageVector,
    title: String,
    onDismiss: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .padding(
                horizontal = 18.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = thirdColor,
            modifier = Modifier.size(25.dp)
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        Text(
            text = title,
            color = secondaryColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Outlined.Close,
            contentDescription = "Close",
            tint = Color(0xFF8E8E8E),
            modifier = Modifier
                .size(25.dp)
                .clickable(
                    onClick = onDismiss
                )
        )
    }
}

@Composable
private fun ManagerItem(
    manager: EmployeeManager
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .padding(
                horizontal = 18.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ProfileDialogImage(
            imageUrl = manager.imageUrl
        )

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Column {

            Text(
                text = manager.name,
                color = secondaryColor,
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = manager.jobName,
                color = Color(0xFF999999),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun DepartmentDialog(
    departmentName: String,
    partationName: String,
    isLoading: Boolean,
    errorRes: Int?,
    onDismiss: () -> Unit
) {
    val localizedContext = LocalContext.current
    val localizedLayoutDirection = LocalLayoutDirection.current


    Dialog(
        onDismissRequest = onDismiss
    ) {
        CompositionLocalProvider(
            LocalContext provides localizedContext,
            LocalLayoutDirection provides localizedLayoutDirection
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = Color.White
            ) {

                Box {

                    Column {

                        DialogHeader(
                            icon = Icons.Outlined.BusinessCenter,
                            title = stringResource(R.string.your_department),
                            onDismiss = onDismiss
                        )

                        ProfileDivider()

                        when {

                            isLoading -> {

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp),
                                    contentAlignment =
                                        Alignment.Center
                                ) {

                                    CircularProgressIndicator(
                                        color = thirdColor
                                    )
                                }
                            }

                            errorRes != null -> {
                                Text(
                                    text = stringResource(errorRes),
                                    color = ErrorRed,
                                    fontSize = 12.sp,
                                    modifier = Modifier.fillMaxWidth().padding(24.dp)
                                )
                            }

                            else -> {

                                DepartmentItem(
                                    departmentName =
                                        departmentName,

                                    partationName =
                                        partationName
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
private fun DepartmentItem(
    departmentName: String,
    partationName: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 18.dp,
                vertical = 18.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .background(ProfileBlue),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.logo_2b
                ),
                contentDescription = null,
                modifier = Modifier.size(35.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Column {

            Text(
                text = departmentName,
                color = secondaryColor,
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = partationName,
                color = Color(0xFF999999),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun EmployeesDialog(
    employees: List<EmployeeManager>,
    isLoading: Boolean,
    errorRes: Int?,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(18.dp),
            color = Color.White
        ) {

            Column {

                DialogHeader(
                    icon = Icons.Outlined.People,
                    title = stringResource(R.string.your_employees),
                    onDismiss = onDismiss
                )

                ProfileDivider()

                when {

                    isLoading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = thirdColor
                            )
                        }
                    }

                    errorRes != null -> {
                        Text(
                            text = stringResource(errorRes),
                            color = ErrorRed,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        )
                    }

                    employees.isEmpty() -> {
                        Text(
                            text = stringResource(R.string.no_employees_found),
                            color = secondaryColor,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        )
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 500.dp)
                        ) {
                            items(
                                items = employees
                            ) { employee ->

                                ManagerItem(
                                    manager = employee
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileDialogImage(
    imageUrl: String
) {

    val fullUrl = when {

        imageUrl.startsWith("http") -> {
            imageUrl
        }

        imageUrl.isBlank() -> {
            null
        }

        else -> {
            PROFILE_IMAGE_BASE_URL +
                    imageUrl.removePrefix("/")
        }
    }

    if (fullUrl == null) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFE8E8E8)),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector =
                    Icons.Outlined.PersonOutline,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(28.dp)
            )
        }

    } else {

        AsyncImage(
            model = fullUrl,
            contentDescription = "Manager profile picture",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    TwoBTheme {
        ProfileContent(
            state = ProfileState(
                name = "Mohamed Ameen",
                machineCode = "123456",
                jobTitle = "Android Developer",
                imageUrl = "",
                isLoading = false
            ),
            onAction = {}
        )
    }
}