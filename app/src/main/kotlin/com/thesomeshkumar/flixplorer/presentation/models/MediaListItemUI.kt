package com.thesomeshkumar.flixplorer.presentation.models

data class MediaListItemUI(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Double,
    val releaseDate: String,
    val mediaType: String
)
