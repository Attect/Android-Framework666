package studio.attect.framework666.simple

import android.util.Log
import studio.attect.framework666.interfaces.LogcatPrinter

/**
 * 标准Logcat打印器
 * 会将message按每MAX_LOG_SIZE长度分割输出以避免实际输出长度限制
 *
 * @author Attect
 */
class NormalLogcatPrinter : LogcatPrinter {
    override fun d(tag: String, message: String) {
        for (i in 0..message.length / MAX_LOG_SIZE) {
            val start = i * MAX_LOG_SIZE
            var end = (i + 1) * MAX_LOG_SIZE
            end = if (end > message.length) message.length else end
            Log.d(tag, message.substring(start, end))
        }
    }

    override fun i(tag: String, message: String) {
        for (i in 0..message.length / MAX_LOG_SIZE) {
            val start = i * MAX_LOG_SIZE
            var end = (i + 1) * MAX_LOG_SIZE
            end = if (end > message.length) message.length else end
            Log.d(tag, message.substring(start, end))
        }
    }

    override fun w(tag: String, message: String) {
        for (i in 0..message.length / MAX_LOG_SIZE) {
            val start = i * MAX_LOG_SIZE
            var end = (i + 1) * MAX_LOG_SIZE
            end = if (end > message.length) message.length else end
            Log.d(tag, message.substring(start, end))
        }
    }

    override fun e(tag: String, message: String) {
        for (i in 0..message.length / MAX_LOG_SIZE) {
            val start = i * MAX_LOG_SIZE
            var end = (i + 1) * MAX_LOG_SIZE
            end = if (end > message.length) message.length else end
            Log.d(tag, message.substring(start, end))
        }
    }

    companion object {

        /**
         * 默认分割长度
         * 过长可能导致不完整（根据设备和环境）
         */
        @JvmStatic
        val MAX_LOG_SIZE = 1000
    }
}