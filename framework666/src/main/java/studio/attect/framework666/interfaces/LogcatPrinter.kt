package studio.attect.framework666.interfaces

/**
 * 发布后Logcat输出处理程序
 * d/i/w/e
 *
 * 所有message保持原始长度和连续（未经任何处理）
 * @author attect
 */
interface LogcatPrinter {
    /**
     * Debug级别
     * @param tag 辨识标签
     * @param message 信息
     */
    fun d(tag: String, message: String)

    /**
     * 关键信息级别
     * @param tag 辨识标签
     * @param message 信息
     */
    fun i(tag: String, message: String)

    /**
     * 警告信息级别
     * @param tag 辨识标签
     * @param message 信息
     */
    fun w(tag: String, message: String)

    /**
     * 错误信息级别
     * @param tag 辨识标签
     * @param message 信息
     */
    fun e(tag: String, message: String)
}