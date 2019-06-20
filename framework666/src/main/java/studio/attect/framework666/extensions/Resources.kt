package studio.attect.framework666.extensions

import android.content.res.Resources

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
