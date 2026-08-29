package com.example.twob.data.local

import com.example.twob.data.local.datastore.UserPreferencesRepository

class SessionInitializer(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend fun initialize() {
        userPreferencesRepository.initializeToken()
    }
}
