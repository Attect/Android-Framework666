package studio.attect.framework666.extensions

import android.graphics.PorterDuff
import android.view.Menu
import androidx.annotation.ColorInt
import androidx.core.view.forEach

/**
 * 给所有图标设置颜色
 * 前提是有
 * 后添加的无效
 *
 * @param color 颜色值
 * @param mode 上色模式
 */
@JvmOverloads
fun Menu.setAllIconColor(@ColorInt color: Int, mode: PorterDuff.Mode = PorterDuff.Mode.SRC_ATOP) {
    this.forEach {
        it.setIconColor(color, mode)
    }
}