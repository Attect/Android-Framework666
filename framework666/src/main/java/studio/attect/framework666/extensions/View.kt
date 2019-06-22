package studio.attect.framework666.extensions

import android.content.Context
import android.os.ResultReceiver
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * 唤出软键盘
 * 并在此View上输入
 *
 * 隐藏 @see Activity.hideSoftKeyboard
 *
 * @param flag 0/InputMethodManager.HIDE_IMPLICIT_ONLY/InputMethodManager.HIDE_NOT_ALWAYS
 * @param receiver android.os.ResultReceiver
 */
@JvmOverloads
fun View.showSoftKeyboard(flag: Int = 0, receiver: ResultReceiver? = null) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, flag, receiver)
}