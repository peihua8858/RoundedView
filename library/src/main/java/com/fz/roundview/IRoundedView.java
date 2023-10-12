package com.fz.roundview;

import android.content.Context;

import androidx.annotation.ColorInt;

/**
 * 圆形视图api 接口
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2022/2/18 15:32
 */
public interface IRoundedView {
    /**
     * 设置边框宽度
     *
     * @param borderWidth
     */
    void setBorderWidth(float borderWidth);

    /**
     * 设置是否绘制边框
     *
     * @param drawBorder
     */
    void setDrawBorder(boolean drawBorder);

    /**
     * 设置边框颜色
     *
     * @param borderColor
     */
    void setBorderColor(@ColorInt int borderColor);

    /**
     * 设置圆角半径
     *
     * @param radius
     */
    void setRadius(float radius);

    /**
     * 设置圆角半径
     *
     * @param radius 8 个值的数组，4 对 [X,Y] 半径
     */
    void setRadius(float[] radius);

    /**
     * 设置是否绘制成圆形
     *
     * @param drawCircle
     */
    void setDrawCircle(boolean drawCircle);

    /**
     * 获取视图的宽度
     *
     * @return
     */
    int getWidth();

    /**
     * 获取视图的高度
     *
     * @return
     */
    int getHeight();

    /**
     * 获取上下文
     *
     * @return
     */
    Context getContext();

    /**
     * 重新绘制
     */
    void postInvalidate();

    /**
     * 设置圆角
     *
     * @param leftTopRadius     左上角半径
     * @param leftBottomRadius  左下角半径
     * @param rightTopRadius    右上角半径
     * @param rightBottomRadius 右下角半径
     * @author dingpeihua
     * @date 2023/10/12 18:23
     * @version 1.0
     */
    void setRoundedCorners(float leftTopRadius, float leftBottomRadius, float rightTopRadius, float rightBottomRadius);
}
