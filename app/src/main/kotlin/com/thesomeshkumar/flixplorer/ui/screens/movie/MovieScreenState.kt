package com.thesomeshkumar.flixplorer.ui.screens.movie

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.thesomeshkumar.flixplorer.ui.models.HomeMediaModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class MovieScreenState(
    val upcoming: Flow<PagingData<HomeMediaModel>>,
    val popular: Flow<PagingData<HomeMediaModel>>,
    val topRated: Flow<PagingData<HomeMediaModel>>
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
