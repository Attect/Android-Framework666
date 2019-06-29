package studio.attect.framework666.demo.helper

/**
 * 草!崩溃了
 *
 * @author Attect
 */
object BugFucker {
    /**
     * 进入无响应
     * 需要在主线程调用
     */
    fun fuckANR() {
        while (true) {
        }
    }

    /**
     * 随机产生一种崩溃
     * 需要更多可以自己实现
     */
    fun fuckCrash() {
        when ((0..1).shuffled().last()) {
            0 -> {
                arrayListOf<Int>()[1]
            }
            1 -> {
                "ab**c".toInt()
            }
        }
    }
}