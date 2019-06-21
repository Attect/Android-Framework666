package studio.attect.framework666.demo

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import studio.attect.framework666.ActivityX
import studio.attect.framework666.extensions.setStatusBarColor

class MainActivity : ActivityX() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, theme))

        title = "Hello"
    }
}
