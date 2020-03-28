package studio.attect.framework666.componentX

import android.os.Bundle
import androidx.fragment.app.Fragment
import studio.attect.framework666.simple.NotFoundComponentX
import studio.attect.framework666.simple.NotFoundComponentX.Arguments

/**
 * ComponentX的地图
 * 根据String类型的Tag返回对应的ComponentXMaker
 * 但也可能找不到
 *
 * 推荐在Application中add（可以称之为注册吧）App拥有的所有
 * 当然如果你用了一些插件框架，也要注意在插件加载时将插件中的add进来
 *
 * 为什么tag是字符串类型：为了序列化不出事
 *
 * @author Attect
 */
object ComponentXMap : HashMap<String, ComponentXProvider>() {

    fun mark(componentXProvider: ComponentXProvider) {
        put(componentXProvider.getTag(), componentXProvider)
    }

    fun detail(tag: String?): ComponentXProvider {
        tag?.let { t ->
            get(t)?.let {
                return it
            }
        }

        return NotFoundComponentX.Companion
    }

    @JvmOverloads
    fun go(tag: String?, arguments: Bundle? = null): Fragment {
        tag?.let { t ->
            get(t)?.let {
                val componentX = it.newInstance()
                if (componentX is NavigatedComponentX) {
                    return componentX.getComponentXFragmentInstance(arguments)
                }
                val fragment = componentX.getComponentXFragmentInstance()
                if (arguments != null) {
                    fragment.arguments = arguments
                }
                return fragment
            }
        }
        val componentX = NotFoundComponentX.newInstance()
        val notFoundArguments = Arguments()
        notFoundArguments.tagName = tag ?: "NULL"
        val fragment = componentX.getComponentXFragmentInstance()
        fragment.arguments = notFoundArguments.toBundle()
        return fragment
    }
}