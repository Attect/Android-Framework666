package studio.attect.framework666.extensions

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.msgpack.core.MessageBufferPacker
import studio.attect.framework666.DataXOffice
import studio.attect.framework666.R
import studio.attect.framework666.activity.OnBackPressedQueueActivity


/**
 * 自动处理参数的Bundle的key
 */
const val COMPONENTX_AUTO_ARG_KEY = "_arg_"

/**
 * 设置一个对象参数
 * 与原来的setArguments互斥！
 * 需要在被装载之前调用（与正常设置arguments相同）
 * 数据将被DataXOffice处理，即数据结构将受其数据处理能力的限制
 */
fun Fragment.setAnyArgument(any: Any?) {
    val dataXOffice = DataXOffice()
    dataXOffice.put(any)
    arguments = Bundle().apply {
        (dataXOffice.packer as? MessageBufferPacker)?.let { packer ->
            val byteArray = packer.toByteArray()
            putByteArray(COMPONENTX_AUTO_ARG_KEY, byteArray)
        }
    }
}

/**
 * 获得一个对象参数
 * 与 @see setAnyArgument 方法成对配合
 * 若没有参数或发生任何错误将返回null
 */
fun <T> Fragment.getAnyArguments(clazz: Class<T>): T? {
    val byteArray = arguments?.getByteArray(COMPONENTX_AUTO_ARG_KEY) ?: return null
    val dataXOffice = DataXOffice()
    dataXOffice.unpack(byteArray)
    return dataXOffice.get(clazz)
}

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

        val backUpArrow: View? = findViewById(R.id.backUpArrow)
        backUpArrow?.setOnClickListener {
            fragmentActivity.onBackPressed()
        }
        if (backUpArrow == null && fragmentActivity is AppCompatActivity) {
            fragmentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
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


