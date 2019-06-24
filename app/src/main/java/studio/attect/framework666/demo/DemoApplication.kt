package studio.attect.framework666.demo

import studio.attect.framework666.ApplicationX
import studio.attect.framework666.compomentX.ComponentXMap
import studio.attect.framework666.demo.fragment.NormalComponent

/**
 * 此Demo App的Application
 *
 * @author Attect
 */
class DemoApplication : ApplicationX() {

    override fun onCreate() {
        super.onCreate()

        markComponents()
    }

    private fun markComponents() {
        ComponentXMap.mark(NormalComponent.Companion)
    }
}