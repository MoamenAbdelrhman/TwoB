package com.example.twob.login

sealed interface LoginAction {

    data class IdChanged(val value: String) : LoginAction

    data class PasswordChanged(val value: String) : LoginAction

    data object TogglePasswordVisibility : LoginAction

    data object ToggleFingerprint : LoginAction

    data object LoginClicked : LoginAction
}