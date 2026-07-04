package com.genuine.leone.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val playlistUrl: String,
    val playlistName: String,
    val userAgent: String,
    val sortNum: Int,
    val filePath: String? = null
)
