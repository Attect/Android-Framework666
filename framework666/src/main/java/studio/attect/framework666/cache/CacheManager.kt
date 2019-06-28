package studio.attect.framework666.cache

import android.content.Context
import android.util.Base64
import java.io.File
import java.nio.charset.Charset

object CacheManager {
    val cacheDirName = "framework666_Cache"
    fun ensureCacheDir(context: Context): Boolean {
        val dir = context.getFileStreamPath(cacheDirName)
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

    fun getCacheFileName(tag: String) = "$cacheDirName/Cache_" + Base64.encodeToString(tag.toByteArray(Charset.forName("UTF-8")), Base64.DEFAULT)
}