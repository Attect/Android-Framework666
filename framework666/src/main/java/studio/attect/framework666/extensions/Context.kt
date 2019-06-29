package studio.attect.framework666.extensions

import android.app.ActivityManager
import android.content.Context
import android.content.Context.MODE_PRIVATE
import studio.attect.framework666.DataXOffice
import studio.attect.framework666.Logcat
import studio.attect.framework666.cache.CacheDataX
import studio.attect.framework666.cache.CacheManager
import studio.attect.framework666.interfaces.DataX
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * 获得进程名
 * 不一定成功
 * 不使用反射
 */
val Context.progressName: String?
    get() {
        val pid = android.os.Process.myPid()
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager? ?: return null
        val progress = activityManager.runningAppProcesses ?: return null
        progress.forEach {
            if (it.pid == pid) return it.processName
        }
        return null
    }

/**
 * 缓存一个DataX
 */
fun <T : DataX> Context.cacheDataX(tag: String, @CacheDataX.Companion.StoreType storeType: Int, data: T): Boolean {
    if (!CacheManager.ensureCacheDir(this)) return false

    val cacheDataX = CacheDataX(data).apply {
        this.tag = tag
        this.storeType = storeType
    }

    val file = File(CacheManager.getCacheFileName(this, tag))
    val outputStream = FileOutputStream(file)
    try {
        outputStream.write(DataXOffice().putDataX(cacheDataX).offWork())

    } catch (e: IllegalStateException) {
        Logcat.w("缓存${tag}写入失败(数据内容)")
        e.printStackTrace()
        return false
    } catch (e: IOException) {
        Logcat.w("缓存${tag}写入失败(文件系统)")
        e.printStackTrace()
        return false
    } finally {
        try {
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
    return true
}