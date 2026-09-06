package com.example.twob.services.hrletter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.twob.R
import com.example.twob.data.repositories.HRLetterStatus
import com.example.twob.services.hrletter.HRLetterRequestAction
import com.example.twob.services.hrletter.HRLetterRequestState
import com.example.twob.ui.theme.TwoBTheme

private val Orange = Color(0xFFFF6B2C)

@Composable
internal fun HRLetterListContent(
    state: HRLetterRequestState,
    onAction: (HRLetterRequestAction) -> Unit
) {

    val allStatuses = listOf(
        HRLetterStatus(
            id = null,
            name = stringResource(R.string.all)
        )
    ) + state.statuses

    // The server now filters by status (see HRLetterRequestViewModel /
    // HRLetterRepositoryImpl), so `state.requests` already IS the list for
    // the selected tab. No client-side filtering needed here anymore.

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        when {

            // Very first fetch of the screen: nothing has ever loaded yet,
            // so a full-screen spinner is fine — there are no tabs worth
            // keeping on screen at this point.
            state.isLoading && !state.hasLoadedRequests -> {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    CircularProgressIndicator(
                        color = Orange
                    )
                }
            }

            else -> {

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    // Tabs live here, OUTSIDE the loading/empty branches
                    // below, so tapping a tab (or any background refresh)
                    // never unmounts them.
                    HRLetterStatusTabs(
                        statuses = allStatuses,
                        selectedStatus = state.selectedStatus,
                        onStatusSelected = { status ->

                            onAction(
                                HRLetterRequestAction.StatusSelected(
                                    status
                                )
                            )
                        }
                    )

                    when {

                        // Switching tabs: only this inner area shows a
                        // loader while the new status's letters are
                        // fetched from the server.
                        state.isTabLoading -> {

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {

                                CircularProgressIndicator(
                                    color = Orange
                                )
                            }
                        }

                        state.requests.isEmpty() -> {

                            EmptyHRLetterState()
                        }

                        else -> {

                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(
                                    start = 20.dp,
                                    end = 20.dp,
                                    top = 18.dp,
                                    bottom = 100.dp
                                ),
                                verticalArrangement =
                                    Arrangement.spacedBy(10.dp)
                            ) {

                                items(
                                    items = state.requests,
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
        }

        FloatingActionButton(
            onClick = {
                onAction(
                    HRLetterRequestAction.AddRequestClicked
                )
            },
            containerColor = Orange,
            contentColor = Color.White,
            shape = androidx.compose.foundation.shape.CircleShape,
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