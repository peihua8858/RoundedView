package com.peihua8858.roundedview.ksp

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.BOOLEAN
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FLOAT
import com.squareup.kotlinpoet.FLOAT_ARRAY
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec

abstract class AbstractKspRoundedMethod(
    protected val packageName: String,
    protected val processor: KspRoundedProcessor
) : IKspRoundedMethod {

    protected fun isAssignable(classDeclaration: KSClassDeclaration, superClazz: String): Boolean {
        val child = classDeclaration.asStarProjectedType()
        val supper1 = processor.getClassDeclarationByName(superClazz)?.asStarProjectedType()
        return supper1 != null && supper1.isAssignableFrom(child)
    }
}

/**
 * 类方法生成实现
 * @author dingpeihua
 * @date 2022/2/15 16:50
 * @version 1.0
 */
class KspRoundedMethodImpl(packageName: String, processor: KspRoundedProcessor) :
    AbstractKspRoundedMethod(packageName, processor) {
    override fun constructor(typeBuilder: TypeSpec.Builder, classDeclaration: KSClassDeclaration) {
        val contextType: TypeName = ClassName("android.content", "Context")
        val attributeSetType: TypeName = ClassName("android.util", "AttributeSet").copy(nullable = true)
        val constructorOne = FunSpec.constructorBuilder()
            .addModifiers(KModifier.PUBLIC)
            .addParameter("context", contextType)
            .callThisConstructor("context", "null")
            .build()
        val constructorTwo = FunSpec.constructorBuilder()
            .addModifiers(KModifier.PUBLIC)
            .addParameter("context", contextType)
            .addParameter("attrs", attributeSetType)
            .callThisConstructor("context", "attrs", "0")
            .build()
        val constructorThreeBuilder = FunSpec.constructorBuilder()
            .addParameter("context", contextType)
            .addParameter("attrs", attributeSetType)
            .addParameter("defStyleAttr", Int::class)
        val initFun = CodeBlock.builder()
        initFun.addStatement("mRoundViewDelegate = RoundViewDelegate(this, context, attrs)")
        typeBuilder.addFunction(constructorOne)
            .addFunction(constructorTwo)
            .primaryConstructor(constructorThreeBuilder.build())
            .addSuperclassConstructorParameter("context")
            .addSuperclassConstructorParameter("attrs")
            .addSuperclassConstructorParameter("defStyleAttr")
            .addInitializerBlock(initFun.build())

    }

    override fun setBorderWidth(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("setBorderWidth")
            .addModifiers(KModifier.OVERRIDE)
            .addModifiers(KModifier.PUBLIC)
            .addParameter("borderWidth", FLOAT)
            .addStatement("mRoundViewDelegate.setBorderWidth(borderWidth)")
            .build()
        typeBuilder.addFunction(methodSpec)
    }

    override fun setDrawBorder(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("setDrawBorder")
            .addModifiers(KModifier.OVERRIDE)
            .addModifiers(KModifier.PUBLIC)
            .addParameter("drawBorder", BOOLEAN)
            .addStatement("mRoundViewDelegate.setDrawBorder(drawBorder)")
            .build()
        typeBuilder.addFunction(methodSpec)
    }

    override fun setBorderColor(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("setBorderColor")
            .addModifiers(KModifier.OVERRIDE)
            .addModifiers(KModifier.PUBLIC)
            .addParameter("borderColor", INT)
            .addStatement("mRoundViewDelegate.setBorderColor(borderColor)")
            .build()
        typeBuilder.addFunction(methodSpec)
    }

    override fun setRadius(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("setRadius")
            .addModifiers(KModifier.OVERRIDE)
            .addModifiers(KModifier.PUBLIC)
            .addParameter("radius", FLOAT)
            .addStatement("mRoundViewDelegate.setRadius(radius)")
            .build()
        typeBuilder.addFunction(methodSpec)
    }

    override fun setRadiusArr(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("setRadius")
            .addModifiers(KModifier.OVERRIDE)
            .addModifiers(KModifier.PUBLIC)
            .addParameter("radius", FLOAT_ARRAY)
            .addStatement("mRoundViewDelegate.setRadius(radius)")
            .build()
        typeBuilder.addFunction(methodSpec)
    }

    override fun setDrawCircle(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("setDrawCircle")
            .addModifiers(KModifier.OVERRIDE)
            .addModifiers(KModifier.PUBLIC)
            .addParameter("drawCircle", BOOLEAN)
            .addStatement("mRoundViewDelegate.setDrawCircle(drawCircle)")
            .build()
        typeBuilder.addFunction(methodSpec)
    }

    override fun onLayout(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("onLayout")
            .addModifiers(KModifier.OVERRIDE)
            .addModifiers(KModifier.PUBLIC)
            .addParameter("changed", BOOLEAN)
            .addParameter("left", INT)
            .addParameter("top", INT)
            .addParameter("right", INT)
            .addParameter("bottom", INT)
            .addStatement("super.onLayout(changed, left, top, right, bottom)")
            .addStatement("mRoundViewDelegate.roundRectSet(getWidth(), getHeight())")
            .build()
        typeBuilder.addFunction(methodSpec)
    }

    override fun onDraw(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("draw")
            .addModifiers(KModifier.OVERRIDE)
            .addModifiers(KModifier.PUBLIC)
            .addParameter( "canvas",ClassName("android.graphics", "Canvas"))
            .addStatement("mRoundViewDelegate.canvasSetLayer(canvas)")
            .addStatement("super.draw(canvas)")
            .addStatement("mRoundViewDelegate.drawBorders(canvas)")
            .addStatement("canvas.restore()")
            .build()
        typeBuilder.addFunction(methodSpec)
    }

    override fun setRoundedCorners(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("setRoundedCorners")
            .addModifiers(KModifier.OVERRIDE)
            .addModifiers(KModifier.PUBLIC)
            .addParameter("leftTopRadius",FLOAT)
            .addParameter("leftBottomRadius",FLOAT)
            .addParameter("rightTopRadius",FLOAT)
            .addParameter("rightBottomRadius",FLOAT)
            .addStatement("mRoundViewDelegate.setRoundedCorners(leftTopRadius,leftBottomRadius,rightTopRadius,rightBottomRadius)")
            .build()
        typeBuilder.addFunction(methodSpec)
    }
}