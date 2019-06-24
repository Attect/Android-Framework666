package studio.attect.framework666.simple

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import studio.attect.framework666.FragmentX
import studio.attect.framework666.R
import studio.attect.framework666.compomentX.ComponentX
import studio.attect.framework666.compomentX.ComponentXCompanion

/**
 * 浏览所有注册了的ComponentX
 * 用于辅助测试
 *
 * @author Attect
 */
class ComponentXExplorer : FragmentX() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.componentx_explorer, container, false)
    }

    companion object : ComponentXCompanion {
        override fun getTag(): String = "componentx_explorer"

        override fun getIcon(context: Context?): Drawable? {
            context?.let {
                ResourcesCompat.getDrawable(context.resources, android.R.drawable.btn_star_big_on, context.theme)
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