package studio.attect.framework666.demo.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.SparseIntArray
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.irozon.sneaker.Sneaker
import kotlinx.android.synthetic.main.componentx_crash_and_anr.*
import net.steamcrafted.materialiconlib.IconValue
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder
import studio.attect.framework666.FragmentX
import studio.attect.framework666.compomentX.ComponentX
import studio.attect.framework666.compomentX.ComponentXProvider
import studio.attect.framework666.demo.R
import studio.attect.framework666.demo.helper.BugFucker
import studio.attect.framework666.extensions.currentSafeTop
import studio.attect.framework666.extensions.dp2px
import studio.attect.framework666.extensions.isAlive
import studio.attect.framework666.extensions.transparentStatusBar
import studio.attect.framework666.viewModel.SignalViewModel

/**
 * 崩溃及异常Crash模拟ComponentX
 * 调用提供的Bug产生器产生一个ANR或Crash
 * 当然，这是开发调试框架时时测试用的
 *
 * @author Attect
 */
class CrashAndANRComponentX : FragmentX() {
    private val spicyEyeColors = SparseIntArray()
    private val moreTextView = arrayListOf<AppCompatTextView>()
    private var play = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityX?.signal?.observe(this, Observer {
            when (it) {
                SignalViewModel.CRASH -> {
                    Sneaker.with(requireActivity())
                        .setTitle(getString(R.string.framework_bug_title))
                        .setMessage(getString(R.string.framework_bug_message))
                        .autoHide(false)
                        .sneakError()
                }
            }
        })
        //region colors
        spicyEyeColors.put(getColor(R.color.red_600), getColor(R.color.cover_on_red_600))
        spicyEyeColors.put(getColor(R.color.pink_600), getColor(R.color.cover_on_pink_600))
        spicyEyeColors.put(getColor(R.color.purple_600), getColor(R.color.cover_on_purple_600))
        spicyEyeColors.put(getColor(R.color.deep_purple_600), getColor(R.color.cover_on_deep_purple_600))
        spicyEyeColors.put(getColor(R.color.indigo_600), getColor(R.color.cover_on_indigo_600))
        spicyEyeColors.put(getColor(R.color.blue_600), getColor(R.color.cover_on_blue_600))
        spicyEyeColors.put(getColor(R.color.light_blue_600), getColor(R.color.cover_on_light_blue_600))
        spicyEyeColors.put(getColor(R.color.cyan_600), getColor(R.color.cover_on_cyan_600))
        spicyEyeColors.put(getColor(R.color.teal_600), getColor(R.color.cover_on_teal_600))
        spicyEyeColors.put(getColor(R.color.green_600), getColor(R.color.cover_on_green_600))
        spicyEyeColors.put(getColor(R.color.light_green_600), getColor(R.color.cover_on_light_green_600))
        spicyEyeColors.put(getColor(R.color.lime_600), getColor(R.color.cover_on_lime_600))
        spicyEyeColors.put(getColor(R.color.yellow_600), getColor(R.color.cover_on_yellow_600))
        spicyEyeColors.put(getColor(R.color.amber_600), getColor(R.color.cover_on_amber_600))
        spicyEyeColors.put(getColor(R.color.orange_600), getColor(R.color.cover_on_orange_600))
        spicyEyeColors.put(getColor(R.color.deep_orange_600), getColor(R.color.cover_on_deep_orange_600))
        spicyEyeColors.put(getColor(R.color.brown_600), getColor(R.color.cover_on_brown_600))
        spicyEyeColors.put(getColor(R.color.grey_600), getColor(R.color.cover_on_grey_600))
        spicyEyeColors.put(getColor(R.color.blue_grey_600), getColor(R.color.cover_on_blue_grey_600))
        spicyEyeColors.put(getColor(R.color.red_800), getColor(R.color.cover_on_red_800))
        spicyEyeColors.put(getColor(R.color.pink_800), getColor(R.color.cover_on_pink_800))
        spicyEyeColors.put(getColor(R.color.purple_800), getColor(R.color.cover_on_purple_800))
        spicyEyeColors.put(getColor(R.color.deep_purple_800), getColor(R.color.cover_on_deep_purple_800))
        spicyEyeColors.put(getColor(R.color.indigo_800), getColor(R.color.cover_on_indigo_800))
        spicyEyeColors.put(getColor(R.color.blue_800), getColor(R.color.cover_on_blue_800))
        spicyEyeColors.put(getColor(R.color.light_blue_800), getColor(R.color.cover_on_light_blue_800))
        spicyEyeColors.put(getColor(R.color.cyan_800), getColor(R.color.cover_on_cyan_800))
        spicyEyeColors.put(getColor(R.color.teal_800), getColor(R.color.cover_on_teal_800))
        spicyEyeColors.put(getColor(R.color.green_800), getColor(R.color.cover_on_green_800))
        spicyEyeColors.put(getColor(R.color.light_green_800), getColor(R.color.cover_on_light_green_800))
        spicyEyeColors.put(getColor(R.color.lime_800), getColor(R.color.cover_on_lime_800))
        spicyEyeColors.put(getColor(R.color.yellow_800), getColor(R.color.cover_on_yellow_800))
        spicyEyeColors.put(getColor(R.color.amber_800), getColor(R.color.cover_on_amber_800))
        spicyEyeColors.put(getColor(R.color.orange_800), getColor(R.color.cover_on_orange_800))
        spicyEyeColors.put(getColor(R.color.deep_orange_800), getColor(R.color.cover_on_deep_orange_800))
        spicyEyeColors.put(getColor(R.color.brown_800), getColor(R.color.cover_on_brown_800))
        spicyEyeColors.put(getColor(R.color.grey_800), getColor(R.color.cover_on_grey_800))
        spicyEyeColors.put(getColor(R.color.blue_grey_800), getColor(R.color.cover_on_blue_grey_800))
        spicyEyeColors.put(getColor(R.color.red_400), getColor(R.color.cover_on_red_400))
        spicyEyeColors.put(getColor(R.color.pink_400), getColor(R.color.cover_on_pink_400))
        spicyEyeColors.put(getColor(R.color.purple_400), getColor(R.color.cover_on_purple_400))
        spicyEyeColors.put(getColor(R.color.deep_purple_400), getColor(R.color.cover_on_deep_purple_400))
        spicyEyeColors.put(getColor(R.color.indigo_400), getColor(R.color.cover_on_indigo_400))
        spicyEyeColors.put(getColor(R.color.blue_400), getColor(R.color.cover_on_blue_400))
        spicyEyeColors.put(getColor(R.color.light_blue_400), getColor(R.color.cover_on_light_blue_400))
        spicyEyeColors.put(getColor(R.color.cyan_400), getColor(R.color.cover_on_cyan_400))
        spicyEyeColors.put(getColor(R.color.teal_400), getColor(R.color.cover_on_teal_400))
        spicyEyeColors.put(getColor(R.color.green_400), getColor(R.color.cover_on_green_400))
        spicyEyeColors.put(getColor(R.color.light_green_400), getColor(R.color.cover_on_light_green_400))
        spicyEyeColors.put(getColor(R.color.lime_400), getColor(R.color.cover_on_lime_400))
        spicyEyeColors.put(getColor(R.color.yellow_400), getColor(R.color.cover_on_yellow_400))
        spicyEyeColors.put(getColor(R.color.amber_400), getColor(R.color.cover_on_amber_400))
        spicyEyeColors.put(getColor(R.color.orange_400), getColor(R.color.cover_on_orange_400))
        spicyEyeColors.put(getColor(R.color.deep_orange_400), getColor(R.color.cover_on_deep_orange_400))
        spicyEyeColors.put(getColor(R.color.brown_400), getColor(R.color.cover_on_brown_400))
        spicyEyeColors.put(getColor(R.color.grey_400), getColor(R.color.cover_on_grey_400))
        spicyEyeColors.put(getColor(R.color.blue_grey_400), getColor(R.color.cover_on_blue_grey_400))
        //endregion
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.componentx_crash_and_anr, container, false)
        val textContainer = view.findViewById<LinearLayout>(R.id.container)
        for (i in 0 until 20) {
            val line = LinearLayout(context)
            line.orientation = LinearLayout.HORIZONTAL
            val leftText = AppCompatTextView(context)
            val rightText = AppCompatTextView(context)

            val lp = LinearLayout.LayoutParams(0, resources.dp2px(60f)).apply {
                weight = 1f
            }

            leftText.setText(getRandomTextRes())
            leftText.layoutParams = lp
            leftText.textSize = 32f
            leftText.gravity = Gravity.CENTER
            updateSpiceEyeColor(leftText)


            rightText.setText(getRandomTextRes())
            rightText.layoutParams = lp
            rightText.textSize = 32f
            rightText.gravity = Gravity.CENTER
            updateSpiceEyeColor(rightText)

            line.addView(leftText)
            line.addView(rightText)

            moreTextView.add(leftText)
            moreTextView.add(rightText)

            textContainer.addView(line)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        windowInsets?.observe(this, Observer {
            if (header.paddingTop == 0) {
                header.setPadding(header.left, it.currentSafeTop, header.right, header.bottom)
            }
        })
        crash.setOnClickListener {
            //Thread() {
            BugFucker.fuckCrash()
            //}.start()
        }
        anr.setOnClickListener {
            BugFucker.fuckANR()
        }
    }

    override fun onStart() {
        super.onStart()
        play = true
        updateSpicyEyeHeaderUI()
        updateSpiceEyeMoreUI()
    }

    override fun onStop() {
        super.onStop()
        play = false
    }

    override fun onDestroy() {
        super.onDestroy()
        moreTextView.clear()
    }

    private fun updateSpicyEyeHeaderUI() {
        view?.postDelayed({
            if (!isAlive()) return@postDelayed
            val darkText = ResourcesCompat.getColor(resources, R.color.cover_black, requireContext().theme) == updateSpiceEyeColor(header)
            activityX?.transparentStatusBar(!darkText)
            updateSpiceEyeColor(crash)
            updateSpiceEyeColor(anr)
            if (play) updateSpicyEyeHeaderUI()
        }, (400..800).shuffled().last().toLong())
    }

    private fun updateSpiceEyeMoreUI() {
        container?.postDelayed({
            moreTextView.forEach {
                it.setText(getRandomTextRes())
                updateSpiceEyeColor(it)
            }
            if (play) updateSpiceEyeMoreUI()
        }, (50..200).shuffled().last().toLong())
    }

    private fun getColor(@ColorRes color: Int) = ResourcesCompat.getColor(resources, color, requireContext().theme)

    @StringRes
    private fun getRandomTextRes() = arrayListOf(R.string.framework_force_anr, R.string.framework_play_egg, R.string.framework_gg, R.string.framework_crash).shuffled().last()

    private fun updateSpiceEyeColor(textView: AppCompatTextView?): Int {
        val position = (0 until spicyEyeColors.size()).shuffled().last()
        val mainColor = spicyEyeColors.keyAt(position)
        val coverColor = spicyEyeColors[mainColor]
        textView?.background = ColorDrawable(mainColor)
        textView?.setTextColor(coverColor)
        return coverColor
    }

    companion object : ComponentXProvider {
        override fun getTag(): String = "crash_and_anr"

        override fun getIcon(context: Context?): Drawable? {
            context?.let {
                return MaterialDrawableBuilder.with(context).setIcon(IconValue.ANDROID_DEBUG_BRIDGE).setSizeDp(24).setColor(Color.WHITE).build()
            }
            return null
        }

        override fun getName(context: Context?): String {
            context?.let {
                return context.getString(R.string.framework_hate_bug)
            }
            return "讨厌BUG!"
        }

        override fun getColor(context: Context?): Int? = null

        override fun newInstance(): ComponentX = CrashAndANRComponentX()

    }

}