package com.genuine.leone.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stream(
    val mediaStreamUrl: String,
    val cookieValue: String? = null,
    val refererValue: String? = null,
    val originValue: String? = null,
    val drmLicense: String? = null,
    val userAgent: String? = null,
    val drmSchema: String? = null
) : Parcelable
