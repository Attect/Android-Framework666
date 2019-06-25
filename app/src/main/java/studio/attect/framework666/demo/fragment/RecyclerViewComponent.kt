package studio.attect.framework666.demo.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.component_recycler_view.*
import net.steamcrafted.materialiconlib.IconValue
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder
import studio.attect.framework666.FragmentX
import studio.attect.framework666.compomentX.ComponentX
import studio.attect.framework666.compomentX.ComponentXCompanion
import studio.attect.framework666.demo.R
import studio.attect.framework666.extensions.systemLongAnimTime
import studio.attect.framework666.interfaces.UniqueData
import studio.attect.framework666.simple.SimpleRecyclerViewAdapter

/**
 * 回收视图Demo组件
 *
 * @author Attect
 */
class RecyclerViewComponent : FragmentX() {
    private val recyclerViewAdapter = SimpleRecyclerViewAdapter()
    private val layoutManager by lazy { LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.component_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setColorSchemeColors(ResourcesCompat.getColor(resources, R.color.colorPrimary, requireActivity().theme))
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.postDelayed({
                swipeRefreshLayout.isRefreshing = false
                resetAllData()
            }, 2000) //模拟两秒后停止
        }

        recyclerView.layoutManager = layoutManager

        //设置分割线
        val divider = DividerItemDecoration(requireContext(), layoutManager.orientation)
        recyclerView.addItemDecoration(divider)

        recyclerViewAdapter.viewHolderFactory = VHFactory()
        recyclerView.adapter = recyclerViewAdapter

        reset.setOnClickListener {
            resetAllData()
        }
        addOne.setOnClickListener {
            addOneData()
        }
        addMore.setOnClickListener { addMoreData() }
        updateOne.setOnClickListener { updateOneData() }
        updateMore.setOnClickListener { updateMoreData() }
        removeOne.setOnClickListener { removeOneData() }
        removeMore.setOnClickListener { removeMoreData() }

        resetAllData()
    }

    /**
     * 刷新整个列表
     */
    private fun resetAllData() {
        recyclerViewAdapter.clearData()
        val defaultData = arrayListOf<SimpleRecyclerViewAdapter.SimpleListData>()
        for (i in 0 until 10) {
            val sld = SimpleRecyclerViewAdapter.SimpleListData()
            sld.data = ItemData()
            sld.type = randomType()
            defaultData.add(sld)
        }
        recyclerViewAdapter.addMoreData(defaultData)
    }

    /**
     * 添加一个随机数据
     */
    private fun addOneData() {
        val data = SimpleRecyclerViewAdapter.SimpleListData()
        data.data = ItemData()
        data.type = randomType()
        val position = (0..recyclerViewAdapter.itemCount).shuffled().last()
        recyclerView.smoothScrollToPosition(position)
        recyclerView.postDelayed(
            {
                recyclerViewAdapter.addData(data, position)
                Toast.makeText(requireContext(), "添加一个列表项：类型[${data.type}] 数据:${(data.data as ItemData).text}", Toast.LENGTH_LONG).show()
            },
            resources.systemLongAnimTime
        )
    }

    /**
     * 添加多个随机数据
     */
    private fun addMoreData() {
        val moreData = arrayListOf<SimpleRecyclerViewAdapter.SimpleListData>()
        val position = (0..recyclerViewAdapter.itemCount).shuffled().last()
        val number = (1..5).shuffled().last()
        for (i in 0 until number) {
            val sld = SimpleRecyclerViewAdapter.SimpleListData()
            sld.data = ItemData()
            sld.type = randomType()
            moreData.add(sld)
        }
        recyclerView.smoothScrollToPosition(position)

        recyclerView.postDelayed(
            {
                recyclerViewAdapter.addMoreData(moreData, position)
                Toast.makeText(requireContext(), "添加${number}个列表项", Toast.LENGTH_LONG).show()
            },
            resources.systemLongAnimTime
        )
    }

    /**
     * 更新一个随机数据
     * 可能没有数据被更新
     */
    private fun updateOneData() {
        val data = SimpleRecyclerViewAdapter.SimpleListData()
        data.data = ItemData()
        data.type = randomType()
        val position = recyclerViewAdapter.updateData(data, true)
        if (position != null) {
            recyclerView.smoothScrollToPosition(position)
            recyclerView.postDelayed(
                {
                    recyclerViewAdapter.updateData(data)
                    Toast.makeText(requireContext(), "更新一个列表项：类型[${data.type}] 数据:${(data.data as ItemData).text}", Toast.LENGTH_LONG).show()
                },
                resources.systemLongAnimTime
            )
        } else {
            Toast.makeText(requireContext(), "未更新任何数据", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * 更新多个随机数据
     * 可能只有部分甚至没有数据被更新
     */
    private fun updateMoreData() {
        val moreData = arrayListOf<SimpleRecyclerViewAdapter.SimpleListData>()
        val number = (1..10).shuffled().last()
        for (i in 0 until number) {
            val sld = SimpleRecyclerViewAdapter.SimpleListData()
            sld.data = ItemData()
            sld.type = randomType()
            moreData.add(sld)
        }
        val position = recyclerViewAdapter.updateMoreData(moreData, true)
        if (position != null) {
            recyclerView.smoothScrollToPosition(position)
            recyclerView.postDelayed(
                {
                    recyclerViewAdapter.updateMoreData(moreData)
                },
                resources.systemLongAnimTime
            )
        } else {
            Toast.makeText(requireContext(), "未更新任何数据", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * 删除一个随机数据
     * 可能没有数据被删除
     */
    private fun removeOneData() {
        val data = SimpleRecyclerViewAdapter.SimpleListData()
        data.data = ItemData()
        data.type = randomType()
        val position = recyclerViewAdapter.removeData(data, true)
        if (position != null) {
            recyclerView.smoothScrollToPosition(position)
            recyclerView.postDelayed(
                {
                    recyclerViewAdapter.removeData(data)
                    Toast.makeText(requireContext(), "删除一个列表项：类型[${data.type}] 数据:${(data.data as ItemData).text}", Toast.LENGTH_LONG).show()
                },
                resources.systemLongAnimTime
            )
        } else {
            Toast.makeText(requireContext(), "未删除任何数据", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * 删除多个随机数据
     * 可能只有部分甚至没有数据被删除
     */
    private fun removeMoreData() {
        val moreData = arrayListOf<SimpleRecyclerViewAdapter.SimpleListData>()
        val number = (1..10).shuffled().last()
        for (i in 0 until number) {
            val sld = SimpleRecyclerViewAdapter.SimpleListData()
            sld.data = ItemData()
            sld.type = randomType()
            moreData.add(sld)
        }
        val position = recyclerViewAdapter.removeMoreData(moreData, true)
        if (position != null) {
            recyclerView.smoothScrollToPosition(position)
            recyclerView.postDelayed(
                {
                    recyclerViewAdapter.removeMoreData(moreData)
                },
                resources.systemLongAnimTime
            )
        } else {
            Toast.makeText(requireContext(), "未删除任何数据", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * 产生一个随机数据类型
     * 三分之一的几率是未处理的数据类型
     */
    private fun randomType() = (0..2).shuffled().last()

    /**
     * 列表中的数据的结构
     */
    class ItemData : UniqueData {
        var text = (1..50).shuffled().last().toString()
        var color = Color.argb(255, (0..255).shuffled().last(), (0..255).shuffled().last(), (0..255).shuffled().last())
        override fun uniqueTag(): Any = text
    }

    /**
     * ViewHolder工厂
     * 给适配器提供对应类型的ViewHolder
     */
    open class VHFactory : SimpleRecyclerViewAdapter.ViewHolderFactory {
        override fun getViewHolderLayoutResource(type: Int): Int? {
            when (type) {
                TYPE_TEXT -> return R.layout.list_item_text
                TYPE_TEXT_WITH_COLOR -> return R.layout.list_item_text_with_color
            }

            return null
        }

        override fun createViewHolder(type: Int, itemView: View): SimpleRecyclerViewAdapter.BasicViewHolder? {
            when (type) {
                TYPE_TEXT -> {
                    return object : SimpleRecyclerViewAdapter.BasicViewHolder(itemView) {
                        val textView = itemView.findViewById<AppCompatTextView>(R.id.text)
                        override fun applyData(data: Any, position: Int) {
                            if (data is ItemData) {
                                textView.text = data.text
                            }
                        }
                    }
                }
                TYPE_TEXT_WITH_COLOR -> {
                    return object : SimpleRecyclerViewAdapter.BasicViewHolder(itemView) {
                        val textView = itemView.findViewById<AppCompatTextView>(R.id.text)
                        val colorView = itemView.findViewById<View>(R.id.colorBlock)
                        override fun applyData(data: Any, position: Int) {
                            if (data is ItemData) {
                                textView.text = data.text
                                colorView.background = ColorDrawable(data.color)
                            }
                        }

                    }
                }
            }
            return null
        }

    }

    companion object : ComponentXCompanion {
        /**
         * 数据类型：仅文字
         */
        const val TYPE_TEXT = 1

        /**
         * 数据类型：文字+颜色块
         */
        const val TYPE_TEXT_WITH_COLOR = 2

        override fun getTag(): String = "recycler_view_demo"

        override fun getIcon(context: Context?): Drawable? {
            context?.let {
                val builder = MaterialDrawableBuilder.with(it).apply {
                    setIcon(IconValue.FORMAT_LIST_BULLETED)
                    setColor(Color.WHITE)
                    setSizeDp(24)
                }

                return builder.build()
            }
            return null
        }

        override fun getName(context: Context?): String {
            context?.let {
                return context.getString(R.string.recycler_view_demo_title)
            }
            return "回收视图"
        }

        override fun newInstance(): ComponentX = RecyclerViewComponent()

    }
}