package studio.attect.framework666.componentX

import studio.attect.framework666.debug
import java.util.*

abstract class ComponentXContainer {

    init {
        debug("framework ComponentXContainer init")
    }


    companion object {

        /**
         * 所有组件X被放入此Map
         */
        private val componentMap: HashMap<String, Class<out ComponentX>> = HashMap()


        fun register(tag: String, componentX: Class<out ComponentX>) {
            componentMap[tag] = componentX
        }

        fun get(tag: String): ComponentX? {
            componentMap[tag]?.let { clazz ->
                return clazz.newInstance()
            }
            return null
        }
    }

}