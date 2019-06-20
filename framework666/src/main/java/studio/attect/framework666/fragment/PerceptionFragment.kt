package studio.attect.framework666.fragment

import android.media.AudioManager
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import studio.attect.framework666.Logcat
import studio.attect.framework666.helper.Rumble
import studio.attect.framework666.interfaces.PerceptionComponent
import studio.attect.framework666.waring
import studio.attect.staticviewmodelstore.StaticViewModelLifecycleFragment
import java.util.*
import kotlin.collections.HashMap

/**
 * 提供用户感知的Fragment
 * 提示音、振动、TTS语音
 *
 * @author Attect
 */
abstract class PerceptionFragment : StaticViewModelLifecycleFragment(), PerceptionComponent {
    override fun vibrator(level: PerceptionComponent.VibratorLevel) {
        when (level) {
            PerceptionComponent.VibratorLevel.LIGHT -> Rumble.makePattern().beat(10).playPattern()
            PerceptionComponent.VibratorLevel.MEDIUM -> Rumble.makePattern().beat(200).playPattern()
            PerceptionComponent.VibratorLevel.HEAVY -> Rumble.makePattern().beat(1000).playPattern()
            PerceptionComponent.VibratorLevel.NONE -> {
            }
        }
    }

    override fun speakText(text: String) {
        context?.let { mContext ->
            var textToSpeech: TextToSpeech? = null
            textToSpeech = TextToSpeech(mContext, TextToSpeech.OnInitListener {
                if (it == TextToSpeech.SUCCESS) {
                    textToSpeech?.let { speech ->
                        if (speech.isLanguageAvailable(Locale.SIMPLIFIED_CHINESE) >= TextToSpeech.LANG_AVAILABLE) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                val params = Bundle()
                                params.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_NOTIFICATION)
                                params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1f)
                                speech.speak(text, TextToSpeech.QUEUE_ADD, params, text)
                            } else {
                                val params = HashMap<String, String>()
                                params.put(
                                    TextToSpeech.Engine.KEY_PARAM_STREAM,
                                    AudioManager.STREAM_NOTIFICATION.toString()
                                )
                                params.put(TextToSpeech.Engine.KEY_PARAM_VOLUME, "1.0")
                                speech.speak(text, TextToSpeech.QUEUE_ADD, params)
                            }
                        } else {
                            waring("此设备不支持：中文语音输出")
                        }
                    }
                }
            })
        }

    }

    override fun makeNotificationSound() {
        context?.let { mContext ->
            val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ringtone = RingtoneManager.getRingtone(mContext, notificationSound)
            ringtone.play()
        }

    }

    override fun showTopNotify(
        felling: PerceptionComponent.NotifyFelling,
        title: String,
        message: String,
        vibratorLevel: PerceptionComponent.VibratorLevel,
        speak: Boolean
    ) {
        if (activity is PerceptionComponent) {
            val component = activity as PerceptionComponent
            component.showTopNotify(felling, title, message, vibratorLevel, speak)
        }
    }
}