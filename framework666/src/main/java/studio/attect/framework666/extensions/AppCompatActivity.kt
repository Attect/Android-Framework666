package studio.attect.framework666.extensions

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import studio.attect.framework666.R

/**
 * 在Appbar上显示返回箭头
 * 事件已经默认设定好
 * 需要在 setContentView 后调用
 */
fun AppCompatActivity.showBackArrow() {
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    val backUpArrow: View? = findViewById(R.id.backUpArrow)
    backUpArrow?.setOnClickListener {
        onBackPressed()
    }
}