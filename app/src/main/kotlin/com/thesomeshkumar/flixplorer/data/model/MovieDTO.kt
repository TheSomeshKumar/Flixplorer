package com.thesomeshkumar.flixplorer.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDTO(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<Movie>,
    @SerialName("total_results") val totalResults: Int,
    @SerialName("total_pages") val totalPages: Int
) {

    @Serializable
    data class Movie(
        @SerialName("adult") val adult: Boolean,
        @SerialName("backdrop_path") val backdropPath: String?,
        @SerialName("genre_ids") val genreIds: List<Int>,
        @SerialName("id") val id: Int,
        @SerialName("original_language") val originalLanguage: String,
        @SerialName("original_title") val originalTitle: String,
        @SerialName("overview") val overview: String,
        @SerialName("popularity") val popularity: Double,
        @SerialName("poster_path") val posterPath: String?,
        @SerialName("release_date") val releaseDate: String,
        @SerialName("title") val title: String,
        @SerialName("video") val video: Boolean,
        @SerialName("vote_average") val voteAverage: Double,
        @SerialName("vote_count") val voteCount: Int
    )
}
