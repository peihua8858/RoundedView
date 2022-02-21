package com.fz.roundview.compiler

import com.squareup.javapoet.TypeSpec

/**
 * Created by dingpeihua on 2017/8/1.
 */
interface IRoundedMethod{
    fun constructor(typeBuilder: TypeSpec.Builder, clazz: String)
    /**
     * 设置边框宽度
     *
     * @param typeBuilder
     */
    fun setBorderWidth(typeBuilder: TypeSpec.Builder)

    /**
     * 设置是否绘制边框
     *
     * @param typeBuilder
     */
    fun setDrawBorder(typeBuilder: TypeSpec.Builder)

    /**
     * 设置边框颜色
     *
     * @param typeBuilder
     */
    fun setBorderColor(typeBuilder: TypeSpec.Builder)

    /**
     * 设置圆角半径
     *
     * @param typeBuilder
     */
    fun setRadius(typeBuilder: TypeSpec.Builder)

    /**
     * 设置是否绘制成圆形
     *
     * @param typeBuilder
     */
    fun setDrawCircle(typeBuilder: TypeSpec.Builder)

    /**
     * 重写视图布局
     *
     * @param typeBuilder
     */
    fun onLayout(typeBuilder: TypeSpec.Builder)
    /**
     * 重写视图绘制
     *
     * @param typeBuilder
     */
    fun onDraw(typeBuilder: TypeSpec.Builder)
}