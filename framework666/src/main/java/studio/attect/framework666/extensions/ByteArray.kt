package studio.attect.framework666.extensions

import java.lang.StringBuilder

@JvmOverloads
fun ByteArray.toHexString(upper: Boolean = true, headSpace: Boolean = true): String {
    val builder = StringBuilder()
    this.forEachIndexed { _, byte ->
        if (builder.isNotEmpty() && headSpace) {
            builder.append(" ")
        }
        builder.append("%02X".format(byte))
    }
    return if (upper) {
        builder.toString().toUpperCase()
    } else {
        builder.toString().toLowerCase()
    }
}