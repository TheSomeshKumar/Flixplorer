package com.thesomeshkumar.flickophile.ui.screens.tvshow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.thesomeshkumar.flickophile.data.common.RemoteSourceException
import com.thesomeshkumar.flickophile.ui.models.HomeMediaItemUI
import com.thesomeshkumar.flickophile.ui.widget.ErrorView
import com.thesomeshkumar.flickophile.ui.widget.LoadingView
import com.thesomeshkumar.flickophile.ui.widget.MediaGridList
import com.thesomeshkumar.flickophile.util.Constants
import com.thesomeshkumar.flickophile.util.getError

@Composable
fun TvShowScreen(
    viewModel: TvShowViewModel = hiltViewModel(),
    onItemClick: (HomeMediaItemUI) -> Unit
) {
    val tvShowUiState: LazyPagingItems<HomeMediaItemUI> =
        viewModel.uiState.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        MediaGridList(
            list = tvShowUiState,
            gridCount = Constants.HOME_GRID_COUNT
        ) {
            onItemClick(it)
        }

        when (tvShowUiState.loadState.refresh) {
            is LoadState.Loading -> {
                LoadingView(modifier = Modifier.fillMaxSize())
            }
            is LoadState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    val loadStateError = tvShowUiState.loadState.refresh as LoadState.Error
                    val error = (loadStateError.error as RemoteSourceException)
                        .getError(LocalContext.current)
                    ErrorView(errorText = error, modifier = Modifier.fillMaxSize())
                }
            }
            is LoadState.NotLoading -> {}
        }
    }
}
