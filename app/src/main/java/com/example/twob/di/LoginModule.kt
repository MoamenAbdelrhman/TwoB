package com.example.twob.di

import com.example.twob.data.repositories.LoginRepository
import com.example.twob.data.repositories.LoginRepositoryImpl
import com.example.twob.login.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {

    single<LoginRepository> {
        LoginRepositoryImpl(
            loginApi = get(),
            userPreferencesRepository = get(),
            employeeProfileRepository = get()
        )
    }

    viewModel {
        LoginViewModel(
            loginRepository = get()
        )
    }
}
