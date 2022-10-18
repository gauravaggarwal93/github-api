package com.navi.sampleapp.ui.pullrequest

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration

import androidx.recyclerview.widget.LinearLayoutManager
import com.navi.sampleapp.base.PagingLoadStateAdapter
import com.navi.sampleapp.base.ViewBindingActivity
import com.navi.sampleapp.databinding.ActivityPullRequestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter


@AndroidEntryPoint
class PullRequestActivity : ViewBindingActivity<ActivityPullRequestBinding>() {

    private val viewModel: PullRequestViewModel by viewModels()

    private val pullRequestAdapter = PullRequestAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        with(pullRequestAdapter) {
            binding.swipeRefreshLayout.setOnRefreshListener { refresh() }
            binding.recyclerList.layoutManager = LinearLayoutManager(this@PullRequestActivity)
            binding.recyclerList.adapter = withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(this),
                footer = PagingLoadStateAdapter(this)
            )
            binding.recyclerList.addItemDecoration(
                DividerItemDecoration(
                    this@PullRequestActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            with(viewModel) {
                launchOnLifecycleScope {
                    this.pullRequestsFlow.collectLatest { submitData(it) }
                }
                launchOnLifecycleScope {
                    loadStateFlow.collectLatest {
                        binding.swipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
                        // Show loading spinner during initial load or refresh
                        binding.listError.isVisible = it.source.refresh is LoadState.Error
                    }
                }
                launchOnLifecycleScope {
                    loadStateFlow.distinctUntilChangedBy { it.refresh }
                        .filter { it.refresh is LoadState.NotLoading }
                        .collect { binding.recyclerList.scrollToPosition(0) }
                }
            }
        }
    }

    private fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        lifecycleScope.launchWhenCreated {
            execute()
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityPullRequestBinding
        get() = ActivityPullRequestBinding::inflate
}
