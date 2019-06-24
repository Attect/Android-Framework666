package studio.attect.framework666.compomentX

import android.content.Context
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment

/**
 * 组件X
 * 通常由Fragment实现
 * 用于实现“一个主要功能”既能直接放到Activity中，又能作为ViewGroup的子项，并对处于不同环境时作出不同的适配。
 * 且对父容器提供图标和名称
 *
 * 例子：有一个Fragment，它既由一个Activity直接呈现且需要显示返回箭头，又可以放到主界面的ViewPager中，而且ViewPager还带了Tab，因为是主界面由不需要返回箭头
 * 可以为用户提供自定义某个屏幕为App的功能的需求
 *
 * 可以嵌套！！组件中套组件，共享一个ContainerX，对于多层结果传递很有帮助
 *
 * @author Attect
 */
interface ComponentX {
    /**
     * 获得功能承载Fragment
     */
    fun getComponentXFragmentInstance(navArguments: Bundle? = null): Fragment

    /**
     * 获得容器
     * 但也可能不在容器中
     */
    fun getContainerX(): ContainerX?
}