package studio.attect.framework666.viewModel

import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import studio.attect.staticviewmodelstore.StaticViewModelStore

/**
 * 屏幕安全区配置ViewModel
 * 持有WindowInsetsCompat对象
 * 屏幕安全区域：状态栏、导航栏、设备厂商定义范围以及“刘海”部分位置
 * 当Activity初次应用rootView时，以及屏幕旋转时屏幕安全区域将会变化
 *
 * 暂时只认为uApp只会存在用户设备上的其中一个屏幕
 * 折叠屏可能需要更多的考虑
 * 需要注意的是，WindowInsetsCompat不会默认被赋予实例，需要判断是否为null
 *
 * @author attect
 */
class WindowInsetsViewModel : ViewModel() {
    var windowInsetsCompatMutableLiveData: MutableLiveData<WindowInsetsCompat>? = MutableLiveData()

    companion object {
        private const val STORE_KEY = "windowInsets"

        @JvmStatic
        fun newInstance(caller: StaticViewModelStore.StaticViewModelStoreCaller): WindowInsetsViewModel? {
            return caller.getStaticViewModel(STORE_KEY, WindowInsetsViewModel::class.java)
        }
    }

}
