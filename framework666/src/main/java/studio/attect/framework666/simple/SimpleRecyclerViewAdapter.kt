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

/**
 * 让RecyclerView用起来更简单的Adapter
 *
 * 数据频繁变动会变得很方便
 * 数据变更时，可以进行一次假操作获得数据变更的位置，将UI滑动到对应位置后在进行真操作和View作出对应变化
 *
 * @author Attect
 */
class SimpleRecyclerViewAdapter : RecyclerView.Adapter<SimpleRecyclerViewAdapter.ViewHolder<SimpleRecyclerViewAdapter.SimpleListData<Any>>>() {

    /**
     * 根据不同的类型持有不同ViewHolder的class
     */
    private val viewHolderMap = SparseArray<Class<ViewHolder<SimpleListData<Any>>>>()

    /**
     * 根据不同的类型持有不同的布局资源id
     */
    private val layoutMap = SparseIntArray()

    /**
     * 列表所有的数据都在这里
     */
    private val dataList = ArrayList<SimpleListData<Any>>()

    /**
     * 注册一个类型的ViewHolder（Class）
     * 与之对应类型的数据将交给其使用
     */
    fun registerViewHolder(type: Int = 0, viewHolderClass: Class<ViewHolder<SimpleListData<Any>>>) {
        viewHolderMap[type] = viewHolderClass
    }

    /**
     * 注册一个类型的布局资源
     */
    fun registerItemLayout(type: Int = 0, @LayoutRes layoutRes: Int) {
        layoutMap[type] = layoutRes
    }

    /**
     * 添加一个数据
     *
     * @param data 要加入列表的数据
     * @param fake 是否做假操作，可以获取到新数据的位置
     * @return 添加的新数据的位置
     */
    @JvmOverloads
    fun addData(data: SimpleListData<Any>, fake: Boolean = false): Int {
        if (fake) {
            return dataList.size
        }
        dataList.add(data)
        notifyItemInserted(dataList.size - 1)
        return dataList.size - 1
    }

    /**
     * 连续添加多个数据
     *
     * @param moreData 要添加的多条数据
     * @param fake 是否做假操作，可以获得最靠前的新数据的位置或判断是否有数据被添加
     * @return 第一条的位置,null时表示没有任何数据被添加
     */
    @JvmOverloads
    fun addMoreData(moreData: List<SimpleListData<Any>>, fake: Boolean = false): Int? {
        if (moreData.isNotEmpty()) {
            val newStartPosition = dataList.size
            if (!fake) {
                dataList.addAll(moreData)
                notifyItemRangeInserted(newStartPosition, moreData.size)
            }
            return newStartPosition
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
    fun updateData(data: SimpleListData<Any>, fake: Boolean = false): Int? {
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
    fun updateMoreData(moreData: List<SimpleListData<Any>>, fake: Boolean = false): Int? {
        val replaceData = SparseArray<SimpleListData<Any>>()
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
    fun removeData(data: SimpleListData<Any>, fake: Boolean = false): Int? {
        var position = -1;
        dataList.forEachIndexed { index, simpleListData ->
            if (simpleListData.uniqueTag() == data.uniqueTag()) {
                position = index
                return@forEachIndexed
            }
        }
        if (position > -1) {
            dataList.removeAt(position)
            notifyItemRemoved(position)
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
    fun removeMoreData(moreData: List<SimpleListData<Any>>, fake: Boolean = false): Int? {
        val removeData = SparseArray<SimpleListData<Any>>()
        moreData.forEach { removeTarget ->
            dataList.forEachIndexed { index, simpleListData ->
                if (removeTarget.uniqueTag() == simpleListData.uniqueTag()) {
                    removeData[index] = simpleListData //此处和update不一样
                }
            }
        }

        return if (removeData.isNotEmpty()) {
            val idList = arrayListOf<Int>()
            for (i in 0 until removeData.size()) {
                idList.add(i)
            }
            idList.reversed().forEach {
                //颠倒过来，从后往前删，避免key(position)变动
                dataList.removeAt(it)
                notifyItemRemoved(it)
            }
            removeData.keyAt(0)
        } else {
            null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<SimpleListData<Any>> {
        if (viewHolderMap.containsKey(viewType) && layoutMap.containsKey(viewType)) {
            return buildViewHolder(LayoutInflater.from(parent.context).inflate(layoutMap[viewType], parent, false), viewHolderMap[viewType])
        }
        return DefaultViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_unprocessed_data, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder<SimpleListData<Any>>, position: Int) {
        holder.applyData(dataList[position], position)
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        if (dataList.size < position) {
            return dataList[position].type
        }
        return super.getItemViewType(position)
    }

    abstract class ViewHolder<SimpleListData>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * 应用数据到View上
         */
        abstract fun applyData(data: SimpleListData, position: Int)
    }

    class DefaultViewHolder(itemView: View) : ViewHolder<SimpleListData<Any>>(itemView) {
        private val textView: AppCompatTextView = itemView.findViewById(R.id.text)

        override fun applyData(data: SimpleListData<Any>, position: Int) {
            textView.text = itemView.context.getString(R.string.simple_recycler_view_adapter_unprocessed_data).format(data.type, data.uniqueTag());
        }

    }

    abstract class SimpleListData<T>(data: T) {
        /**
         * 数据类型
         * 对应不同类型的ViewHolder
         */
        var type = 0

        /**
         * 获得数据的唯一tag
         * 用于更新数据时的比对
         */
        abstract fun uniqueTag(): Any
    }

    companion object {
        private fun buildViewHolder(view: View, cls: Class<ViewHolder<SimpleListData<Any>>>): ViewHolder<SimpleListData<Any>> {
            val params = arrayOf(view)
            val constructor = cls.getConstructor(View::class.java)
            return constructor.newInstance(params)
        }

    }
}

