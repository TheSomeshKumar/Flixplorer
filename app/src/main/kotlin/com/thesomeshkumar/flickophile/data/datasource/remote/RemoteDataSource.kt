package com.thesomeshkumar.flickophile.data.datasource.remote

import androidx.paging.PagingData
import com.thesomeshkumar.flickophile.data.model.MovieDTO
import com.thesomeshkumar.flickophile.data.model.MovieDetailsDTO
import com.thesomeshkumar.flickophile.data.model.TVShowDTO
import com.thesomeshkumar.flickophile.data.model.TvShowDetailsDTO
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getPopularMovies(): Flow<PagingData<MovieDTO.Movie>>
    fun getPopularTvShows(): Flow<PagingData<TVShowDTO.TVShow>>
    suspend fun getMovieDetails(movieId: Int): MovieDetailsDTO
    suspend fun getTvShowDetails(movieId: Int): TvShowDetailsDTO
}
