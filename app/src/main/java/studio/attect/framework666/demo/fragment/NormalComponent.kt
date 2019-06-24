package studio.attect.framework666.demo.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import net.steamcrafted.materialiconlib.IconValue
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder
import studio.attect.framework666.FragmentX
import studio.attect.framework666.compomentX.ComponentX
import studio.attect.framework666.compomentX.ComponentXMaker
import studio.attect.framework666.demo.R

/**
 * 普通的组件
 *
 * @author Attect
 */
class NormalComponent : FragmentX(), ComponentX {

    companion object : ComponentXMaker {
        override fun getTag(): String = "normal_component"

        override fun getIcon(context: Context?): Drawable? {
            val builder = MaterialDrawableBuilder.with(context).apply {
                setIcon(IconValue.ANDROID)
                setColor(Color.WHITE)
                setSizeDp(24)
            }

            return builder.build()
        }

        override fun getName(context: Context?): String {
            context?.let {
                return context.getString(R.string.normal_component_title)
            }
            return "普通组件"
        }

        override fun newInstance(): ComponentX = NormalComponent()

    }
}