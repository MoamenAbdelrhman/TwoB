package com.example.twob.di

import com.example.twob.data.repositories.EmployeeProfileRepository
import com.example.twob.data.repositories.EmployeeProfileRepositoryImpl
import com.example.twob.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {

    single<EmployeeProfileRepository> {

        EmployeeProfileRepositoryImpl(
            employeeProfileApi = get(),
            userPreferencesRepository = get()
        )
    }

    viewModel {

        ProfileViewModel(
            employeeProfileRepository = get(),
            userPreferencesRepository = get()
        )
    }
}
