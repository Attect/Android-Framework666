package studio.attect.framework666.extensions

import androidx.lifecycle.MutableLiveData

/**
 * 数据变更（引用未变更）
 * 触发观察
 *
 * 可决定是否更新
 */
fun <T> MutableLiveData<T>.update(update: (T?) -> Boolean) {
    if (update.invoke(value)) value = value
}

/**
 * 数据变更（引用未变更）
 * 触发观察
 * 数据不为null时操作
 *
 * 可决定是否更新
 */
fun <T> MutableLiveData<T>.updateIfNotNull(update: (T) -> Boolean) {
    value?.let {
        if (update(it)) value = value
    }
}