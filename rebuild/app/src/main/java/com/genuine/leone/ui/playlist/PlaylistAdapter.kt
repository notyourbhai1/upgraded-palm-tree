package com.genuine.leone.ui.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.genuine.leone.data.entities.PlaylistEntity
import com.genuine.leone.databinding.ItemPlaylistBinding

class PlaylistAdapter(
    private val onAddClicked: () -> Unit
) : ListAdapter<PlaylistEntity, PlaylistAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: PlaylistEntity) {
            binding.textPlaylistName.text = playlist.playlistName
            binding.textPlaylistUrl.text = playlist.playlistUrl
        }
    }

    companion object {
        const val DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PlaylistEntity>() {
            override fun areItemsTheSame(oldItem: PlaylistEntity, newItem: PlaylistEntity) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PlaylistEntity, newItem: PlaylistEntity) =
                oldItem == newItem
        }
    }
}
