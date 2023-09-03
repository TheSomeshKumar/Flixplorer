package com.thesomeshkumar.flixplorer.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesomeshkumar.flixplorer.data.common.RemoteSourceException
import com.thesomeshkumar.flixplorer.data.common.Result
import com.thesomeshkumar.flixplorer.data.common.asResult
import com.thesomeshkumar.flixplorer.data.repository.FlixplorerRepository
import com.thesomeshkumar.flixplorer.ui.models.CreditUI
import com.thesomeshkumar.flixplorer.ui.models.DetailUI
import com.thesomeshkumar.flixplorer.ui.models.VideoUI
import com.thesomeshkumar.flixplorer.ui.navigation.MainScreenRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val flixRepository: FlixplorerRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<ConsolidatedDetailUiState> =
        MutableStateFlow(ConsolidatedDetailUiState.Loading)
    val uiState: StateFlow<ConsolidatedDetailUiState> = _uiState.asStateFlow()

    init {
        val mediaType = savedStateHandle.get<String>(MainScreenRoutes.ARG_MEDIA_TYPE)
        val mediaId = savedStateHandle.get<String>(MainScreenRoutes.ARG_MEDIA_ID)?.toInt()

        if (mediaType != null && mediaId != null) {
            getDetails(mediaType, mediaId)
        } else {
            throw IllegalArgumentException("mediaType & mediaId is required!")
        }
    }

    private fun getDetails(mediaType: String, mediaId: Int) {
        viewModelScope.launch {
            combine(
                flixRepository.getMediaDetails(mediaType, mediaId),
                flixRepository.getMediaCredits(mediaType, mediaId),
                flixRepository.getVideos(mediaType, mediaId)
            ) { details: DetailUI, credits: CreditUI, videos: List<VideoUI> ->
                Triple(details, credits, videos)
            }
                .asResult()
                .collect { result ->
                    _uiState.update {
                        when (result) {
                            is Result.Loading -> { ConsolidatedDetailUiState.Loading }

                            is Result.Success -> {
                                val (detail, credit, videos) = result.response
                                ConsolidatedDetailUiState.Success(detail, credit, videos)
                            }

                            is Result.Error -> {
                                ConsolidatedDetailUiState.Error(result.remoteSourceException)
                            }
                        }
                    }
                }
        }
    }
}

sealed interface ConsolidatedDetailUiState {
    data class Success(val details: DetailUI, val credit: CreditUI, val videos: List<VideoUI>) :
        ConsolidatedDetailUiState

    data class Error(val remoteSourceException: RemoteSourceException) : ConsolidatedDetailUiState
    data object Loading : ConsolidatedDetailUiState
}
