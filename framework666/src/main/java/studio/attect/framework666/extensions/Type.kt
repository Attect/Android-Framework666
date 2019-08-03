package studio.attect.framework666.extensions

import java.lang.reflect.Type

/**
 * 获取Type的简易名称
 */
val Type.rawTypeName: String
    get() {
        val symbolPosition = toString().indexOf("<")
        if (symbolPosition > 0) {
            return toString().replace("class ", "", false).substring(0, symbolPosition)
        } else {
            return toString().replace("class ", "", false)
        }
    }