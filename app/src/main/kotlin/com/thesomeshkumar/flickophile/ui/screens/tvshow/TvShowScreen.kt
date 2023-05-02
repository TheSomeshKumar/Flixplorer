package com.thesomeshkumar.flickophile.ui.screens.tvshow

import android.annotation.SuppressLint
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
fun TvShowScreen(
    viewModel: TvShowViewModel = hiltViewModel(),
    onItemClick: (HomeMediaItemUI) -> Unit
) {
    val tvShowUiState by viewModel.tvShowState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    TvShowScreenContent(tvShowUiState, scrollState, onItemClick)
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TvShowScreenContent(
    tvShowUiState: TvShowScreenUIState,
    scrollState: ScrollState,
    onItemClick: (HomeMediaItemUI) -> Unit
) {
    val airingTodayTvShowLazyItems = tvShowUiState.airingToday.collectAsLazyPagingItems()
    val popularTvShowsLazyItems = tvShowUiState.popular.collectAsLazyPagingItems()
    val topRatedTvShowLazyItems = tvShowUiState.topRated.collectAsLazyPagingItems()

    val tvShowItems = listOf(
        airingTodayTvShowLazyItems,
        popularTvShowsLazyItems,
        topRatedTvShowLazyItems
    )
    val isScreenLoading by
        derivedStateOf {
            tvShowItems.isAnyRefreshing()
        }
    val hasItems by
        derivedStateOf {
            tvShowItems.hasItems()
        }

    val isLoadingError by
        derivedStateOf {
            tvShowItems.isAnyError()
        }

    when {
        hasItems -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                HomeMediaCarousel(
                    airingTodayTvShowLazyItems,
                    onItemClicked = { onItemClick(it) }
                )

                HomeMediaRow(
                    title = stringResource(R.string.popular),
                    list = popularTvShowsLazyItems,
                    onItemClicked = { onItemClick(it) }
                )
                HomeMediaRow(
                    title = stringResource(R.string.top_rated),
                    list = topRatedTvShowLazyItems,
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
