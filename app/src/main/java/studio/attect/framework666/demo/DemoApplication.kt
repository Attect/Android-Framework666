package studio.attect.framework666.demo

import studio.attect.framework666.ApplicationX
import studio.attect.framework666.RuntimeBuildConfig
import studio.attect.framework666.compomentX.ComponentXMap
import studio.attect.framework666.demo.fragment.CacheComponent
import studio.attect.framework666.demo.fragment.CrashAndANRComponentX
import studio.attect.framework666.demo.fragment.NormalComponent
import studio.attect.framework666.demo.fragment.RecyclerViewComponent

/**
 * 此Demo App的Application
 *
 * @author Attect
 */
class DemoApplication : ApplicationX() {

    override fun onCreate() {
        super.onCreate()
        //region 必抄部分
        RuntimeBuildConfig.DEBUG = BuildConfig.DEBUG
        RuntimeBuildConfig.APPLICATION_ID = BuildConfig.APPLICATION_ID
        RuntimeBuildConfig.BUILD_TYPE = BuildConfig.BUILD_TYPE
        RuntimeBuildConfig.FLAVOR = BuildConfig.FLAVOR
        RuntimeBuildConfig.VERSION_CODE = BuildConfig.VERSION_CODE
        RuntimeBuildConfig.VERSION_NAME = BuildConfig.VERSION_NAME
        //endregion

        markComponents()
    }

    private fun markComponents() {
        ComponentXMap.mark(CrashAndANRComponentX.Companion)
        ComponentXMap.mark(CacheComponent.Companion)
        ComponentXMap.mark(NormalComponent.Companion)
        ComponentXMap.mark(RecyclerViewComponent.Companion)
    }


}