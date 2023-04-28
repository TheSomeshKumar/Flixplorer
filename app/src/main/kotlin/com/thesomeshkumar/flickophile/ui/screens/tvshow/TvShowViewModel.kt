package com.thesomeshkumar.flickophile.ui.screens.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.thesomeshkumar.flickophile.data.repository.FlickophileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class TvShowViewModel @Inject constructor(flickRepository: FlickophileRepository) :
    ViewModel() {

    val tvShowState: StateFlow<TvShowScreenUIState> = flow {
        emit(
            TvShowScreenUIState(
                airingToday = flickRepository.getAiringTodayTvShows().cachedIn(viewModelScope),
                popular = flickRepository.getPopularTvShows().cachedIn(viewModelScope),
                topRated = flickRepository.getTopRatedTvShows().cachedIn(viewModelScope)
            )
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), TvShowScreenUIState.default)
}
