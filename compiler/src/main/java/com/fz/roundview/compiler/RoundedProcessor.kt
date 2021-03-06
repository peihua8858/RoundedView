package com.fz.roundview.compiler

import com.fz.roundview.annotation.RoundedView
import com.google.auto.service.AutoService
import com.google.common.collect.ImmutableSet
import com.squareup.javapoet.*
import com.sun.source.util.Trees
import net.ltgt.gradle.incap.IncrementalAnnotationProcessor
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.*
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.MirroredTypesException
import javax.tools.Diagnostic

@SuppressWarnings("unused")
@AutoService(Processor::class)
@IncrementalAnnotationProcessor(IncrementalAnnotationProcessorType.DYNAMIC)
class RoundedProcessor : AbstractProcessor() {
    private val CLASS_JAVA_DOC =
            "Generated by Rounded-Processor. Do not edit it!\n" +
            "@author dingpeihua\n" +
            "@date %s\n" +
            "@version 1.0\n"

    private val PACKAGE_NAME = "com.fz.roundview"
    private val CLASS_PREFIX = "Rounded"
    private var mFileUtils: Filer? = null
    private var mMessager: Messager? = null
    private var mBadgeableMethod: IRoundedMethod? = null
    private var trees: Trees? = null
    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment?) {
        super.init(processingEnvironment)
        mFileUtils = processingEnv.filer
        mMessager = processingEnv.messager
        mBadgeableMethod = RoundedMethodImpl(PACKAGE_NAME, processingEnv)
        try {
            trees = Trees.instance(processingEnv)
        }  catch ( ignored:IllegalArgumentException) {
            try {
                // Get original ProcessingEnvironment from Gradle-wrapped one or KAPT-wrapped one.
                for ( field in processingEnv.javaClass.declaredFields) {
                    if (field.name.equals("processingEnv")) {
                        field.isAccessible = true
                        val javacEnv =  field.get(processingEnv) as ProcessingEnvironment
                        trees = Trees.instance(javacEnv)
                        break
                    }
                }
            } catch ( ignored2:Throwable) {
            }
        }
    }

    override fun getSupportedSourceVersion(): SourceVersion? {
        return SourceVersion.latestSupported()
    }

    /**
     * ?????? Processor ????????????????????????
     *
     * @return ???????????? Set ????????????????????????????????????????????????+??????
     */
    override fun getSupportedAnnotationTypes(): Set<String> {
        val annotationTypes: MutableSet<String> = LinkedHashSet()
        annotationTypes.add(RoundedView::class.java.canonicalName)
        return annotationTypes
    }

    override fun getSupportedOptions(): Set<String>? {
        if (trees != null) {
          return  ImmutableSet.builder<String>().add(IncrementalAnnotationProcessorType.ISOLATING.processorOption).build()
        }
        return super.getSupportedOptions()
    }
    /**
     * ????????????????????????????????????????????????????????? APT ????????????????????????????????????????????????????????????????????????????????? Processor ???????????????????????????????????????????????????
     *
     * @param set              ??????????????? Processor ???????????????????????? Annotations???????????? Processor ?????????????????????????????????????????????????????????????????????
     * @param roundEnvironment ??????????????????????????????????????????????????????????????????????????????
     * @return ???????????? Annotation ??????????????? Processor ?????????????????????????????? true??????????????? Processor ?????????????????? Annotation ????????????
     */
    override fun process(set: Set<TypeElement?>?, roundEnvironment: RoundEnvironment): Boolean {
        val elements: Set<Element>? = roundEnvironment.getElementsAnnotatedWith(
            RoundedView::class.java
        )
        if (elements.isNullOrEmpty()) {
            return true
        }
        mMessager?.let {
            it.printMessage(
                Diagnostic.Kind.NOTE,
                "====================================== RoundedProcessor process START ======================================"
            )
            val viewClassSet: MutableSet<String> = HashSet()
            parseParams(elements, viewClassSet)
            try {
                generate(viewClassSet)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: IOException) {
                it.printMessage(
                    Diagnostic.Kind.ERROR,
                    "Exception occurred when generating class file."
                )
                e.printStackTrace()
            }
            it.printMessage(
                Diagnostic.Kind.NOTE,
                "====================================== RoundedProcessor process END ======================================"
            )
        }
        return true
    }

    private fun parseParams(elements: Set<Element>, viewClassSet: MutableSet<String>) {
        for (element in elements) {
            checkAnnotationValid(element, RoundedView::class.java)
            val classElement = element as TypeElement
            // ?????????????????????
            val badgeAnnotation = classElement.getAnnotation(RoundedView::class.java)
            try {
                badgeAnnotation.value
            } catch (e: MirroredTypesException) {
                val typeMirrors = e.typeMirrors
                for (typeMirror in typeMirrors) {
                    val classTypeMirror = typeMirror as DeclaredType
                    val classTypeElement = classTypeMirror.asElement() as TypeElement
                    val qualifiedName = classTypeElement.qualifiedName.toString()
                    viewClassSet.add(qualifiedName)
                }
            }
        }
    }

    @Throws(IllegalAccessException::class, IOException::class)
    private fun generate(viewClassSet: Set<String>) {
        mMessager?.let {
            it.printMessage(Diagnostic.Kind.NOTE, "?????? " + viewClassSet.size + " ???")
            for (clazz in viewClassSet) {
                val lastDotIndex = clazz.lastIndexOf(".")
                val superPackageName = clazz.substring(0, lastDotIndex)
                val superClassName = clazz.substring(lastDotIndex + 1)
                val className = CLASS_PREFIX + superClassName
                it.printMessage(Diagnostic.Kind.NOTE, "$clazz ====> $className")
                val date = SimpleDateFormat("yyyy/M/dd HH:mm").format(Date())
                val javaDoc = String.format(CLASS_JAVA_DOC, date)
                val typeBuilder = TypeSpec.classBuilder(className)
                    .addJavadoc(javaDoc)
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(ClassName.get(superPackageName, superClassName))
                    .addSuperinterface(ClassName.get(PACKAGE_NAME, "IRoundedView"))
                    .addField(
                        ClassName.get(PACKAGE_NAME, "RoundViewDelegate"),
                        "mRoundViewDelegate",
                        Modifier.PRIVATE
                    )
                generateMethod(typeBuilder, clazz)
                val javaFile = JavaFile.builder(PACKAGE_NAME, typeBuilder.build()).build()
                javaFile.writeTo(mFileUtils)
            }
        }
    }

    private fun generateMethod(typeBuilder: TypeSpec.Builder, clazz: String) {
        mBadgeableMethod?.apply {
            constructor(typeBuilder, clazz)
            setBorderWidth(typeBuilder)
            setDrawBorder(typeBuilder)
            setBorderColor(typeBuilder)
            setRadius(typeBuilder)
            setRadiusArr(typeBuilder)
            setDrawCircle(typeBuilder)
            onLayout(typeBuilder)
            onDraw(typeBuilder)
        }

    }

    private fun checkAnnotationValid(annotatedElement: Element, clazz: Class<*>): Boolean {
        if (annotatedElement.kind != ElementKind.CLASS) {
            error(annotatedElement, "%s must be declared on class.", clazz.simpleName)
            return false
        }
        if (annotatedElement.modifiers.contains(Modifier.PRIVATE)) {
            error(
                annotatedElement,
                "%s must can not be private.",
                (annotatedElement as TypeElement).qualifiedName
            )
            return false
        }
        return true
    }

    private fun error(element: Element, message: String, vararg args: Any) {
        var message1: String = message
        if (args.isNotEmpty()) {
            message1 = String.format(message1, *args)
        }
        mMessager?.printMessage(Diagnostic.Kind.ERROR, message1, element)
    }
}