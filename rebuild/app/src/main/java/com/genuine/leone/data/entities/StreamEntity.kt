package com.genuine.leone.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "streams")
data class StreamEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mediaStreamUrl: String,
    val cookieValue: String? = null,
    val refererValue: String? = null,
    val originValue: String? = null,
    val drmLicense: String? = null,
    val userAgent: String? = null,
    val drmSchema: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
