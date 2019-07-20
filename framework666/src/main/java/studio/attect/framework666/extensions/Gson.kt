package studio.attect.framework666.extensions

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

/**
 * 相当方便的Gson字符串转对象
 * 解决了嵌套范型麻烦的问题
 * 做了异常捕获，防止太多意外崩溃影响体验
 *
 * 内联函数（为了解决泛型）
 * 只能在Kotlin中使用
 *
 * @param T 期望结果类型
 * @param json JSON格式的字符串
 * @return 解析JSON后得到的对象，可能为null
 */
inline fun <reified T : Any> Gson.fromJson(json: String?): T? {
    try {
        return fromJson<T>(json, object : TypeToken<T>() {}.type)
    } catch (e: JsonSyntaxException) {
        e.printStackTrace()
    } catch (e: JsonIOException) {
        e.printStackTrace()
    }
    return null
}