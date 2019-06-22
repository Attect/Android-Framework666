package studio.attect.framework666.extensions

import android.graphics.PorterDuff
import android.view.MenuItem
import androidx.annotation.ColorInt

/**
 * 设置图标颜色
 * 前提是要有图标
 * 之后设置的无效
 *
 * @param color 颜色值
 * @param mode 上色模式
 */
@JvmOverloads
fun MenuItem.setIconColor(@ColorInt color: Int, mode: PorterDuff.Mode = PorterDuff.Mode.SRC_ATOP) {
    icon?.setColorFilter(color, mode)
}