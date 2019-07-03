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

/**
 * 普通的组件
 *
 * @author Attect
 */
class NormalComponent : FragmentX() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.component_normal, container, false)
    }

    companion object : ComponentXCompanion {
        override fun getTag(): String = "normal_component"

        override fun getIcon(context: Context?): Drawable? {
            context?.let {
                val builder = MaterialDrawableBuilder.with(it).apply {
                    setIcon(IconValue.ANDROID)
                    setColor(Color.WHITE)
                    setSizeDp(24)
                }

                return builder.build()
            }
            return null
        }

        override fun getName(context: Context?): String {
            context?.let {
                return context.getString(R.string.normal_component_title)
            }
            return "普通组件"
        }

        override fun getColor(context: Context?): Int? = null

        override fun newInstance(): ComponentX = NormalComponent()

    }
}