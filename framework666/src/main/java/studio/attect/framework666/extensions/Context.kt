package studio.attect.framework666.extensions

import android.app.ActivityManager
import android.content.Context
import studio.attect.framework666.DataXOffice
import studio.attect.framework666.Logcat
import studio.attect.framework666.RuntimeBuildConfig
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
fun <T : DataX> Context.writeCacheDataX(tag: String, @CacheDataX.Companion.StoreType storeType: Int, data: T): Boolean {
    if (!CacheManager.ensureCacheDir(this)) return false

    val cacheDataX = CacheDataX(data).apply {
        this.tag = tag
        this.storeType = storeType
    }

    val file = File(CacheManager.getCacheFileName(this, tag))
    Logcat.d("写入缓存文件[$tag]：${file.absolutePath}")
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

/**
 * 根据[tags]快速检测有效缓存
 * 过程中失效的缓存将会被删除
 * 有效的将会作为DataX返回（无数据部分）
 *
 * 应该根据返回的文件头来决定是否读取完整缓存，避免无用的性能消耗
 * 比如提示用户存在草稿是否恢复……
 *
 * MessagePack并不需要数据完整也能读出头部数据
 * @return 有效的缓存文件头，不包含数据部分
 */
fun Context.fastCheckCache(tags: List<String>): ArrayList<CacheDataX<DataX>> {
    val resultArray = arrayListOf<CacheDataX<DataX>>()
    tags.forEach { tag ->
        val file = File(CacheManager.getCacheFileName(this, tag))
        if (file.exists() && file.isFile && file.canRead() && file.length() > CacheDataX.FILE_HEAD_LENGTH_MIN_LENGTH) {
            file.readBytes()
            file.inputStream().use { input ->
                //先尝试读出文件头的长度数据
                val preLoadByteArray = ByteArray(CacheDataX.FILE_HEAD_LENGTH_MIN_LENGTH)

                //读取固定FILE_HEAD_LENGTH_MIN_LENGTH长度内容（如果连这点数据都没办法一次性读入……储存介质没救了）
                val preLoadDataSize = input.read(preLoadByteArray, 0, CacheDataX.FILE_HEAD_LENGTH_MIN_LENGTH)
                if (preLoadDataSize < CacheDataX.FILE_HEAD_LENGTH_MIN_LENGTH) {
                    if (file.delete()) {
                        Logcat.i("缓存:${tag}文件读取失败，已删除")
                    } else {
                        Logcat.w("缓存:${tag}文件读取失败，且删除失败")
                    }
                    return@use null
                } //读不够直接弃了

                //由DataXOffice解析出头部长度数据，如果发生任何错误都直接弃了
                var headerSize = DataXOffice().unpack(preLoadByteArray).getLong()?.toInt()
                if (headerSize == null) {
                    file.delete()
                    return@use null
                }
                headerSize++ //需要多预读一位，否则MessagePack解码会出错

                //除去已经预读的内容，剩下的header的长度
                headerSize -= preLoadDataSize

                //真正的head内容数组
                val remainingByteArray = ByteArray(headerSize)

                //不能跳过之前的长度数据！！因为MessagePack用了半字节！！
                var offset = 0
                while (headerSize > 0) {
                    val read = input.read(remainingByteArray, offset, headerSize)
                    if (read < 0) break
                    headerSize -= read
                    offset += read
                }
                if (headerSize == 0) (preLoadByteArray + remainingByteArray) else (preLoadByteArray + remainingByteArray.copyOf(offset))
            }
        } else {
            if (file.exists()) file.delete() //文件太小，删了
            null //啥子玩意儿不会自动null，必须写这个else
        }?.let { headByteArray ->
            val dataXOffice = DataXOffice().apply { unpack(headByteArray) }
            val cacheDataX = CacheDataX(null)
            cacheDataX.applyFromOffice(dataXOffice)
            if (cacheDataX.versionCode == RuntimeBuildConfig.VERSION_CODE && //检查版本
                cacheDataX.time > 0 && (cacheDataX.effectiveDuration <= 0 || (System.currentTimeMillis() - cacheDataX.time) <= cacheDataX.effectiveDuration)
            ) { //计算保质期
                resultArray.add(cacheDataX)
            } else {
                //过期的就删了
                file.delete()
            }
        }
    }

    return resultArray
}

/**
 * 读取缓存
 * 同样会进行检查（但因为更费时不要用这个方法检查），期间如果发现数据无效同样会被删除
 * 最好在子线程执行此方法
 *
 * @param tag 缓存的tag
 * @param dataClass 数据DataX的类
 * @return null为不存在有效缓存，否则tag与缓存数据成对返回
 */
fun Context.readCacheDataX(tag: String, dataClass: Class<out DataX>): Pair<String, DataX>? {
    val file = File(CacheManager.getCacheFileName(this, tag))
    file.readBytes()
    if (file.exists() && file.isFile && file.canRead() && file.length() > CacheDataX.FILE_HEAD_LENGTH_MIN_LENGTH) {
        file.inputStream().use { input ->
            //先尝试读出文件头的长度数据
            val preLoadByteArray = ByteArray(CacheDataX.FILE_HEAD_LENGTH_MIN_LENGTH)

            //读取固定FILE_HEAD_LENGTH_MIN_LENGTH长度内容（如果连这点数据都没办法一次性读入……储存介质没救了）
            val preLoadDataSize = input.read(preLoadByteArray, 0, CacheDataX.FILE_HEAD_LENGTH_MIN_LENGTH)
            if (preLoadDataSize < CacheDataX.FILE_HEAD_LENGTH_MIN_LENGTH) {
                if (file.delete()) {
                    Logcat.i("缓存:${tag}文件读取失败，已删除")
                } else {
                    Logcat.w("缓存:${tag}文件读取失败，且删除失败")
                }
                return@use null
            } //读不够直接弃了

            //由DataXOffice解析出头部长度数据，如果发生任何错误都直接弃了
            val headerSize = DataXOffice().unpack(preLoadByteArray).getLong()?.toInt()
            if (headerSize == null) {
                file.delete()
                return@use null
            }

            file.readBytes()
        }
    } else {
        if (file.exists()) file.delete() //文件太小，删了
        null //啥子玩意儿不会自动null，必须写这个else
    }?.let { dataByteArray ->
        val dataXOffice = DataXOffice().apply { unpack(dataByteArray) }
        val dataX = dataClass.newInstance()
        val cacheDataX = CacheDataX(dataX)
        cacheDataX.applyFromOffice(dataXOffice)
        if (cacheDataX.versionCode == RuntimeBuildConfig.VERSION_CODE && //检查版本
            cacheDataX.time > 0 && (cacheDataX.effectiveDuration <= 0 || (System.currentTimeMillis() - cacheDataX.time) <= cacheDataX.effectiveDuration)
        ) { //计算保质期
            cacheDataX.data?.let { data ->
                return Pair(tag, data)
            }
        } else {
            //过期的就删了
            file.delete()
        }
    }
    return null
}

/**
 * 请求删除缓存数据
 * 不一定成功
 *
 * 可以主线程操作
 * @return 是否成功删除
 */
fun Context.deleteCacheDataX(tag: String): Boolean {
    val file = File(CacheManager.getCacheFileName(this, tag))
    if (file.exists() && file.canWrite()) {
        return file.delete()
    }
    return false
}