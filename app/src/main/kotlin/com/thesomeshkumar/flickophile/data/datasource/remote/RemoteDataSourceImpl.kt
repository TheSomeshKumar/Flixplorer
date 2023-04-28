package com.thesomeshkumar.flickophile.data.datasource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thesomeshkumar.flickophile.data.model.MovieDTO
import com.thesomeshkumar.flickophile.data.model.MovieDetailsDTO
import com.thesomeshkumar.flickophile.data.model.TVShowDTO
import com.thesomeshkumar.flickophile.data.model.TvShowDetailsDTO
import com.thesomeshkumar.flickophile.data.paging.AiringTodayTvShowSource
import com.thesomeshkumar.flickophile.data.paging.NowPlayingMoviesSource
import com.thesomeshkumar.flickophile.data.paging.PopularMoviesSource
import com.thesomeshkumar.flickophile.data.paging.PopularTvShowSource
import com.thesomeshkumar.flickophile.data.paging.TopRatedMoviesSource
import com.thesomeshkumar.flickophile.data.paging.TopRatedTvShowSource
import com.thesomeshkumar.flickophile.data.paging.UpcomingMoviesSource
import com.thesomeshkumar.flickophile.util.Constants
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(private val apis: ApiService) : RemoteDataSource {

    override fun getUpcomingMovies(): Flow<PagingData<MovieDTO.Movie>> = Pager(
        config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
        pagingSourceFactory = {
            UpcomingMoviesSource(api = apis)
        }
    ).flow

    override fun getNowPlayingMovies(): Flow<PagingData<MovieDTO.Movie>> = Pager(
        config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
        pagingSourceFactory = {
            NowPlayingMoviesSource(api = apis)
        }
    ).flow

    override fun getPopularMovies(): Flow<PagingData<MovieDTO.Movie>> = Pager(
        config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
        pagingSourceFactory = {
            PopularMoviesSource(api = apis)
        }
    ).flow

    override fun getTopMovies(): Flow<PagingData<MovieDTO.Movie>> = Pager(
        config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
        pagingSourceFactory = {
            TopRatedMoviesSource(api = apis)
        }
    ).flow

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsDTO =
        apis.getMoviesDetails(movieId)

    override fun getAiringTodayTvShows(): Flow<PagingData<TVShowDTO.TVShow>> = Pager(
        config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
        pagingSourceFactory = {
            AiringTodayTvShowSource(api = apis)
        }
    ).flow

    override fun getPopularTvShows(): Flow<PagingData<TVShowDTO.TVShow>> = Pager(
        config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
        pagingSourceFactory = {
            PopularTvShowSource(api = apis)
        }
    ).flow

    override fun getTopRatedTvShows(): Flow<PagingData<TVShowDTO.TVShow>> = Pager(
        config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
        pagingSourceFactory = {
            TopRatedTvShowSource(api = apis)
        }
    ).flow

    override suspend fun getTvShowDetails(tvShowId: Int): TvShowDetailsDTO =
        apis.getTvShowDetails(tvShowId)
}
