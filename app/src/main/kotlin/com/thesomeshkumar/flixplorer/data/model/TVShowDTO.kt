package com.thesomeshkumar.flixplorer.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TVShowDTO(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<TVShow>,
    @SerialName("total_results") val totalResults: Int,
    @SerialName("total_pages") val totalPages: Int
) {
    @Serializable
    data class TVShow(
        @SerialName("id") val id: Int,
        @SerialName("poster_path") val posterPath: String?,
        @SerialName("popularity") val popularity: Double,
        @SerialName("backdrop_path") val backdropPath: String?,
        @SerialName("vote_average") val voteAverage: Double,
        @SerialName("overview") val overview: String,
        @SerialName("first_air_date") val firstAirDate: String,
        @SerialName("original_language") val originalLanguage: String,
        @SerialName("vote_count") val voteCount: Int,
        @SerialName("name") val name: String,
        @SerialName("original_name") val originalName: String
    )
}
