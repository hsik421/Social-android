package com.app.mobile.social.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.mobile.social.data.SocialModel
import com.app.mobile.social.databinding.ItemReplyBinding

class ReplyListAdapter : ListAdapter<SocialModel.Reply,ReplyListAdapter.ReplyViewHolder>(object : DiffUtil.ItemCallback<SocialModel.Reply>(){
    override fun areItemsTheSame(oldItem: SocialModel.Reply, newItem: SocialModel.Reply): Boolean = false
    override fun areContentsTheSame(oldItem: SocialModel.Reply, newItem: SocialModel.Reply): Boolean = false
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder {
        return ReplyViewHolder(ItemReplyBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
        holder.onBind(getItem(holder.adapterPosition))
    }

    inner class ReplyViewHolder(private val binding : ItemReplyBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(item : SocialModel.Reply){
            binding.item = item
        }
    }
}