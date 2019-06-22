package studio.attect.framework666.extensions

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.RawRes
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import kotlin.math.roundToInt

/**
 * 系统动画时间：短
 */
val Resources.systemShortAnimTime: Long
    get() = getInteger(android.R.integer.config_shortAnimTime).toLong()

/**
 * 系统动画时间：中等
 */
val Resources.systemMediumAnimTime: Long
    get() = getInteger(android.R.integer.config_mediumAnimTime).toLong()

/**
 * 系统动画时间：长
 */
val Resources.systemLongAnimTime: Long
    get() = getInteger(android.R.integer.config_longAnimTime).toLong()

/**
 * dp转像素
 */
fun Resources.dp2px(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics).roundToInt()
}

/**
 * 像素转dp
 */
fun Resources.px2dp(px: Int): Float {
    return px / displayMetrics.density
}

/**
 * sp转像素
 * 只用与字体
 * 与用户设置有关
 */
fun Resources.sp2px(sp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, displayMetrics).roundToInt()
}

/**
 * 像素转sp
 * 只用于字体
 * 与用户设置有关
 */
fun Resources.px2sp(px: Int): Float {
    return px / displayMetrics.scaledDensity
}

/**
 * pt转像素
 * 字体尺寸
 * 1pt为1/72英寸
 */
fun Resources.pt2px(pt: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, pt, displayMetrics).roundToInt()
}

/**
 * 像素转pt
 * 字体尺寸
 * 1pt为1/72英寸
 */
fun Resources.px2pt(px: Int): Float {
    return px / (displayMetrics.xdpi * (1.0f / 72))
}

/**
 * 英寸转像素
 * 1英寸约为2.54厘米
 */
fun Resources.in2px(inch: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, inch, displayMetrics).roundToInt()
}

/**
 * 像素转英寸
 * 1英寸约为2.54厘米
 */
fun Resources.px2in(px: Int): Float {
    return px / displayMetrics.xdpi
}

/**
 * 毫米转像素
 */
fun Resources.mm2px(mm: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm, displayMetrics).roundToInt()
}

/**
 * 像素转毫米
 */
fun Resources.px2mm(px: Int): Float {
    return px / (displayMetrics.xdpi * (1.0f / 25.4f))
}

/**
 * 从raw资源中读取一个字符串数据文件
 *
 * @param id 资源id
 * @return
 */
fun Resources.getRawText(@RawRes id: Int): String {
    val inputStream = openRawResource(id)
    val reader = BufferedReader(InputStreamReader(inputStream))
    val stringBuilder = StringBuilder()
    try {
        reader.readLines().forEach {
            stringBuilder.append(it)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return stringBuilder.toString()
}