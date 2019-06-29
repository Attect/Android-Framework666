package studio.attect.framework666.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import studio.attect.framework666.interfaces.DataX

/**
 * 缓存数据模型
 * 使用子线程进行缓存操作时，从此类观察结果
 */
class CacheDataXViewModel : ViewModel() {
    /**
     * 快速检查缓存的结果
     * 数据中只包含头部数据
     */
    val fastCacheDataXResultList = MutableLiveData<ArrayList<DataX>>()

    /**
     * 缓存数据读取结果
     * 注意线程安全
     * 取值后应该置为null
     */
    val cacheDataX = MutableLiveData<DataX>()

    /**
     * 等待读取的缓存的tag合集
     * 子线程将会从中取出，读取文件后变更此内容
     * //todo 观察此数据变化，每观察到一次变化且内部有值则启动新线程读取（不应该同时多个线程读取，因为IO只有一个，迸发不会提高效率）
     */
    val cacheDataXLoadList = MutableLiveData<ArrayList<String>>()

    /**
     * 有一个线程正在读取
     */
    val readerWorking = false
}