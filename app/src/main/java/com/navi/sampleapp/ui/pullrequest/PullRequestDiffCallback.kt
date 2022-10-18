package com.navi.sampleapp.ui.pullrequest

import androidx.recyclerview.widget.DiffUtil
import com.navi.sampleapp.model.PullRequest

class PullRequestDiffCallback: DiffUtil.ItemCallback<PullRequest>() {
    override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
            return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
        return oldItem == newItem
    }
}