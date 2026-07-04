package com.genuine.leone.model

data class ContentBean(
    val name: String,
    val logoUrl: String? = null,
    val groupTitle: String? = null,
    val streamUrl: String,
    val userAgent: String? = null,
    val referer: String? = null,
    val origin: String? = null,
    val cookie: String? = null,
    val drmLicense: String? = null,
    val drmSchema: String? = null,
    val channelId: String? = null,
    val tvgId: String? = null,
    val isFavorite: Boolean = false
)
