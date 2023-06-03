package com.thesomeshkumar.flixplorer.ui.screens.movie

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.thesomeshkumar.flixplorer.ui.models.HomeMediaItemUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class MovieScreenUIState(
    val upcoming: Flow<PagingData<HomeMediaItemUI>>,
    val nowPlaying: Flow<PagingData<HomeMediaItemUI>>,
    val popular: Flow<PagingData<HomeMediaItemUI>>,
    val topRated: Flow<PagingData<HomeMediaItemUI>>
) {
    companion object {
        val default: MovieScreenUIState = MovieScreenUIState(
            upcoming = emptyFlow(),
            nowPlaying = emptyFlow(),
            popular = emptyFlow(),
            topRated = emptyFlow()
        )
    }
}
