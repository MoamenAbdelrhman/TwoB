package com.example.twob.profile

sealed interface ProfileEffect {
    data object NavigateToLogin : ProfileEffect
}
