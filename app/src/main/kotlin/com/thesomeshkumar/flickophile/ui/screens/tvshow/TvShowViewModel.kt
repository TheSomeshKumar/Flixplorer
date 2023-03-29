package com.thesomeshkumar.flickophile.ui.screens.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thesomeshkumar.flickophile.data.repository.FlickophileRepository
import com.thesomeshkumar.flickophile.ui.models.HomeMediaItemUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class TvShowViewModel @Inject constructor(flickRepository: FlickophileRepository) :
    ViewModel() {

    val uiState: Flow<PagingData<HomeMediaItemUI>> =
        flickRepository
            .getPopularTvShows()
            .cachedIn(viewModelScope)
}
