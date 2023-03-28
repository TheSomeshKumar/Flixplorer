package com.thesomeshkumar.flickophile.data.model // ktlint-disable filename

import com.thesomeshkumar.flickophile.data.model.MovieDTO.Movie
import com.thesomeshkumar.flickophile.data.model.TVShowDTO.TVShow
import com.thesomeshkumar.flickophile.ui.models.DetailUI
import com.thesomeshkumar.flickophile.ui.models.MediaHomeUI

fun TVShow.mapToUI() = MediaHomeUI(
    id = id,
    name = name,
    posterPath = posterPath ?: "N/A",
    backdropPath = backdropPath ?: "N/A",
    overview = overview.ifBlank { "N/A" }
)

fun Movie.mapToUI() = MediaHomeUI(
    id = id,
    name = title,
    posterPath = posterPath ?: "N/A",
    backdropPath = backdropPath ?: "N/A",
    overview = overview.ifBlank { "N/A" }
)

fun MovieDetailsDTO.mapToUI() = DetailUI(
    backdropPath = backdropPath,
    genres = genres.mapToUI(),
    homepage = homepage,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    status = status,
    tagline = tagline,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun TvShowDetailsDTO.mapToUI() = DetailUI(
    backdropPath = backdropPath,
    genres = genres.mapToUI(),
    homepage = homepage,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalName,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = firstAirDate,
    status = status,
    tagline = tagline,
    title = name,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun List<Genre>.mapToUI(): List<DetailUI.Genre> = map {
    DetailUI.Genre(id = it.id, name = it.name)
}
