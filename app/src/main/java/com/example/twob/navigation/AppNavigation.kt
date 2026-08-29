package com.example.twob.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.twob.components.MainDestination
import com.example.twob.login.LoginScreen
import com.example.twob.profile.ProfileScreen
import com.example.twob.services.ServicesScreen
import com.example.twob.services.ServiceDestination
import com.example.twob.services.resignation.RequestResignationScreen
import com.example.twob.services.resignation.officialholidays.OfficialHolidaysScreen
import com.example.twob.session.SessionState
import com.example.twob.session.SessionViewModel

private const val LOGIN_ROUTE = "login"
private const val PROFILE_ROUTE = "profile"
private const val SERVICES_ROUTE = "services"
private const val RESIGNATION_ROUTE = "resignation_request"

private const val OFFICIAL_HOLIDAYS_ROUTE = "official_holidays"
@Composable
fun AppNavigation(
    sessionViewModel: SessionViewModel
) {

    val sessionState by
    sessionViewModel.state.collectAsStateWithLifecycle()

    if (sessionState is SessionState.Loading) {
        return
    }

    val navController = rememberNavController()

    val startDestination =
        when (sessionState) {

            SessionState.Authenticated ->
                PROFILE_ROUTE

            SessionState.Unauthenticated ->
                LOGIN_ROUTE

            SessionState.Loading ->
                return
        }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(LOGIN_ROUTE) {

            LoginScreen(
                onLoginSuccess = {

                    navController.navigate(
                        PROFILE_ROUTE
                    ) {

                        popUpTo(LOGIN_ROUTE) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        composable(PROFILE_ROUTE) {

            ProfileScreen(
                onLogout = {

                    navController.navigate(
                        LOGIN_ROUTE
                    ) {
                        popUpTo(PROFILE_ROUTE) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                },

                onDestinationSelected = { destination ->

                    navigateToDestination(
                        navController = navController,
                        destination = destination
                    )
                }
            )
        }

        composable(SERVICES_ROUTE) {

            ServicesScreen(
                onServiceClick = { service ->

                    when (service.destination) {

                        ServiceDestination.RESIGNATION -> {
                            navController.navigate(RESIGNATION_ROUTE) {
                                launchSingleTop = true
                            }
                        }

                        ServiceDestination.OFFICIAL_HOLIDAYS -> {
                            navController.navigate(OFFICIAL_HOLIDAYS_ROUTE) {
                                launchSingleTop = true
                            }
                        }

                        else -> Unit
                    }
                },
                onDestinationSelected = { destination ->

                    navigateToDestination(
                        navController = navController,
                        destination = destination
                    )
                }
            )

        }

        composable(RESIGNATION_ROUTE) {

            RequestResignationScreen(
                onBack = {
                    navController.popBackStack()
                },
                onDestinationSelected = { destination ->
                    navigateToDestination(
                        navController = navController,
                        destination = destination
                    )
                }
            )
        }
        composable(OFFICIAL_HOLIDAYS_ROUTE) {

            OfficialHolidaysScreen(
                onBackClick = {
                    navController.popBackStack()
                },

                onDestinationSelected = { destination ->
                    navigateToDestination(
                        navController = navController,
                        destination = destination
                    )
                }
            )
        }
    }
}

private fun navigateToDestination(
    navController: NavHostController,
    destination: MainDestination
) {
    when (destination) {

        MainDestination.PROFILE -> {
            navController.navigate(PROFILE_ROUTE) {
                popUpTo(
                    navController.graph.startDestinationId
                ) {
                    saveState = true
                }

                launchSingleTop = true
                restoreState = true
            }
        }

        MainDestination.SERVICES -> {
            navController.navigate(SERVICES_ROUTE) {
                popUpTo(
                    navController.graph.startDestinationId
                ) {
                    saveState = true
                }

                launchSingleTop = true
                restoreState = true
            }
        }

        MainDestination.FINGERPRINT -> Unit

        MainDestination.ASK_2B -> Unit

        MainDestination.NOTIFICATION -> Unit
    }
}
