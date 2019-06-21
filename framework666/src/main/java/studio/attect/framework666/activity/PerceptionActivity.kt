package studio.attect.framework666.activity

import android.media.AudioManager
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.irozon.sneaker.Sneaker
import studio.attect.framework666.Logcat
import studio.attect.framework666.debug
import studio.attect.framework666.extensions.coverToTTSFriendlyString
import studio.attect.framework666.helper.Rumble
import studio.attect.framework666.interfaces.PerceptionComponent
import studio.attect.framework666.waring
import java.util.*
import kotlin.collections.HashMap

/**
 * 提供用户感知的Activity
 * 提示音、振动、TTS语音（仅处理中文）
 *
 * @author Attect
 */
abstract class PerceptionActivity : OnBackPressedQueueActivity(), PerceptionComponent {
    private val speakQueue = arrayListOf<String>()
    private var textToSpeechInitLock = false
    private var textToSpeechDone = false

    var textToSpeech: TextToSpeech? = null

    /**
     * 给用户一个振动反馈
     */
    override fun vibrator(level: PerceptionComponent.VibratorLevel) {
        when (level) {
            PerceptionComponent.VibratorLevel.LIGHT -> Rumble.makePattern().beat(10).playPattern()
            PerceptionComponent.VibratorLevel.MEDIUM -> Rumble.makePattern().beat(200).playPattern()
            PerceptionComponent.VibratorLevel.HEAVY -> Rumble.makePattern().beat(1000).playPattern()
            PerceptionComponent.VibratorLevel.NONE -> {
            }
        }
    }

    /**
     * 通过tts引擎生成语音播放给用户
     * 以铃声通道
     * 此段代码仅处理中文
     */
    override fun speakText(text: String) {
        speakQueue.add(text)
        if (textToSpeechInitLock) return
        if (textToSpeech == null || !textToSpeechDone) {
            textToSpeechInitLock = true
            textToSpeech = TextToSpeech(this, TextToSpeech.OnInitListener {
                if (it == TextToSpeech.SUCCESS) {
                    textToSpeech?.let {
                        debug("textToSpeech init done")
                        textToSpeechInitLock = false
                        textToSpeechDone = true
                        handleSpeakQueue()
                    }
                }
            })
        } else if (textToSpeechDone) {
            handleSpeakQueue()
        }

    }

    private fun handleSpeakQueue() {
        speakQueue.forEach {
            debug("TTS:$it")
            callTextToSpeechToTalk(it)
        }
        speakQueue.clear()
    }

    private fun callTextToSpeechToTalk(text: String) {
        textToSpeech?.let { speech ->
            if (speech.isLanguageAvailable(Locale.SIMPLIFIED_CHINESE) >= TextToSpeech.LANG_AVAILABLE) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val params = Bundle()
                    params.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_NOTIFICATION)
                    params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1f)
                    speech.speak(text, TextToSpeech.QUEUE_ADD, params, text)
                } else {
                    val params = HashMap<String, String>()
                    params.put(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_NOTIFICATION.toString())
                    params.put(TextToSpeech.Engine.KEY_PARAM_VOLUME, "1.0")
                    @Suppress("DEPRECATION") //针对旧版本的调用
                    speech.speak(text, TextToSpeech.QUEUE_ADD, params)
                }
            } else {
                waring("此设备不支持：中文语音输出")
            }
        }

    }

    /**
     * 响一声系统通知音
     */
    override fun makeNotificationSound() {
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(applicationContext, notificationSound)
        ringtone.play()
    }

    /**
     * 顶部覆盖Appbar给用户一个醒目的提示
     */
    override fun showTopNotify(
        felling: PerceptionComponent.NotifyFelling,
        title: String,
        message: String,
        vibratorLevel: PerceptionComponent.VibratorLevel,
        speak: Boolean
    ) {
        val sneakBar = Sneaker.with(this).setTitle(title).setMessage(message).autoHide(false)
        when (felling) {
            PerceptionComponent.NotifyFelling.SUCCESS -> {
                sneakBar.sneakSuccess()
            }
            PerceptionComponent.NotifyFelling.WARING -> {
                sneakBar.sneakWarning()
            }
            PerceptionComponent.NotifyFelling.ERROR -> {
                sneakBar.sneakError()
            }
        }

        vibrator(vibratorLevel)
        makeNotificationSound()
        if (speak) {
            speakText(title.coverToTTSFriendlyString())
            speakText(message.coverToTTSFriendlyString())
        }
    }


}