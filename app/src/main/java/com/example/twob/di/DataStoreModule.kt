package com.example.twob.di

import com.example.twob.data.local.SessionInitializer
import com.example.twob.data.local.datastore.UserPreferencesRepository
import com.example.twob.data.local.datastore.UserPreferencesRepositoryImpl
import com.example.twob.data.remote.auth.TokenProvider
import com.example.twob.data.remote.auth.TokenProviderImpl
import org.koin.dsl.module

val dataStoreModule = module {

    single<TokenProvider> {
        TokenProviderImpl()
    }

    single<UserPreferencesRepository> {
        UserPreferencesRepositoryImpl(
            context = get(),
            tokenProvider = get()
        )
    }

    single {
        SessionInitializer(
            userPreferencesRepository = get()
        )
    }
}
