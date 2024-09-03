package com.thesomeshkumar.flixplorer.data.model

import com.thesomeshkumar.flixplorer.ui.models.CreditUI
import com.thesomeshkumar.flixplorer.ui.models.DetailUI
import com.thesomeshkumar.flixplorer.ui.models.GenreUI
import com.thesomeshkumar.flixplorer.ui.models.HomeMediaModel
import com.thesomeshkumar.flixplorer.ui.models.PeopleUI
import com.thesomeshkumar.flixplorer.ui.models.VideoUI
import com.thesomeshkumar.flixplorer.util.Constants
import com.thesomeshkumar.flixplorer.util.minuteToRelativeTime
import com.thesomeshkumar.flixplorer.util.toYear

fun TVShowDTO.TVShow.mapToUI() = HomeMediaModel(
    id = id,
    name = name,
    posterPath = posterPath?.removePrefix("/") ?: Constants.NONE,
    backdropPath = backdropPath?.removePrefix("/") ?: Constants.NONE,
    overview = overview.ifBlank { Constants.NONE }
)

fun MovieDTO.Movie.mapToUI() = HomeMediaModel(
    id = id,
    name = title,
    posterPath = posterPath?.removePrefix("/") ?: Constants.NONE,
    backdropPath = backdropPath?.removePrefix("/") ?: Constants.NONE,
    overview = overview.ifBlank { Constants.NONE }
)

fun MovieDetailsDTO.mapToUI() = DetailUI(
    backdropPath = backdropPath?.removePrefix("/") ?: Constants.NONE,
    genres = genres.mapToUI(),
    homepage = homepage,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview ?: Constants.NONE,
    popularity = popularity,
    posterPath = posterPath?.removePrefix("/") ?: Constants.NONE,
    releaseDate = releaseDate.toYear(),
    status = status,
    tagline = tagline ?: Constants.NONE,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount,
    runtime = runtime?.minuteToRelativeTime(),
    videos = videos.videos.mapToUI(),
    credits = credits.mapToUI()
)

fun TvShowDetailsDTO.mapToUI() = DetailUI(
    backdropPath = backdropPath ?: Constants.NONE,
    genres = genres.mapToUI(),
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
    runtime = episodeRunTime
        .firstOrNull()
        ?.minuteToRelativeTime(),
    videos = videos.videos.mapToUI(),
    credits = credits.mapToUI()
)

fun List<GenreDTO>.mapToUI(): GenreUI {
    return map {
        GenreUI(
            id = it.id,
            name = it.name
        )
    }.firstOrNull() ?: GenreUI(
        0,
        Constants.NONE
    )
}

fun CreditsDTO.mapToUI(): CreditUI {
    val totalItemToTake = 10
    return CreditUI(
        cast = cast
            .take(totalItemToTake)
            .mapCast(),
        crew = crew
            .take(totalItemToTake)
            .mapCrew()
    )
}

fun List<CreditsDTO.Crew>.mapCrew(): List<PeopleUI> {
    return map { crew: CreditsDTO.Crew ->
        PeopleUI(
            id = crew.id,
            creditId = crew.creditId,
            name = crew.name,
            role = crew.job,
            profilePath = crew.profilePath
        )
    }
}

fun List<CreditsDTO.Cast>.mapCast(): List<PeopleUI> {
    return map { cast: CreditsDTO.Cast ->
        PeopleUI(
            id = cast.id,
            creditId = cast.creditId,
            name = cast.name,
            role = cast.character,
            profilePath = cast.profilePath
        )
    }
}

fun List<VideoDTO.Videos>.mapToUI(): List<VideoUI> {
    return this
        .sortedWith(
            compareByDescending {
                it.type == Constants.VIDEO_TYPE_TRAILER
            }
        )
        .map { video ->
            VideoUI(
                video.id,
                video.key,
                video.name,
                video.type
            )
        }
}
