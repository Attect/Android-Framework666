package studio.attect.framework666.extensions

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorInt
import studio.attect.framework666.magicChange.flymeSetStatusBarLightMode
import studio.attect.framework666.magicChange.miUISetStatusBarLightMode

val Activity.rootView: View
    get() {
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        return if (viewGroup.childCount == 0) viewGroup else viewGroup.getChildAt(0)
    }


/**
 * 设置状态栏的颜色
 * 如果是Android LOLLIPOP以下，则没有效果
 *
 * @param color 色彩int值，非资源id
 */
fun Activity.setStatusBarColor(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = window
        //这一步最好要做，因为如果这两个flag没有清除的话下面没有生效
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        getWindow().statusBarColor = color
    }
}

/**
 * 设置状态栏为透明
 * 一些版本的操作系统不支持图标明暗风格变更
 * 使用此框架默认全局都为透明状态栏，不透明效果自己进行伪实现(滑稽)
 *
 * @param lightIconStyle 图标明暗风格,true图标为白色
 */
@JvmOverloads
fun Activity.transparentStatusBar(lightIconStyle: Boolean = true) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !lightIconStyle) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }
    miUISetStatusBarLightMode(this.window, !lightIconStyle)
    flymeSetStatusBarLightMode(this.window, !lightIconStyle)
}