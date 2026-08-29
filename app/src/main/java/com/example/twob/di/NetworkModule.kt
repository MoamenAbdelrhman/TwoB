package com.example.twob.di

import android.content.pm.ApplicationInfo
import com.example.twob.data.remote.api.EmployeeProfileApi
import com.example.twob.data.remote.api.LoginApi
import com.example.twob.data.remote.api.OfficialHolidaysApi
import com.example.twob.data.remote.api.ResignationApi
import com.example.twob.data.remote.auth.TokenProvider
import com.example.twob.data.remote.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://shantafactory.com/HR/api/"

val networkModule = module {

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = if (
                androidContext().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
            ) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<LoginApi> {
        get<Retrofit>().create(LoginApi::class.java)
    }

    single<AuthInterceptor> {
        AuthInterceptor(
            tokenProvider = get<TokenProvider>()
        )
    }

    single<OkHttpClient>(named("authenticatedClient")) {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Retrofit>(named("authenticatedRetrofit")) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get(named("authenticatedClient")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    single<EmployeeProfileApi> {
        get<Retrofit>(
            qualifier = named("authenticatedRetrofit")
        ).create(EmployeeProfileApi::class.java)
    }

    single<ResignationApi> {
        get<Retrofit>(
            qualifier = named("authenticatedRetrofit")
        ).create(ResignationApi::class.java)
    }

    single<OfficialHolidaysApi> {
        get<Retrofit>(
            qualifier = named("authenticatedRetrofit")
        ).create(OfficialHolidaysApi::class.java)
    }
}
