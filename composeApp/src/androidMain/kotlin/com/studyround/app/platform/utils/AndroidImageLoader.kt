package com.studyround.app.platform.utils

import android.content.Context
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import com.seiko.imageloader.intercept.imageMemoryCacheConfig
import com.seiko.imageloader.intercept.painterMemoryCacheConfig
import com.seiko.imageloader.option.androidContext

class AndroidImageLoader(
    private val applicationContext: Context,
    private val provider: PlatformFileProvider,
) : StudyRoundImageLoader {
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
                    directory(provider.getImageCachePath("image_cache"))
                    maxSizeBytes(256L * 1024 * 1024) // 256MB
                }
            }
        }
    }
}
