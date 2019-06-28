package studio.attect.framework666

/**
 * 此类是BuildConfig的镜像
 * 用于解决一些在library中关键数据无法获取或者获取效率低下的问题
 * 理论上被设置值以后就不应该被变更(set做了一些处理)
 * 默认值为框架的值，因此不要忘了设置
 *
 * 复制以下代码到你的Application的onCreate中(鼠标中键选中复制）
 * RuntimeBuildConfig.DEBUG = BuildConfig.DEBUG
 * RuntimeBuildConfig.APPLICATION_ID = BuildConfig.APPLICATION_ID
 * RuntimeBuildConfig.BUILD_TYPE = BuildConfig.BUILD_TYPE
 * RuntimeBuildConfig.FLAVOR = BuildConfig.FLAVOR
 * RuntimeBuildConfig.VERSION_CODE = BuildConfig.VERSION_CODE
 * RuntimeBuildConfig.VERSION_NAME = BuildConfig.VERSION_NAME
 *
 * @author Attect
 */
object RuntimeBuildConfig {
    var DEBUG: Boolean = BuildConfig.DEBUG
        set(value) {
            if (field == BuildConfig.DEBUG) field = value
        }

    var APPLICATION_ID = BuildConfig.APPLICATION_ID
        set(value) {
            if (field == BuildConfig.APPLICATION_ID) field = value
        }

    var BUILD_TYPE = BuildConfig.BUILD_TYPE
        set(value) {
            if (field == BuildConfig.BUILD_TYPE) field = value
        }

    var FLAVOR = BuildConfig.FLAVOR
        set(value) {
            if (field == BuildConfig.FLAVOR) field = value
        }

    var VERSION_CODE = BuildConfig.VERSION_CODE
        set(value) {
            if (field == BuildConfig.VERSION_CODE) field = value
        }

    var VERSION_NAME = BuildConfig.VERSION_NAME
        set(value) {
            if (field == BuildConfig.VERSION_NAME) field = value
        }
}