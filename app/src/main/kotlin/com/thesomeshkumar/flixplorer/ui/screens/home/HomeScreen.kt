@file:OptIn(ExperimentalAnimationApi::class)

package com.thesomeshkumar.flixplorer.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.thesomeshkumar.flixplorer.R
import com.thesomeshkumar.flixplorer.ui.navigation.BottomBarScreen
import com.thesomeshkumar.flixplorer.ui.navigation.FlixplorerBottomBar
import com.thesomeshkumar.flixplorer.ui.navigation.MainScreenNavGraph
import com.thesomeshkumar.flixplorer.ui.navigation.MainScreenRoutes
import com.thesomeshkumar.flixplorer.ui.widget.FlixTopAppBar

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val screens = listOf(
        BottomBarScreen.Movies,
        BottomBarScreen.TvShows
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val topBarDestination = screens.any { it.route == currentDestination?.route }

    Scaffold(topBar = {
        AnimatedVisibility(
            visible = topBarDestination,
            enter = slideInVertically(animationSpec = spring(visibilityThreshold = IntOffset.Zero)),
            exit = slideOutVertically(animationSpec = spring(visibilityThreshold = IntOffset.Zero))
        ) {
            FlixTopAppBar(stringResource(id = R.string.app_name), onSettingsClick = {
                navController.navigate(MainScreenRoutes.SettingsScreen.route)
            })
        }
    }, bottomBar = { FlixplorerBottomBar(navController = navController) }) { padding ->
        MainScreenNavGraph(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}
