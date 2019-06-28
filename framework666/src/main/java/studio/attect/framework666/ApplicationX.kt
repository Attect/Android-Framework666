package studio.attect.framework666

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import studio.attect.framework666.compomentX.ComponentXMap
import studio.attect.framework666.helper.Rumble
import studio.attect.framework666.simple.ComponentXExplorer
import studio.attect.framework666.simple.CrashAndANRComponentX
import studio.attect.framework666.viewModel.SignalViewModel
import studio.attect.staticviewmodelstore.StaticViewModelStore

/**
 * 综合Application
 * 初始化一些东西
 * 使用此框架，必须继承此类实现自己的Application或者直接使用此Application代替默认的
 *
 * 此类的父类会随着开发和变更而变更，不可通过反射获取准确类型
 * @author Attect
 */

open class ApplicationX : Application() {

    override fun onCreate() {
        super.onCreate()
        //崩溃处理，如果你有别的崩溃处理逻辑注释掉此行即可
        fuckCrash()

        //振动
        Rumble.init(applicationContext)

        ComponentXMap.mark(ComponentXExplorer.Companion)
        ComponentXMap.mark(CrashAndANRComponentX.Companion)
    }

    /**
     * 阻止崩溃，并通过SignalViewModel向所有观察者告知App发生了严重错误
     * 错误发生后将继续运行
     * 应该显著提示用户（数据可能严重错误了），例如截图并转向Bug反馈界面
     */
    private fun fuckCrash() {
        val crashHandler = CrashHandler()
        Thread.setDefaultUncaughtExceptionHandler(crashHandler)
        Handler(Looper.getMainLooper()).post {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    StaticViewModelStore.getViewModelProvider(SignalViewModel.STORE_KEY, this@ApplicationX).get(SignalViewModel::class.java).signal.value = SignalViewModel.CRASH
                }
            }
        }
    }

    /**
     * 全局未捕获异常处理器
     * 通过SignalViewModel向所有观察者告知App发生了严重错误
     * 不论哪个线程
     */
    inner class CrashHandler : Thread.UncaughtExceptionHandler {
        override fun uncaughtException(t: Thread?, e: Throwable?) {
            e?.printStackTrace()
            object : Thread() {
                override fun run() {
                    Looper.prepare()
                    StaticViewModelStore.getViewModelProvider(SignalViewModel.STORE_KEY, this@ApplicationX).get(SignalViewModel::class.java).signal.postValue(SignalViewModel.CRASH)
                    Looper.loop()
                }
            }.start()
        }

    }
}