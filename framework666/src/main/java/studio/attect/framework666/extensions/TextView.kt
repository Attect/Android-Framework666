package studio.attect.framework666.extensions

import android.os.Build
import android.text.Html
import android.widget.TextView

/**
 * 设置html文本到TextView中
 * 对高低版本的系统做了处理
 *
 * @param html HTML文本
 * @param flags 默认Html.FROM_HTML_SEPARATOR_LINE_BREAK_HEADING)
 */
@Suppress("DEPRECATION")
@JvmOverloads
fun TextView.setHtml(html: String?, flags: Int = 2) {
    if (html == null) {
        text = ""
        return
    }
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, flags)
    } else {
        Html.fromHtml(html)
    }
}