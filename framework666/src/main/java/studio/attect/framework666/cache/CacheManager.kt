package studio.attect.framework666.cache

import android.content.Context
import android.util.Base64
import java.io.File
import java.nio.charset.Charset

object CacheManager {
    private const val cacheDirName = "framework666_Cache"
    fun ensureCacheDir(context: Context): Boolean {
        val cacheDir = context.cacheDir
        val dir = File(cacheDir.absolutePath + "/" + cacheDirName)
        if (!dir.exists()) {
            return dir.mkdirs()
        } else {
            if (!dir.isDirectory) {
                if (dir.delete()) {
                    return dir.mkdirs()
                } else {
                    return false
                }
            }
        }
        return true
    }

    fun getCacheFileName(context: Context, tag: String) = context.cacheDir.absolutePath + "/$cacheDirName/Cache_" + Base64.encodeToString(tag.toByteArray(Charset.forName("UTF-8")), Base64.NO_WRAP)
}