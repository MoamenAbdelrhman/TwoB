package com.example.twob.login

import androidx.annotation.StringRes

data class LoginState(
    val id: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val useFingerprint: Boolean = false,
    val isLoading: Boolean = false,
    val isLoginError: Boolean = false,
    val errorMessage: String? = null,
    @StringRes val errorMessageRes: Int? = null
)