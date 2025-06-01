package com.thesomeshkumar.flixplorer.core.network.error

sealed class NetworkError : Exception() {
    data class ApiError(val code: Int, override val message: String) : NetworkError()
    data class NoInternet(override val message: String = "No internet connection") : NetworkError()
    data class Unknown(override val message: String = "Unknown error occurred") : NetworkError()
}
