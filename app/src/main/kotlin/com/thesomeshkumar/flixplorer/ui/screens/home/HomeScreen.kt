package com.thesomeshkumar.flixplorer.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.thesomeshkumar.flixplorer.R
import com.thesomeshkumar.flixplorer.ui.component.FlixTopAppBar
import com.thesomeshkumar.flixplorer.ui.navigation.BottomBarScreen
import com.thesomeshkumar.flixplorer.ui.navigation.FlixplorerBottomBar
import com.thesomeshkumar.flixplorer.ui.navigation.MainScreenNavGraph
import com.thesomeshkumar.flixplorer.ui.navigation.SettingsScreen
import com.thesomeshkumar.flixplorer.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val screens = listOf(
        BottomBarScreen.Movies,
        BottomBarScreen.TvShows
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showNavigationState = rememberSaveable { (mutableStateOf(false)) }
    showNavigationState.value = screens.any { it.route == currentDestination?.route }

    Scaffold(topBar = {
        AnimatedVisibility(
            visible = showNavigationState.value,
            enter = slideInVertically(
                animationSpec = tween(Constants.ANIM_TIME_SHORT)
            ) {
                -it
            },
            exit = slideOutVertically(
                animationSpec = tween(Constants.ANIM_TIME_SHORT)
            ) {
                -it
            }
        ) {
            FlixTopAppBar(stringResource(id = R.string.app_name), onSettingsClick = {
                navController.navigate(SettingsScreen)
            })
        }
    }, bottomBar = {
        AnimatedVisibility(
            visible = showNavigationState.value,
            enter = slideInVertically(
                animationSpec = tween(Constants.ANIM_TIME_SHORT)
            ) {
                it
            },
            exit = slideOutVertically(
                animationSpec = tween(Constants.ANIM_TIME_SHORT)
            ) {
                it
            }
        ) {
            FlixplorerBottomBar(navController = navController)
        }
    }) { padding: PaddingValues ->
        MainScreenNavGraph(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}
