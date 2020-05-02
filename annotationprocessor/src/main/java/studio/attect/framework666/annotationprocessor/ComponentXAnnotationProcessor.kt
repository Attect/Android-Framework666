package studio.attect.framework666.annotationprocessor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import studio.attect.framework666.annotations.Component
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

/**
 * 对应App内所有Component注解进行管理类生成
 *
 * 生成对应：studio.attect.framework666.ComponentXManager
 * @author Attect
 */
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("studio.attect.framework666.annotations.Component")
@SupportedOptions
class ComponentXAnnotationProcessor : AnnotationProcessorX() {
    /**
     * ClassName:ComponentX
     */
    private val componentXClassName = ClassName("studio.attect.framework666.componentX", "ComponentX")

    /**
     * ClassName:Class<out ComponentX>
     */
    private val componentXOutClazzClassName = Class::class.asTypeName().parameterizedBy(
        WildcardTypeName.producerOf(componentXClassName)
    )

    override fun process(generatePath: String, annotations: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {
        val scanList = ArrayList<Pair<ClassName, Component>>()
        roundEnvironment?.getElementsAnnotatedWith(Component::class.java)?.forEach { element ->
            if (element is TypeElement) {
                element.getAnnotation(Component::class.java)?.let { annotation ->
                    warn("Scanned ComponentX [${annotation.tag}] -> ${element.qualifiedName}")
                    scanList += Pair(element.asClassName(), annotation)
                }
            }
        }
        if (scanList.isEmpty()) return true
        val fileBuilder = FileSpec.builder("studio.attect.framework666", "ComponentXManager").apply {
            addType(
                TypeSpec.objectBuilder("ComponentXManager").apply {
                    this.superclass(ClassName("studio.attect.framework666.componentX", "ComponentXContainer"))
                    addKdoc(
                        CodeBlock.of("组件X维护\n代码由ComponentXAnnotationProcessor自动生成\n用于维护组件X的路由及管理\n\n@author Attect", "")
                    )
                    addInitializerBlock(CodeBlock.builder().apply {
                        scanList.forEach {
                            addStatement("register(%S,%T::class.java)", it.second.tag, it.first)
                        }
                    }.build())
                    addFunction(
                        FunSpec.builder("get")
                            .addKdoc("获得指定tag的ComponentX")
                            .addParameter("tag", String::class.asTypeName())
                            .addStatement("return Companion.get(tag)")
                            .returns(componentXClassName.copy(true))
                            .build()
                    )
//                    addProperty(
//                        PropertySpec.builder(
//                            "componentMap", componentXMapOutClassName
//                        ).delegate(CodeBlock.builder().beginControlFlow("lazy").add("initMap()\n").endControlFlow().build()).addKdoc("所有组件X被放入此Map").build()
//                    )
//                    addFunction(
//                        FunSpec
//                            .builder("initMap")
//                            .addKdoc("载入所有组件X的Class")
//                            .addStatement("val result = %T()", componentXMapClassName)
//                            .apply {
//                                scanList.forEach {
//                                    addStatement("result.put(%S,%T::class.java)", it.second.tag, it.first)
//                                }
//                            }
//                            .addStatement("return result")
//                            .returns(componentXMapOutClassName)
//                            .build()
//                    )
                }.build()
            )


        }
        fileBuilder.build().writeTo(File(generatePath))


        return true
    }
}
