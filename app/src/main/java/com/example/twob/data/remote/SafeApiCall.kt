package com.example.twob.data.remote

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): NetworkResult<T> = withContext(Dispatchers.IO) {
    try {
        NetworkResult.Success(apiCall())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        NetworkResult.Error(
            message = e.message ?: "Something went wrong",
            exception = e
        )
    }
}
