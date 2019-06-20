package studio.attect.framework666.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import studio.attect.staticviewmodelstore.StaticViewModelStore
import kotlin.math.sign

/**
 * 信号数据
 * 负责App生命周期维护
 *
 * @author attect
 */
class SignalViewModel : ViewModel() {

    private val signal = MutableLiveData<Int>()

    public fun getSignal(): MutableLiveData<Int> {
        if (signal.value == null) signal.value = NOTING
        return signal
    }

    companion object {

        /**
         * 退出所有Activity
         * 只有Activity应该处理
         */
        const val EXIT_ACTIVITY = Integer.MIN_VALUE + 2

        /**
         * 啥也没有发生
         */
        const val NOTING = 0

        private const val STORE_KEY = "appLifeSignal"

        @JvmStatic
        fun newInstance(caller: StaticViewModelStore.StaticViewModelStoreCaller): SignalViewModel? {
            return caller.getStaticViewModel(STORE_KEY, SignalViewModel::class.java)
        }
    }
}