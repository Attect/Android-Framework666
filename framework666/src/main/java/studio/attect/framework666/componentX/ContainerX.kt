package studio.attect.framework666.componentX

import android.os.Bundle

/**
 * 容器X
 * 用于承载ComponentX
 *
 * 告知ComponentX：
 * 是否应该显示返回箭头
 * 是否需要处理屏幕安全距离
 *
 * 以及处理ComponentX传递的结果
 *
 * @author Attect
 */
interface ContainerX {
    /**
     * 是否应该显示返回箭头
     */
    fun shouldShowBackAllow(): Boolean

    /**
     * 是否保持屏幕安全范围
     */
    fun shouldKeepScreenSafeArea(): Boolean

    /**
     * 处理结果数据
     */
    fun handleResult(resultCode: Int, bundle: Bundle)

}