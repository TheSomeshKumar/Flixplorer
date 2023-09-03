package com.thesomeshkumar.flixplorer.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class MovieDTO(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Movie>,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int
) {

    @Keep
    data class Movie(
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("backdrop_path") val backdropPath: String?,
        @SerializedName("genre_ids") val genreIds: List<Int>,
        @SerializedName("id") val id: Int,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("original_title") val originalTitle: String,
        @SerializedName("overview") val overview: String,
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("release_date") val releaseDate: String,
        @SerializedName("title") val title: String,
        @SerializedName("video") val video: Boolean,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("vote_count") val voteCount: Int
    )
}
