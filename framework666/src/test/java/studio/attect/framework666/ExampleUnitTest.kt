package studio.attect.framework666

import org.junit.Assert
import org.junit.Test
import studio.attect.framework666.componentX.ComponentXProvider

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val map = HashMap<String, Class<FragmentX>>()
        map.put("a", ComponentXProvider::class.java as Class<FragmentX>)
        Assert.assertEquals(4, 2 + 2.toLong())
    }
}