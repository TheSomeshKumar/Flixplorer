package com.thesomeshkumar.flixplorer.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesomeshkumar.flixplorer.data.common.RemoteSourceException
import com.thesomeshkumar.flixplorer.data.common.Result
import com.thesomeshkumar.flixplorer.data.common.asResult
import com.thesomeshkumar.flixplorer.data.repository.FlixplorerRepository
import com.thesomeshkumar.flixplorer.ui.models.DetailUI
import com.thesomeshkumar.flixplorer.ui.navigation.MainScreenRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val flixRepository: FlixplorerRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        val mediaType = savedStateHandle.get<String>(MainScreenRoutes.ARG_MEDIA_TYPE)
        val mediaId = savedStateHandle
            .get<String>(MainScreenRoutes.ARG_MEDIA_ID)
            ?.toInt()

        if (mediaType != null && mediaId != null) {
            getDetails(
                mediaType,
                mediaId
            )
        } else {
            throw IllegalArgumentException("mediaType & mediaId is required!")
        }
    }

    fun getDetails(
        mediaType: String,
        mediaId: Int
    ) {
        viewModelScope.launch {
            flixRepository
                .getDetails(
                    mediaType,
                    mediaId
                )
                .asResult()
                .collect { result ->
                    _uiState.update {
                        when (result) {
                            is Result.Loading -> DetailUiState.Loading
                            is Result.Success -> DetailUiState.Success(result.response)
                            is Result.Error -> DetailUiState.Error(result.remoteSourceException)
                        }
                    }
                }
        }
    }
}

sealed interface DetailUiState {
    data class Success(val details: DetailUI) : DetailUiState
    data class Error(val remoteSourceException: RemoteSourceException) : DetailUiState
    data object Loading : DetailUiState
}
