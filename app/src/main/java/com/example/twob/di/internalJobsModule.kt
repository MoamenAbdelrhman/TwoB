package com.example.twob.di

import com.example.twob.data.repositories.InternalJobsRepositoryImpl
import com.example.twob.services.internaljobs.InternalJobsRepository
import com.example.twob.services.internaljobs.InternalJobsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val internalJobsModule =
    module {

        single<InternalJobsRepository> {
            InternalJobsRepositoryImpl(
                internalJobsApi = get(),
                userPreferencesRepository = get(),
                context = get()
            )
        }

        viewModel {
            InternalJobsViewModel(
                repository = get()
            )
        }
    }