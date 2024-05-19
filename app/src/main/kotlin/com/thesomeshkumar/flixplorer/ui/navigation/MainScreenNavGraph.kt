package com.thesomeshkumar.flixplorer.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
                        FlixDetails(
                            type = Constants.MEDIA_TYPE_MOVIE,
                            id = it.id.toString(),
                            name = it.name,
                            backdrop = it.backdropPath.removePrefix("/"),
                            poster = it.posterPath.removePrefix("/")
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
                        FlixDetails(
                            type = Constants.MEDIA_TYPE_TV_SHOW,
                            id = it.id.toString(),
                            name = it.name,
                            backdrop = it.backdropPath.removePrefix("/"),
                            poster = it.posterPath.removePrefix("/")
                        )
                    )
                }
            }

            composable<FlixDetails> { arguments: NavBackStackEntry ->
                val args = arguments.toRoute<FlixDetails>()
                val name = args.name
                val backdrop = args.backdrop
                val poster = args.poster

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

            composable<SettingsScreen> {
                SettingsScreen(onNavigationUp = {
                    navController.popBackStack()
                })
            }
        }
    }
}
