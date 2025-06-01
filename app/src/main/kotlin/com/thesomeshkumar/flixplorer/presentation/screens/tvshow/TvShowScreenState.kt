package com.thesomeshkumar.flixplorer.presentation.screens.tvshow

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.thesomeshkumar.flixplorer.presentation.models.MediaListItemUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class TvShowScreenState(
    val airingToday: Flow<PagingData<MediaListItemUI>>,
    val popular: Flow<PagingData<MediaListItemUI>>,
    val topRated: Flow<PagingData<MediaListItemUI>>
) {
    companion object {
        val default: TvShowScreenState = TvShowScreenState(
            airingToday = emptyFlow(),
            popular = emptyFlow(),
            topRated = emptyFlow()
        )
    }
}
