package studio.attect.framework666

import studio.attect.framework666.viewModel.SignalViewModel
import studio.attect.staticviewmodelstore.StaticViewModelLifecycleService


abstract class ServiceX : StaticViewModelLifecycleService() {

    private lateinit var signalViewModel: SignalViewModel

    override fun onCreate() {
        super.onCreate()
        SignalViewModel.newInstance(this)?.let { signalViewModel = it }
    }
}