package studio.attect.framework666.interfaces

/**
 * 意外退出处理
 * 未捕获的异常导致的
 * 用户通过其它方式直接终止的不会走此
 *
 * @author Attect
 */
interface UnExpectedExitHandler {
    fun OnUnExpectedExit()
}