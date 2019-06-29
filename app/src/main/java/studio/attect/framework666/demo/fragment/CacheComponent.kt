package studio.attect.framework666.demo.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.steamcrafted.materialiconlib.IconValue
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder
import studio.attect.framework666.FragmentX
import studio.attect.framework666.compomentX.ComponentX
import studio.attect.framework666.compomentX.ComponentXCompanion
import studio.attect.framework666.demo.R

class CacheComponent : FragmentX() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.component_cache, container, false)
    }

    companion object : ComponentXCompanion {
        override fun getTag(): String = "cache_test"

        override fun getIcon(context: Context?): Drawable? {
            context?.let {
                return MaterialDrawableBuilder.with(context).setIcon(IconValue.FILE_UNDO).setSizeDp(24).setColor(Color.WHITE).build()
            }
            return null
        }

        override fun getName(context: Context?): String {
            context?.let {
                return it.getString(R.string.cache_component_title)
            }
            return "缓存测试"
        }

        override fun newInstance(): ComponentX = CacheComponent()

    }
}