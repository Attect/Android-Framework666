package studio.attect.framework666

import studio.attect.framework666.interfaces.LogcatPrinter
import studio.attect.framework666.simple.NormalLogcatPrinter

/**
 * Logcat输出
 * 与Log使用方法非常相似
 *
 * @author Attect
 */
object Logcat {
    /**
     * 输出时用于辨别的tag信息
     * 可以修改
     */
    @JvmStatic
    var TAG = "APP"

    /**
     * 发布版本的输出打印器
     * 一些错误跟踪扩展可以在用于运行时继续收集打印数据，有助于排错
     * 也可决定是否在用户设备的Logcat空间输出相关内容
     *
     * 例子：
     * 如果你想在发布版本也保持正常输出logcat，则使用
     * releaseLogcatPrinters.add(NormalLogcatPrinter())
     */
    @JvmStatic
    val releaseLogcatPrinters = arrayListOf<LogcatPrinter>()


    @JvmStatic
    val defaultLogcatPrinter = NormalLogcatPrinter()

    @JvmStatic
    fun d(tag: String = TAG, message: String) {
        if (RuntimeBuildConfig.DEBUG) {
            defaultLogcatPrinter.d(tag, message)
        } else if (releaseLogcatPrinters.isNotEmpty()) {
            releaseLogcatPrinters.forEach {
                it.d(tag, message)
            }
        }
    }

    @JvmStatic
    fun d(message: String) = d(TAG, message)

    @JvmStatic
    fun w(tag: String = TAG, message: String) {
        if (RuntimeBuildConfig.DEBUG) {
            defaultLogcatPrinter.w(tag, message)
        } else if (releaseLogcatPrinters.isNotEmpty()) {
            releaseLogcatPrinters.forEach {
                it.w(tag, message)
            }
        }
    }

    @JvmStatic
    fun w(message: String) = w(TAG, message)


    @JvmStatic
    fun i(tag: String = TAG, message: String) {
        if (RuntimeBuildConfig.DEBUG) {
            defaultLogcatPrinter.i(tag, message)
        } else if (releaseLogcatPrinters.isNotEmpty()) {
            releaseLogcatPrinters.forEach {
                it.i(tag, message)
            }
        }
    }

    @JvmStatic
    fun i(message: String) = i(TAG, message)


    @JvmStatic
    fun e(tag: String = TAG, message: String) {
        if (RuntimeBuildConfig.DEBUG) {
            defaultLogcatPrinter.e(tag, message)
        } else if (releaseLogcatPrinters.isNotEmpty()) {
            releaseLogcatPrinters.forEach {
                it.e(tag, message)
            }
        }
    }

    @JvmStatic
    fun e(message: String) = i(TAG, message)

}

fun String?.debug(tag: String = Logcat.TAG) = Logcat.d(tag, this ?: "[null]")
fun String?.info(tag: String = Logcat.TAG) = Logcat.i(tag, this ?: "[null]")
fun String?.waring(tag: String = Logcat.TAG) = Logcat.w(tag, this ?: "[null]")
fun String?.error(tag: String = Logcat.TAG) = Logcat.e(tag, this ?: "[null]")