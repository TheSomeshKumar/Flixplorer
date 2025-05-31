package com.thesomeshkumar.flixplorer.ui.screens.tvshow

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
import com.thesomeshkumar.flixplorer.ui.component.ErrorView
import com.thesomeshkumar.flixplorer.ui.component.LoadingView
import com.thesomeshkumar.flixplorer.ui.component.MediaCarouselM3
import com.thesomeshkumar.flixplorer.ui.component.MediaRow
import com.thesomeshkumar.flixplorer.ui.models.HomeMediaModel
import com.thesomeshkumar.flixplorer.util.getError
import com.thesomeshkumar.flixplorer.util.hasItems
import com.thesomeshkumar.flixplorer.util.isAnyError
import com.thesomeshkumar.flixplorer.util.isAnyRefreshing

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.TvShowScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    viewModel: TvShowViewModel = hiltViewModel(),
    onItemClick: (HomeMediaModel) -> Unit,
) {
    val tvShowUiState by viewModel.tvShowState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    TvShowScreenContent(
        tvShowUiState,
        scrollState,
        animatedVisibilityScope,
        modifier,
        onItemClick
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun SharedTransitionScope.TvShowScreenContent(
    tvShowUiState: TvShowScreenState,
    scrollState: ScrollState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    onItemClick: (HomeMediaModel) -> Unit,
) {
    val airingTodayTvShowLazyItems = tvShowUiState.airingToday.collectAsLazyPagingItems()
    val popularTvShowsLazyItems = tvShowUiState.popular.collectAsLazyPagingItems()
    val topRatedTvShowLazyItems = tvShowUiState.topRated.collectAsLazyPagingItems()

    val tvShowItems = listOf(
        airingTodayTvShowLazyItems,
        popularTvShowsLazyItems,
        topRatedTvShowLazyItems
    )
    val isScreenLoading by derivedStateOf {
        tvShowItems.isAnyRefreshing()
    }
    val hasItems by derivedStateOf {
        tvShowItems.hasItems()
    }

    val isLoadingError by derivedStateOf {
        tvShowItems.isAnyError()
    }

    when {
        hasItems -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                MediaCarouselM3(
                    list = airingTodayTvShowLazyItems,
                    carouselLabel = stringResource(R.string.upcoming_tv_shows),
                    animatedVisibilityScope = animatedVisibilityScope,
                    onItemClicked = { onItemClick(it) }
                )

                MediaRow(
                    title = stringResource(R.string.popular),
                    list = popularTvShowsLazyItems,
                    animatedVisibilityScope,
                    onItemClicked = { onItemClick(it) }
                )
                MediaRow(
                    title = stringResource(R.string.top_rated),
                    list = topRatedTvShowLazyItems,
                    animatedVisibilityScope,
                    onItemClicked = { onItemClick(it) }
                )
            }
        }

        isScreenLoading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

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
