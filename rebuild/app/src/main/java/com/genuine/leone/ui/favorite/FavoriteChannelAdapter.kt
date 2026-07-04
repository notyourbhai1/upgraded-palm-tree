package com.genuine.leone.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.genuine.leone.data.entities.FavoriteChannelEntity
import com.genuine.leone.databinding.ItemFavoriteChannelBinding

class FavoriteChannelAdapter(
    private val onRemoveClicked: (FavoriteChannelEntity) -> Unit
) : ListAdapter<FavoriteChannelEntity, FavoriteChannelAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onRemoveClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemFavoriteChannelBinding,
        private val onRemoveClicked: (FavoriteChannelEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favorite: FavoriteChannelEntity) {
            binding.textFavoriteName.text = favorite.favoriteChannel
            binding.buttonRemoveFavorite.setOnClickListener { onRemoveClicked(favorite) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteChannelEntity>() {
            override fun areItemsTheSame(oldItem: FavoriteChannelEntity, newItem: FavoriteChannelEntity) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FavoriteChannelEntity, newItem: FavoriteChannelEntity) =
                oldItem == newItem
        }
    }
}
