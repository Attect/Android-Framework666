package studio.attect.framework666

import android.app.Activity
import android.app.Application
import android.os.Handler
import android.os.Looper
import studio.attect.framework666.componentX.COC
import studio.attect.framework666.helper.Rumble
import studio.attect.framework666.simple.ComponentXExplorer
import studio.attect.framework666.simple.NotFoundComponentX
import studio.attect.framework666.simple.NotFoundComponentX.Companion.NOT_FOUND_COMPONENT_X
import studio.attect.framework666.viewModel.SignalViewModel
import studio.attect.framework666.viewModel.SignalViewModel.signal
import kotlin.reflect.full.declaredFunctions

/**
 * 综合Application
 * 初始化一些东西
 * 使用此框架，必须继承此类实现自己的Application或者直接使用此Application代替默认的
 *
 * 此类的父类会随着开发和变更而变更，不可通过反射获取准确类型
 * @author Attect
 */

open class ApplicationX : Application() {
    var hasInitialized = false

    override fun onCreate() {
        super.onCreate()

        if (!hasInitialized) {
            hasInitialized = true
            //崩溃处理，如果你有别的崩溃处理逻辑注释掉此行即可
            fuckCrash()

            //振动
            Rumble.init(applicationContext)


        }

        //自动注册可重复调用，应对部分热重启情况
        COC.register(NOT_FOUND_COMPONENT_X, NotFoundComponentX::class.java)
        COC.register("_explorer_", ComponentXExplorer::class.java)
        autoRegister()

    }

    /**
     * 自定义处理主线程未捕获异常逻辑
     * 如果你采用了bugly之类的异常上报扩展，可以在此处手动提交Throwable
     * 此方法在信号之前执行
     */
    open fun mainThreadUnCatchException(e: Throwable) {

    }

    /**
     * 自定义处理子线程未捕获异常逻辑
     * 如果你采用了bugly之类的异常上报扩展，可以在此处手动提交Throwable
     * 此方法在信号之前执行
     */
    open fun childThreadUnCatchException(thread: Thread?, e: Throwable?) {

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
                    mainThreadUnCatchException(e)
                    signal.value = SignalViewModel.CRASH
                }
            }
        }
    }

    private fun autoRegister() {
        val instance = Class.forName("studio.attect.framework666.ComponentXAutoRegister").newInstance()
        instance::class.declaredFunctions.forEach { kFunction ->
            if (kFunction.name == "autoRegister") {
                kFunction.call(instance)
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
            childThreadUnCatchException(t, e)
            object : Thread() {
                override fun run() {
                    Looper.prepare()
                    signal.postValue(SignalViewModel.CRASH)
                    Looper.loop()
                }
            }.start()
        }

    }

    companion object {
        /**
         * 应用最最初始的点击图标后首要启动的Activity的Class
         * 用于一些在外部返回到App的情况
         * 如 StartComponentXIntentService
         *
         * 请在自己的Application中设置这个的值
         */
        var mainLauncherActivityClass: Class<out Activity>? = null
    }
}