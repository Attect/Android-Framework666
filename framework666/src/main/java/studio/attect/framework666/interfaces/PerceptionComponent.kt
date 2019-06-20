package studio.attect.framework666.interfaces

/**
 * 为用户感官提供感知的组件
 *
 * @author Attect
 */
interface PerceptionComponent {
    /**
     * 振动
     * @param level 振动等级
     */
    fun vibrator(level: VibratorLevel)

    /**
     * TTS语音读出给定字符串
     */
    fun speakText(text: String)

    /**
     * 发出一个提示音
     */
    fun makeNotificationSound()

    /**
     * 顶部弹出一个非常明显的内部UI通知
     * 告知用户一些信息和状态
     *
     */
    fun showTopNotify(
        felling: NotifyFelling,
        title: String,
        message: String,
        vibratorLevel: VibratorLevel,
        speak: Boolean
    )

    /**
     * 振动等级
     * 轻
     * 中
     * 重
     */
    enum class VibratorLevel {
        NONE, LIGHT, MEDIUM, HEAVY
    }

    /**
     * 通知状态感觉
     * 成功
     * 警告
     * 错误
     */
    enum class NotifyFelling {
        SUCCESS, WARING, ERROR
    }
}
