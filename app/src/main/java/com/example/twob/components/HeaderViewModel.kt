package com.example.twob.components

import androidx.lifecycle.ViewModel
import com.example.twob.data.local.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import androidx.lifecycle.viewModelScope

class HeaderViewModel(
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val imageUrl: StateFlow<String?> =
        userPreferencesRepository.imageUrl
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )
}