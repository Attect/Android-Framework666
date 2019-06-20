package studio.attect.framework666.extensions

import android.os.Build
import android.util.TypedValue
import android.view.ContextThemeWrapper

/**
 * 获得系统自带点击波纹背景资源id
 */
val ContextThemeWrapper.selectableItemBackgroundResource: Int
    get() {
        val outValue = TypedValue()
        theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        return outValue.resourceId
    }


val ContextThemeWrapper.selectableItemBackgroundBorderless: Int
    get() {
        val outValue = TypedValue()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true)
        }
        return outValue.resourceId
    }