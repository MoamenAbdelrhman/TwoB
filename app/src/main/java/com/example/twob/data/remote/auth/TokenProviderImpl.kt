package com.example.twob.data.remote.auth

class TokenProviderImpl : TokenProvider {

    private var token: String? = null

    override fun getToken(): String? = token

    override fun updateToken(token: String?) {
        this.token = token
    }

    override fun clearToken() {
        this.token = null
    }
}