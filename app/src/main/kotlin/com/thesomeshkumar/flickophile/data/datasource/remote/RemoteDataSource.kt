package com.thesomeshkumar.flickophile.data.datasource.remote

import androidx.paging.PagingData
import com.thesomeshkumar.flickophile.data.model.MovieDTO
import com.thesomeshkumar.flickophile.data.model.MovieDetailsDTO
import com.thesomeshkumar.flickophile.data.model.TVShowDTO
import com.thesomeshkumar.flickophile.data.model.TvShowDetailsDTO
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getUpcomingMovies(): Flow<PagingData<MovieDTO.Movie>>
    fun getNowPlayingMovies(): Flow<PagingData<MovieDTO.Movie>>
    fun getPopularMovies(): Flow<PagingData<MovieDTO.Movie>>
    fun getTopMovies(): Flow<PagingData<MovieDTO.Movie>>
    suspend fun getMovieDetails(movieId: Int): MovieDetailsDTO

    fun getAiringTodayTvShows(): Flow<PagingData<TVShowDTO.TVShow>>
    fun getPopularTvShows(): Flow<PagingData<TVShowDTO.TVShow>>
    fun getTopRatedTvShows(): Flow<PagingData<TVShowDTO.TVShow>>
    suspend fun getTvShowDetails(tvShowId: Int): TvShowDetailsDTO
}
