package studio.attect.framework666.annotations

/**
 * 组件标签[tag]
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Component(val tag: String)