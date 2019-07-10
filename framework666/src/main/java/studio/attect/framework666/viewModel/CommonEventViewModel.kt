package studio.attect.framework666.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * 通用事件状态ViewModel
 * 通常用于一个Activity内部组件观察结果后决定继续响应
 * 若需带上其它数据，请继承此类
 *
 * @author attect
 */
open class CommonEventViewModel : ViewModel() {

    val event = MutableLiveData<Int>()
        get() {
            if (field.value == null) field.value = NONE
            return field
    }


    companion object {


        /**
         * 消极事件
         */
        @JvmStatic
        val NEGATIVE = -1

        /**
         * 无事件
         */
        @JvmStatic
        val NONE = 0

        /**
         * 积极事件
         */
        @JvmStatic
        val POSITIVE = 1

        /**
         * 继续操作
         */
        @JvmStatic
        val CONTINUE = 2

        /**
         * 返回向前
         */
        @JvmStatic
        val FORWARD = 3

        /**
         * 有数据变更
         */
        @JvmStatic
        val DATA_UPDATED = 4
    }
}
