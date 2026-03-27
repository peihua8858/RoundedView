package com.peihua8858.roundview.compiler

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec

abstract class AbstractKspRoundedMethod(protected val packageName:String, protected val processor: KspRoundedProcessor) :
    IKspRoundedMethod {

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
    override fun constructor(typeBuilder: TypeSpec.Builder, clazz: String) {
        val contextType: TypeName = ClassName("android.content", "Context")
        val attributeSetType: TypeName = ClassName("android.util", "AttributeSet").copy(nullable = true)
        val constructorOne = FunSpec.constructorBuilder()
            .addModifiers(KModifier.PUBLIC)
            .addParameter("context",contextType)
            .callThisConstructor("context", "null")
            .build()
        val constructorTwo = FunSpec.constructorBuilder()
            .addModifiers(KModifier.PUBLIC)
            .addParameter("context",contextType)
            .addParameter( "attrs",attributeSetType)
            .addStatement("this(context, attrs, 0)")
            .build()
        val constructorThreeBuilder = FunSpec.constructorBuilder()
            .addModifiers(KModifier.PUBLIC)
            .addParameter("context",contextType)
            .addParameter( "attrs",attributeSetType)
            .addParameter("defStyleAttr",Int::class)
            .addStatement("super(context, attrs, defStyleAttr)")
        constructorThreeBuilder.addStatement("mRoundViewDelegate = new RoundViewDelegate(this, context, attrs)")
        typeBuilder.addFunction(constructorOne)
            .addFunction(constructorTwo)
            .addFunction(constructorThreeBuilder.build())
    }

    override fun setBorderWidth(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("setBorderWidth")
            .addAnnotation(Override::class.java)
            .addModifiers(KModifier.PUBLIC)
            .addParameter(TypeName.FLOAT, "borderWidth")
            .addStatement("mRoundViewDelegate.setBorderWidth(borderWidth)")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun setDrawBorder(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("setDrawBorder")
            .addAnnotation(Override::class.java)
            .addModifiers(KModifier.PUBLIC)
            .addParameter(TypeName.BOOLEAN, "drawBorder")
            .addStatement("mRoundViewDelegate.setDrawBorder(drawBorder)")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun setBorderColor(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("setBorderColor")
            .addAnnotation(Override::class.java)
            .addModifiers(KModifier.PUBLIC)
            .addParameter(TypeName.INT, "borderColor")
            .addStatement("mRoundViewDelegate.setBorderColor(borderColor)")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun setRadius(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("setRadius")
            .addAnnotation(Override::class.java)
            .addModifiers(KModifier.PUBLIC)
            .addParameter(TypeName.FLOAT, "radius")
            .addStatement("mRoundViewDelegate.setRadius(radius)")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun setRadiusArr(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("setRadius")
            .addAnnotation(Override::class.java)
            .addModifiers(KModifier.PUBLIC)
            .addParameter(ArrayTypeName.of(TypeName.FLOAT), "radius")
            .addStatement("mRoundViewDelegate.setRadius(radius)")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun setDrawCircle(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("setDrawCircle")
            .addAnnotation(Override::class.java)
            .addModifiers(KModifier.PUBLIC)
            .addParameter(TypeName.BOOLEAN, "drawCircle")
            .addStatement("mRoundViewDelegate.setDrawCircle(drawCircle)")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun onLayout(typeBuilder: TypeSpec.Builder) {
        //boolean changed, int left, int top, int right,
        //                            int bottom
        //mRoundViewDelegate.roundRectSet(getWidth(), getHeight());
        val methodSpec = FunSpec.builder("onLayout")
            .addAnnotation(Override::class.java)
            .addModifiers(KModifier.PUBLIC)
            .addParameter(TypeName.BOOLEAN, "changed")
            .addParameter(TypeName.INT, "left")
            .addParameter(TypeName.INT, "top")
            .addParameter(TypeName.INT, "right")
            .addParameter(TypeName.INT, "bottom")
            .addStatement("super.onLayout(changed, left, top, right, bottom)")
            .addStatement("mRoundViewDelegate.roundRectSet(getWidth(), getHeight())")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun onDraw(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("draw")
            .addAnnotation(Override::class.java)
            .addModifiers(KModifier.PUBLIC)
            .addParameter(ClassName.get("android.graphics", "Canvas"), "canvas")
            .addStatement("mRoundViewDelegate.canvasSetLayer(canvas)")
            .addStatement("super.draw(canvas)")
            .addStatement("mRoundViewDelegate.drawBorders(canvas)")
            .addStatement("canvas.restore()")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun setRoundedCorners(typeBuilder: TypeSpec.Builder) {
        val methodSpec = FunSpec.builder("setRoundedCorners")
            .addAnnotation(Override::class.java)
            .addModifiers(KModifier.PUBLIC)
            .addParameter(TypeName.FLOAT, "leftTopRadius")
            .addParameter(TypeName.FLOAT, "leftBottomRadius")
            .addParameter(TypeName.FLOAT, "rightTopRadius")
            .addParameter(TypeName.FLOAT, "rightBottomRadius")
            .addStatement("mRoundViewDelegate.setRoundedCorners(leftTopRadius,leftBottomRadius,rightTopRadius,rightBottomRadius)")
            .build()
        typeBuilder.addMethod(methodSpec)
    }
}