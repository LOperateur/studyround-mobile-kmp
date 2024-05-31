package com.studyround.app.platform.utils

import okio.Path

interface PlatformFileProvider {
    fun getImageCachePath(imageCacheName: String): Path

    fun getDatabasePath(dbFileName: String): String

    fun getHttpCacheDirectory(): String
}
