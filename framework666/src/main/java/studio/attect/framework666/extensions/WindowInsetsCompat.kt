package studio.attect.framework666.extensions

import androidx.core.view.WindowInsetsCompat

/**
 * 屏幕左边安全显示距离
 * 屏幕旋转以后，一些屏幕缺口有可能在左边
 */
val WindowInsetsCompat.currentSafeLeft: Int
    get() {
        var size = 0
        displayCutout?.safeInsetLeft?.let {
            size = it
        }
        if (size == 0) size = systemWindowInsetLeft
        return size
    }

/**
 * 屏幕顶部安全显示距离
 * 包含状态栏
 */
val WindowInsetsCompat.currentSafeTop: Int
    get() {
        var size = 0
        displayCutout?.safeInsetTop?.let {
            size = it
        }
        if (size == 0) size = systemWindowInsetTop
        return size
    }

/**
 * 屏幕右边安全显示距离
 * 屏幕旋转以后，一些屏幕缺口可能在右边
 */
val WindowInsetsCompat.currentSafeRight: Int
    get() {
        var size = 0
        displayCutout?.safeInsetRight?.let {
            size = it
        }
        if (size == 0) size = systemWindowInsetRight
        return size
    }

/**
 * 屏幕底部安全显示距离
 * 如果启用了180度选钻
 */
val WindowInsetsCompat.currentSafeBottom: Int
    get() {
        var size = 0
        displayCutout?.safeInsetBottom?.let {
            size = it
        }
        if (size == 0) size = systemWindowInsetBottom
        return size
    }
