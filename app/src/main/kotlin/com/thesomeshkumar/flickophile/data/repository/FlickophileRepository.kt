package com.thesomeshkumar.flickophile.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.thesomeshkumar.flickophile.data.datasource.remote.RemoteDataSource
import com.thesomeshkumar.flickophile.data.model.mapToUI
import com.thesomeshkumar.flickophile.ui.models.DetailUI
import com.thesomeshkumar.flickophile.ui.models.HomeMediaItemUI
import com.thesomeshkumar.flickophile.util.Constants
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FlickophileRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getPopularMovies(): Flow<PagingData<HomeMediaItemUI>> =
        remoteDataSource.getPopularMovies().map {
            it.map { movieDto ->
                movieDto.mapToUI()
            }
        }

    fun getPopularTvShows(): Flow<PagingData<HomeMediaItemUI>> =
        remoteDataSource.getPopularTvShows().map {
            it.map { tvShowDto ->
                tvShowDto.mapToUI()
            }
        }

    fun getMediaDetails(mediaType: String, movieId: Int): Flow<DetailUI> = flow {
        when (mediaType) {
            Constants.MEDIA_TYPE_MOVIE -> {
                emit(remoteDataSource.getMovieDetails(movieId).mapToUI())
            }
            Constants.MEDIA_TYPE_TV_SHOW -> {
                emit(remoteDataSource.getTvShowDetails(movieId).mapToUI())
            }
        }
    }
}
