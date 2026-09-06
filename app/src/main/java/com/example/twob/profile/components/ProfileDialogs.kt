package com.example.twob.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.twob.R
import com.example.twob.data.repositories.EmployeeManager
import com.example.twob.profile.ProfileAction
import com.example.twob.profile.ProfileDialog
import com.example.twob.profile.ProfileState
import com.example.twob.ui.theme.secondaryColor
import com.example.twob.ui.theme.thirdColor
import coil.compose.AsyncImage

private val ProfileBlue = Color(0xFF0D2E6B)
private val ErrorRed = Color(0xFFEF4444)

private const val PROFILE_IMAGE_BASE_URL =
    "https://shantafactory.com/HR/api/"

@Composable
internal fun ProfileDialogs(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {
    if (state.activeDialog == ProfileDialog.MANAGER) {

        ManagerDialog(
            managers = state.managers,
            isLoading = state.isDialogLoading,
            errorRes = state.dialogErrorRes,
            onDismiss = {
                onAction(
                    ProfileAction.DismissDialog
                )
            }
        )
    }

    if (state.activeDialog == ProfileDialog.EMPLOYEES) {

        EmployeesDialog(
            employees = state.employees,
            isLoading = state.isDialogLoading,
            errorRes = state.dialogErrorRes,
            onDismiss = {
                onAction(
                    ProfileAction.DismissDialog
                )
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