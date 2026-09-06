package com.example.twob.services.hrletter

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.twob.R
import com.example.twob.components.AppHeader
import com.example.twob.components.AppPageHeader
import com.example.twob.components.HeaderViewModel
import com.example.twob.components.MainBottomNavigation
import com.example.twob.components.MainDestination
import com.example.twob.services.hrletter.components.HRLetterCreateContent
import com.example.twob.services.hrletter.components.HRLetterErrorDialog
import com.example.twob.services.hrletter.components.HRLetterListContent
import org.koin.androidx.compose.koinViewModel

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
            HRLetterErrorDialog(
                errorMessage = state.errorMessage,
                onAction = onAction
            )
        }
    }
}