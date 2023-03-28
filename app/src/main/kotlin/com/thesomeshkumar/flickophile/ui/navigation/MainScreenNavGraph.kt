package com.thesomeshkumar.flickophile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.thesomeshkumar.flickophile.ui.screens.detail.DetailsScreen
import com.thesomeshkumar.flickophile.ui.screens.movie.MoviesScreen
import com.thesomeshkumar.flickophile.ui.screens.tvshow.TvShowScreen
import com.thesomeshkumar.flickophile.util.Constants

@Composable
fun MainScreenNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = NavGraph.BOTTOM_BAR_GRAPH,
        startDestination = BottomBarScreen.Movies.route
    ) {
        composable(route = BottomBarScreen.Movies.route) {
            MoviesScreen {
                navController.navigate(
                    MainScreenRoutes.MediaDetail.withArgs(
                        Constants.MEDIA_TYPE_MOVIE,
                        it.id.toString(),
                        it.name,
                        it.backdropPath.removePrefix("/")
                    )
                )
            }
        }

        composable(route = BottomBarScreen.TvShows.route) {
            TvShowScreen {
                navController.navigate(
                    MainScreenRoutes.MediaDetail.withArgs(
                        Constants.MEDIA_TYPE_TV_SHOW,
                        it.id.toString(),
                        it.name,
                        it.backdropPath.removePrefix("/")
                    )
                )
            }
        }

        composable(
            route = MainScreenRoutes.MediaDetail.route
        ) { navBackStackEntry ->
            val name = navBackStackEntry.arguments?.getString(MainScreenRoutes.ARG_MEDIA_NAME) ?: ""
            val posterUrl =
                navBackStackEntry.arguments?.getString(MainScreenRoutes.ARG_MEDIA_POSTER) ?: ""

            DetailsScreen(name, posterUrl) {
                navController.popBackStack()
            }
        }
    }
}
