package studio.attect.framework666.extensions

import androidx.appcompat.app.AppCompatActivity

/**
 * 在Appbar上显示返回箭头
 * 事件已经默认设定好
 * 需要在 setContentView 后调用
 */
fun AppCompatActivity.showBackArrow() {
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
}