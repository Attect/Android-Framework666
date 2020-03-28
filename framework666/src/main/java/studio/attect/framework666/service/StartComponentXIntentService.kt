package studio.attect.framework666.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import studio.attect.framework666.ApplicationX
import studio.attect.framework666.ContainerXActivity
import studio.attect.framework666.componentX.ArgumentX

/**
 * 一个延后委托处理的ComponentX启动任务
 * <tag,Arguments>
 */
typealias PendingComponentXTask = Pair<String, Bundle?>

/**
 * 此IntentService用于实现：点击通知后，先返回App原来的界面，再转到目标组件X
 * 这样启动目标组件X后用户执行返回操作可以平滑回到原来的操作不至于原堆栈被清空
 *
 * @author Attect
 */
class StartComponentXIntentService : IntentService("StartComponentXIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            if (it.hasExtra(TAG_KEY)) {
                task.postValue(Pair(it.getStringExtra(TAG_KEY) ?: "", it.apply { removeExtra(TAG_KEY) }.extras))
            }
        }
        ApplicationX.mainLauncherActivityClass?.let { mainActivityClass ->
            val activityIntent = Intent(applicationContext, mainActivityClass).apply {
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            applicationContext.startActivity(activityIntent)
        }

    }

    companion object {
        const val TAG_KEY = "__TAG__"

        /**
         * 待执行任务
         */
        var task = MutableLiveData<PendingComponentXTask?>()

        /**
         * 可执行此任务的Activity执行此方法进行尝试执行需要进行的组件X任务
         * 也可已由Fragment执行，在你觉得适当的时机
         * 所有ContainerXActivity会在onResume时进行此操作
         */
        fun tryExecutePendingComponentXTask(context: Context) {
            val currentTask = task.value
            if (currentTask != null) {
                task.value = null
                ContainerXActivity.startActivity(context, currentTask.first, object : ArgumentX {
                    var bundle: Bundle = Bundle()
                    override fun fromBundle(bundle: Bundle?) {
                        bundle?.let { this.bundle = it }
                    }

                    override fun toBundle(): Bundle = bundle

                }.apply { fromBundle(currentTask.second) })
            }
        }
    }
}