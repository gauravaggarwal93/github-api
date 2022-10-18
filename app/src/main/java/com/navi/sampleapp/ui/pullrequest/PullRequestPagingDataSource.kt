package com.navi.sampleapp.ui.pullrequest

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.navi.sampleapp.model.PullRequest
import com.navi.sampleapp.model.PullRequestService

private const val STARTING_PAGE_INDEX = 1
class PullRequestPagingDataSource(private val service: PullRequestService, private val pageSize: Int) : PagingSource<Int, PullRequest>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PullRequest> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {
            val pagedResponse = service.getPullRequest(pageNumber, pageSize)
            LoadResult.Page(
                data = pagedResponse,
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = if (pagedResponse.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PullRequest>): Int = 1
}