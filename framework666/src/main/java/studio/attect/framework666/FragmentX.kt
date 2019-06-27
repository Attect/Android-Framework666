package studio.attect.framework666

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import studio.attect.framework666.compomentX.ComponentX
import studio.attect.framework666.compomentX.ContainerX
import studio.attect.framework666.extensions.*
import studio.attect.framework666.fragment.PerceptionFragment
import studio.attect.framework666.interfaces.UnExpectedExitHandler

/**
 * 使用本框架
 * Fragment就应该继承此类！
 *
 * 此类的父类会随着开发和变更而变更，不可通过反射获取准确类型
 * @author Attect
 */
abstract class FragmentX : PerceptionFragment(), ComponentX, UnExpectedExitHandler {

    val applicationX: ApplicationX? = null
        get() {
            if (field == null) {
                activity?.application?.let {
                    return it as ApplicationX
                }
            }
            return field
        }

    val activityX: ActivityX? = null
        get() {
            if (field == null) {
                activity?.let { ac ->
                    if (ac is ActivityX) return ac
                }
            }
            return field
        }

    //region ViewModel
    val commonEvent: MutableLiveData<Int>?
        get() {
            if (activity is ActivityX) {
                val activityX = activity as ActivityX
                return activityX.commonEvent
            }
            return null
        }

    val windowInsets: MutableLiveData<WindowInsetsCompat>?
        get() {
            if (activity is ActivityX) {
                val activityX = activity as ActivityX
                return activityX.windowInsets
            }
            return null
        }

    //endregion

    //region 约定布局
    /**
     * AppbarLayout的父层布局
     */
    var appbarLayoutParent: ViewGroup? = null

    /**
     * Appbar布局
     * 可给它设定背景来改变标题栏背景
     */
    var appbarLayout: AppBarLayout? = null

    /**
     * 可伸展的Appbar布局
     */
    var collapsingToolbarLayout: CollapsingToolbarLayout? = null

    /**
     * 标题、导航控制、菜单等组件的容器
     */
    var toolbar: Toolbar? = null

    var toolbarTitle: AppCompatTextView? = null
    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAppbar()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getContainerX()?.let {
            if (it.shouldShowBackAllow()) showBackArrow()
        }
    }

    override fun onStart() {
        super.onStart()
        toolbar?.setBackArrowColor()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 默认响应Appbar左侧返回箭头
            android.R.id.home -> {
                if (activity != null) {
                    activity?.onBackPressed()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getComponentXFragmentInstance(navArguments: Bundle?): Fragment = this

    override fun getContainerX(): ContainerX? {
        var pf = parentFragment
        do {
            if (pf != null && pf is ContainerX) return pf
            pf = pf?.parentFragment
        } while (pf != null)
        if (activity != null && activity is ContainerX) return activity as ContainerX
        return null
    }

    override fun OnUnExpectedExit() {
    }


    /**
     * 初始化约定结构的Appbar
     * 固定Toolbar和伸缩两种
     *
     * 仅有这个Fragment作为整屏显示时调用（若Activity已处理安全边距也不调用）
     */
    private fun initAppbar() {
        appbarLayoutParent = findViewById(R.id.appbarLayoutParent)
        appbarLayout = appbarLayoutParent?.findViewById(R.id.appbarLayout)
        appbarLayoutParent?.let { parent ->
            toolbar = appbarLayout?.findViewById(R.id.toolbar) //只对Appbar中的toolbar操作
            collapsingToolbarLayout = appbarLayoutParent?.findViewById(R.id.collapsingToolbarLayout) //这个View父级一定是CoordinatorLayout（否则就不起作用了）
            windowInsets?.observe(this, Observer { windowInsetsCompat ->
                parent.layoutParams?.let { lp ->
                    if (lp is ViewGroup.MarginLayoutParams) {
                        lp.leftMargin = windowInsetsCompat.currentSafeLeft
                        if (toolbar == null) lp.topMargin = windowInsetsCompat.currentSafeTop
                        lp.rightMargin = windowInsetsCompat.currentSafeRight
                        lp.bottomMargin = windowInsetsCompat.currentSafeBottom
                    }
                }
            })

            if (toolbar != null && toolbar?.parent is AppBarLayout) { //只对appbar中的toolbar操作
                toolbar?.layoutParams?.height = resources.getDimensionPixelSize(R.dimen.toolbar_height)

                toolbarTitle = toolbar?.findViewById(R.id.toolbarTitle) //只对toolbar中的toolbarTitle操作
                toolbarTitle?.apply {
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.toolbar_title_text_size).toFloat())
                    setTextColor(ResourcesCompat.getColor(resources, R.color.appbarTitleColor, activityX?.theme))
                }

                activityX?.setSupportActionBar(toolbar) //需要这一步，否则菜单可能设置不上
                //清空Android原有的标题
                toolbar?.title = ""
                activityX?.title = ""

                val appbarTitleColor = ResourcesCompat.getColor(resources, R.color.appbarTitleColor, activityX?.theme)
                toolbar?.apply {
                    setTitleTextColor(appbarTitleColor)
                    setSubtitleTextColor(appbarTitleColor)
                }

                windowInsets?.observe(this, Observer { windowInsetsCompat ->
                    toolbar?.layoutParams?.let { lp ->
                        if (lp is ViewGroup.MarginLayoutParams) {
                            lp.topMargin = windowInsetsCompat.currentSafeTop
                        }
                    }
                })
            }

            if (collapsingToolbarLayout != null && collapsingToolbarLayout?.parent is CoordinatorLayout) {
                collapsingToolbarLayout?.apply {
                    setContentScrimColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.collapsing_toolbar_layout_content_scrim,
                            activityX?.theme
                        )
                    )
                    setExpandedTitleColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.collapsing_toolbar_layout_expanded_title,
                            activityX?.theme
                        )
                    )
                    title = ""
                }
                windowInsets?.observe(this, Observer { windowInsetsCompat ->
                    collapsingToolbarLayout?.layoutParams?.let { lp ->
                        if (lp is ViewGroup.MarginLayoutParams) {
                            lp.topMargin = windowInsetsCompat.currentSafeTop
                        }
                    }
                })
            }
        }
    }

    fun setTitle(title: CharSequence?) {
        collapsingToolbarLayout?.title = title
        if (toolbarTitle != null) {
            toolbarTitle?.text = title
        } else {
            activityX?.setTitle(title)
        }
    }

    fun setTitle(@StringRes resId: Int) {
        collapsingToolbarLayout?.title = getString(resId)
        if (toolbarTitle != null) {
            toolbarTitle?.setText(resId)
        } else {
            activityX?.setTitle(resId)
        }
    }
}