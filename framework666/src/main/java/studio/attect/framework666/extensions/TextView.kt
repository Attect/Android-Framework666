package studio.attect.framework666.extensions

import android.os.Build
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
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

/**
 * 设置文字点击
 * @see <a href="https://stackoverflow.com/a/45727769">How to set the part of the text view is clickable</a>
 */
fun TextView.makeTextClick(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        val startIndexOfLink = this.text.toString().indexOf(link.first)
        spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    this.movementMethod = LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}