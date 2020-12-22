package studio.attect.framework666.componentX

import androidx.fragment.app.Fragment
import studio.attect.framework666.debug
import studio.attect.framework666.extensions.setAnyArgument
import studio.attect.framework666.simple.NotFoundComponentX.Companion.NOT_FOUND_COMPONENT_X
import java.util.*

typealias COC = ComponentXContainer

object ComponentXContainer {

    /**
     * 所有组件X的Class被放入此Map
     */
    internal val componentMap: HashMap<String, Class<out ComponentX>> = HashMap()

    /**
     * 向容器中注册一个组件
     */
    fun register(tag: String, componentX: Class<out ComponentX>) {
        componentMap[tag] = componentX
        "[COC] 注册 $tag 指向 ${componentX.simpleName}".debug()
    }

    /**
     * 根据[tag]创建一个组件X
     * 可带一个基本类型参数[argument]
     */
    fun create(tag: String, argument: Any? = null): ComponentX {
        componentMap[tag]?.let { clazz ->
            val componentX = clazz.newInstance()
            argument?.let { args ->
                if (componentX is Fragment) {
                    componentX.setAnyArgument(args)
                }
            }
            //重新考虑Fragment导航实现
//                if (componentX is NavigatedComponentX) {
//                    return componentX.getComponentXFragmentInstance(argumentBundle).apply {
//                        arguments = argumentBundle
//                    } as? ComponentX
//                }
            return componentX
        }
        return create(NOT_FOUND_COMPONENT_X)
    }

    operator fun get(tag: String, argument: Any? = null) = create(tag, argument).toFragment()

    /**
     * 根据tag获得组件X的Class
     */
    fun getClass(tag: String): Class<out ComponentX>? = componentMap[tag]

    /**
     * 根据组件X的Class反查tag
     */
    fun getTag(clazz: Class<out ComponentX>): String {
        componentMap.forEach { (key, value) ->
            if (value == clazz) return key
        }
        return NOT_FOUND_COMPONENT_X
    }


}