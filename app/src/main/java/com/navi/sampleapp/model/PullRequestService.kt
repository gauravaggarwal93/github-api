package com.navi.sampleapp.model

import javax.inject.Inject

class PullRequestService @Inject constructor(var api:PullRequestApi) {

    suspend fun getPullRequest(pageNumber: Int, pageSize: Int): List<PullRequest> {
        return api.getPullRequest(pageNumber, pageSize)
    }
}