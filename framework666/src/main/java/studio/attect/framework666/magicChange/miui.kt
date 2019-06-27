package studio.attect.framework666.magicChange

import android.annotation.SuppressLint
import android.view.Window

/**
 * 设置早期MIUI状态栏黑白模式
 *
 * @param window
 * @param darkIcon 图标是否为暗色
 * @return
 */
@SuppressLint("PrivateApi")
fun miUISetStatusBarLightMode(window: Window?, darkIcon: Boolean): Boolean {
    var result = false
    if (window != null) {
        val clazz = window.javaClass
        try {
            val darkModeFlag: Int
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField =
                clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            if (darkIcon) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
            }
            result = true
        } catch (e: Exception) {

        }

    }
    return result
}