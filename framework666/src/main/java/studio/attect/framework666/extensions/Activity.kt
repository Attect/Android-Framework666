package studio.attect.framework666.extensions

import android.app.Activity
import android.view.View
import android.view.ViewGroup

val Activity.rootView: View
    get() {
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        return if (viewGroup.childCount == 0) viewGroup else viewGroup.getChildAt(0)
    }