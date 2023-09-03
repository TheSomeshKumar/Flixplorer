package com.thesomeshkumar.flixplorer.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.thesomeshkumar.flixplorer.data.datasource.local.UserPreferences
import com.thesomeshkumar.flixplorer.data.datasource.remote.RemoteDataSource
import com.thesomeshkumar.flixplorer.data.model.mapToUI
import com.thesomeshkumar.flixplorer.ui.models.CreditUI
import com.thesomeshkumar.flixplorer.ui.models.DetailUI
import com.thesomeshkumar.flixplorer.ui.models.HomeMediaItemUI
import com.thesomeshkumar.flixplorer.ui.models.VideoUI
import com.thesomeshkumar.flixplorer.util.Constants
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
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

    fun getTopMovies(): Flow<PagingData<HomeMediaItemUI>> = remoteDataSource.getTopMovies().map {
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

    fun getMediaDetails(mediaType: String, mediaId: Int): Flow<DetailUI> = when (mediaType) {
        Constants.MEDIA_TYPE_MOVIE -> {
            remoteDataSource.getMovieDetails(mediaId).map { it.mapToUI() }
        }

        Constants.MEDIA_TYPE_TV_SHOW -> {
            remoteDataSource.getTvShowDetails(mediaId)
                .map {
                    it.mapToUI()
                }
        }

        else -> {
            throw IllegalArgumentException(
                "Unknown mediaType! It can only be" +
                    " Constants.MEDIA_TYPE_MOVIE or Constants.MEDIA_TYPE_TV_SHOW"
            )
        }
    }

    fun getMediaCredits(mediaType: String, mediaId: Int): Flow<CreditUI> = when (mediaType) {
        Constants.MEDIA_TYPE_MOVIE -> {
            remoteDataSource.getMovieCredits(mediaId).map { it.mapToUI() }
        }

        Constants.MEDIA_TYPE_TV_SHOW -> {
            remoteDataSource.getTvShowCredits(mediaId).map { it.mapToUI() }
        }

        else -> {
            throw IllegalArgumentException(
                "Unknown mediaType! It can only be" +
                    " Constants.MEDIA_TYPE_MOVIE or Constants.MEDIA_TYPE_TV_SHOW"
            )
        }
    }

    fun getVideos(showType: String, showId: Int): Flow<List<VideoUI>> =
        remoteDataSource.getVideos(showType, showId).map { videoResponse ->
            videoResponse.videos
                .filter {
                    it.site == "YouTube" && (it.type == "Trailer" || it.type == "Teaser")
                }
                .mapToUI()
        }
}
