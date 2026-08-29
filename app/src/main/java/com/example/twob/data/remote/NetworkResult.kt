package com.example.twob.data.remote

sealed interface NetworkResult<out T> {

    data object Loading : NetworkResult<Nothing>

    data class Success<T>(
        val data: T
    ) : NetworkResult<T>

    data class Error(
        val message: String,
        val exception: Throwable? = null
    ) : NetworkResult<Nothing>
}