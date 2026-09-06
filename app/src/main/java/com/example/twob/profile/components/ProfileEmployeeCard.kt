package com.example.twob.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.twob.R
import com.example.twob.profile.ProfileState
import com.example.twob.ui.theme.secondaryColor
import com.example.twob.ui.theme.thirdColor

private val ProfileBlue = Color(0xFF0D2E6B)
private val CardBackground = Color(0xFFF4F4F4)

private const val PROFILE_IMAGE_BASE_URL =
    "https://shantafactory.com/HR/api/"

@Composable
internal fun EmployeeInfoCard(
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
            .width(200.dp)
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
