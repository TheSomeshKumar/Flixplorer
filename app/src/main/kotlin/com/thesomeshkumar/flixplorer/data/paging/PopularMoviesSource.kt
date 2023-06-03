package com.thesomeshkumar.flixplorer.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thesomeshkumar.flixplorer.data.common.RequestErrorHandler
import com.thesomeshkumar.flixplorer.data.datasource.remote.ApiService
import com.thesomeshkumar.flixplorer.data.model.MovieDTO
import javax.inject.Inject

class PopularMoviesSource @Inject constructor(
    private val api: ApiService
) : PagingSource<Int, MovieDTO.Movie>() {

    override fun getRefreshKey(state: PagingState<Int, MovieDTO.Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDTO.Movie> {
        return try {
            val nextPage = params.key ?: 1
            val popularMovies = api.getPopularMovies(nextPage)
            LoadResult.Page(
                data = popularMovies.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (popularMovies.results.isEmpty()) null else popularMovies.page + 1
            )
        } catch (e: java.lang.Exception) {
            LoadResult.Error(throwable = RequestErrorHandler.getRequestError(e))
        }
    }
}
