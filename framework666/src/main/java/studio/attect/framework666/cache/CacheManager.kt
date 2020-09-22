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
    /**
     * 主要框架缓存文件夹名称
     * 此处缓存将长时间保留
     */
    private const val cacheDirName = "framework666_Cache"

    /**
     * 复制ContentResolver的FileDescriptor内容的缓存
     * 将在操作完成或者每次App启动时清空
     */
    private const val contentResolverCacheDirName = "framework666_ContentResolverCache"

    /**
     * 下一个ContentResolver复制文件的索引号
     * 用于生成文件名
     */
    private var contentResolverCacheFileIndex = 0

    /**
     * 确保框架缓存目录正常存在
     */
    fun ensureCacheDir(context: Context): Boolean {
        return ensureDir(context.cacheDir, cacheDirName)
    }

    /**
     * 确保ContentResolver复制数据用的缓存目录存在
     */
    fun ensureContentResolverCacheDir(context: Context): Boolean {
        return ensureDir(context.cacheDir, contentResolverCacheDirName)
    }

    /**
     * 确保指定目录下的指定目录存在
     */
    private fun ensureDir(baseDir: File, dirName: String): Boolean {
        val dir = File(baseDir.absolutePath + "/" + dirName)
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
     * 清理ContentResolver复制产生的遗漏清理的缓存数据\
     * 此方法由ApplicationX启动时在子线程中调用
     */
    fun clearContentResolverCache(context: Context) {
        val dir = File(context.cacheDir.absolutePath + "/" + contentResolverCacheDirName)
        if (dir.exists() && dir.isDirectory) dir.deleteRecursively()
    }

    /**
     * 获得给定[tag]的缓存文件名
     */
    fun getCacheFileName(context: Context, tag: String) = context.cacheDir.absolutePath + "/$cacheDirName/Cache_" + Base64.encodeToString(tag.toByteArray(Charset.forName("UTF-8")), Base64.NO_WRAP)

    /**
     *
     */
    fun generateContentResolverFileTarget(context: Context): Pair<File, Int> {
        val index = contentResolverCacheFileIndex++
        val file = File(context.cacheDir.absolutePath + "/$contentResolverCacheDirName/File_${index}")
        return Pair(file, index)
    }
}