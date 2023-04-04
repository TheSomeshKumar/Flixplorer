@file:OptIn(ExperimentalAnimationApi::class)

package com.thesomeshkumar.flickophile.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.thesomeshkumar.flickophile.R
import com.thesomeshkumar.flickophile.ui.navigation.BottomBarScreen
import com.thesomeshkumar.flickophile.ui.navigation.FlickophileBottomBar
import com.thesomeshkumar.flickophile.ui.navigation.MainScreenNavGraph
import com.thesomeshkumar.flickophile.ui.widget.FlickTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController = rememberAnimatedNavController()) {
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
            FlickTopAppBar(stringResource(id = R.string.app_name))
        }
    }, bottomBar = { FlickophileBottomBar(navController = navController) }) { padding ->
        MainScreenNavGraph(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}
