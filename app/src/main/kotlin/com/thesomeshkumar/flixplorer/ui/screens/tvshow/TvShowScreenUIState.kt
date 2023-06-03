package com.thesomeshkumar.flixplorer.ui.screens.tvshow

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.thesomeshkumar.flixplorer.ui.models.HomeMediaItemUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class TvShowScreenUIState(
    val airingToday: Flow<PagingData<HomeMediaItemUI>>,
    val popular: Flow<PagingData<HomeMediaItemUI>>,
    val topRated: Flow<PagingData<HomeMediaItemUI>>
) {
    companion object {
        val default: TvShowScreenUIState = TvShowScreenUIState(
            airingToday = emptyFlow(),
            popular = emptyFlow(),
            topRated = emptyFlow()
        )
    }
}
