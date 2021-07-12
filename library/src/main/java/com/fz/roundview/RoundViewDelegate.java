package com.fz.roundview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 绘制圆角视图的代理，注意需要给当前视图设置背景，如果没有背景可以设置成透明
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2020/6/22 14:45
 */
public class RoundViewDelegate {
    private final RectF roundRect = new RectF();
    /**
     * 半径，单位像素
     */
    private float mRadius = 10;
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();
    private boolean isDrawCircle = false;
    private View mView;
    private float mBorderWidth;
    private int mBorderColor;
    private Paint paint;
    /**
     * 用来裁剪图片的ptah
     */
    private Path path;
    /**
     * 是否绘制边框
     */
    private boolean isDrawBorder;

    public RoundViewDelegate(View view, Context context, AttributeSet attrs) {
        this.mView = view;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundView);
            mRadius = ta.getDimension(R.styleable.RoundView_rv_radius, 0);
            mBorderWidth = ta.getDimension(R.styleable.RoundView_rv_borderWidth, 0);
            mBorderColor = ta.getColor(R.styleable.RoundView_rv_borderColor, Color.TRANSPARENT);
            isDrawBorder = ta.getBoolean(R.styleable.RoundView_rv_drawBorder, (mBorderWidth > 0));
            ta.recycle();
        }
        init();
    }

    private void init() {
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        zonePaint.setAntiAlias(true);
//        zonePaint.setColor(Color.WHITE);
        paint = new Paint();
        path = new Path();
    }

    /**
     * 设置边框宽度
     *
     * @author dingpeihua
     * @date 2020/6/22 16:40
     * @version 1.0
     */
    public void setBorderWidth(float borderWidth) {
        this.mBorderWidth = borderWidth;
        if (mView != null) {
            mView.invalidate();
        }
    }

    /**
     * 设置边框颜色
     *
     * @param borderColor
     * @author dingpeihua
     * @date 2020/6/22 16:40
     * @version 1.0
     */
    public void setBorderColor(int borderColor) {
        this.mBorderColor = borderColor;
        if (mView != null) {
            mView.invalidate();
        }
    }

    /**
     * 设置是否绘制边框
     *
     * @param drawBorder
     * @author dingpeihua
     * @date 2020/6/28 14:46
     * @version 1.0
     */
    public void setDrawBorder(boolean drawBorder) {
        isDrawBorder = drawBorder;
        if (mView != null) {
            mView.invalidate();
        }
    }

    /**
     * 从新设置圆角
     *
     * @param radius
     */
    public void setRadius(float radius) {
        this.mRadius = radius;
        if (mView != null) {
            mView.invalidate();
        }
    }

    /**
     * 是否绘制成圆形
     *
     * @author dingpeihua
     * @date 2020/6/22 14:26
     * @version 1.0
     */
    public void setDrawCircle(boolean drawCircle) {
        isDrawCircle = drawCircle;
    }

    /**
     * 圆角区域设置
     *
     * @param width
     * @param height
     */
    public void roundRectSet(int width, int height) {
        roundRect.set(0, 0, width, height);
    }

    /**
     * 画布区域裁剪
     *
     * @param canvas
     */
    public void canvasSetLayer(Canvas canvas) {
        CanvasCompat.saveLayer(canvas, roundRect, zonePaint);
        int width = mView.getWidth();
        int height = mView.getHeight();
        if (isDrawCircle) {
            canvas.drawCircle(width / 2f, height / 2f, mRadius, zonePaint);
        } else {
            canvas.drawRoundRect(roundRect, mRadius, mRadius, zonePaint);
        }
        CanvasCompat.saveLayer(canvas, roundRect, maskPaint);
    }

    /**
     * 绘制圆角边框
     *
     * @param canvas
     * @author dingpeihua
     * @date 2020/6/22 16:34
     * @version 1.0
     */
    public void drawBorders(Canvas canvas) {
        if (mBorderWidth > 0 && isDrawBorder) {
            path.reset();
            paint.setStrokeWidth(mBorderWidth);
            paint.setColor(mBorderColor);
            paint.setStyle(Paint.Style.STROKE);
            if (isDrawCircle) {
                path.addCircle(mView.getWidth() / 2.0f, mView.getHeight() / 2.0f,
                        mRadius, Path.Direction.CCW);
            } else {
                path.addRoundRect(roundRect, new float[]{mRadius, mRadius,
                                mRadius, mRadius, mRadius, mRadius, mRadius, mRadius},
                        Path.Direction.CCW);
            }
            canvas.drawPath(path, paint);
        }
    }
}
