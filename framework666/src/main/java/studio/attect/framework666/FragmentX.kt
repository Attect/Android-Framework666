package studio.attect.framework666

import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import studio.attect.framework666.extensions.*
import studio.attect.framework666.fragment.PerceptionFragment

/**
 * 使用本框架
 * Fragment就应该继承此类！
 *
 * 此类的父类会随着开发和变更而变更，不可通过反射获取准确类型
 * @author Attect
 */
class FragmentX : PerceptionFragment() {
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
                    if (ac is ActivityX) return ac as ActivityX
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
     * 可伸展的Appbar布局
     */
    var collapsingToolbarLayout: CollapsingToolbarLayout? = null

    /**
     * 标题、导航控制、菜单等组件的容器
     */
    var toolbar: Toolbar? = null

    var toolbarTitle: AppCompatTextView? = null
    //endregion


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

    /**
     * 初始化约定结构的Appbar
     * 固定Toolbar和伸缩两种
     *
     * 仅有这个Fragment作为整屏显示时调用（若Activity已处理安全边距也不调用）
     */
    private fun initAppbar() {
        appbarLayoutParent = findViewById(R.id.appbarLayoutParent)
        appbarLayoutParent?.let { parent ->
            toolbar = findViewById(R.id.toolbar) //只对Appbar中的toolbar操作
            collapsingToolbarLayout =
                findViewById(R.id.collapsingToolbarLayout) //这个View父级一定是CoordinatorLayout（否则就不起作用了）
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
                toolbarTitle = toolbar?.findViewById(R.id.toolbarTitle) //只对toolbar中的toolbarTitle操作

                activityX?.setSupportActionBar(toolbar) //需要这一步，否则菜单可能设置不上
                //清空Android原有的标题
                toolbar?.title = ""
                activityX?.title = ""

                val appbarTitleColor = ResourcesCompat.getColor(resources, R.color.appbar_title_color, activityX?.theme)
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