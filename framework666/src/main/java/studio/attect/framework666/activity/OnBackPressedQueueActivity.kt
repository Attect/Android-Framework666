package studio.attect.framework666.activity

import studio.attect.staticviewmodelstore.StaticViewModelLifecycleActivity

/**
 * 支持处理返回事件逻辑队列的Activity
 * 用于应对Google的AppCompat包在疯狂变更实现的情况
 *
 * @author Attect
 */
abstract class OnBackPressedQueueActivity : StaticViewModelLifecycleActivity() {
    private val onBackPressQueue = arrayListOf<OnBackPressedCallback>()

    override fun onBackPressed() {
        onBackPressQueue.reversed().forEach {
            if (it.handleOnBackPressed()) return
        }
        super.onBackPressed()
    }

    fun runOnBackPressedQueue(): Boolean {
        onBackPressQueue.reversed().forEach {
            if (it.handleOnBackPressed()) return true
        }
        return false
    }

    fun addOnBackPressedCallback(callback: OnBackPressedCallback) {
        if (onBackPressQueue.contains(callback)) {
            onBackPressQueue.remove(callback)
        }
        onBackPressQueue.add(callback)
    }


    fun removeOnBackPressedCallback(callback: OnBackPressedCallback) {
        onBackPressQueue.remove(callback)

    }

    interface OnBackPressedCallback {
        fun handleOnBackPressed(): Boolean
    }
}