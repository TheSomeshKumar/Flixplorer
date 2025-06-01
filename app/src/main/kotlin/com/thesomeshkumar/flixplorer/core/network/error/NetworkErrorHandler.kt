package com.thesomeshkumar.flixplorer.core.network.error

import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NetworkErrorHandler @Inject constructor() {
    fun handleError(throwable: Throwable): NetworkError {
        return when (throwable) {
            is IOException -> NetworkError.NoInternet()
            is HttpException -> NetworkError.ApiError(throwable.code(), throwable.message())
            else -> NetworkError.Unknown(throwable.message ?: "Unknown error occurred")
        }
    }
}
