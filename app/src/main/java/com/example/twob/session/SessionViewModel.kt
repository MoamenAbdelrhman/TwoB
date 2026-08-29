package com.example.twob.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twob.data.local.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SessionViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow<SessionState>(SessionState.Loading)

    val state: StateFlow<SessionState> =
        _state.asStateFlow()

    init {
        observeSession()
    }

    private fun observeSession() {

        viewModelScope.launch {

            userPreferencesRepository.initializeToken()

            userPreferencesRepository.token
                .catch {
                    _state.value =
                        SessionState.Unauthenticated
                }
                .collect { token ->

                    _state.value =
                        if (token.isNullOrBlank()) {
                            SessionState.Unauthenticated
                        } else {
                            SessionState.Authenticated
                        }
                }
        }
    }
}