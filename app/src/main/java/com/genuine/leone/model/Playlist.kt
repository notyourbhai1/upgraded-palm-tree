package com.genuine.leone.model

data class Playlist(
    val playlistName: String,
    val playlistUrl: String,
    val userAgent: String,
    val sortNum: Int,
    val filePath: String? = null
)
