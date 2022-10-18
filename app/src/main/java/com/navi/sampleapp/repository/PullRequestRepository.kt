package com.navi.sampleapp.repository

import androidx.paging.PagingData
import com.navi.sampleapp.model.PullRequest
import kotlinx.coroutines.flow.Flow

interface PullRequestRepository {
    suspend fun getAllPullRequests(): Flow<PagingData<PullRequest>>
}