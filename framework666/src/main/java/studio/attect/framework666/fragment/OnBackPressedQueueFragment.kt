package studio.attect.framework666.fragment

import studio.attect.framework666.activity.OnBackPressedQueueActivity
import studio.attect.staticviewmodelstore.StaticViewModelLifecycleFragment

/**
 * 与OnBackPressedQueueActivity对应的Fragment
 * 会自动根据生命周期将返回事件从Activity中解除和恢复
 *
 * @author Attect
 */
open class OnBackPressedQueueFragment : StaticViewModelLifecycleFragment() {

    private val onBackPressQueue = arrayListOf<OnBackPressedQueueActivity.OnBackPressedCallback>()

    override fun onStart() {
        super.onStart()
        if (activity is OnBackPressedQueueActivity) {
            onBackPressQueue.forEach {
                (activity as OnBackPressedQueueActivity).addOnBackPressedCallback(it)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (activity is OnBackPressedQueueActivity) {
            onBackPressQueue.forEach {
                (activity as OnBackPressedQueueActivity).removeOnBackPressedCallback(it)
            }
        }
    }

    fun addOnBackPressedCallback(callback: OnBackPressedQueueActivity.OnBackPressedCallback) {
        if (activity is OnBackPressedQueueActivity) {
            if (onBackPressQueue.contains(callback)) onBackPressQueue.remove(callback)
            onBackPressQueue.add(callback)
            (activity as OnBackPressedQueueActivity).addOnBackPressedCallback(callback)
        }
    }

    fun removeOnBackPressedCallback(callback: OnBackPressedQueueActivity.OnBackPressedCallback) {
        if (activity is OnBackPressedQueueActivity) {
            onBackPressQueue.remove(callback)
            (activity as OnBackPressedQueueActivity).removeOnBackPressedCallback(callback)
        }
    }

}