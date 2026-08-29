package com.example.twob.data.remote.auth

interface TokenProvider {

    fun getToken(): String?

    fun updateToken(token: String?)

    fun clearToken()
}