package com.app.mobile.social

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.mobile.social.databinding.ItemAdBinding
import com.app.mobile.social.databinding.ItemContentBinding
import javax.inject.Inject


class SocialListAdapter @Inject constructor() :
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
            ContentViewHolder(
                ItemContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ADViewHolder(ItemAdBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SocialModel.Social -> 0
            else -> 1
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).idx.toLong()
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
        fun onBind(item : SocialModel.Social) {
            binding.item = item
        }
    }

    inner class ADViewHolder(private val binding: ItemAdBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item : SocialModel.AD) {
            binding.root.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.url)))
        }
    }
}