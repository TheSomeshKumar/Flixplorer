package com.thesomeshkumar.flixplorer

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.VideoFrameDecoder
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlixplorerApp : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader
            .Builder(this)
            .components {
                add(VideoFrameDecoder.Factory())
            }
            .logger(DebugLogger())
            .build()
    }
}
