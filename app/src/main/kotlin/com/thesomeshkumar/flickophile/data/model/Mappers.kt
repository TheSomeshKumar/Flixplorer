package com.thesomeshkumar.flickophile.data.model // ktlint-disable filename

import com.thesomeshkumar.flickophile.data.model.MovieDTO.Movie
import com.thesomeshkumar.flickophile.data.model.TVShowDTO.TVShow
import com.thesomeshkumar.flickophile.ui.models.DetailUI
import com.thesomeshkumar.flickophile.ui.models.HomeMediaItemUI
import com.thesomeshkumar.flickophile.util.Constants
import com.thesomeshkumar.flickophile.util.toYear

fun TVShow.mapToUI() = HomeMediaItemUI(
    id = id,
    name = name,
    posterPath = posterPath ?: Constants.NONE,
    backdropPath = backdropPath ?: Constants.NONE,
    overview = overview.ifBlank { Constants.NONE }
)

fun Movie.mapToUI() = HomeMediaItemUI(
    id = id,
    name = title,
    posterPath = posterPath ?: Constants.NONE,
    backdropPath = backdropPath ?: Constants.NONE,
    overview = overview.ifBlank { Constants.NONE }
)

fun MovieDetailsDTO.mapToUI() = DetailUI(
    backdropPath = backdropPath ?: Constants.NONE,
    genres = genreDTOS.mapToUI(),
    homepage = homepage,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview ?: Constants.NONE,
    popularity = popularity,
    posterPath = posterPath ?: Constants.NONE,
    releaseDate = releaseDate.toYear(),
    status = status,
    tagline = tagline ?: Constants.NONE,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount,
    runtime = runtime
)

fun TvShowDetailsDTO.mapToUI() = DetailUI(
    backdropPath = backdropPath ?: Constants.NONE,
    genres = genreDTOS.mapToUI(),
    homepage = homepage,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalName,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath ?: Constants.NONE,
    releaseDate = firstAirDate.toYear(),
    status = status,
    tagline = tagline,
    title = name,
    voteAverage = voteAverage,
    voteCount = voteCount,
    runtime = episodeRunTime.first()
)

fun List<GenreDTO>.mapToUI(): DetailUI.Genre {
    return map {
        DetailUI.Genre(id = it.id, name = it.name)
    }.firstOrNull() ?: DetailUI.Genre(0, Constants.NONE)
}
