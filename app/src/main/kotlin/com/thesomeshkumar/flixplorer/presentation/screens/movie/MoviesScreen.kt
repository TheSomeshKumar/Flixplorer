package com.thesomeshkumar.flixplorer.presentation.screens.movie

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.thesomeshkumar.flixplorer.R
import com.thesomeshkumar.flixplorer.data.common.RemoteSourceException
import com.thesomeshkumar.flixplorer.presentation.common.ErrorView
import com.thesomeshkumar.flixplorer.presentation.common.LoadingView
import com.thesomeshkumar.flixplorer.presentation.common.MediaCarouselM3
import com.thesomeshkumar.flixplorer.presentation.common.MediaRow
import com.thesomeshkumar.flixplorer.presentation.models.MediaListItemUI
import com.thesomeshkumar.flixplorer.util.getError
import com.thesomeshkumar.flixplorer.util.hasItems
import com.thesomeshkumar.flixplorer.util.isAnyError
import com.thesomeshkumar.flixplorer.util.isAnyRefreshing

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MoviesScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = hiltViewModel(),
    onItemClick: (MediaListItemUI) -> Unit
) {
    val movieState by viewModel.moviesState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    MoviesScreenContent(
        movieState,
        scrollState,
        animatedVisibilityScope,
        modifier,
        onItemClick
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun SharedTransitionScope.MoviesScreenContent(
    movieState: MovieScreenState,
    scrollState: ScrollState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    onItemClick: (MediaListItemUI) -> Unit
) {
    val upcomingMoviesLazyItems = movieState.upcoming.collectAsLazyPagingItems()
    val popularMoviesLazyItems = movieState.popular.collectAsLazyPagingItems()
    val topRatedMoviesLazyItems = movieState.topRated.collectAsLazyPagingItems()

    val movieItems = listOf(
        upcomingMoviesLazyItems,
        popularMoviesLazyItems,
        topRatedMoviesLazyItems
    )
    val isScreenLoading by derivedStateOf {
        movieItems.isAnyRefreshing()
    }
    val hasItems by derivedStateOf {
        movieItems.hasItems()
    }

    val isLoadingError by derivedStateOf {
        movieItems.isAnyError()
    }

    when {
        hasItems -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                MediaCarouselM3(
                    list = upcomingMoviesLazyItems,
                    carouselLabel = stringResource(R.string.upcoming_movies),
                    animatedVisibilityScope = animatedVisibilityScope,
                    onItemClicked = { onItemClick(it) }
                )
                MediaRow(
                    title = stringResource(R.string.popular),
                    list = popularMoviesLazyItems,
                    animatedVisibilityScope = animatedVisibilityScope,
                    onItemClicked = { onItemClick(it) }
                )
                MediaRow(
                    title = stringResource(R.string.top_rated),
                    list = topRatedMoviesLazyItems,
                    animatedVisibilityScope = animatedVisibilityScope,
                    onItemClicked = { onItemClick(it) }
                )
            }
        }

        isScreenLoading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        // Todo: Possible UI rewrite to handle each API error in their own UI container
        isLoadingError.first -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val loadStateError = isLoadingError.second!!
                val error =
                    (loadStateError.error as RemoteSourceException).getError(LocalContext.current)
                ErrorView(
                    errorText = error,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
