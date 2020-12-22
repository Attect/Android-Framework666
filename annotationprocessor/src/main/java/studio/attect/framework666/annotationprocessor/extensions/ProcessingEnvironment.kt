package studio.attect.framework666.annotationprocessor.extensions

import javax.annotation.processing.ProcessingEnvironment

/**
 *  当前构建生成目录
 */
val ProcessingEnvironment.generatedPath: String?
    get() {
        return this.options["kapt.kotlin.generated"]
    }