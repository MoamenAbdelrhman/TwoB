package com.example.twob.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.twob.R
import com.example.twob.components.AppHeader
import com.example.twob.components.MainBottomNavigation
import com.example.twob.components.MainDestination
import com.example.twob.profile.ProfileAction
import com.example.twob.profile.ProfileState
import com.example.twob.ui.theme.TwoBTheme
import com.example.twob.ui.theme.thirdColor

private val ErrorRed = Color(0xFFEF4444)

@Composable
internal fun ProfileContent(
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
            modifier = Modifier.fillMaxSize()
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
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {

                    EmployeeInfoCard(
                        state = state,
                        onManagerClick = {
                            onAction(
                                ProfileAction.ManagerClicked
                            )
                        },
                        onEmployeesClick = {
                            onAction(
                                ProfileAction.EmployeesClicked
                            )
                        },
                        onDepartmentClick = {
                            onAction(
                                ProfileAction.DepartmentClicked
                            )
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    ProfileSectionContainer {

                        ProfileSectionTitle(
                            title = stringResource(
                                R.string.personal_report
                            )
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        ProfileActionRow(
                            icon = Icons.Outlined.AccessTime,
                            title = stringResource(
                                R.string.shift_and_time_off
                            ),
                            onClick = null
                        )

                        ProfileDivider()
                    }

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    ProfileSectionContainer {

                        ProfileSectionTitle(
                            title = stringResource(
                                R.string.settings
                            )
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        LanguageRow(
                            culture = state.culture,
                            onLanguageSelected = { culture ->
                                onAction(
                                    ProfileAction.LanguageSelected(
                                        culture
                                    )
                                )
                            }
                        )

                        ProfileDivider()

                        ProfileActionRow(
                            icon = Icons.Outlined.Lock,
                            title = stringResource(
                                R.string.change_password
                            ),
                            onClick = null
                        )

                        ProfileDivider()

                        ProfileActionRow(
                            icon = Icons.Outlined.Logout,
                            title = stringResource(
                                R.string.log_out
                            ),
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

            MainBottomNavigation(
                selectedDestination = MainDestination.PROFILE,
                onDestinationSelected = onDestinationSelected
            )
        }
    }
}