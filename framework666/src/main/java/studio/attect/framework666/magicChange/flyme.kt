package studio.attect.framework666.magicChange

import android.view.Window
import android.view.WindowManager

/**
 * 设置Flyme状态栏黑白模式
 *
 * @param window
 * @param darkIcon 图标是否为暗色
 * @return
 */
fun flymeSetStatusBarLightMode(window: Window?, darkIcon: Boolean): Boolean {
    var result = false
    if (window != null) {
        try {
            val lp = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java
                .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java
                .getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            if (darkIcon) {
                value = value or bit
            } else {
                value = value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            window.attributes = lp
            result = true
        } catch (e: Exception) {

        }

    }
    return result
}