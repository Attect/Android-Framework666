package studio.attect.framework666.annotationprocessor

import studio.attect.framework666.annotationprocessor.extensions.generatedPath
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

/**
 * 注解处理器X
 * 把一些麻烦操作封装一下
 *
 * @author Attect
 */
abstract class AnnotationProcessorX : AbstractProcessor() {
    override fun process(annotations: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {
        processingEnv.generatedPath?.let {
            return process(it, annotations, roundEnvironment)
        }
        processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "项目构建过程路径获取失败")
        return false
    }

    abstract fun process(generatePath: String, annotations: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean

    /**
     * 在Build Output中输出错误等级信息
     * 将会导致构建终止
     */
    fun error(message: String) {
        processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, message)
    }

    /**
     * 在Build Output中输出警告等级信息
     */
    fun warn(message: String) {
        processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, message)
    }

    /**
     * 在Build Output中输出记录等级信息
     * 如果没有打开对应输出等级将不会被看到
     */
    fun note(message: String) {
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, message)
    }
}