package com.example.twob.session

sealed interface SessionState {

    data object Loading : SessionState

    data object Authenticated : SessionState

    data object Unauthenticated : SessionState
}