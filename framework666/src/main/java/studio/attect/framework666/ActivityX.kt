package studio.attect.framework666

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.ViewCompat
import studio.attect.framework666.activity.PerceptionActivity
import studio.attect.framework666.extensions.getViewModel
import studio.attect.framework666.extensions.rootView
import studio.attect.framework666.viewModel.CommonEventViewModel
import studio.attect.framework666.viewModel.SignalViewModel
import studio.attect.framework666.viewModel.WindowInsetsViewModel

/**
 * 使用本框架
 * Activity就应该继承此类！
 *
 * 此类的父类会随着开发和变更而变更，不可通过反射获取准确类型
 * @author Attect
 */
abstract class ActivityX : PerceptionActivity() {

    val applicationX = application as ApplicationX

    //region ViewModel
    lateinit var signalViewModel: SignalViewModel
        private set

    lateinit var commonEventViewModel: CommonEventViewModel
        private set

    lateinit var windowInsetsViewModel: WindowInsetsViewModel
        private set

    //endregion

    /**
     * 当前正在负责观察WindowInsets变化的View
     */
    private var windowInsetsWatchingView: View? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        SignalViewModel.newInstance(this)?.let { signalViewModel = it }
        commonEventViewModel = getViewModel(CommonEventViewModel::class.java)
        WindowInsetsViewModel.newInstance(this)?.let { windowInsetsViewModel = it }
    }

    override fun onStart() {
        super.onStart()
        watchWindowInsetsChange(rootView)
    }

    /**
     * 通过一个View来监听屏幕安全区域的变化
     * 可以判断到状态栏/屏幕缺口/虚拟键盘等区域
     * 对View直接使用ViewCompat.setOnApplyWindowInsetsListener也可获取到类似的效果，但ViewCompat.setOnApplyWindowInsetsListener方法中获取的WindowInsets可能不可控的被消耗掉
     * 因此此处新编写此观察逻辑来确保有途径获取到当前Window中一个始终不变的WindowInsets来让界面作出相关适应
     *
     * @param view 通过哪个View来观察WindowInsets变化，此View应该在布局中尽量顶层
     */
    private fun watchWindowInsetsChange(view: View?) {
        if (view != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            windowInsetsWatchingView?.let {
                //清空之前view的观察，防止多次触发
                ViewCompat.setOnApplyWindowInsetsListener(it, null)
            }
            windowInsetsWatchingView = view
            ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
                windowInsetsViewModel.windowInsetsCompatMutableLiveData?.value = insets
                insets //不消费
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 默认响应Appbar左侧返回箭头
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}