package com.genuine.leone.ui.channel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.genuine.leone.databinding.ItemChannelBinding
import com.genuine.leone.model.ContentBean

class ChannelAdapter(
    private val onChannelClicked: (ContentBean) -> Unit
) : ListAdapter<ContentBean, ChannelAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onChannelClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemChannelBinding,
        private val onChannelClicked: (ContentBean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(content: ContentBean) {
            binding.textChannelName.text = content.name
            Glide.with(binding.imageChannelLogo).load(content.logoUrl).into(binding.imageChannelLogo)
            binding.root.setOnClickListener { onChannelClicked(content) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ContentBean>() {
            override fun areItemsTheSame(oldItem: ContentBean, newItem: ContentBean) =
                oldItem.streamUrl == newItem.streamUrl

            override fun areContentsTheSame(oldItem: ContentBean, newItem: ContentBean) =
                oldItem == newItem
        }
    }
}
