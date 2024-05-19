package com.thesomeshkumar.flixplorer.ui.navigation

import com.thesomeshkumar.flixplorer.R
import kotlinx.serialization.Serializable

object NavGraph {
    const val BOTTOM_BAR_GRAPH = "bottom_bar_graph"
}

sealed class BottomBarScreen(
    val route: String,
    val title: Int,
    val icon: Int
) {

    data object Movies : BottomBarScreen(
        route = "movies",
        title = R.string.title_movies,
        icon = R.drawable.ic_home_movie

    )

    data object TvShows : BottomBarScreen(
        route = "tvshows",
        title = R.string.title_shows,
        icon = R.drawable.ic_home_tv
    )
}

@Serializable
data class FlixDetails(
    val type: String,
    val id: String,
    val name: String,
    val backdrop: String,
    val poster: String
)

@Serializable
object SettingsScreen
