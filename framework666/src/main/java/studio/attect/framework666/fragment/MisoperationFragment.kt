package studio.attect.framework666.fragment

import android.view.View

/**
 * 处理误操作的Fragment
 * 主要由于动画或设备性能导致的操作抖动
 *
 * @author Attect
 */
abstract class MisoperationFragment : PerceptionFragment() {

    private val blockLeaveClickView = ArrayList<View>()

    override fun onResume() {
        super.onResume()
        blockLeaveClickView.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        blockLeaveClickView.clear()
    }

    /**
     * 出发离开的点击监听器
     *
     * 适用于
     * 点击以后会离开当前界面
     * 再次返回后才可再次点击
     * 的情况
     *
     *
     * 注意：如果在ViewHolder中使用记得ViewHolder需要为内部类(如果你遇到了怎么无法import此类时十有八九是因为这个原因）
     */
    abstract inner class OnClickLeaveListener : View.OnClickListener {
        override fun onClick(v: View) {
            if (!blockLeaveClickView.contains(v)) {
                blockLeaveClickView.add(v)
                onValidClick(v)
            }
        }

        abstract fun onValidClick(v: View)

    }
}