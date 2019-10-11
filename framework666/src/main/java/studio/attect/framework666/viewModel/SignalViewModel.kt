package studio.attect.framework666.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * 信号数据
 * 负责App生命周期维护
 *
 * @author attect
 */
object SignalViewModel : ViewModel() {

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

    val signal = MutableLiveData<Int>()
        get() {
            if (field.value == null) field.postValue(NOTING)
            return field
    }

}