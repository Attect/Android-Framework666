package studio.attect.framework666.simple

import android.util.SparseArray
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.util.containsKey
import androidx.core.util.forEach
import androidx.core.util.isNotEmpty
import androidx.core.util.set
import androidx.recyclerview.widget.RecyclerView
import studio.attect.framework666.R
import studio.attect.framework666.interfaces.UniqueData

/**
 * 让RecyclerView用起来更简单的Adapter
 *
 * 数据频繁变动会变得很方便
 * 数据变更时，可以进行一次假操作获得数据变更的位置，将UI滑动到对应位置后在进行真操作和View作出对应变化
 *
 * @author Attect
 */
class SimpleRecyclerViewAdapter : RecyclerView.Adapter<SimpleRecyclerViewAdapter.BasicViewHolder>() {

    var viewHolderFactory: ViewHolderFactory? = null

    /**
     * 列表所有的数据都在这里
     */
    private val dataList = ArrayList<SimpleListData>()


    /**
     * 添加一个数据
     *
     * @param data 要加入列表的数据
     * @param position 要插入的位置，默认为列表末尾
     * @param fake 是否做假操作，可以获取到新数据的位置
     * @return 添加的新数据的位置
     */
    @JvmOverloads
    fun addData(data: SimpleListData, position: Int = dataList.size, fake: Boolean = false): Int {
        if (fake) {
            return position
        }
        dataList.add(position, data)
        notifyItemInserted(position)
        return position
    }

    /**
     * 连续添加多个数据
     *
     * @param moreData 要添加的多条数据
     * @param fake 是否做假操作，可以获得最靠前的新数据的位置或判断是否有数据被添加
     * @return 第一条的位置,null时表示没有任何数据被添加
     */
    @JvmOverloads
    fun addMoreData(moreData: List<SimpleListData>, position: Int = dataList.size, fake: Boolean = false): Int? {
        if (moreData.isNotEmpty()) {
            if (!fake) {
                dataList.addAll(position, moreData)
                notifyItemRangeInserted(position, moreData.size)
            }
            return position
        }
        return null
    }

    /**
     * 更新一个数据
     * 根据唯一tag进行更新
     * 只会更新一条（废话，数据唯一标识）
     *
     * @param data 要更新的数据，不依赖内存地址判断而是根据唯一Tag
     * @param fake 是否做假操作，可以获得被更新的数据的位置或判断是否有数据被更新
     * @return 更新的数据的位置，如果没有数据被更新则为null
     */
    fun updateData(data: SimpleListData, fake: Boolean = false): Int? {
        var position = -1;
        dataList.forEachIndexed { index, simpleListData ->
            if (simpleListData.uniqueTag() == data.uniqueTag()) {
                position = index
                return@forEachIndexed
            }
        }
        if (position > -1) {
            if (!fake) {
                dataList[position] = data
                notifyItemChanged(position)
            }
            return position
        }
        return null
    }

    /**
     * 更新多个数据
     * 根据唯一的tag进行更新
     *
     * @param moreData 要更新的多个数据，更新可能/可以不连续，不依赖内存地址判断而是根据唯一Tag
     * @param fake 是否做假操作，可以获得最靠前的被更新的数据的位置或判断是否有数据被更新
     * @return 第一条被变更的数据的位置，若没有数据发生变化则返回null
     */
    fun updateMoreData(moreData: List<SimpleListData>, fake: Boolean = false): Int? {
        val replaceData = SparseArray<SimpleListData>()
        moreData.forEach { newData ->
            dataList.forEachIndexed { index, simpleListData ->
                if (newData.uniqueTag() == simpleListData.uniqueTag()) {
                    replaceData[index] = newData
                }
            }
        }

        return if (replaceData.isNotEmpty()) {
            if (!fake) {
                replaceData.forEach { key, value ->
                    dataList[key] = value
                    notifyItemChanged(key)
                }
            }
            replaceData.keyAt(0)
        } else {
            null
        }
    }

    /**
     * 删除一条数据
     * 根据唯一的tag进行删除
     *
     * @param data 要删除的数据，不依赖内存地址判断而是根据唯一Tag
     * @param fake 是否做假操作，可以获得被删除的数据的位置或判断有没有数据被删除
     * @return 删除的数据的位置，如果没有数据被删除则为null
     */
    fun removeData(data: SimpleListData, fake: Boolean = false): Int? {
        var position = -1;
        dataList.forEachIndexed { index, simpleListData ->
            if (simpleListData.uniqueTag() == data.uniqueTag()) {
                position = index
                return@forEachIndexed
            }
        }
        if (position > -1) {
            if (!fake) {
                dataList.removeAt(position)
                notifyItemRemoved(position)
            }

            return position
        }
        return null
    }

    /**
     * 删除多个数据
     * 删除可能/可以是不连续的操作
     * 根据唯一的tag作为判断标准进行删除
     *
     * @param moreData 要删除的多个数据，删除可能/可以不连续，不依赖内存地址判断而是根据唯一Tag
     * @param fake 是否做假操作，可以获得最靠前的被删除的数据的位置或判断是否有数据被删除
     * @return 首条被删除的数据的位置，如果没有数据被删除则为null
     */
    fun removeMoreData(moreData: List<SimpleListData>, fake: Boolean = false): Int? {
        val removeData = SparseArray<SimpleListData>()
        moreData.forEach { removeTarget ->
            dataList.forEachIndexed { index, simpleListData ->
                if (removeTarget.uniqueTag() == simpleListData.uniqueTag()) {
                    removeData[index] = simpleListData //此处和update不一样
                }
            }
        }

        return if (removeData.isNotEmpty()) {
            if (!fake) {
                val idList = arrayListOf<Int>()
                for (i in 0 until removeData.size()) {
                    idList.add(i)
                }
                idList.reversed().forEach {
                    //颠倒过来，从后往前删，避免key(position)变动
                    dataList.removeAt(it)
                    notifyItemRemoved(it)
                }
            }
            removeData.keyAt(0)
        } else {
            null
        }
    }

    /**
     * 清空表内容
     * @return 移除了多少条数据
     */
    fun clearData(): Int {
        val size = dataList.size
        dataList.clear()
        notifyItemRangeRemoved(0, size)
        return size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder {
        viewHolderFactory?.let { factory ->
            factory.getViewHolderLayoutResource(viewType)?.let { layout ->
                factory.createViewHolder(viewType, LayoutInflater.from(parent.context).inflate(layout, parent, false))?.let {
                    return it
                }
            }
        }
        return DefaultViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_unprocessed_data, parent, false))
    }

    override fun onBindViewHolder(holderBasic: BasicViewHolder, position: Int) {
        if (holderBasic is DefaultViewHolder) {
            holderBasic.applyData(dataList[position], position)
            return
        }
        holderBasic.applyData(dataList[position].data, position)
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        if (position < dataList.size) {
            return dataList[position].type
        }
        return super.getItemViewType(position)
    }

    abstract class BasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * 应用数据到View上
         */
        abstract fun applyData(data: Any, position: Int)
    }

    interface ViewHolderFactory {

        @LayoutRes
        fun getViewHolderLayoutResource(type: Int): Int?

        fun createViewHolder(type: Int, itemView: View): BasicViewHolder?
    }


    class DefaultViewHolder(itemView: View) : BasicViewHolder(itemView) {
        private val textView: AppCompatTextView = itemView.findViewById(R.id.text)

        override fun applyData(data: Any, position: Int) {
            if (data is SimpleListData) {
                textView.text = itemView.context.getString(R.string.simple_recycler_view_adapter_unprocessed_data).format(data.type, data.uniqueTag());
            }
        }

    }

    class SimpleListData() {
        var data: UniqueData = object : UniqueData {
            override fun uniqueTag(): Any {
                return Any()
            }
        }

        /**
         * 数据类型
         * 对应不同类型的ViewHolder
         */
        var type = 0

        fun uniqueTag() = data.uniqueTag()

    }

}

