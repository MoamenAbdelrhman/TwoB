package com.example.twob.di

import com.example.twob.session.SessionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val sessionModule = module {

    viewModel {
        SessionViewModel(
            userPreferencesRepository = get()
        )
    }
}