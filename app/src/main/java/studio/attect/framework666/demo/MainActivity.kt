package studio.attect.framework666.demo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import studio.attect.framework666.ActivityX
import studio.attect.framework666.compomentX.ComponentXMap
import studio.attect.framework666.demo.fragment.NormalComponent
import studio.attect.framework666.extensions.fromJson
import studio.attect.framework666.extensions.setStatusBarColor

class MainActivity : ActivityX() {
    private var bottomNavigationTags = arrayListOf<String>()
    private val bottomNavigationViewHolders = arrayListOf<BottomNavigationViewHolder>()

    private var currentComponentTag = ""

    private val preference: SharedPreferences by lazy {
        getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, theme))

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        Gson().fromJson<ArrayList<String>>(preference.getString(BOTTOM_TAG_PREFERENCE_NAME, "[]"))?.let {
            bottomNavigationTags.clear()
            bottomNavigationTags.addAll(it)
        }

        if (bottomNavigationTags.size != BOTTOM_NAVIGATION_NUM) {
            bottomNavigationTags.clear()
            bottomNavigationTags.add(NormalComponent.getTag())
            for (i in 1 until BOTTOM_NAVIGATION_NUM - 1) bottomNavigationTags.add("component_selector_$i")
            bottomNavigationTags.add("TEST_NOT_FOUND")
        }

        refreshBottomNavigation()
    }

    private fun refreshBottomNavigation() {
        bottomNavigation.removeAllViews()
        bottomNavigationTags.forEach { tag ->
            val componentXCompanion = ComponentXMap.detail(tag)
            val viewHolder = BottomNavigationViewHolder()
            val view = layoutInflater.inflate(R.layout.bottom_navigation_item, bottomNavigation, false)
            viewHolder.imageView = view.findViewById(R.id.icon)
            viewHolder.label = view.findViewById(R.id.label)

            viewHolder.imageView.setImageDrawable(componentXCompanion.getIcon(this))
            viewHolder.label.text = componentXCompanion.getName(this)

            viewHolder.tag = tag

            viewHolder.setActive(currentComponentTag == componentXCompanion.getTag())

            view.setOnClickListener { v ->
                changePage(tag)
            }

            bottomNavigationViewHolders.add(viewHolder)

            bottomNavigation.addView(view)
        }

        changePage(NormalComponent.getTag()) //默认页
    }

    private fun changePage(tag: String) {
        if (tag == currentComponentTag) return
        bottomNavigationViewHolders.forEach {
            it.setActive(it.tag == tag)
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, ComponentXMap.go(tag)).commit()
        currentComponentTag = tag
    }

    inner class BottomNavigationViewHolder {
        lateinit var imageView: AppCompatImageView
        lateinit var label: AppCompatTextView
        var tag: Any? = null

        fun setActive(active: Boolean) {
            val color = ResourcesCompat.getColor(
                resources, if (active) {
                    R.color.colorPrimary
                } else {
                    R.color.grey_500
                }, theme
            )
            imageView.setColorFilter(color)
            label.setTextColor(color)
        }
    }

    companion object {

        const val PREFERENCES_NAME = "main"

        const val BOTTOM_TAG_PREFERENCE_NAME = "bottomTag"

        const val BOTTOM_NAVIGATION_NUM = 5
    }
}
