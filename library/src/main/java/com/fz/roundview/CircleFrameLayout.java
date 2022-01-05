package com.fz.roundview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 圆形布局
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2019/9/21 11:56
 */
public class CircleFrameLayout extends FrameLayout {
    private RoundViewDelegate mRoundViewDelegate;

    public CircleFrameLayout(Context context) {
        this(context, null);
    }

    public CircleFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mRoundViewDelegate = new RoundViewDelegate(this, context, attrs);
    }

    public void setBorderWidth(float borderWidth) {
        mRoundViewDelegate.setBorderWidth(borderWidth);
    }

    public void setBorderColor(int borderColor) {
        mRoundViewDelegate.setBorderColor(borderColor);
    }

    public void setRadius(float radius) {
        mRoundViewDelegate.setRadius(radius);
    }

    public void setDrawCircle(boolean drawCircle) {
        mRoundViewDelegate.setDrawCircle(drawCircle);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mRoundViewDelegate.roundRectSet(getWidth(), getHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        mRoundViewDelegate.canvasSetLayer(canvas);
        super.draw(canvas);
        mRoundViewDelegate.drawBorders(canvas);
        canvas.restore();
    }
}
