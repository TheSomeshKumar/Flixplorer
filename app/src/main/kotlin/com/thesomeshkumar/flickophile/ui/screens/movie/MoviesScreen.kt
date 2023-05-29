package com.thesomeshkumar.flickophile.ui.screens.movie

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.thesomeshkumar.flickophile.R
import com.thesomeshkumar.flickophile.data.common.RemoteSourceException
import com.thesomeshkumar.flickophile.ui.models.HomeMediaItemUI
import com.thesomeshkumar.flickophile.ui.widget.ErrorView
import com.thesomeshkumar.flickophile.ui.widget.HomeMediaCarousel
import com.thesomeshkumar.flickophile.ui.widget.HomeMediaRow
import com.thesomeshkumar.flickophile.ui.widget.LoadingView
import com.thesomeshkumar.flickophile.util.getError
import com.thesomeshkumar.flickophile.util.hasItems
import com.thesomeshkumar.flickophile.util.isAnyError
import com.thesomeshkumar.flickophile.util.isAnyRefreshing

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onItemClick: (HomeMediaItemUI) -> Unit
) {
    val movieState by viewModel.moviesState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    MoviesScreenContent(movieState, scrollState, onItemClick)
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun MoviesScreenContent(
    movieState: MovieScreenUIState,
    scrollState: ScrollState,
    onItemClick: (HomeMediaItemUI) -> Unit
) {
    val upcomingMoviesLazyItems = movieState.upcoming.collectAsLazyPagingItems()
    val nowPlayingMoviesLazyItems = movieState.nowPlaying.collectAsLazyPagingItems()
    val popularMoviesLazyItems = movieState.popular.collectAsLazyPagingItems()
    val topRatedMoviesLazyItems = movieState.topRated.collectAsLazyPagingItems()

    val movieItems = listOf(
        upcomingMoviesLazyItems,
        nowPlayingMoviesLazyItems,
        popularMoviesLazyItems,
        topRatedMoviesLazyItems
    )
    val isScreenLoading by
        derivedStateOf {
            movieItems.isAnyRefreshing()
        }
    val hasItems by
        derivedStateOf {
            movieItems.hasItems()
        }

    val isLoadingError by
        derivedStateOf {
            movieItems.isAnyError()
        }

    when {
        hasItems -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                HomeMediaCarousel(
                    list = upcomingMoviesLazyItems,
                    carouselLabel = stringResource(R.string.upcoming_movies),
                    onItemClicked = { onItemClick(it) }
                )

                HomeMediaRow(
                    title = stringResource(R.string.now_playing),
                    list = nowPlayingMoviesLazyItems,
                    onItemClicked = { onItemClick(it) }
                )
                HomeMediaRow(
                    title = stringResource(R.string.popular),
                    list = popularMoviesLazyItems,
                    onItemClicked = { onItemClick(it) }
                )
                HomeMediaRow(
                    title = stringResource(R.string.top_rated),
                    list = topRatedMoviesLazyItems,
                    onItemClicked = { onItemClick(it) }
                )
            }
        }

        isScreenLoading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        isLoadingError.first -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val loadStateError = isLoadingError.second!!
                val error = (loadStateError.error as RemoteSourceException)
                    .getError(LocalContext.current)
                ErrorView(errorText = error, modifier = Modifier.fillMaxSize())
            }
        }
    }
}
