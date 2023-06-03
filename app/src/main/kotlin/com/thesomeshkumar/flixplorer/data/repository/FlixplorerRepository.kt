package com.thesomeshkumar.flixplorer.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.thesomeshkumar.flixplorer.data.datasource.local.UserPreferences
import com.thesomeshkumar.flixplorer.data.datasource.remote.RemoteDataSource
import com.thesomeshkumar.flixplorer.data.model.mapToUI
import com.thesomeshkumar.flixplorer.ui.models.DetailUI
import com.thesomeshkumar.flixplorer.ui.models.HomeMediaItemUI
import com.thesomeshkumar.flixplorer.util.Constants
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

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

    fun getUpcomingMovies(): Flow<PagingData<HomeMediaItemUI>> =
        remoteDataSource.getUpcomingMovies().map {
            it.map { movieDto ->
                movieDto.mapToUI()
            }
        }

    fun getNowPlayingMovies(): Flow<PagingData<HomeMediaItemUI>> =
        remoteDataSource.getNowPlayingMovies().map {
            it.map { movieDto ->
                movieDto.mapToUI()
            }
        }

    fun getPopularMovies(): Flow<PagingData<HomeMediaItemUI>> =
        remoteDataSource.getPopularMovies().map {
            it.map { movieDto ->
                movieDto.mapToUI()
            }
        }

    fun getTopMovies(): Flow<PagingData<HomeMediaItemUI>> =
        remoteDataSource.getTopMovies().map {
            it.map { movieDto ->
                movieDto.mapToUI()
            }
        }

    fun getAiringTodayTvShows(): Flow<PagingData<HomeMediaItemUI>> =
        remoteDataSource.getAiringTodayTvShows().map {
            it.map { tvShowDto ->
                tvShowDto.mapToUI()
            }
        }

    fun getPopularTvShows(): Flow<PagingData<HomeMediaItemUI>> =
        remoteDataSource.getPopularTvShows().map {
            it.map { tvShowDto ->
                tvShowDto.mapToUI()
            }
        }

    fun getTopRatedTvShows(): Flow<PagingData<HomeMediaItemUI>> =
        remoteDataSource.getTopRatedTvShows().map {
            it.map { tvShowDto ->
                tvShowDto.mapToUI()
            }
        }

    fun getMediaDetails(mediaType: String, mediaId: Int): Flow<DetailUI> = flow {
        when (mediaType) {
            Constants.MEDIA_TYPE_MOVIE -> {
                emit(remoteDataSource.getMovieDetails(mediaId).mapToUI())
            }

            Constants.MEDIA_TYPE_TV_SHOW -> {
                emit(remoteDataSource.getTvShowDetails(mediaId).mapToUI())
            }
        }
    }
}
