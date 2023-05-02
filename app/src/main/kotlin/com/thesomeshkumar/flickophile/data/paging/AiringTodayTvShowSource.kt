package com.thesomeshkumar.flickophile.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thesomeshkumar.flickophile.data.common.RequestErrorHandler
import com.thesomeshkumar.flickophile.data.datasource.remote.ApiService
import com.thesomeshkumar.flickophile.data.model.TVShowDTO
import javax.inject.Inject

class AiringTodayTvShowSource @Inject constructor(
    private val api: ApiService
) : PagingSource<Int, TVShowDTO.TVShow>() {

    override fun getRefreshKey(state: PagingState<Int, TVShowDTO.TVShow>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVShowDTO.TVShow> {
        return try {
            val nextPage = params.key ?: 1
            val popularTvShows = api.getAiringTodayTvShows(nextPage)
            LoadResult.Page(
                data = popularTvShows.results.shuffled(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (popularTvShows.results.isEmpty()) null else popularTvShows.page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = RequestErrorHandler.getRequestError(e))
        }
    }
}
