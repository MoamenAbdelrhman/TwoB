package com.example.twob.di

import com.example.twob.data.repositories.ResignationRepositoryImpl
import com.example.twob.services.resignation.ResignationRepository
import com.example.twob.services.resignation.ResignationViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val resignationModule = module {

    singleOf(
        ::ResignationRepositoryImpl
    ) bind ResignationRepository::class

    viewModel {
        ResignationViewModel(
            resignationRepository = get()
        )
    }
}