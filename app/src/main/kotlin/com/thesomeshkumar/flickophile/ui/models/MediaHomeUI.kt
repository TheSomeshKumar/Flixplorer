package com.thesomeshkumar.flickophile.ui.models

import androidx.annotation.Keep

@Keep
data class MediaHomeUI(
    val id: Int,
    val name: String,
    val posterPath: String,
    val backdropPath: String,
    val overview: String
)
