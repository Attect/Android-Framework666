package studio.attect.framework666.compomentX

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

/**
 * 导航组件X
 * 此组件是虚的，用于辅助ContainerX加载Navigation Graph
 * Navigation Graph中的Fragment也可为ComponentX的实现，并共享此ContainerX
 *
 * @author Attect
 */
abstract class NavigatedComponentX : ComponentX {

    var hostFragment: NavHostFragment? = null

    /**
     * 给ContainerX一个NavHostFragment
     * 剩下的交给NavHostFragment处理
     * @param navArguments 此参数会传递给startDestination
     */
    override fun getComponentXFragmentInstance(navArguments: Bundle?): Fragment {
        if (hostFragment == null) hostFragment = NavHostFragment.create(navMapXmlId(), navArguments)
        return hostFragment!!
    }

    /**
     * 此类组件不应该由此对象查找容器
     */
    override fun getContainerX(): ContainerX? = null

    /**
     * 指定res/navigation
     */
    @NavigationRes
    abstract fun navMapXmlId(): Int
}