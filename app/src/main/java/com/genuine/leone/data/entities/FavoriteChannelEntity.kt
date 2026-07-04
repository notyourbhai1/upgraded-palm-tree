package com.genuine.leone.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_channels")
data class FavoriteChannelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val favoriteChannel: String,
    val playlistUrl: String
)
