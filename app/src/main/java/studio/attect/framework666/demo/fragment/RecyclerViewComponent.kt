package studio.attect.framework666.demo.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.steamcrafted.materialiconlib.IconValue
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder
import studio.attect.framework666.FragmentX
import studio.attect.framework666.annotations.Component
import studio.attect.framework666.demo.R
import studio.attect.framework666.demo.databinding.ComponentRecyclerViewBinding
import studio.attect.framework666.extensions.systemLongAnimTime
import studio.attect.framework666.interfaces.UniqueData
import studio.attect.framework666.simple.SimpleRecyclerViewAdapter

/**
 * 回收视图Demo组件
 *
 * @author Attect
 */
@Component("simpleRecyclerView")
class RecyclerViewComponent : FragmentX() {
    private val recyclerViewAdapter = SimpleRecyclerViewAdapter(this)
    private val layoutManager by lazy { LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) }
    private val binding by BindView { inflater, container, _ ->
        ComponentRecyclerViewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.setColorSchemeColors(ResourcesCompat.getColor(resources, R.color.colorPrimary, requireActivity().theme))
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.postDelayed({
                binding.swipeRefreshLayout.isRefreshing = false
                resetAllData()
            }, 2000) //模拟两秒后停止
        }

        binding.recyclerView.layoutManager = layoutManager

        //设置分割线
        val divider = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.recyclerView.addItemDecoration(divider)

        recyclerViewAdapter.registerViewHolder(R.layout.list_item_text, TextViewHolder::class.java)
        recyclerViewAdapter.registerViewHolder(R.layout.list_item_text_with_color, TextColorViewHolder::class.java)

        binding.recyclerView.adapter = recyclerViewAdapter

        binding.reset.setOnClickListener {
            resetAllData()
        }
        binding.addOne.setOnClickListener {
            addOneData()
        }
        binding.addMore.setOnClickListener { addMoreData() }
        binding.updateOne.setOnClickListener { updateOneData() }
        binding.updateMore.setOnClickListener { updateMoreData() }
        binding.removeOne.setOnClickListener { removeOneData() }
        binding.removeMore.setOnClickListener { removeMoreData() }
        binding.removeAll.setOnClickListener { recyclerViewAdapter.clearData() }

        resetAllData()
    }

    /**
     * 刷新整个列表
     */
    private fun resetAllData() {
        recyclerViewAdapter.clearData()
        val defaultData = arrayListOf<SimpleRecyclerViewAdapter.SimpleListData<out UniqueData>>()
        for (i in 0 until 10) {
            defaultData.add(SimpleRecyclerViewAdapter.SimpleListData(ItemData(), randomLayoutRes()))
        }
        recyclerViewAdapter.addMoreData(defaultData, 0) //layoutRes已由SimpleListData确定
    }

    /**
     * 添加一个随机数据
     */
    private fun addOneData() {
        val position = (0..recyclerViewAdapter.itemCount).shuffled().last()
        val layoutRes = randomLayoutRes()
        binding.recyclerView.smoothScrollToPosition(position)
        binding.recyclerView.postDelayed(
            {
                val data = ItemData()
                recyclerViewAdapter.addData(data, layoutRes, position)
                Toast.makeText(requireContext(), "添加一个列表项：位置[$position] 布局:$layoutRes 数据:${data.text}", Toast.LENGTH_LONG).show()
            },
            resources.systemLongAnimTime
        )
    }

    /**
     * 添加多个随机数据
     * 随机的布局
     */
    private fun addMoreData() {
        val moreData = arrayListOf<SimpleRecyclerViewAdapter.SimpleListData<ItemData>>()
        val position = (0..recyclerViewAdapter.itemCount).shuffled().last()
        val number = (2..5).shuffled().last()
        for (i in 0 until number) {
            moreData.add(SimpleRecyclerViewAdapter.SimpleListData(ItemData(), randomLayoutRes()))
        }
        binding.recyclerView.smoothScrollToPosition(position)

        binding.recyclerView.postDelayed(
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
        val data = ItemData()
        val position = recyclerViewAdapter.updateData(data, 0, true)
        val layoutRes = randomLayoutRes()
        if (position != null) {
            binding.recyclerView.smoothScrollToPosition(position)
            binding.recyclerView.postDelayed(
                {
                    recyclerViewAdapter.updateData(data, layoutRes)
                    Toast.makeText(requireContext(), "更新一个列表项：位置[$position] 布局:$layoutRes 数据:${data.text}", Toast.LENGTH_LONG).show()
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
        val moreData = arrayListOf<SimpleRecyclerViewAdapter.SimpleListData<ItemData>>()
        val number = (2..10).shuffled().last()
        val position = recyclerViewAdapter.updateMoreData(moreData, 0, true)
        for (i in 0 until number) {
            moreData.add(SimpleRecyclerViewAdapter.SimpleListData(ItemData(), randomLayoutRes()))
        }
        if (position.isNotEmpty()) {
            binding.recyclerView.smoothScrollToPosition(position[0])
            binding.recyclerView.postDelayed(
                {
                    recyclerViewAdapter.updateMoreData(moreData, 0) //layoutRes已经由SimpleListData确定
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
        val data = ItemData()
        val position = recyclerViewAdapter.removeData(data, true)
        if (position != null) {
            binding.recyclerView.smoothScrollToPosition(position)
            binding.recyclerView.postDelayed(
                {
                    recyclerViewAdapter.removeData(data)
                    Toast.makeText(requireContext(), "删除一个列表项：位置[$position]", Toast.LENGTH_LONG).show()
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
        val moreData = arrayListOf<SimpleRecyclerViewAdapter.SimpleListData<ItemData>>()
        val number = (2..10).shuffled().last()
        for (i in 0 until number) {
            moreData.add(SimpleRecyclerViewAdapter.SimpleListData(ItemData(), randomLayoutRes()))
        }
        val position = recyclerViewAdapter.removeMoreData(moreData, true)
        if (position.isNotEmpty()) {
            binding.recyclerView.smoothScrollToPosition(position[0])
            binding.recyclerView.postDelayed(
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
    private fun randomLayoutRes() = arrayListOf(0, R.layout.list_item_text, R.layout.list_item_text_with_color).shuffled().last()

    /**
     * 列表中的数据的结构
     */
    class ItemData : UniqueData {
        var text = (1..50).shuffled().last().toString()
        var color = Color.argb(255, (0..255).shuffled().last(), (0..255).shuffled().last(), (0..255).shuffled().last())
        override fun uniqueTag(): Any = text
    }

    inner class TextViewHolder(itemView: View) : SimpleRecyclerViewAdapter.BasicViewHolder<ItemData>(itemView) {
        private val textView = itemView.findViewById<AppCompatTextView>(R.id.text)

        override fun applyData(data: ItemData, position: Int) {
            textView.text = data.text
        }
    }

    inner class TextColorViewHolder(itemView: View) : SimpleRecyclerViewAdapter.BasicViewHolder<ItemData>(itemView) {
        private val textView = itemView.findViewById<AppCompatTextView>(R.id.text)
        private val colorView = itemView.findViewById<View>(R.id.colorBlock)

        override fun applyData(data: ItemData, position: Int) {
            textView.text = data.text
            colorView.background = ColorDrawable(data.color)
        }
    }

    override fun getName(context: Context?): String? {
        context?.let {
            return context.getString(R.string.recycler_view_demo_title)
        }
        return "回收视图"
    }

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

}