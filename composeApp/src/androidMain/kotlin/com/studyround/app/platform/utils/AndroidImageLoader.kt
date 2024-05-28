package com.studyround.app.platform.utils

import android.content.Context
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import com.seiko.imageloader.intercept.imageMemoryCacheConfig
import com.seiko.imageloader.intercept.painterMemoryCacheConfig
import com.seiko.imageloader.option.androidContext
import okio.Path.Companion.toOkioPath

class AndroidImageLoader(private val applicationContext: Context) : StudyRoundImageLoader {
    override fun generateImageLoader(): ImageLoader {
        return ImageLoader {
            options {
                androidContext(applicationContext)
            }
            components {
                setupDefaultComponents()
            }
            interceptor {
                // cache 25% memory bitmap
                bitmapMemoryCacheConfig {
                    maxSizePercent(applicationContext, 0.25)
                }
                // cache 100 image
                imageMemoryCacheConfig {
                    maxSize(100)
                }
                // cache 100 painter
                painterMemoryCacheConfig {
                    maxSize(100)
                }
                diskCacheConfig {
                    directory(applicationContext.cacheDir.resolve("image_cache").toOkioPath())
                    maxSizeBytes(256L * 1024 * 1024) // 256MB
                }
            }
        }
    }
}
