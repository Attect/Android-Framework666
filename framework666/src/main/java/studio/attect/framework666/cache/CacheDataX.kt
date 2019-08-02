package studio.attect.framework666.cache

import androidx.annotation.IntDef
import studio.attect.framework666.DataXOffice
import studio.attect.framework666.RuntimeBuildConfig
import studio.attect.framework666.interfaces.DataX

/**
 * 可缓存的数据
 *
 * @author Attect
 */
class CacheDataX() : DataX {
    constructor(dataX: Any?) : this() {
        data = dataX
    }

    var data: Any? = null

    /**
     * 创建数据的Build版本
     * 创建缓存时记得设置，不设置就是框架的版本
     */
    var versionCode: Int = RuntimeBuildConfig.VERSION_CODE

    @StoreType
    var storeType = STORE_TYPE_UNKNOWN

    /**
     * 数据的tag
     * 应该对应业务和操作者唯一
     */
    var tag: String = "unTag"
        set(value) {
            if (value.length > 64) throw IllegalStateException("缓存tag太长了")
            field = value
        }

    /**
     * 创建时间
     */
    var time = System.currentTimeMillis()

    /**
     * 有效时长
     */
    var effectiveDuration = -1L


    override fun putToOffice(office: DataXOffice) {
        office.putLong(evalHeaderSize()) //head total bytes size:1~9
        office.putInt(versionCode)
        office.putInt(storeType)
        office.putString(tag)
        office.putLong(time)
        office.putLong(effectiveDuration)
        data?.let {
            if (it is DataX) {
                it.putToOffice(office)
            } else {
                office.put(it)
            }
        }

    }

    override fun applyFromOffice(office: DataXOffice) {
        office.getLong() //空操作一次，跳过head total bytes数据
        office.getInt()?.let { versionCode = it }
        office.getInt()?.let { storeType = it }
        office.getString()?.let { tag = it }
        office.getLong()?.let { time = it }
        office.getLong()?.let { effectiveDuration = it }
        data?.let {
            if (it is DataX) {
                it.applyFromOffice(office)
            } else {
                office.put(it)
            }
        }

    }

    /**
     * 求出头部大小
     */
    private fun evalHeaderSize(): Long {
        val office = DataXOffice()
        office.putInt(versionCode)
        office.putInt(storeType)
        office.putString(tag)
        office.putLong(time)
        office.putLong(effectiveDuration)
        return office.backLog()
    }


    companion object {
        /**
         * 储存类型：忘了设置储存类型类型
         * 总之，肯定是忘了设置才会为此值
         */
        const val STORE_TYPE_UNKNOWN = 0

        /**
         * 储存类型：崩溃自动储存
         * 发生严重错误时自动储存的数据为此类型
         */
        const val STORE_TYPE_CRASH = -1

        /**
         * 储存类型：始终自动储存
         * 有些东西，改完就存，例如临时设置
         */
        const val STORE_TYPE_AUTO = 1

        /**
         * 储存类型：手动储存草稿
         * 用户手动点击暂时储存的
         */
        const val STORE_TYPE_SKETCH = 2

        @IntDef(STORE_TYPE_CRASH, STORE_TYPE_AUTO, STORE_TYPE_SKETCH)
        @Retention(AnnotationRetention.SOURCE)
        annotation class StoreType

        /**
         * 保存为文件后，要读出文件头至少要读取的字节数
         * 此值由MessagePack的Packer.packLong(Long.MAX_VALUE)后得到
         *
         */
        const val FILE_HEAD_LENGTH_MIN_LENGTH = 9

    }


}