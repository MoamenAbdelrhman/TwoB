package com.example.twob.di

import com.example.twob.components.HeaderViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val headerModule = module {

    viewModel {
        HeaderViewModel(
            userPreferencesRepository = get()
        )
    }
}