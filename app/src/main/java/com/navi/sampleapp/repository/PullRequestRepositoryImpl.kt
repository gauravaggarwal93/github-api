package com.navi.sampleapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.navi.sampleapp.model.PullRequest
import com.navi.sampleapp.model.PullRequestService
import com.navi.sampleapp.ui.pullrequest.PullRequestPagingDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 10
class PullRequestRepositoryImpl @Inject constructor(
    private val service: PullRequestService,
) : PullRequestRepository {

    override suspend fun getAllPullRequests(): Flow<PagingData<PullRequest>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = { PullRequestPagingDataSource(service, PAGE_SIZE) }
        ).flow
    }
}
