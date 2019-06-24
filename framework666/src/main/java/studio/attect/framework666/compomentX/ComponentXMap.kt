package studio.attect.framework666.compomentX

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
object ComponentXMap : HashMap<String, ComponentXCompanion>() {

    fun mark(componentXCompanion: ComponentXCompanion) {
        put(componentXCompanion.getTag(), componentXCompanion)
    }

    fun go(tag: String?): ComponentX {
        tag?.let { t ->
            get(t)?.let {
                return it.newInstance()
            }
        }
        val componentX = NotFoundComponentX.Companion.newInstance()
        val arguments = Arguments()
        arguments.tagName = tag ?: "NULL"
        return componentX
    }
}