package studio.attect.framework666.demo.activity

import android.os.Bundle
import studio.attect.framework666.ActivityX
import studio.attect.framework666.demo.R

/**
 * 普普通通的Activity
 *
 * @author Attect
 */
class NormalActivity : ActivityX() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal)
    }

}
