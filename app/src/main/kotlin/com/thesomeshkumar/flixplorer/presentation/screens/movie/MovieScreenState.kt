package com.thesomeshkumar.flixplorer.presentation.screens.movie

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.thesomeshkumar.flixplorer.presentation.models.MediaListItemUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class MovieScreenState(
    val upcoming: Flow<PagingData<MediaListItemUI>>,
    val popular: Flow<PagingData<MediaListItemUI>>,
    val topRated: Flow<PagingData<MediaListItemUI>>
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
