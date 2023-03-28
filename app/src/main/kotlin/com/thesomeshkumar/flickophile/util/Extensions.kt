package com.thesomeshkumar.flickophile.util

import android.content.Context
import com.thesomeshkumar.flickophile.R
import com.thesomeshkumar.flickophile.data.common.RemoteSourceException
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
fun Double.roundTo(decimalPlaces: Int): String {
    return "%.${decimalPlaces}f".format(this)
}
