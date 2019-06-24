package studio.attect.framework666.simple

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.not_found_componetx.*
import studio.attect.framework666.FragmentX
import studio.attect.framework666.R
import studio.attect.framework666.compomentX.ArgumentX
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argumentX = Arguments()
        argumentX.fromBundle(arguments)

        information.text = getString(R.string.componentx_not_found_information).format(argumentX.tagName)
    }

    class Arguments : ArgumentX {
        private val TAG = "tag"
        var tagName = ""

        override fun fromBundle(bundle: Bundle?) {
            bundle?.let {
                if (it.containsKey(TAG)) {
                    tagName = it.getString(TAG, "")
                }
            }
        }

        override fun toBundle(): Bundle {
            val bundle = Bundle()
            bundle.putString(TAG, tagName)
            return bundle
        }

    }

    companion object : ComponentXMaker {

        override fun getTag(): String = "" //就是这么任性，空字符串就是此ComponentX

        override fun getIcon(context: Context?): Drawable? {
            context?.let {
                ResourcesCompat.getDrawable(context.resources, android.R.drawable.ic_dialog_alert, context.theme)
            }
            return null
        }


        override fun getName(context: Context?): String {
            context?.let {
                return context.resources.getString(R.string.componentx_not_found)
            }
            return "功能缺失"
        }

        override fun newInstance(): ComponentX = NotFoundComponentX()

    }
}