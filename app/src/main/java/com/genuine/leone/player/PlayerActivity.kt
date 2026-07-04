package com.genuine.leone.player

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.FrameworkMediaDrm
import androidx.media3.exoplayer.drm.HttpMediaDrmCallback
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import com.genuine.leone.databinding.ActivityPlayerBinding
import com.genuine.leone.model.Stream

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stream = intent.getParcelableStreamExtra()
        if (stream != null) {
            initializePlayer(stream)
        }
    }

    private fun buildHttpDataSourceFactory(stream: Stream): DefaultHttpDataSource.Factory {
        val headers = mutableMapOf<String, String>()
        stream.refererValue?.let { headers["Referer"] = it }
        stream.originValue?.let { headers["Origin"] = it }
        stream.cookieValue?.let { headers["Cookie"] = it }

        return DefaultHttpDataSource.Factory()
            .setUserAgent(stream.userAgent ?: DEFAULT_USER_AGENT)
            .setDefaultRequestProperties(headers)
            .setAllowCrossProtocolRedirects(true)
    }

    private fun buildDrmSessionManager(stream: Stream): DefaultDrmSessionManager? {
        val licenseUrl = stream.drmLicense ?: return null
        if (Build.VERSION.SDK_INT < 18) return null

        val uuid = when (stream.drmSchema?.lowercase()) {
            "clearkey" -> C.CLEARKEY_UUID
            "playready" -> C.PLAYREADY_UUID
            else -> C.WIDEVINE_UUID
        }

        val drmCallback = HttpMediaDrmCallback(licenseUrl, DefaultHttpDataSource.Factory())
        return DefaultDrmSessionManager.Builder()
            .setUuidAndExoMediaDrmProvider(uuid, FrameworkMediaDrm.DEFAULT_PROVIDER)
            .build(drmCallback)
    }

    private fun initializePlayer(stream: Stream) {
        val dataSourceFactory = buildHttpDataSourceFactory(stream)
        val mediaSourceFactory = DefaultMediaSourceFactory(dataSourceFactory)
        val drmSessionManager = buildDrmSessionManager(stream)
        if (drmSessionManager != null) {
            mediaSourceFactory.setDrmSessionManagerProvider { drmSessionManager }
        }

        val exoPlayer = ExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()

        exoPlayer.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                handlePlaybackFailure(stream)
            }
        })

        val mediaItem = MediaItem.fromUri(stream.mediaStreamUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true

        binding.playerView.player = exoPlayer
        player = exoPlayer
    }

    private fun handlePlaybackFailure(stream: Stream) {
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    companion object {
        const val EXTRA_STREAM = "extra_stream"
        const val DEFAULT_USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
    }
}

private fun android.content.Intent.getParcelableStreamExtra(): Stream? {
    return getParcelableExtra(PlayerActivity.EXTRA_STREAM)
}
