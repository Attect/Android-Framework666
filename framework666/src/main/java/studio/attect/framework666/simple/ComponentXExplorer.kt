package studio.attect.framework666.simple

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.componentx_explorer.*
import studio.attect.framework666.FragmentX
import studio.attect.framework666.R
import studio.attect.framework666.compomentX.ComponentX
import studio.attect.framework666.compomentX.ComponentXCompanion
import studio.attect.framework666.compomentX.ComponentXMap
import studio.attect.framework666.interfaces.UniqueData

/**
 * 浏览所有注册了的ComponentX
 * 用于辅助测试
 *
 * @author Attect
 */
class ComponentXExplorer : FragmentX() {

    private val recyclerViewAdapter = SimpleRecyclerViewAdapter()
    private val layoutManager by lazy { LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.componentx_explorer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = layoutManager

        //设置分割线
        val divider = DividerItemDecoration(requireContext(), layoutManager.orientation)
        recyclerView.addItemDecoration(divider)

        recyclerViewAdapter.registerViewHolder(R.layout.list_item_compoment, MyViewHolder::class.java)
        recyclerView.adapter = recyclerViewAdapter

        val componentXList = arrayListOf<ItemData>()
        ComponentXMap.keys.forEach { key ->
            ComponentXMap[key]?.let {
                val itemData = ItemData()
                itemData.componentXCompanion = it
                componentXList.add(itemData)
            }
        }
        recyclerViewAdapter.addMoreData(componentXList, R.layout.list_item_compoment)
    }

    class ItemData : UniqueData {
        lateinit var componentXCompanion: ComponentXCompanion

        override fun uniqueTag(): Any = componentXCompanion.getTag()
    }


    class MyViewHolder(itemView: View) : SimpleRecyclerViewAdapter.BasicViewHolder<ItemData>(itemView) {
        private val iconView = itemView.findViewById<AppCompatImageView>(R.id.icon).apply {
            setColorFilter(ResourcesCompat.getColor(itemView.context.resources, R.color.colorPrimary, itemView.context.theme))
        }
        private val titleView = itemView.findViewById<AppCompatTextView>(R.id.title)
        private val tagView = itemView.findViewById<AppCompatTextView>(R.id.tag)

        override fun applyData(data: ItemData, position: Int) {
            iconView.setImageDrawable(data.componentXCompanion.getIcon(itemView.context))
            titleView.text = data.componentXCompanion.getName(itemView.context)
            tagView.text = data.componentXCompanion.getTag()
        }
    }

    companion object : ComponentXCompanion {
        override fun getTag(): String = "componentx_explorer"

        override fun getIcon(context: Context?): Drawable? {
            context?.let {
                return ResourcesCompat.getDrawable(context.resources, android.R.drawable.btn_star, context.theme)
            }
            return null
        }

        override fun getName(context: Context?): String {
            context?.let {
                return it.getString(R.string.componentx_explorer)
            }
            return "组件浏览"
        }

        override fun newInstance(): ComponentX = ComponentXExplorer()

    }
}