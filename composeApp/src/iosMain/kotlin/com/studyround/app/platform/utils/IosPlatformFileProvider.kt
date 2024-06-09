package com.studyround.app.platform.utils

import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

class IosPlatformFileProvider : PlatformFileProvider {
    override fun getImageCachePath(imageCacheName: String): Path {
        return getCacheDir().toPath().resolve(imageCacheName)
    }

    override fun getDatabasePath(dbFileName: String): String {
        return "${documentsDirectory()}/$dbFileName"
    }

    private fun documentsDirectory(): String {
        return NSSearchPathForDirectoriesInDomains(
            directory = NSDocumentDirectory,
            domainMask = NSUserDomainMask,
            true,
        ).first() as String
    }

    private fun getCacheDir(): String {
        return NSSearchPathForDirectoriesInDomains(
            NSCachesDirectory,
            NSUserDomainMask,
            true,
        ).first() as String
    }
}
