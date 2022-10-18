package com.navi.sampleapp.ui.pullrequest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.navi.sampleapp.databinding.ItemListBinding
import com.navi.sampleapp.model.PullRequest
import com.navi.sampleapp.util.getProgressDrawable
import com.navi.sampleapp.util.loadImage

class PullRequestAdapter: PagingDataAdapter<PullRequest, PullRequestAdapter.PullListRequestViewHolder>(PullRequestDiffCallback()) {

   override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = PullListRequestViewHolder(
      ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
   )

   override fun onBindViewHolder(holder: PullListRequestViewHolder, position: Int) {
      getItem(position)?.let { holder.bind(it) }
   }

   class PullListRequestViewHolder(binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
      private val title = binding.tvTitle
      private val created_date = binding.tvCreatedDate
      private val closed_date = binding.tvClosedDate
      private val imageView = binding.imgUser
      private val username = binding.tvUserText
      private val progressDrawable = getProgressDrawable(binding.root.context)


      fun bind(pullRequest: PullRequest) {
         title.text = pullRequest.title
         created_date.text = pullRequest.created_date
         closed_date.text = pullRequest.closed_date
         username.text = pullRequest.user.name
         imageView.loadImage(pullRequest.user.avatar_url, progressDrawable)
      }
   }
}