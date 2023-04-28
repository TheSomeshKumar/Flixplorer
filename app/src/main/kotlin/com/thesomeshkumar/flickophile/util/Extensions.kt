package com.thesomeshkumar.flickophile.util

import android.content.Context
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.thesomeshkumar.flickophile.R
import com.thesomeshkumar.flickophile.data.common.RemoteSourceException
import java.time.LocalDate
import okhttp3.ResponseBody

fun RemoteSourceException.getError(context: Context): String {
    return when (messageResource) {
        is Int -> return context.getString(messageResource)
        is ResponseBody? -> return messageResource!!.string()
        is String -> return messageResource
        else -> context.getString(R.string.error_unexpected_message)
    }
}

fun String.toFullPosterUrl(): String = Constants.TMDB_POSTER_PATH_URL + this

fun Double.roundTo(decimalPlaces: Int): String = "%.${decimalPlaces}f".format(this)

fun Int.minuteToRelativeTime(): String {
    val hours: Int = this / 60
    val minutes: Int = this % 60
    return String.format("%dh:%02dm", hours, minutes)
}

fun String.toYear(): String {
    val localDate = LocalDate.parse(this)
    return localDate.year.toString()
}

fun Iterable<LazyPagingItems<*>>.isAnyRefreshing(): Boolean =
    any { it.loadState.refresh is LoadState.Loading }

fun Iterable<LazyPagingItems<*>>.hasItems(): Boolean =
    any { it.itemCount > 0 }

fun Iterable<LazyPagingItems<*>>.isAnyError(): Pair<Boolean, LoadState.Error?> {
    return if (any { it.loadState.refresh is LoadState.Error }) {
        val errorList: LazyPagingItems<*>? = this.find { it.loadState.refresh is LoadState.Error }
        true to errorList?.loadState?.refresh as LoadState.Error
    } else {
        false to null
    }
}

fun Iterable<LazyPagingItems<*>>.refreshAll() {
    return forEach { it.refresh() }
}
