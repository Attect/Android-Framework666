package studio.attect.framework666.extensions

import java.security.MessageDigest
import java.util.*

@JvmOverloads
fun ByteArray.toHexString(
    upper: Boolean = true,
    headSpace: Boolean = true,
    locale: Locale = Locale.getDefault()
): String {
    val builder = StringBuilder()
    this.forEachIndexed { _, byte ->
        if (builder.isNotEmpty() && headSpace) {
            builder.append(" ")
        }
        builder.append("%02X".format(byte))
    }
    return if (upper) {
        builder.toString().toUpperCase(locale)
    } else {
        builder.toString().toLowerCase(locale)
    }
}

/**
 * ByteArray直接计算MD5
 */
fun ByteArray.toMD5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this)
    return bytes.toHexString(upper = true, headSpace = false)
}