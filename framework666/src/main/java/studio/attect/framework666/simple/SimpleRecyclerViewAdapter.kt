package studio.attect.framework666.simple

import android.util.SparseArray
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
 * 注意：ViewType在此类作为LayoutRes使用！！
 *
 * @param owner 持有者引用，因ViewHolder可能为内部类而准备的
 * @author Attect
 */
class SimpleRecyclerViewAdapter<T>(val owner: T) : RecyclerView.Adapter<SimpleRecyclerViewAdapter.BasicViewHolder<out UniqueData>>() {

    /**
     * 根据不同的类型持有不同ViewHolder的class
     */
    private val viewHolderMap = SparseArray<Class<out BasicViewHolder<out UniqueData>>>()

    /**
     * 列表所有的数据都在这里
     */
    val dataList = ArrayList<SimpleListData<out UniqueData>>()

    /**
     * 注册一个类型的ViewHolder（Class）
     * 与之对应类型的数据将交给其使用
     *
     * @param layoutRes 对应的布局的id
     */
    fun registerViewHolder(@LayoutRes layoutRes: Int = 0, basicViewHolderClass: Class<out BasicViewHolder<out UniqueData>>) {
        viewHolderMap[layoutRes] = basicViewHolderClass
    }

    /**
     * 添加一个数据
     *
     * @param data 要加入列表的数据
     * @param layoutRes 添加的数据对应的布局,fake为true时，可以给定任意值
     * @param position 要插入的位置，默认为列表末尾
     * @param fake 是否做假操作，可以获取到新数据的位置
     * @return 添加的新数据的位置
     */
    @JvmOverloads
    fun addData(data: UniqueData, @LayoutRes layoutRes: Int, position: Int = dataList.size, fake: Boolean = false): Int {
        if (fake) {
            return position
        }
        dataList.add(position, SimpleListData(data, layoutRes))
        notifyItemInserted(position)
        return position
    }

    /**
     * 连续添加多个数据
     *
     * @param moreData 要添加的多条数据,如果需要特别定制，部分值可传入SimpleListData，就可以为同一系列数据类型中的其中一个或多个使用不同的layout
     * @layoutRes 数据集对应的布局资源，若数据自身为SimpleListData则采用数据指定的，fake为true时，可以给定任意值
     * @param fake 是否做假操作，可以获得最靠前的新数据的位置或判断是否有数据被添加
     * @return 第一条的位置,null时表示没有任何数据被添加
     */
    @JvmOverloads
    fun addMoreData(moreData: List<UniqueData>, @LayoutRes layoutRes: Int, position: Int = dataList.size, fake: Boolean = false): Int? {
        if (!moreData.isNullOrEmpty()) {
            if (!fake) {
                moreData.forEach {
                    if (it is SimpleListData<*>) {
                        dataList.add(it)
                    } else {
                        dataList.add(SimpleListData(it, layoutRes))
                    }
                }
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
     * 此方法作用为无需判断数据的存在及其位置无脑通知视图更新内容
     *
     * @param data 要更新的数据，不依赖内存地址判断而是根据唯一Tag
     * @param layoutRes 更新数据后的布局，fake为true时，可以给定任意值
     * @param fake 是否做假操作，可以获得被更新的数据的位置或判断是否有数据被更新
     * @return 更新的数据的位置，如果没有数据被更新则为null
     */
    fun updateData(data: UniqueData, @LayoutRes layoutRes: Int, fake: Boolean = false): Int? {
        var position = -1
        dataList.forEachIndexed { index, simpleListData ->
            if (simpleListData.uniqueTag() == data.uniqueTag()) {
                position = index
                return@forEachIndexed
            }
        }
        if (position > -1) {
            if (!fake) {
                dataList[position] = SimpleListData(data, layoutRes)
                notifyItemChanged(position)
            }
            return position
        }
        return null
    }

    /**
     * 更新多个数据
     * 根据唯一的tag进行更新
     * 此方法作用为无需判断数据的存在及其位置无脑通知视图更新内容
     *
     * @param moreData 要更新的多个数据，更新可能/可以不连续，不依赖内存地址判断而是根据唯一Tag
     * @param layoutRes 数据集对应的布局资源，若数据自身为SimpleListData则采用数据指定的，fake为true时，可以给定任意值
     * @param fake 是否做假操作，可以获得最靠前的被更新的数据的位置或判断是否有数据被更新
     * @return 变更的数据的位置，若没有数据变更则为空的数组
     */
    fun updateMoreData(moreData: List<UniqueData>, @LayoutRes layoutRes: Int, fake: Boolean = false): IntArray {
        val replaceData = SparseArray<SimpleListData<out UniqueData>>()
        moreData.forEach { newData ->
            dataList.forEachIndexed { index, simpleListData ->
                if (newData.uniqueTag() == simpleListData.uniqueTag()) {
                    if (newData is SimpleListData<*>) {
                        replaceData[index] = newData
                    } else {
                        replaceData[index] = SimpleListData(newData, layoutRes)
                    }
                }
            }
        }

        val intArray = IntArray(replaceData.size())
        for (i in 0 until replaceData.size()) {
            intArray[i] = replaceData.keyAt(i)
        }

        return if (replaceData.isNotEmpty()) {
            if (!fake) {
                replaceData.forEach { key, value ->
                    dataList[key] = value
                    notifyItemChanged(key)
                }
            }
            intArray
        } else {
            IntArray(0)
        }
    }

    /**
     * 删除一条数据
     * 根据唯一的tag进行删除
     * 此方法可用于无需判断数据是否存在进行安全删除操作调用，并能得知结果
     *
     * @param data 要删除的数据，不依赖内存地址判断而是根据唯一Tag
     * @param fake 是否做假操作，可以获得被删除的数据的位置或判断有没有数据被删除，fake为true时，可以给定任意值
     * @return 删除的数据的位置，如果没有数据被删除则为null
     */
    fun removeData(data: UniqueData, fake: Boolean = false): Int? {
        var position = -1
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
     * 此方法可用于无需判断数据是否存在进行安全删除操作调用，并能得知结果
     *
     * @param moreData 要删除的多个数据，删除可能/可以不连续，不依赖内存地址判断而是根据唯一Tag
     * @param fake 是否做假操作，可以获得最靠前的被删除的数据的位置或判断是否有数据被删除，fake为true时，可以给定任意值
     * @return 被删除的数据的所有位置，若没有数据被删除则为空的数组
     */
    fun removeMoreData(moreData: List<UniqueData>, fake: Boolean = false): IntArray {
        val removeData = SparseArray<SimpleListData<out UniqueData>>()
        moreData.forEach { removeTarget ->
            dataList.forEachIndexed { index, simpleListData ->
                if (removeTarget.uniqueTag() == simpleListData.uniqueTag()) {
                    removeData[index] = simpleListData //此处和update不一样
                }
            }
        }

        val intArray = IntArray(removeData.size())
        for (i in 0 until removeData.size()) {
            intArray[i] = removeData.keyAt(i)
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
            intArray
        } else {
            IntArray(0)
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

    /**
     * 自动创建ViewHolder
     * 如果有注册对应布局资源的ViewHolder则自动进行创建
     * 否则返回一个 SimpleRecyclerViewAdapter.DefaultViewHolder 告知未注册对应ViewHolder
     *
     * @param parent 父层布局
     * @param viewType 实际为布局资源id
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder<out UniqueData> {
        if (viewHolderMap.containsKey(viewType)) {
            return buildViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false), viewHolderMap[viewType])
        }
        return DefaultViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_unprocessed_data, parent, false))
    }

    override fun onBindViewHolder(holderBasic: BasicViewHolder<out UniqueData>, position: Int) {
        if (holderBasic is DefaultViewHolder) {
            holderBasic.applyData(dataList[position], position)
            return
        }

        //此处范型一定成功，因为T类型一定是UniqueData的子类型，反正，已经验证过了没有错误，虽然不优雅
        @Suppress("UNCHECKED_CAST")
        (holderBasic as BasicViewHolder<UniqueData>).applyData(dataList[position].listData, position)
    }

    override fun getItemCount(): Int = dataList.size

    /**
     * 此类变更为返回数据对应的布局
     *
     * @return 数据对应布局资源id
     */
    override fun getItemViewType(position: Int): Int {
        if (position < dataList.size) {
            return dataList[position].layoutRes
        }
        return R.layout.recycler_view_unprocessed_data
    }

    /**
     * 根据BasicViewHolder相关Class实例化类并传递参数
     *
     * @param view #onCreateViewHolder 中对应布局的实例
     * @param cls BasicViewHolder.class
     */
    private fun buildViewHolder(view: View, cls: Class<out BasicViewHolder<out UniqueData>>): BasicViewHolder<out UniqueData> {
        //处理ViewHolder为内部类的情况
        cls.constructors[0]?.parameterTypes?.let { parameterTypes ->
            if (parameterTypes.size > 1) {
                val constructor = cls.getConstructor(parameterTypes[0], View::class.java)
                return constructor.newInstance(owner, view)
            }
        }

        val constructor = cls.getConstructor(View::class.java)
        return constructor.newInstance(view)

    }

    abstract class BasicViewHolder<T : UniqueData>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * 应用数据到View上
         */
        abstract fun applyData(data: T, position: Int)

    }

    /**
     * 一个默认的ViewHolder
     * 用于防止疏忽导致崩溃，并在实际界面上显示出具体问题
     * 这同时也是一个ViewHolder的例子
     */
    private class DefaultViewHolder(itemView: View) : BasicViewHolder<SimpleListData<out UniqueData>>(itemView) {

        private val textView: AppCompatTextView = itemView.findViewById(R.id.text)

        override fun applyData(data: SimpleListData<out UniqueData>, position: Int) {
            textView.text = itemView.context.getString(R.string.simple_recycler_view_adapter_unprocessed_data).format(data.layoutRes, data.uniqueTag())
        }

    }

    /**
     * 简单列表数据类
     * 将UniqueData与布局资源进行绑定
     */
    class SimpleListData<T : UniqueData>(val listData: T, @LayoutRes val layoutRes: Int) : UniqueData {
        override fun uniqueTag() = listData.uniqueTag()

    }

}

