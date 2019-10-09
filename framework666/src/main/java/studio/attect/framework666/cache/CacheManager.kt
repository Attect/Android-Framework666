package studio.attect.framework666.cache

import android.content.Context
import android.util.Base64
import java.io.File
import java.nio.charset.Charset

/**
 * 缓存管理器
 * 管理和确保一些数据
 *
 * @author Attect
 */
object CacheManager {
    private const val cacheDirName = "framework666_Cache"

    /**
     * 确保框架缓存目录正常存在
     */
    fun ensureCacheDir(context: Context): Boolean {
        val cacheDir = context.cacheDir
        val dir = File(cacheDir.absolutePath + "/" + cacheDirName)
        if (!dir.exists()) {
            return dir.mkdirs()
        } else {
            if (!dir.isDirectory) {
                return if (dir.delete()) {
                    dir.mkdirs()
                } else {
                    false
                }
            }
        }
        return true
    }

    /**
     * 清除框架缓存目录
     */
    fun clearCache(context: Context) {
        val dir = File(context.cacheDir.absolutePath + "/" + cacheDirName)
        if (dir.exists() && dir.isDirectory) dir.deleteRecursively()
    }

    /**
     * 获得给定[tag]的缓存文件名
     */
    fun getCacheFileName(context: Context, tag: String) = context.cacheDir.absolutePath + "/$cacheDirName/Cache_" + Base64.encodeToString(tag.toByteArray(Charset.forName("UTF-8")), Base64.NO_WRAP)
}