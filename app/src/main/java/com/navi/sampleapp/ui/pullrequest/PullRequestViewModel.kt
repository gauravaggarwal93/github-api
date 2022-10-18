package com.navi.sampleapp.ui.pullrequest

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.navi.sampleapp.base.BaseViewModel
import com.navi.sampleapp.model.PullRequest
import com.navi.sampleapp.repository.PullRequestRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PullRequestViewModel @Inject constructor(var pullRequestRepository: PullRequestRepositoryImpl) :
    BaseViewModel() {

    private lateinit var _pullRequestsFlow: Flow<PagingData<PullRequest>>
    val pullRequestsFlow: Flow<PagingData<PullRequest>>
        get() = _pullRequestsFlow

    init {
        getAllPullRequests()
    }

    private fun getAllPullRequests() = launchPagingAsync({
        pullRequestRepository.getAllPullRequests()
    }, { flow ->
        _pullRequestsFlow = flow.cachedIn(viewModelScope)
    })

}

