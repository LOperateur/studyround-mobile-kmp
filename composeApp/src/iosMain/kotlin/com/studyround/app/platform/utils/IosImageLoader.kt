package com.studyround.app.platform.utils

import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import com.seiko.imageloader.intercept.imageMemoryCacheConfig
import com.seiko.imageloader.intercept.painterMemoryCacheConfig

class IosImageLoader(private val provider: PlatformFileProvider) : StudyRoundImageLoader {
    override fun generateImageLoader(): ImageLoader {
        return ImageLoader {
            components {
                setupDefaultComponents()
            }
            interceptor {
                // cache 32MB bitmap
                bitmapMemoryCacheConfig {
                    maxSize(32 * 1024 * 1024) // 32MB
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
