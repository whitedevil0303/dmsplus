package com.cinematichororuniverse.dmsplus.ui.components

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util

@Composable
fun HLSVideoPlayer(
    hlsUrl: String,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = true,
    onPlayerReady: (ExoPlayer) -> Unit = {},
    onPlaybackStateChanged: (Int) -> Unit = {}
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val httpDataSourceFactory = DefaultHttpDataSource.Factory()
                    .setUserAgent(Util.getUserAgent(context, "DMS+"))
                    .setConnectTimeoutMs(30000)
                    .setReadTimeoutMs(30000)

                val hlsMediaSource = HlsMediaSource.Factory(httpDataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(hlsUrl))

                setMediaSource(hlsMediaSource)
                playWhenReady = autoPlay
                prepare()

                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        super.onPlaybackStateChanged(playbackState)
                        isLoading = playbackState == Player.STATE_BUFFERING
                        hasError = playbackState == Player.STATE_IDLE
                        onPlaybackStateChanged(playbackState)
                    }

                    override fun onPlayerError(error: com.google.android.exoplayer2.PlaybackException) {
                        super.onPlayerError(error)
                        hasError = true
                        isLoading = false
                    }
                })

                onPlayerReady(this)
            }
    }

    DisposableEffect(
        AndroidView(
            modifier = modifier,
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                    setShowBuffering(StyledPlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                    setControllerVisibilityListener(
                        StyledPlayerView.ControllerVisibilityListener { visibility ->
                            // Handle controller visibility if needed
                        }
                    )
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            }
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Loading overlay
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        }
    }

    // Error overlay
    if (hasError) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                androidx.compose.material3.Text(
                    text = "Failed to load video",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                androidx.compose.material3.Text(
                    text = "Check your internet connection",
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun rememberExoPlayer(hlsUrl: String): ExoPlayer {
    val context = LocalContext.current
    return remember(hlsUrl) {
        ExoPlayer.Builder(context).build().apply {
            val httpDataSourceFactory = DefaultHttpDataSource.Factory()
                .setUserAgent(Util.getUserAgent(context, "DMS+"))

            val hlsMediaSource = HlsMediaSource.Factory(httpDataSourceFactory)
                .createMediaSource(MediaItem.fromUri(hlsUrl))

            setMediaSource(hlsMediaSource)
            prepare()
        }
    }
}