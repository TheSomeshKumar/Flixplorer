package com.thesomeshkumar.flixplorer.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.thesomeshkumar.flixplorer.ui.screens.detail.DetailsScreen
import com.thesomeshkumar.flixplorer.ui.screens.movie.MoviesScreen
import com.thesomeshkumar.flixplorer.ui.screens.settings.SettingsScreen
import com.thesomeshkumar.flixplorer.ui.screens.tvshow.TvShowScreen
import com.thesomeshkumar.flixplorer.util.Constants

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainScreenNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            route = NavGraph.BOTTOM_BAR_GRAPH,
            startDestination = BottomBarScreen.Movies.route,
        ) {
            composable(route = BottomBarScreen.Movies.route) {
                MoviesScreen(
                    animatedVisibilityScope = this,
                    modifier = modifier
                ) {
                    navController.navigate(
                        MainScreenRoutes.MediaDetail.withArgs(
                            Constants.MEDIA_TYPE_MOVIE,
                            it.id.toString(),
                            it.name,
                            it.backdropPath.removePrefix(
                                "/"
                            ),
                            it.posterPath.removePrefix(
                                "/"
                            )
                        )
                    )
                }
            }

            composable(route = BottomBarScreen.TvShows.route) {
                TvShowScreen(
                    animatedVisibilityScope = this,
                    modifier = modifier
                ) {
                    navController.navigate(
                        MainScreenRoutes.MediaDetail.withArgs(
                            type = Constants.MEDIA_TYPE_TV_SHOW,
                            id = it.id.toString(),
                            name = it.name,
                            backdrop = it.backdropPath.removePrefix(
                                "/"
                            ),
                            poster = it.posterPath.removePrefix(
                                "/"
                            )
                        )
                    )
                }
            }

            composable(route = MainScreenRoutes.MediaDetail.route) { navBackStackEntry ->
                val name =
                    navBackStackEntry.arguments?.getString(MainScreenRoutes.ARG_MEDIA_NAME) ?: ""
                val backdrop =
                    navBackStackEntry.arguments?.getString(MainScreenRoutes.ARG_MEDIA_BACKDROP)
                        ?: ""
                val poster =
                    navBackStackEntry.arguments?.getString(MainScreenRoutes.ARG_MEDIA_POSTER) ?: ""

                DetailsScreen(
                    name = name,
                    backdrop = backdrop,
                    poster = poster,
                    animatedVisibilityScope = this,
                    onNavigationUp = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = MainScreenRoutes.SettingsScreen.route) {
                SettingsScreen(
                    onNavigationUp = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
