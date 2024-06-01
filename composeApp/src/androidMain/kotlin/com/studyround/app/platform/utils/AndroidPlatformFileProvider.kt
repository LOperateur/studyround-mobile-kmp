package com.studyround.app.platform.utils

import android.content.Context
import okio.Path
import okio.Path.Companion.toOkioPath

class AndroidPlatformFileProvider(private val context: Context) : PlatformFileProvider {
    override fun getImageCachePath(imageCacheName: String): Path {
        return context.cacheDir.resolve(imageCacheName).toOkioPath()
    }

    override fun getDatabasePath(dbFileName: String): String {
        val dbFile = context.getDatabasePath(dbFileName)
        return dbFile.absolutePath
    }

    override fun getHttpCacheDirectory(): String {
        TODO("Not yet implemented")
    }

}
