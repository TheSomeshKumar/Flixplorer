package com.thesomeshkumar.flixplorer.ui.screens.tvshow

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.thesomeshkumar.flixplorer.ui.models.HomeMediaUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class TvShowScreenState(
    val airingToday: Flow<PagingData<HomeMediaUI>>,
    val popular: Flow<PagingData<HomeMediaUI>>,
    val topRated: Flow<PagingData<HomeMediaUI>>
) {
    companion object {
        val default: TvShowScreenState = TvShowScreenState(
            airingToday = emptyFlow(),
            popular = emptyFlow(),
            topRated = emptyFlow()
        )
    }
}
