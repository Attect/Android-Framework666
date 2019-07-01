package studio.attect.framework666.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import studio.attect.staticviewmodelstore.StaticViewModelStore

/**
 * 信号数据
 * 负责App生命周期维护
 *
 * @author attect
 */
class SignalViewModel : ViewModel() {

    val signal = MutableLiveData<Int>()
        get() {
            if (field.value == null) field.postValue(null)
            return field
    }

    companion object {

        /**
         * 退出所有Activity
         * 只有Activity应该处理
         */
        const val EXIT_ACTIVITY = Integer.MIN_VALUE + 2

        /**
         * 发生了崩溃
         * 应该立即处理数据的应急保存及其它逻辑
         */
        const val CRASH = Integer.MIN_VALUE + 3

        /**
         * 啥也没有发生
         */
        const val NOTING = 0

        const val STORE_KEY = "appLifeSignal"

        @JvmStatic
        fun newInstance(caller: StaticViewModelStore.StaticViewModelStoreCaller): SignalViewModel? {
            return caller.getStaticViewModel(STORE_KEY, SignalViewModel::class.java)
        }

    }
}