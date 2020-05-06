package studio.attect.framework666.simple

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.componentx_not_found.*
import studio.attect.framework666.FragmentX
import studio.attect.framework666.R
import studio.attect.framework666.componentX.ArgumentX

/**
 * ComponentXMap不存在对应目标但又意外调用时返回一个错误提示的ComponentX
 *
 * @author Attect
 */
class NotFoundComponentX : FragmentX() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.componentx_not_found, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argumentX = Arguments()
        argumentX.fromBundle(arguments)

        information.text = getString(R.string.componentx_not_found_information).format(argumentX.tagName)
    }

    class Arguments : ArgumentX {
        private val TAG = "tag"
        var tagName = "not set"

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

    override fun getName(context: Context?): String? {
        context?.let {
            return context.resources.getString(R.string.componentx_not_found)
        }
        return "功能缺失"
    }

    companion object {
        const val NOT_FOUND_COMPONENT_X = "_not_found_"
    }
}