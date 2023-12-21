package com.thesomeshkumar.flixplorer.ui.screens.movie

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.thesomeshkumar.flixplorer.ui.models.HomeMediaUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class MovieScreenState(
    val upcoming: Flow<PagingData<HomeMediaUI>>,
    val popular: Flow<PagingData<HomeMediaUI>>,
    val topRated: Flow<PagingData<HomeMediaUI>>
) {
    companion object {
        val default: MovieScreenState =
            MovieScreenState(
                upcoming = emptyFlow(),
                popular = emptyFlow(),
                topRated = emptyFlow()
            )
    }
}
