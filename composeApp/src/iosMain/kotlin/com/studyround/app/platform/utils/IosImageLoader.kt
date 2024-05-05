package com.studyround.app.platform.utils

import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import com.seiko.imageloader.intercept.imageMemoryCacheConfig
import com.seiko.imageloader.intercept.painterMemoryCacheConfig
import okio.Path.Companion.toPath
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

class IosImageLoader : SRImageLoader {
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
                    directory(getCacheDir().toPath().resolve("image_cache"))
                    maxSizeBytes(256L * 1024 * 1024) // 256MB
                }
            }
        }
    }

    private fun getCacheDir(): String {
        return NSSearchPathForDirectoriesInDomains(
            NSCachesDirectory,
            NSUserDomainMask,
            true,
        ).first() as String
    }
}
