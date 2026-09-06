package com.example.twob.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.twob.components.MainDestination
import com.example.twob.profile.components.ProfileContent
import com.example.twob.profile.components.ProfileDialogs
import org.koin.androidx.compose.koinViewModel

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

    ProfileDialogs(
        state = state,
        onAction = onAction
    )
}