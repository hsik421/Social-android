package com.app.mobile.social.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.mobile.social.data.SocialModel
import com.app.mobile.social.databinding.ItemAdBinding
import com.app.mobile.social.databinding.ItemContentBinding


class SocialListAdapter constructor(
    private val viewModel : SocialViewModel
) :
    ListAdapter<SocialModel.Resource, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<SocialModel.Resource>() {
        override fun areItemsTheSame(
            oldItem: SocialModel.Resource,
            newItem: SocialModel.Resource
        ): Boolean = oldItem.idx == newItem.idx

        override fun areContentsTheSame(
            oldItem: SocialModel.Resource,
            newItem: SocialModel.Resource
        ): Boolean = false
    }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val binding = ItemContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
            ContentViewHolder(binding)
        } else {
            ADViewHolder(ItemAdBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }
    /***
     * Content List 댓글 View 타입 or 아래와같은 로직
     */
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SocialModel.Social -> 0
            else -> 1
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).idx
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContentViewHolder -> {
                holder.onBind(getItem(position) as SocialModel.Social)
            }
            is ADViewHolder -> {
                holder.onBind(getItem(position) as SocialModel.AD)
            }
        }

    }

    inner class ContentViewHolder(private val binding: ItemContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: SocialModel.Social) {
            binding.item = item
            binding.socialVm = viewModel
            setupView(item)
        }
        /***
         * Content List 에서 댓글을 다보여줘야 하는 것 인가
         */
        private fun setupView(item: SocialModel.Social){
            binding.commentRecycler.apply {
                layoutManager = LinearLayoutManager(binding.root.context,LinearLayoutManager.VERTICAL,false)
                isNestedScrollingEnabled = false
                adapter = ReplyListAdapter().also {
                    it.submitList(item.replies.toMutableList())
                }
            }
        }
    }

    inner class ADViewHolder(private val binding: ItemAdBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: SocialModel.AD) {
            binding.item = item
            binding.socialVm = viewModel
        }
    }

}