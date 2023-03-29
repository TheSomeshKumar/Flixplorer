package com.thesomeshkumar.flickophile.ui.models

import androidx.annotation.Keep

@Keep
data class DetailUI(
    val backdropPath: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val status: String,
    val tagline: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Long
) {
    data class Genre(
        val id: Int,
        val name: String
    )
}
