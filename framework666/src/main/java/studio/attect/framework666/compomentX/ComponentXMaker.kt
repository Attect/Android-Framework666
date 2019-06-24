package studio.attect.framework666.compomentX

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

/**
 * ComponentX的基本信息和实例获取 接口
 * 通常ComponentX的接口来负责实现，也可以单独实现
 *
 * 避免需要实例化一个重型对象后才可获取图标和名称等信息
 * 此对象应该会被持久强引用(ComponentXMap持有)
 *
 * @author Attect
 */
interface ComponentXMaker {

    /**
     * 对应ComponentX的tag
     */
    fun getTag(): String

    /**
     * 获得图片Drawable
     */
    fun getIcon(context: Context?): Drawable?

    /**
     * 获得功能名称
     * context通常用来搞定多语言适配
     * 你必须确保始终返回一个名称，必定存在一个写死的
     */
    fun getName(context: Context?): String

    /**
     * 获得一个ComponentX实例
     */
    fun newInstance(): ComponentX
}