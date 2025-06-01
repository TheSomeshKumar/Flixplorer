@file:Suppress("MagicNumber")

package com.thesomeshkumar.flixplorer.data.model.mapper

import com.thesomeshkumar.flixplorer.data.model.dto.CreditsDTO
import com.thesomeshkumar.flixplorer.data.model.dto.GenreDTO
import com.thesomeshkumar.flixplorer.data.model.dto.Movie
import com.thesomeshkumar.flixplorer.data.model.dto.MovieDetailsDTO
import com.thesomeshkumar.flixplorer.data.model.dto.TVShow
import com.thesomeshkumar.flixplorer.data.model.dto.TvShowDetailsDTO
import com.thesomeshkumar.flixplorer.data.model.dto.VideoDTO
import com.thesomeshkumar.flixplorer.presentation.models.DetailUI
import com.thesomeshkumar.flixplorer.presentation.models.GenreUI
import com.thesomeshkumar.flixplorer.presentation.models.MediaListItemUI
import com.thesomeshkumar.flixplorer.presentation.models.PeopleUI
import com.thesomeshkumar.flixplorer.presentation.models.VideoUI
import com.thesomeshkumar.flixplorer.util.Constants
import com.thesomeshkumar.flixplorer.util.toDefaultFormattedDate

fun TVShow.mapToUI() = MediaListItemUI(
    id = id,
    title = name,
    overview = overview.ifBlank { Constants.NONE },
    posterPath = posterPath?.removePrefix("/") ?: Constants.NONE,
    backdropPath = backdropPath?.removePrefix("/") ?: Constants.NONE,
    voteAverage = voteAverage,
    releaseDate = firstAirDate.toDefaultFormattedDate(),
    mediaType = Constants.MEDIA_TYPE_TV_SHOW
)

fun Movie.mapToUI() = MediaListItemUI(
    id = id,
    title = title,
    overview = overview.ifBlank { Constants.NONE },
    posterPath = posterPath?.removePrefix("/") ?: Constants.NONE,
    backdropPath = backdropPath?.removePrefix("/") ?: Constants.NONE,
    voteAverage = voteAverage,
    releaseDate = releaseDate.toDefaultFormattedDate(),
    mediaType = Constants.MEDIA_TYPE_MOVIE
)

fun MovieDetailsDTO.mapToUI() = DetailUI(
    id = id,
    title = title,
    overview = overview ?: Constants.NONE,
    posterPath = posterPath?.removePrefix("/") ?: Constants.NONE,
    backdropPath = backdropPath?.removePrefix("/") ?: Constants.NONE,
    voteAverage = voteAverage,
    releaseDate = releaseDate.toDefaultFormattedDate(),
    genres = genres.map { it.mapToUI() }.first(),
    credits = credits.cast.take(10).map { it.mapToPeopleUI() },
    crews = credits.crew.take(10).map { it.mapToPeopleUI() },
    videos = videos.videos.mapToUI(),
    runtime = runtime,
    tagline = tagline ?: Constants.NONE
)

fun TvShowDetailsDTO.mapToUI() = DetailUI(
    id = id,
    title = name,
    overview = overview,
    posterPath = posterPath ?: Constants.NONE,
    backdropPath = backdropPath ?: Constants.NONE,
    voteAverage = voteAverage,
    releaseDate = firstAirDate.toDefaultFormattedDate(),
    genres = genres.map { it.mapToUI() }.first(),
    credits = credits.cast.take(10).map { it.mapToPeopleUI() },
    crews = credits.crew.take(10).map { it.mapToPeopleUI() },
    videos = videos.videos.mapToUI(),
    runtime = episodeRunTime.firstOrNull(),
    tagline = tagline ?: Constants.NONE
)

fun GenreDTO.mapToUI() = GenreUI(
    id = id,
    name = name
)

fun CreditsDTO.Cast.mapToPeopleUI() = PeopleUI(
    id = id,
    name = name,
    profilePath = profilePath,
    character = character,
)

fun CreditsDTO.Crew.mapToPeopleUI() = PeopleUI(
    id = id,
    name = name,
    profilePath = profilePath,
    character = knownForDepartment,
)

fun List<VideoDTO.Videos>.mapToUI(): List<VideoUI> {
    return this
        .sortedWith(
            compareByDescending {
                it.type == Constants.VIDEO_TYPE_TRAILER
            }
        )
        .map { video ->
            VideoUI(
                id = video.id,
                key = video.key,
                name = video.name,
                site = video.site,
                type = video.type
            )
        }
}
