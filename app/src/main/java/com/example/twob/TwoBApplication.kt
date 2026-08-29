package com.example.twob

import android.app.Application
import com.example.twob.di.dataStoreModule
import com.example.twob.di.headerModule
import com.example.twob.di.loginModule
import com.example.twob.di.networkModule
import com.example.twob.di.officialHolidaysModule
import com.example.twob.di.profileModule
import com.example.twob.di.resignationModule
import com.example.twob.di.sessionModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TwoBApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@TwoBApplication)

            modules(
                networkModule,
                dataStoreModule,
                loginModule,
                profileModule,
                sessionModule,
                resignationModule,
                officialHolidaysModule,
                headerModule
            )
        }
    }
}