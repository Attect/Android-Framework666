package studio.attect.framework666.extensions

import java.text.DecimalFormat

/**
 * long数据转可阅读的粗略数据大小字符串
 * 按标准单位，而不是微软的坑爹单位
 * @param keepZeroEnd 保持两位小数
 */
fun Long.toDataSizeString(keepZeroEnd: Boolean = true): String {
    var size = this
    var result: Double
    if (this < 1024) return "${this}Byte"

    val decimalFormat = DecimalFormat(if (keepZeroEnd) "0.00" else "#.##")
    result = size.toDouble()
    result /= 1024.0
    if (result < 1024) return decimalFormat.format(result) + "KiB"
    size /= 1024

    result = size.toDouble()
    result /= 1024.0
    if (result < 1024) return decimalFormat.format(result) + "MiB"
    size /= 1024

    result = size.toDouble()
    result /= 1024.0
    if (result < 1024) return decimalFormat.format(result) + "GiB"
    size /= 1024

    result = size.toDouble()
    result /= 1024.0
    if (result < 1024) return decimalFormat.format(result) + "TiB"
    size /= 1024

    result = size.toDouble()
    result /= 1024.0
    if (result < 1024) return decimalFormat.format(result) + "PiB"
    size /= 1024

    result = size.toDouble()
    result /= 1024.0
    if (result < 1024) return decimalFormat.format(result) + "EiB"
    size /= 1024

    //其实下面到不了了，long长度不足
    result = size.toDouble()
    result /= 1024.0
    return if (result < 1024) decimalFormat.format(result) + "ZiB" else "超级大"
}