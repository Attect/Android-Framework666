package studio.attect.framework666

import android.os.Build
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import pub.devrel.easypermissions.EasyPermissions
import studio.attect.framework666.activity.MisoperationActivity
import studio.attect.framework666.extensions.*
import studio.attect.framework666.interfaces.DataX
import studio.attect.framework666.viewModel.CacheDataXViewModel
import studio.attect.framework666.viewModel.CommonEventViewModel
import studio.attect.framework666.viewModel.SignalViewModel
import studio.attect.framework666.viewModel.WindowInsetsViewModel

/**
 * 使用本框架
 * Activity就应该继承此类！
 *
 * 此类的父类会随着开发和变更而变更，不可通过反射获取准确类型
 * @author Attect
 */
abstract class ActivityX : MisoperationActivity() {

    val applicationX by lazy {
        application as ApplicationX
    }

    //region ViewModel
    private lateinit var signalViewModel: SignalViewModel

    val signal: MutableLiveData<Int>
        get() = signalViewModel.signal

    private lateinit var commonEventViewModel: CommonEventViewModel

    val commonEvent: MutableLiveData<Int>
        get() = commonEventViewModel.event

    private lateinit var windowInsetsViewModel: WindowInsetsViewModel

    val windowInsets: MutableLiveData<WindowInsetsCompat>
        get() = windowInsetsViewModel.windowInsetsCompatMutableLiveData


    lateinit var cacheDataXViewModel: CacheDataXViewModel

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

    /**
     * 当前正在负责观察WindowInsets变化的View
     */
    private var windowInsetsWatchingView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SignalViewModel.newInstance(this)?.let { signalViewModel = it }
        commonEventViewModel = getViewModel(CommonEventViewModel::class.java)
        WindowInsetsViewModel.newInstance(this)?.let { windowInsetsViewModel = it }
        cacheDataXViewModel = ViewModelProviders.of(this).get(CacheDataXViewModel::class.java)

        //观察缓存读取队列
        cacheDataXViewModel.cacheDataXLoadMap.observe(this, Observer {
            if (cacheDataXViewModel.readerWorking || cacheDataXViewModel.cacheDataXLoadMap.value == null || cacheDataXViewModel.cacheDataXLoadMap.value?.isEmpty() == true) return@Observer
            readCacheOnBackgroundThread()
        })

        //观察缓存成功读取
        cacheDataXViewModel.cacheDataX.observe(this, Observer {
            var size = 0
            cacheDataXViewModel.cacheDataXLoadMap.value?.size?.let { size = it }
            if (!cacheDataXViewModel.readerWorking && size > 0) {
                readCacheOnBackgroundThread()
            }
        })

        signal.observe(this, Observer {
            when (it) {
                SignalViewModel.EXIT_ACTIVITY -> {
                    finish()
                }
            }
        })


        transparentStatusBar(true) //如果想改变默认颜色，无需删掉这个，可以直接再调用一次传入不同的参数
    }


    override fun onStart() {
        super.onStart()
        watchWindowInsetsChange(rootView)
        toolbar?.setBackArrowColor()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 默认响应Appbar左侧返回箭头
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initAppbar()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        initAppbar()
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
        initAppbar()
    }

    override fun setTitle(title: CharSequence?) {
        collapsingToolbarLayout?.title = title
        if (toolbarTitle != null) {
            toolbarTitle?.text = title
        } else {
            super.setTitle(title)
        }
    }

    override fun setTitle(@StringRes resId: Int) {
        collapsingToolbarLayout?.title = getString(resId)
        if (toolbarTitle != null) {
            toolbarTitle?.setText(resId)
        } else {
            super.setTitle(resId)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //使用EasyPermission进行权限处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 通过一个View来监听屏幕安全区域的变化
     * 可以判断到状态栏/屏幕缺口/虚拟键盘等区域
     * 对View直接使用ViewCompat.setOnApplyWindowInsetsListener也可获取到类似的效果，但ViewCompat.setOnApplyWindowInsetsListener方法中获取的WindowInsets可能不可控的被消耗掉
     * 因此此处新编写此观察逻辑来确保有途径获取到当前Window中一个始终不变的WindowInsets来让界面作出相关适应
     *
     * @param view 通过哪个View来观察WindowInsets变化，此View应该在布局中尽量顶层
     */
    private fun watchWindowInsetsChange(view: View?) {
        if (view != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            windowInsetsWatchingView?.let {
                //清空之前view的观察，防止多次触发
                ViewCompat.setOnApplyWindowInsetsListener(it, null)
            }
            windowInsetsWatchingView = view
            ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
                windowInsets.value = insets
                insets //不消费
            }
        }
    }

    /**
     * 初始化约定结构的Appbar
     * 固定Toolbar和伸缩两种
     * 在setContentView后自动调用
     */
    private fun initAppbar() {
        appbarLayoutParent = findViewById(R.id.appbarLayoutParent)
        appbarLayout = appbarLayoutParent?.findViewById(R.id.appbarLayout)
        appbarLayoutParent?.let { parent ->
            toolbar = appbarLayout?.findViewById(R.id.toolbar) //只对Appbar中的toolbar操作
            collapsingToolbarLayout = appbarLayoutParent?.findViewById(R.id.collapsingToolbarLayout) //这个View父级一定是CoordinatorLayout（否则就不起作用了）
            windowInsets.observe(this, Observer { windowInsetsCompat ->
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
                toolbar?.setBackArrowColor()
                toolbar?.layoutParams?.height = resources.getDimensionPixelSize(R.dimen.toolbar_height)

                toolbarTitle = toolbar?.findViewById(R.id.toolbarTitle) //只对toolbar中的toolbarTitle操作
                toolbarTitle?.apply {
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.toolbar_title_text_size).toFloat())
                    setTextColor(ResourcesCompat.getColor(resources, R.color.appbarTitleColor, theme))
                }


                setSupportActionBar(toolbar)
                //清空Android原有的标题
                toolbar?.title = ""
                super.setTitle("")

                val appbarTitleColor = ResourcesCompat.getColor(resources, R.color.appbarTitleColor, theme)
                toolbar?.apply {
                    setTitleTextColor(appbarTitleColor)
                    setSubtitleTextColor(appbarTitleColor)
                }

                windowInsets.observe(this, Observer { windowInsetsCompat ->
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
                            theme
                        )
                    )
                    setExpandedTitleColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.collapsing_toolbar_layout_expanded_title,
                            theme
                        )
                    )
                    title = ""
                }
                windowInsets.observe(this, Observer { windowInsetsCompat ->
                    collapsingToolbarLayout?.layoutParams?.let { lp ->
                        if (lp is ViewGroup.MarginLayoutParams) {
                            lp.topMargin = windowInsetsCompat.currentSafeTop
                        }
                    }
                })
            }
        }
    }


    private fun readCacheOnBackgroundThread() {
        cacheDataXViewModel.cacheDataXLoadMap.value?.let { loadList ->
            cacheDataXViewModel.readerWorking = true
            Thread() {
                val firstTag = loadList.keys.first()
                val dataXClass = loadList[firstTag]
                loadList.remove(firstTag)
                dataXClass?.let { cls ->
                    cacheDataXViewModel.cacheDataX.value = readCacheDataX(firstTag, cls)
                }
                if (loadList.size == 0) cacheDataXViewModel.readerWorking = false
            }.start()
        }
    }

    /**
     * 检查缓存情况
     * 子线程操作
     * 推荐在OnCreate中调用，setContentView之前，用户体验最佳
     * 结果在cacheDataXViewModel.fastCacheDataXResultList中观察
     */
    fun checkCacheX(vararg tags: String) {
        Thread() {
            cacheDataXViewModel.fastCacheDataXResultList.postValue(fastCheckCache(tags.toList()))
        }.start()
    }

    fun readCacheX(vararg cacheInstance: Pair<String, Class<out DataX>>) {
        //刷新数据触发观察
        cacheDataXViewModel.cacheDataXLoadMap.update {
            it?.let { map ->
                cacheInstance.forEach {
                    map[it.first] = it.second
                }
            }
        }
    }
}