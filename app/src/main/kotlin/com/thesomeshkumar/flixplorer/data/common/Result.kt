package com.thesomeshkumar.flixplorer.data.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class Result<out T> {
    data class Success<out T>(val response: T) : Result<T>()
    data class Error(val remoteSourceException: RemoteSourceException) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> = this
    .map<T, Result<T>> {
        Result.Success(it)
    }
    .onStart {
        emit(Result.Loading)
    }
    .catch {
        emit(Result.Error(RequestErrorHandler.getRequestError(it)))
    }
