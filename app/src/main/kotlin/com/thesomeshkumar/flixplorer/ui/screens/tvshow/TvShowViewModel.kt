package com.thesomeshkumar.flixplorer.ui.screens.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.thesomeshkumar.flixplorer.data.repository.FlixplorerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class TvShowViewModel @Inject constructor(flixRepository: FlixplorerRepository) :
    ViewModel() {

    val tvShowState: StateFlow<TvShowScreenUIState> = flow {
        emit(
            TvShowScreenUIState(
                airingToday = flixRepository.getAiringTodayTvShows().cachedIn(viewModelScope),
                popular = flixRepository.getPopularTvShows().cachedIn(viewModelScope),
                topRated = flixRepository.getTopRatedTvShows().cachedIn(viewModelScope)
            )
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), TvShowScreenUIState.default)
}
