package com.thesomeshkumar.flixplorer.data.paging.tvshow

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thesomeshkumar.flixplorer.core.network.error.RequestErrorHandler
import com.thesomeshkumar.flixplorer.data.datasource.remote.ApiService
import com.thesomeshkumar.flixplorer.data.model.dto.TVShowDTO
import javax.inject.Inject

class PopularTvShowSource @Inject constructor(
    private val api: ApiService
) : PagingSource<Int, TVShowDTO.TVShow>() {

    override fun getRefreshKey(state: PagingState<Int, TVShowDTO.TVShow>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVShowDTO.TVShow> {
        return try {
            val nextPage = params.key ?: 1
            val popularTvShows = api.getPopularTvShows(nextPage)
            LoadResult.Page(
                data = popularTvShows.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (popularTvShows.results.isEmpty()) null else popularTvShows.page + 1
            )
        } catch (ignore: Exception) {
            LoadResult.Error(throwable = RequestErrorHandler.getRequestError(ignore))
        }
    }
}
