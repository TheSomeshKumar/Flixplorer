package com.thesomeshkumar.flickophile.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesomeshkumar.flickophile.data.common.RemoteSourceException
import com.thesomeshkumar.flickophile.data.common.Result
import com.thesomeshkumar.flickophile.data.common.asResult
import com.thesomeshkumar.flickophile.data.repository.FlickophileRepository
import com.thesomeshkumar.flickophile.ui.models.DetailUI
import com.thesomeshkumar.flickophile.ui.navigation.MainScreenRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    flickophileRepository: FlickophileRepository
) : ViewModel() {

    private val mediaType = savedStateHandle.get<String>(MainScreenRoutes.ARG_MEDIA_TYPE)!!
    private val mediaId = savedStateHandle.get<String>(MainScreenRoutes.ARG_MEDIA_ID)!!.toInt()

    val uiState: StateFlow<DetailUiState> = flickophileRepository.getMediaDetails(
        mediaType,
        mediaId
    )
        .asResult()
        .map {
            when (it) {
                is Result.Loading -> DetailUiState.Loading
                is Result.Success -> {
                    DetailUiState.Success(it.response)
                }
                is Result.Error -> DetailUiState.Error(it.remoteSourceException)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailUiState.Loading
        )
}

sealed interface DetailUiState {
    data class Success(val details: DetailUI) : DetailUiState
    data class Error(val remoteSourceException: RemoteSourceException) : DetailUiState
    object Loading : DetailUiState
}
