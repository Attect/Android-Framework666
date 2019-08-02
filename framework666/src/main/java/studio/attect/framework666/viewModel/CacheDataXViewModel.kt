package studio.attect.framework666.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import studio.attect.framework666.cache.CacheDataX
import studio.attect.framework666.interfaces.DataX
import java.util.concurrent.ConcurrentHashMap

/**
 * 缓存数据模型
 * 使用子线程进行缓存操作时，从此类观察结果
 */
class CacheDataXViewModel : ViewModel() {
    /**
     * 快速检查缓存的结果
     * 数据中只包含头部数据
     */
    val fastCacheDataXResultList = MutableLiveData<ArrayList<CacheDataX>>()

    /**
     * 缓存数据读取结果
     * 注意线程安全
     * 取值后应该置为null
     */
    val cacheDataX = MutableLiveData<Pair<String, Any>?>()

    /**
     * 等待读取的缓存的tag合集
     * 子线程将会从中取出，读取文件后变更此内容
     */
    val cacheDataXLoadMap = MutableLiveData<ConcurrentHashMap<String, Class<out DataX>>>()
        get() {
            if (field.value == null) field.value = ConcurrentHashMap()
            return field
        }

    /**
     * 有一个线程正在读取
     */
    var readerWorking = false
}