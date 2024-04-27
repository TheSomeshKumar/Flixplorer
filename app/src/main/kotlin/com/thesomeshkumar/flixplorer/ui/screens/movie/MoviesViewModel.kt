package com.thesomeshkumar.flixplorer.ui.screens.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.thesomeshkumar.flixplorer.data.repository.FlixplorerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val flixRepository: FlixplorerRepository) :
    ViewModel() {

    val moviesState: StateFlow<MovieScreenState> = flow {
        emit(
            MovieScreenState(
                upcoming = flixRepository
                    .getUpcomingMovies()
                    .cachedIn(viewModelScope),
                popular = flixRepository
                    .getPopularMovies()
                    .cachedIn(viewModelScope),
                topRated = flixRepository
                    .getTopMovies()
                    .cachedIn(viewModelScope)
            )
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        MovieScreenState.default
    )
}
