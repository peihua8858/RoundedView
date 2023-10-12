package com.fz.roundview.compiler

import com.squareup.javapoet.*
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Modifier

/**
 * 类方法生成实现
 * @author dingpeihua
 * @date 2022/2/15 16:50
 * @version 1.0
 */
class RoundedMethodImpl(packageName: String, processingEnv: ProcessingEnvironment) :
    AbstractRoundedMethod(packageName, processingEnv) {
    override fun constructor(typeBuilder: TypeSpec.Builder, clazz: String) {
        val contextType: TypeName = ClassName.get("android.content", "Context")
        val attributeSetType: TypeName = ClassName.get("android.util", "AttributeSet")
        val constructorOne = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addParameter(contextType, "context")
            .addStatement("this(context, null)")
            .build()
        val constructorTwo = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addParameter(contextType, "context")
            .addParameter(attributeSetType, "attrs")
            .addStatement("this(context, attrs, 0)")
            .build()
        val constructorThreeBuilder = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addParameter(contextType, "context")
            .addParameter(attributeSetType, "attrs")
            .addParameter(Int::class.javaPrimitiveType, "defStyleAttr")
            .addStatement("super(context, attrs, defStyleAttr)")
        constructorThreeBuilder.addStatement("mRoundViewDelegate = new RoundViewDelegate(this, context, attrs)")
        typeBuilder.addMethod(constructorOne)
            .addMethod(constructorTwo)
            .addMethod(constructorThreeBuilder.build())
    }

    override fun setBorderWidth(typeBuilder: TypeSpec.Builder) {
        val methodSpec = MethodSpec.methodBuilder("setBorderWidth")
            .addAnnotation(Override::class.java)
            .addModifiers(Modifier.PUBLIC)
            .addParameter(TypeName.FLOAT, "borderWidth")
            .addStatement("mRoundViewDelegate.setBorderWidth(borderWidth)")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun setDrawBorder(typeBuilder: TypeSpec.Builder) {
        val methodSpec = MethodSpec.methodBuilder("setDrawBorder")
            .addAnnotation(Override::class.java)
            .addModifiers(Modifier.PUBLIC)
            .addParameter(TypeName.BOOLEAN, "drawBorder")
            .addStatement("mRoundViewDelegate.setDrawBorder(drawBorder)")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun setBorderColor(typeBuilder: TypeSpec.Builder) {
        val methodSpec = MethodSpec.methodBuilder("setBorderColor")
            .addAnnotation(Override::class.java)
            .addModifiers(Modifier.PUBLIC)
            .addParameter(TypeName.INT, "borderColor")
            .addStatement("mRoundViewDelegate.setBorderColor(borderColor)")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun setRadius(typeBuilder: TypeSpec.Builder) {
        val methodSpec = MethodSpec.methodBuilder("setRadius")
            .addAnnotation(Override::class.java)
            .addModifiers(Modifier.PUBLIC)
            .addParameter(TypeName.FLOAT, "radius")
            .addStatement("mRoundViewDelegate.setRadius(radius)")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun setRadiusArr(typeBuilder: TypeSpec.Builder) {
        val methodSpec = MethodSpec.methodBuilder("setRadius")
            .addAnnotation(Override::class.java)
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ArrayTypeName.of(TypeName.FLOAT), "radius")
            .addStatement("mRoundViewDelegate.setRadius(radius)")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun setDrawCircle(typeBuilder: TypeSpec.Builder) {
        val methodSpec = MethodSpec.methodBuilder("setDrawCircle")
            .addAnnotation(Override::class.java)
            .addModifiers(Modifier.PUBLIC)
            .addParameter(TypeName.BOOLEAN, "drawCircle")
            .addStatement("mRoundViewDelegate.setDrawCircle(drawCircle)")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun onLayout(typeBuilder: TypeSpec.Builder) {
        //boolean changed, int left, int top, int right,
        //                            int bottom
        //mRoundViewDelegate.roundRectSet(getWidth(), getHeight());
        val methodSpec = MethodSpec.methodBuilder("onLayout")
            .addAnnotation(Override::class.java)
            .addModifiers(Modifier.PUBLIC)
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
        val methodSpec = MethodSpec.methodBuilder("draw")
            .addAnnotation(Override::class.java)
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ClassName.get("android.graphics", "Canvas"), "canvas")
            .addStatement("mRoundViewDelegate.canvasSetLayer(canvas)")
            .addStatement("super.draw(canvas)")
            .addStatement("mRoundViewDelegate.drawBorders(canvas)")
            .addStatement("canvas.restore()")
            .build()
        typeBuilder.addMethod(methodSpec)
    }

    override fun setRoundedCorners(typeBuilder: TypeSpec.Builder) {
        val methodSpec = MethodSpec.methodBuilder("setRoundedCorners")
            .addAnnotation(Override::class.java)
            .addModifiers(Modifier.PUBLIC)
            .addParameter(TypeName.FLOAT, "leftTopRadius")
            .addParameter(TypeName.FLOAT, "leftBottomRadius")
            .addParameter(TypeName.FLOAT, "rightTopRadius")
            .addParameter(TypeName.FLOAT, "rightBottomRadius")
            .addStatement("mRoundViewDelegate.setRoundedCorners(leftTopRadius,leftBottomRadius,rightTopRadius,rightBottomRadius)")
            .build()
        typeBuilder.addMethod(methodSpec)
    }
}