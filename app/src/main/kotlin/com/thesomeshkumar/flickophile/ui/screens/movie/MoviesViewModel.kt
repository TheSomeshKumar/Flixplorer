package com.thesomeshkumar.flickophile.ui.screens.movie

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
class MoviesViewModel @Inject constructor(private val flickRepository: FlickophileRepository) :
    ViewModel() {

    val moviesState: StateFlow<MovieScreenUIState> = flow {
        emit(
            MovieScreenUIState(
                upcoming = flickRepository.getUpcomingMovies().cachedIn(viewModelScope),
                nowPlaying = flickRepository.getNowPlayingMovies().cachedIn(viewModelScope),
                popular = flickRepository.getPopularMovies().cachedIn(viewModelScope),
                topRated = flickRepository.getTopMovies().cachedIn(viewModelScope)
            )
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MovieScreenUIState.default)
}
