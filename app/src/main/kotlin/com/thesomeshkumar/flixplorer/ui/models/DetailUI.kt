package com.thesomeshkumar.flixplorer.ui.models

import androidx.annotation.Keep

@Keep
data class DetailUI(
    val backdropPath: String,
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
    val voteCount: Long,
    val runtime: String?,
    val genres: GenreUI,
    val videos: List<VideoUI>,
    val credits: CreditUI
)
