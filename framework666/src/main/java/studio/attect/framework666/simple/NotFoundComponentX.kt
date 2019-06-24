package studio.attect.framework666.simple

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import studio.attect.framework666.FragmentX
import studio.attect.framework666.R
import studio.attect.framework666.compomentX.ComponentX
import studio.attect.framework666.compomentX.ComponentXMaker

/**
 * ComponentXMap不存在对应目标但又意外调用时返回一个错误提示的ComponentX
 *
 * @author Attect
 */
class NotFoundComponentX : FragmentX(), ComponentX {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.not_found_componetx, container, false)
    }

    companion object : ComponentXMaker {
        override fun getTag(): String = "" //就是这么任性，空字符串就是此ComponentX

        override fun getIconResource(): Int = android.R.drawable.ic_dialog_alert //默认给一个系统的警告图标


        override fun getName(context: Context?): String {
            context?.let {
                return context.resources.getString(R.string.componentx_not_found)
            }
            return "功能缺失"
        }

        override fun newInstance(): ComponentX = NotFoundComponentX()

    }
}