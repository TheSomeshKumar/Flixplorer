package com.thesomeshkumar.flixplorer.data.datasource.remote

import androidx.paging.PagingData
import com.thesomeshkumar.flixplorer.data.model.dto.Movie
import com.thesomeshkumar.flixplorer.data.model.dto.MovieDetailsDTO
import com.thesomeshkumar.flixplorer.data.model.dto.TVShow
import com.thesomeshkumar.flixplorer.data.model.dto.TvShowDetailsDTO
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getUpcomingMovies(): Flow<PagingData<Movie>>
    fun getPopularMovies(): Flow<PagingData<Movie>>
    fun getTopMovies(): Flow<PagingData<Movie>>
    fun getMovieDetails(movieId: Int): Flow<MovieDetailsDTO>

    fun getAiringTodayTvShows(): Flow<PagingData<TVShow>>
    fun getPopularTvShows(): Flow<PagingData<TVShow>>
    fun getTopRatedTvShows(): Flow<PagingData<TVShow>>
    fun getTvShowDetails(tvShowId: Int): Flow<TvShowDetailsDTO>
}
