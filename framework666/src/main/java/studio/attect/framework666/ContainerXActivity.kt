package studio.attect.framework666

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import studio.attect.framework666.compomentX.ArgumentX
import studio.attect.framework666.compomentX.ComponentX
import studio.attect.framework666.compomentX.ComponentXMap
import studio.attect.framework666.compomentX.ContainerX

/**
 * 万能的承载ComponentX的Activity
 * 会从ComponentXMap中根据tag自动取出对象并实例化
 * 理论上所有ComponentX丢进来都应该能正常运行
 *
 * 最好从固定的startActivity启动
 * 也提供了产生PendingIntent用于在Notification中“延迟”启动
 *
 * 会将参数传递给ComponentX
 *
 * 要求显示返回箭头
 * 不处理屏幕安全区域
 * 会将result作为Activity Result返回前一层（取决于启动方法）
 *
 * @author Attect
 */
class ContainerXActivity : ActivityX(), ContainerX {
    private var tag = ""
    private var componentX: ComponentX? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!intent.hasExtra(DATA_KEY)) {
            finish()
            return
        }

        tag = intent.getStringExtra(DATA_KEY)

        if (TextUtils.isEmpty(tag)) {
            finish()
            return
        }

        componentX = ComponentXMap.detail(tag).newInstance()

        if (componentX == null) {
            finish()
            return
        }

        setContentView(R.layout.activity_container_x)

        componentX?.let {
            val fragment = it.getComponentXFragmentInstance(intent.getBundleExtra(ARGUMENT_KEY))
            if (fragment !is NavHostFragment) {
                fragment.arguments = intent.getBundleExtra(ARGUMENT_KEY)
            }
            supportFragmentManager.beginTransaction().add(R.id.componentXContainer, fragment, tag).commit()
        }
    }

    /**
     * 额外处理了NavHostFragment中使用返回导航的逻辑
     * 不至于直接finish掉，而是正常按照Navigation Graph的箭头走
     */
    override fun onBackPressed() {
        if (componentX != null) {
            componentX?.let {
                val fragment = it.getComponentXFragmentInstance()
                if (fragment is NavHostFragment) {
                    if (runOnBackPressedQueue()) {
                        return
                    }
                    if (!fragment.navController.navigateUp()) {
                        super.onBackPressed()
                    }
                } else {

                    super.onBackPressed()
                }
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun shouldShowBackAllow(): Boolean {
        return true
    }

    override fun shouldKeepScreenSafeArea(): Boolean {
        return true
    }

    /**
     * 将result作为Activity的Result
     */
    override fun handleResult(resultCode: Int, bundle: Bundle) {
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(resultCode, intent)
        finish()
    }

    companion object {
        private const val DATA_KEY = "tag"
        private const val ARGUMENT_KEY = "arguments"

        /**
         * 启动一个ComponentX
         * 不带任何参数
         */
        @JvmStatic
        fun startActivity(context: Context, tag: String) {
            val intent = Intent(context, ContainerXActivity::class.java)
            intent.putExtra(DATA_KEY, tag)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        /**
         * 启动一个ComponentX
         * 带参数
         */
        @JvmStatic
        fun startActivity(context: Context, tag: String, argument: ArgumentX?) {
            val intent = Intent(context, ContainerXActivity::class.java)
            intent.putExtra(DATA_KEY, tag)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            argument?.let {
                intent.putExtra(ARGUMENT_KEY, it.toBundle())
            }
            context.startActivity(intent)
        }

        /**
         * 需要返回结果的启动方式
         * 结果返回到Fragment
         *
         * @param fragment 接收参数的Fragment
         * @param tag ComponentX对应的tag
         * @param argument 传递给ComponentX的参数
         * @param requestCode 用于区分结果的code
         */
        @JvmOverloads
        @JvmStatic
        fun startActivityForResult(fragment: Fragment, tag: String, argument: ArgumentX?, requestCode: Int = Int.MIN_VALUE) {
            fragment.context?.let { context ->
                val intent = Intent(context, ContainerXActivity::class.java)
                intent.putExtra(DATA_KEY, tag)
                argument?.let {
                    intent.putExtra(ARGUMENT_KEY, it.toBundle())
                }
                if (requestCode > Int.MIN_VALUE) { //大于此值则视为需要结果回调
                    fragment.startActivityForResult(intent, requestCode)
                } else {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    fragment.startActivity(intent)
                }
            }
        }

        /**
         * 需要返回结果的启动方式
         * 结果返回到Activity
         *
         * @param activity 接收参数的Fragment
         * @param tag ComponentX对应的tag
         * @param argument 传递给ComponentX的参数
         * @param requestCode 用于区分结果的code
         */
        @JvmOverloads
        @JvmStatic
        fun startActivityForResult(activity: Activity, tag: String, argument: ArgumentX?, requestCode: Int = Int.MIN_VALUE) {
            val intent = Intent(activity, ContainerXActivity::class.java)
            intent.putExtra(DATA_KEY, tag)
            argument?.let {
                intent.putExtra(ARGUMENT_KEY, it.toBundle())
            }
            if (requestCode > Int.MIN_VALUE) {
                activity.startActivityForResult(intent, requestCode)
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                activity.startActivity(intent)
            }
        }

        /**
         * 获得一个Pending Intent
         * 通常用于通知、Widget中延迟启动设定好的ComponentX
         *
         * @param context 上下文
         * @param tag ComponentX对应的tag
         * @param action Intent的Action，也可用于防止Notification中分组里点击任何一个都是同一个Pending Intent
         * @param requestCode 请求码，默认为时间毫秒，防止Notification中分组里点击任何一个都是同一个Pending Intent
         */
        @JvmOverloads
        @JvmStatic
        fun getPendingIntent(context: Context, tag: String, argument: ArgumentX? = null, action: String? = null, requestCode: Int = System.currentTimeMillis().toInt()): PendingIntent {
            val intent = Intent(context, ContainerXActivity::class.java)
            intent.putExtra(DATA_KEY, tag)
            intent.action = action
            argument?.let {
                intent.putExtra(ARGUMENT_KEY, it.toBundle())
            }
            return PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

    }
}