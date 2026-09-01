package com.example.twob.di

import com.example.twob.data.local.datastore.UserPreferencesRepository
import com.example.twob.data.remote.api.HRLetterApi
import com.example.twob.data.repositories.HRLetterRepository
import com.example.twob.data.repositories.HRLetterRepositoryImpl
import com.example.twob.services.hrletter.HRLetterRequestViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel
import retrofit2.Retrofit

val hrLetterModule = module {

    single<HRLetterApi> {
        get<Retrofit>(
            qualifier = named("authenticatedRetrofit")
        ).create(HRLetterApi::class.java)
    }

    single<HRLetterRepository> {
        HRLetterRepositoryImpl(
            api = get()
        )
    }

    viewModel {
        HRLetterRequestViewModel(
            repository = get(),
            userPreferencesRepository = get(),
            connectivityObserver = get()
        )
    }
}