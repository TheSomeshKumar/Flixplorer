package com.thesomeshkumar.flickophile.data.datasource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thesomeshkumar.flickophile.data.model.MovieDTO
import com.thesomeshkumar.flickophile.data.model.MovieDetailsDTO
import com.thesomeshkumar.flickophile.data.model.TVShowDTO
import com.thesomeshkumar.flickophile.data.model.TvShowDetailsDTO
import com.thesomeshkumar.flickophile.data.paging.PopularMoviesSource
import com.thesomeshkumar.flickophile.data.paging.PopularTvShowSource
import com.thesomeshkumar.flickophile.util.Constants
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(private val apis: ApiService) : RemoteDataSource {

    override fun getPopularMovies(): Flow<PagingData<MovieDTO.Movie>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
            pagingSourceFactory = {
                PopularMoviesSource(api = apis)
            }
        ).flow
    }

    override fun getPopularTvShows(): Flow<PagingData<TVShowDTO.TVShow>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEM_LOAD_PER_PAGE),
            pagingSourceFactory = {
                PopularTvShowSource(api = apis)
            }
        ).flow
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsDTO {
        return apis.getMoviesDetails(movieId)
//        return handleResponse {
//            apis.getMoviesDetails(movieId)
//        }
    }

    override suspend fun getTvShowDetails(showId: Int): TvShowDetailsDTO {
        return apis.getTvShowDetails(showId)
//        return handleResponse {
//            apis.getTvShowDetails(showId)
//        }
    }
}
