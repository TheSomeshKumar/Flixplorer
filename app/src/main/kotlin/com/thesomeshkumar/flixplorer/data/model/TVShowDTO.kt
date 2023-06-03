package com.thesomeshkumar.flixplorer.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class TVShowDTO(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<TVShow>,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int
) {
    @Keep
    data class TVShow(
        @SerializedName("id") val id: Int,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("backdrop_path") val backdropPath: String?,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("overview") val overview: String,
        @SerializedName("first_air_date") val firstAirDate: String,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("vote_count") val voteCount: Int,
        @SerializedName("name") val name: String,
        @SerializedName("original_name") val originalName: String
    )
}
