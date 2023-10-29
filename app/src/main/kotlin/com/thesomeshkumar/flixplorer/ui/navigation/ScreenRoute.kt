package com.thesomeshkumar.flixplorer.ui.navigation

import com.thesomeshkumar.flixplorer.R

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

sealed class MainScreenRoutes(internal open val route: String) {
    data object MediaDetail :
        MainScreenRoutes(
            route = "mediaDetail" + "/{$ARG_MEDIA_TYPE}" + "/{$ARG_MEDIA_ID}" + "/{$ARG_MEDIA_NAME}" + "/{$ARG_MEDIA_BACKDROP}" + "/{$ARG_MEDIA_POSTER}"
        ) {
        fun withArgs(
            type: String,
            id: String,
            name: String,
            backdrop: String,
            poster: String
        ): String = route
            .replace(
                "{$ARG_MEDIA_TYPE}",
                type
            )
            .replace(
                "{$ARG_MEDIA_ID}",
                id
            )
            .replace(
                "{$ARG_MEDIA_NAME}",
                name
            )
            .replace(
                "{$ARG_MEDIA_BACKDROP}",
                backdrop
            )
            .replace(
                "{$ARG_MEDIA_POSTER}",
                poster
            )
    }

    data object SettingsScreen : MainScreenRoutes(route = "settings")

    companion object {
        const val ARG_MEDIA_TYPE: String = "type"
        const val ARG_MEDIA_ID: String = "id"
        const val ARG_MEDIA_NAME: String = "name"
        const val ARG_MEDIA_BACKDROP: String = "backdrop"
        const val ARG_MEDIA_POSTER: String = "poster"
    }
}
