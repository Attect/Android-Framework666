package studio.attect.framework666.componentX

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

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

    /**
     * 返回一个组件名称
     * 注意[context]可能不是当前fragment的上下文
     */
    fun getName(context: Context?): String?

    /**
     * 返回一个主要颜色
     * 推荐可根据主题或夜间模式返回不同的值
     * 注意[context]可能不是当前fragment的上下文
     */
    @ColorInt
    fun getColor(context: Context?): Int

    /**
     * 返回一个图标
     * 注意[context]可能不是当前fragment的上下文
     */
    fun getIcon(context: Context?): Drawable?
}

/**
 * 组件X编程类型直接转化为Fragment
 * 注意这其实是可能失败的，要严格注意你的类型
 */
fun ComponentX.toFragment(): Fragment = this as Fragment

/**
 * 根据组件X的Class获得其有效tag
 * 注意“有效”指的是能正确被扫描并自动注册的组件X及其tag
 */
fun Class<out ComponentX>.tag(): String = ComponentXContainer.getTag(this)

fun KClass<out ComponentX>.tag(): String = this.java.tag()