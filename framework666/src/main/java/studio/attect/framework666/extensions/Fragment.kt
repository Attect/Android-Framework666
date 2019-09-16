package studio.attect.framework666.extensions

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import studio.attect.framework666.R
import studio.attect.framework666.activity.OnBackPressedQueueActivity

/**
 * 转接Activity的runOnUiThread过来
 */
fun Fragment.runOnUiThread(runnable: Runnable) {
    activity?.runOnUiThread(runnable)
}

/**
 * 提供一个与Activity类似的findViewById方法
 * 在onViewCreated之后才可使用，否则必定找不到view
 */
fun <T : View> Fragment.findViewById(@IdRes id: Int): T? {
    return view?.findViewById(id)
}

/**
 * 转接Activity.finish
 */
fun Fragment.finish() {
    activity?.finish()
}

/**
 * 转接Activity.invalidateOptionsMenu
 */
fun Fragment.invalidateOptionsMenu() {
    activity?.invalidateOptionsMenu()
}

/**
 * 此Fragment是否用户可见、在Activity里、有context、不在不可操作的生命周期时段
 * 简单的说就是用户可操作阶段
 */
fun Fragment.isAlive(): Boolean {
    return activity != null && !isDetached && isAdded && !isRemoving && view != null
}

/**
 * 显示返回箭头
 * 但通常指的是fragment的View中的Toolbar上
 * 因此需要注意Fragment中需要activity?.setSupportActionBar后此方法才会生效
 */
fun Fragment.showBackArrow() {
    activity?.let { fragmentActivity ->
        if (fragmentActivity is AppCompatActivity) {
            fragmentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        val backUpArrow: View? = findViewById(R.id.backUpArrow)
        backUpArrow?.setOnClickListener {
            fragmentActivity.onBackPressed()
        }
    }
}

/**
 * 转接OnBackPressedQueueActivity.addOnBackPressedCallback
 */
fun Fragment.addOnBackPressedCallback(callback: OnBackPressedQueueActivity.OnBackPressedCallback) {
    if (activity is OnBackPressedQueueActivity) {
        val onBackPressedQueueActivity = activity as OnBackPressedQueueActivity
        onBackPressedQueueActivity.addOnBackPressedCallback(callback)
    }
}

/**
 * 转接OnBackPressedQueueActivity.removeOnBackPressedCallback
 */
fun Fragment.removeOnBackPressedCallback(callback: OnBackPressedQueueActivity.OnBackPressedCallback) {
    if (activity is OnBackPressedQueueActivity) {
        val onBackPressedQueueActivity = activity as OnBackPressedQueueActivity
        onBackPressedQueueActivity.removeOnBackPressedCallback(callback)
    }
}


