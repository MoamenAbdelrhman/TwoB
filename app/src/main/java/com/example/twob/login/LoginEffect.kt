package com.example.twob.login

sealed interface LoginEffect {
    data object NavigateToProfile : LoginEffect
}
