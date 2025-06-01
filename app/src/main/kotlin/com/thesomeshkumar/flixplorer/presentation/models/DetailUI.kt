package com.thesomeshkumar.flixplorer.presentation.models

data class DetailUI(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val releaseDate: String,
    val genres: GenreUI,
    val credits: List<PeopleUI>,
    val crews: List<PeopleUI>,
    val videos: List<VideoUI>,
    val runtime: Int?,
    val tagline: String?
)

data class GenreUI(
    val id: Int,
    val name: String
)

data class VideoUI(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String
)

data class PeopleUI(
    val id: Int,
    val name: String,
    val profilePath: String?,
    val character: String,
)
