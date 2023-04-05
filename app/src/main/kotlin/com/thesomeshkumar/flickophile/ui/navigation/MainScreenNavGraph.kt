package com.thesomeshkumar.flickophile.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.thesomeshkumar.flickophile.ui.screens.detail.DetailsScreen
import com.thesomeshkumar.flickophile.ui.screens.movie.MoviesScreen
import com.thesomeshkumar.flickophile.ui.screens.settings.SettingsScreen
import com.thesomeshkumar.flickophile.ui.screens.tvshow.TvShowScreen
import com.thesomeshkumar.flickophile.util.Constants

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreenNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        route = NavGraph.BOTTOM_BAR_GRAPH,
        startDestination = BottomBarScreen.Movies.route,
        enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(towards = AnimatedContentScope.SlideDirection.Up) },
        popEnterTransition = {
            slideIntoContainer(towards = AnimatedContentScope.SlideDirection.Down)
        },
        popExitTransition = {
            slideOutOfContainer(towards = AnimatedContentScope.SlideDirection.Down)
        }

    ) {
        composable(route = BottomBarScreen.Movies.route) {
            MoviesScreen(onItemClick = {
                navController.navigate(
                    MainScreenRoutes.MediaDetail.withArgs(
                        Constants.MEDIA_TYPE_MOVIE,
                        it.id.toString(),
                        it.name,
                        it.backdropPath.removePrefix("/")
                    )
                )
            })
        }

        composable(route = BottomBarScreen.TvShows.route) {
            TvShowScreen(onItemClick = {
                navController.navigate(
                    MainScreenRoutes.MediaDetail.withArgs(
                        Constants.MEDIA_TYPE_TV_SHOW,
                        it.id.toString(),
                        it.name,
                        it.backdropPath.removePrefix("/")
                    )
                )
            })
        }

        composable(route = MainScreenRoutes.MediaDetail.route) { navBackStackEntry ->
            val name = navBackStackEntry.arguments?.getString(MainScreenRoutes.ARG_MEDIA_NAME) ?: ""
            val posterUrl =
                navBackStackEntry.arguments?.getString(MainScreenRoutes.ARG_MEDIA_POSTER) ?: ""

            DetailsScreen(name, posterUrl, onNavigationUp = {
                navController.popBackStack()
            })
        }

        composable(route = MainScreenRoutes.SettingsScreen.route) {
            SettingsScreen(onNavigationUp = {
                navController.popBackStack()
            })
        }
    }
}
