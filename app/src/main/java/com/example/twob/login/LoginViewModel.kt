package com.example.twob.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twob.R
import com.example.twob.data.remote.NetworkResult
import com.example.twob.data.remote.dto.LoginResponseDto
import com.example.twob.data.repositories.LoginRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginEffect>(extraBufferCapacity = 1)
    val effect: SharedFlow<LoginEffect> = _effect

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.IdChanged -> updateCredentials(id = action.value)
            is LoginAction.PasswordChanged -> updateCredentials(password = action.value)
            LoginAction.TogglePasswordVisibility -> togglePasswordVisibility()
            LoginAction.ToggleFingerprint -> toggleFingerprint()
            LoginAction.LoginClicked -> login()
        }
    }

    private fun updateCredentials(
        id: String = _state.value.id,
        password: String = _state.value.password
    ) {
        _state.value = _state.value.copy(
            id = id,
            password = password,
            isLoginError = false,
            errorMessage = null,
            errorMessageRes = null
        )
    }

    private fun togglePasswordVisibility() {
        _state.value = _state.value.copy(
            isPasswordVisible = !_state.value.isPasswordVisible
        )
    }

    private fun toggleFingerprint() {
        _state.value = _state.value.copy(
            useFingerprint = !_state.value.useFingerprint
        )
    }

    private fun login() {
        val currentState = _state.value

        if (currentState.isLoading) return

        if (currentState.id.isBlank() || currentState.password.isBlank()) {
            _state.value = currentState.copy(
                isLoginError = true,
                errorMessage = null,
                errorMessageRes = R.string.login_validation_error
            )
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                isLoginError = false,
                errorMessage = null,
                errorMessageRes = null
            )

            when (val result = loginRepository.login(
                email = currentState.id,
                password = currentState.password
            )) {
                is NetworkResult.Success -> handleResponse(result.data)
                is NetworkResult.Error -> showError()
                NetworkResult.Loading -> Unit
            }
        }
    }

    private fun handleResponse(response: LoginResponseDto) {
        _state.value = _state.value.copy(
            isLoading = false,
            isLoginError = !response.success,
            errorMessage = response.message.takeIf { !response.success },
            errorMessageRes = null
        )

        if (response.success) {
            _effect.tryEmit(LoginEffect.NavigateToProfile)
        }
    }

    private fun showError() {
        // NetworkResult.Error only occurs for genuine technical failures
        // (no connection, timeout, parse failure) — that text is never
        // meaningfully localizable, so a generic localized message is
        // shown instead of the raw exception text.
        _state.value = _state.value.copy(
            isLoading = false,
            isLoginError = true,
            errorMessage = null,
            errorMessageRes = R.string.network_error_message
        )
    }
}