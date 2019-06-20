package studio.attect.framework666.extensions

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

/**
 * 获得给定class的ViewModel
 */
fun <T : ViewModel> FragmentActivity.getViewModel(modelClass: Class<T>): T {
    return ViewModelProviders.of(this).get(modelClass)
}

/**
 * 获得给定class和tag的ViewModel
 */
fun <T : ViewModel> FragmentActivity.getViewModel(key: String, modelClass: Class<T>): T {
    return ViewModelProviders.of(this).get(key, modelClass)
}

