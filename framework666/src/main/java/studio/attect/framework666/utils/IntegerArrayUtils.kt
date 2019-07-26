package studio.attect.framework666.utils

import android.os.Build
import java.util.*

object IntegerArrayUtils {
    @JvmStatic
    fun toIntArray(integerArray: Array<Int?>?): IntArray {
        integerArray?.let { integers ->
            if (integers.isEmpty()) return IntArray(0)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Arrays.stream(integers).mapToInt { if (it == null) return@mapToInt 0 else return@mapToInt it.toInt() }.toArray()
            } else {
                val result = IntArray(integers.size)
                for (i in integers.indices) {
                    val value = integers[i]
                    result[i] = value ?: 0
                }
                result
            }
        }
        return IntArray(0)
    }
}
