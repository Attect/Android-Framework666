package studio.attect.framework666.componentX

import android.os.Bundle

/**
 * ComponentX中接收到的参数
 * 其实就是传递给Fragment的Argument啦
 *
 * 此类主要用于规范化，使用Bean来进行参数构建和使用
 * 可以设定默认值、避免粗心遗漏数据或类型犯错
 *
 * @author Attect
 */
interface ArgumentX {
    /**
     * Arguments.fromBundle(Fragment.arguments)
     */
    fun fromBundle(bundle: Bundle?)

    fun toBundle(): Bundle
}