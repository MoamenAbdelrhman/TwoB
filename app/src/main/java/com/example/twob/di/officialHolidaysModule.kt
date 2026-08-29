package com.example.twob.di

import com.example.twob.data.repositories.OfficialHolidaysRepositoryImpl
import com.example.twob.services.resignation.officialholidays.OfficialHolidaysRepository
import com.example.twob.services.resignation.officialholidays.OfficialHolidaysViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val officialHolidaysModule = module {

    single<OfficialHolidaysRepository> {
        OfficialHolidaysRepositoryImpl(
            api = get(),
            preferences = get()
        )
    }

    viewModel {
        OfficialHolidaysViewModel(
            repository = get()
        )
    }
}