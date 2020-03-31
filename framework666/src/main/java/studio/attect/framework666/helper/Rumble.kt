@file:Suppress("DEPRECATION")

package studio.attect.framework666.helper;


import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import studio.attect.framework666.waring
import java.util.*

/**
 * 振动API调用兼容
 * 全局单例，在Application中初始化
 *
 * @url https://github.com/josephrubin/Rumble-4-Android
 * @author josephrubin
 */
object Rumble {
    private var vibrator: Vibrator? = null
    private var rumbleDisabled: Boolean = false

    fun init(applicationContext: Context) {
        vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        rumbleDisabled = vibrator == null || !vibrator!!.hasVibrator()
        if (rumbleDisabled) {
            waring("设备上没有安装振动电机，或者震动电机已被禁用（无权限使用），相关感知反馈调用将会无效")
        }
    }

    private fun apiIndependentVibrate(milliseconds: Long) {
        if (rumbleDisabled) {
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator?.vibrate(milliseconds)
        }
    }

    private fun apiIndependentVibrate(pattern: LongArray) {
        if (rumbleDisabled) {
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createWaveform(pattern, -1))
        } else {
            vibrator?.vibrate(pattern, -1)
        }
    }

    fun stop() {
        if (rumbleDisabled) {
            return
        }

        vibrator?.cancel()
    }

    fun once(milliseconds: Long) {
        apiIndependentVibrate(milliseconds)
    }

    fun makePattern(): RumblePattern {
        return RumblePattern()
    }

    class RumblePattern internal constructor() {
        private val internalPattern: MutableList<Long>
        var isLocked: Boolean = false
            private set

        init {
            isLocked = false
            internalPattern = ArrayList()
            internalPattern.add(0L)
        }

        fun beat(milliseconds: Long): RumblePattern {
            if (isLocked) {
                throw IllegalStateException("RumblePattern is locked! Cannot modify its state.")
            }

            if (internalPattern.size % 2 == 0) {
                internalPattern[internalPattern.size - 1] = internalPattern[internalPattern.size - 1] + milliseconds
            } else {
                internalPattern.add(milliseconds)
            }
            return this
        }

        fun rest(milliseconds: Long): RumblePattern {
            if (isLocked) {
                throw IllegalStateException("RumblePattern is locked! Cannot modify its state.")
            }

            if (internalPattern.size % 2 == 0) {
                internalPattern.add(milliseconds)
            } else {
                internalPattern[internalPattern.size - 1] = internalPattern[internalPattern.size - 1] + milliseconds
            }
            return this
        }

        fun lock() {
            if (isLocked) {
                throw IllegalStateException("RumblePattern is already locked! Use isLocked() to check.")
            }
            isLocked = true
        }

        @JvmOverloads
        fun playPattern(numberOfTimes: Int = 1) {
            if (numberOfTimes < 0) {
                throw IllegalArgumentException("numberOfTimes must be >= 0")
            }

            val endsWithRest = internalPattern.size % 2 == 0

            // We have a List<Long> but we need a long[]. We can't simply use toArray because that yields a Long[].
            // Reserve enough space to hold the full pattern as many times as necessary to play the pattern the right number of times.
            val primitiveArray =
                LongArray(internalPattern.size * numberOfTimes - if (endsWithRest) 0 else numberOfTimes - 1)
            for (i in internalPattern.indices) {
                // Auto unboxing converts each Long to a long.
                primitiveArray[i] = internalPattern[i]
            }

            // Copy the array into itself to duplicate the pattern enough times.
            // Not a simple copy - we must overlay the copies if the pattern ends in a rest.
            //   R    B    R
            // [100, 300, 500]
            //             +
            //           [100, 300, 500]
            for (i in 1 until numberOfTimes) {
                for (j in internalPattern.indices) {
                    val k = j + internalPattern.size * i - if (endsWithRest) 0 else i
                    primitiveArray[k] += primitiveArray[j]
                }
            }

            apiIndependentVibrate(primitiveArray)
        }

        override fun toString(): String {
            return "RumblePattern{" +
                    "internalPattern=" + internalPattern +
                    "}"
        }
    }
}
