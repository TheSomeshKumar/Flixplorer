package com.thesomeshkumar.flixplorer.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.thesomeshkumar.flixplorer.data.datasource.local.UserPreferences
import com.thesomeshkumar.flixplorer.data.datasource.remote.RemoteDataSource
import com.thesomeshkumar.flixplorer.data.model.mapToUI
import com.thesomeshkumar.flixplorer.presentation.models.DetailUI
import com.thesomeshkumar.flixplorer.presentation.models.MediaListItemUI
import com.thesomeshkumar.flixplorer.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FlixplorerRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val userPreferences: UserPreferences
) {
    fun readUseMaterial3(): Flow<Boolean> {
        return userPreferences.useMaterial3
    }

    fun readUseDarkMode(): Flow<String> {
        return userPreferences.useDarkMode
    }

    suspend fun updateUseMaterial3(useMaterial3: Boolean) {
        userPreferences.updateUseMaterial3(useMaterial3)
    }

    suspend fun updateUseDarkMode(useDarkMode: String) {
        userPreferences.updateUseDarkMode(useDarkMode)
    }

    fun getUpcomingMovies(): Flow<PagingData<MediaListItemUI>> = remoteDataSource
        .getUpcomingMovies()
        .map {
            it.map { movieDto ->
                movieDto.mapToUI()
            }
        }

    fun getPopularMovies(): Flow<PagingData<MediaListItemUI>> = remoteDataSource
        .getPopularMovies()
        .map {
            it.map { movieDto ->
                movieDto.mapToUI()
            }
        }

    fun getTopMovies(): Flow<PagingData<MediaListItemUI>> = remoteDataSource
        .getTopMovies()
        .map {
            it.map { movieDto ->
                movieDto.mapToUI()
            }
        }

    fun getAiringTodayTvShows(): Flow<PagingData<MediaListItemUI>> = remoteDataSource
        .getAiringTodayTvShows()
        .map {
            it.map { tvShowDto ->
                tvShowDto.mapToUI()
            }
        }

    fun getPopularTvShows(): Flow<PagingData<MediaListItemUI>> = remoteDataSource
        .getPopularTvShows()
        .map {
            it.map { tvShowDto ->
                tvShowDto.mapToUI()
            }
        }

    fun getTopRatedTvShows(): Flow<PagingData<MediaListItemUI>> = remoteDataSource
        .getTopRatedTvShows()
        .map {
            it.map { tvShowDto ->
                tvShowDto.mapToUI()
            }
        }

    fun getDetails(
        mediaType: String,
        mediaId: Int
    ): Flow<DetailUI> = when (mediaType) {
        Constants.MEDIA_TYPE_MOVIE -> {
            remoteDataSource
                .getMovieDetails(mediaId)
                .map { it.mapToUI() }
        }

        Constants.MEDIA_TYPE_TV_SHOW -> {
            remoteDataSource
                .getTvShowDetails(mediaId)
                .map {
                    it.mapToUI()
                }
        }

        else -> {
            throw IllegalArgumentException(
                "Unknown mediaType! It can only be" + " Constants.MEDIA_TYPE_MOVIE or Constants.MEDIA_TYPE_TV_SHOW"

            )
        }
    }
}
