package com.thesomeshkumar.flixplorer.data.datasource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thesomeshkumar.flixplorer.data.model.Movie
import com.thesomeshkumar.flixplorer.data.model.MovieDetailsDTO
import com.thesomeshkumar.flixplorer.data.model.TVShowDTO
import com.thesomeshkumar.flixplorer.data.model.TvShowDetailsDTO
import com.thesomeshkumar.flixplorer.data.paging.AiringTodayTvShowSource
import com.thesomeshkumar.flixplorer.data.paging.PopularMoviesSource
import com.thesomeshkumar.flixplorer.data.paging.PopularTvShowSource
import com.thesomeshkumar.flixplorer.data.paging.TopRatedMoviesSource
import com.thesomeshkumar.flixplorer.data.paging.TopRatedTvShowSource
import com.thesomeshkumar.flixplorer.data.paging.UpcomingMoviesSource
import com.thesomeshkumar.flixplorer.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSourceImpl(private val apis: ApiService) : RemoteDataSource {

    override fun getUpcomingMovies(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
            pagingSourceFactory = {
                UpcomingMoviesSource(api = apis)
            }
        ).flow

    override fun getPopularMovies(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
            pagingSourceFactory = {
                PopularMoviesSource(api = apis)
            }
        ).flow

    override fun getTopMovies(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
            pagingSourceFactory = {
                TopRatedMoviesSource(api = apis)
            }
        ).flow

    override fun getMovieDetails(movieId: Int): Flow<MovieDetailsDTO> = flow {
        emit(apis.getMoviesDetails(movieId))
    }

    override fun getAiringTodayTvShows(): Flow<PagingData<TVShowDTO.TVShow>> =
        Pager(
            config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
            pagingSourceFactory = {
                AiringTodayTvShowSource(api = apis)
            }
        ).flow

    override fun getPopularTvShows(): Flow<PagingData<TVShowDTO.TVShow>> =
        Pager(
            config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
            pagingSourceFactory = {
                PopularTvShowSource(api = apis)
            }
        ).flow

    override fun getTopRatedTvShows(): Flow<PagingData<TVShowDTO.TVShow>> =
        Pager(
            config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
            pagingSourceFactory = {
                TopRatedTvShowSource(api = apis)
            }
        ).flow

    override fun getTvShowDetails(tvShowId: Int): Flow<TvShowDetailsDTO> = flow {
        emit(apis.getTvShowDetails(tvShowId))
    }
}
