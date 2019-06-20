package studio.attect.framework666.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager

/**
 * ViewPager的OnPageChangeListener的ViewModel版
 * 使用例子：可以用于Activity中的ViewPager中的Fragment感知Activity的ViewPager发生了翻页
 * 实现了
 * @see ViewPager.OnPageChangeListener
 * @sample ViewPager.setOnPageChangeListener(ViewPagerOnPageChangeViewModel)
 *
 * @author Attect
 */
class ViewPagerOnPageChangeViewModel : ViewModel(), ViewPager.OnPageChangeListener {
    var onPageScrolledMutableLiveData: MutableLiveData<OnPageScrolled> = MutableLiveData()

    var onPageSelectedMutableLiveData: MutableLiveData<Int> = MutableLiveData()

    var onPageScrollStateChangedMutableLiveData: MutableLiveData<Int> = MutableLiveData()


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val onPageScrolled = OnPageScrolled()
        onPageScrolled.position = position
        onPageScrolled.positionOffset = positionOffset
        onPageScrolled.positionOffsetPixels = positionOffsetPixels
        onPageScrolledMutableLiveData.value = onPageScrolled
    }

    override fun onPageSelected(position: Int) {
        onPageSelectedMutableLiveData.value = position
    }

    override fun onPageScrollStateChanged(state: Int) {
        onPageScrollStateChangedMutableLiveData.value = state
    }

    /**
     * @see ViewPagerOnPageChangeViewModel.OnPageScrolled
     */
    inner class OnPageScrolled {
        var position: Int = 0
        var positionOffset: Float = 0.toFloat()
        var positionOffsetPixels: Int = 0
    }


}
