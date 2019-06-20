package studio.attect.framework666

import android.app.Application
import studio.attect.framework666.helper.Rumble

/**
 * 综合Application
 * 初始化一些东西
 * 使用此框架，必须继承此类实现自己的Application或者直接使用此Application代替默认的
 *
 * 此类的父类会随着开发和变更而变更，不可通过反射获取准确类型
 * @author Attect
 */

class ApplicationX : Application() {

    override fun onCreate() {
        super.onCreate()

        //振动
        Rumble.init(applicationContext)
    }
}