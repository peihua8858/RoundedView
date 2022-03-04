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
    private float[] mRadiusArr;
    /**
     * 半径，单位像素
     */
    private float mLeftTopRadius = 10;
    /**
     * 半径，单位像素
     */
    private float mLeftBottomRadius = 10;
    /**
     * 半径，单位像素
     */
    private float mRightTopRadius = 10;
    /**
     * 半径，单位像素
     */
    private float mRightBottomRadius = 10;
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();
    private boolean isDrawCircle = false;
    private IRoundedView mRoundedView;
    private float mBorderWidth;
    private int mBorderColor;
    private Paint paint;
    /**
     * 用来裁剪图片的ptah
     */
    private Path path;
    private Path roundPath;
    /**
     * 是否绘制边框
     */
    private boolean isDrawBorder;

    public RoundViewDelegate(IRoundedView view, Context context, AttributeSet attrs) {
        this.mRoundedView = view;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundView);
            mRadius = ta.getDimension(R.styleable.RoundView_rv_radius, 0);
            mLeftTopRadius = ta.getDimension(R.styleable.RoundView_rv_left_top_radius, 0);
            mLeftBottomRadius = ta.getDimension(R.styleable.RoundView_rv_left_bottom_radius, 0);
            mRightTopRadius = ta.getDimension(R.styleable.RoundView_rv_right_top_radius, 0);
            mRightBottomRadius = ta.getDimension(R.styleable.RoundView_rv_right_bottom_radius, 0);
            mBorderWidth = ta.getDimension(R.styleable.RoundView_rv_borderWidth, 0);
            mBorderColor = ta.getColor(R.styleable.RoundView_rv_borderColor, Color.TRANSPARENT);
            isDrawBorder = ta.getBoolean(R.styleable.RoundView_rv_drawBorder, (mBorderWidth > 0));
            isDrawCircle = ta.getBoolean(R.styleable.RoundView_rv_drawCircle, false);
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
        if (mRoundedView != null) {
            mRoundedView.postInvalidate();
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
        if (mRoundedView != null) {
            mRoundedView.postInvalidate();
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
        if (mRoundedView != null) {
            mRoundedView.postInvalidate();
        }
    }

    /**
     * 从新设置圆角
     *
     * @param radius
     */
    public void setRadius(float radius) {
        this.mRadius = radius;
        if (mRoundedView != null) {
            mRoundedView.postInvalidate();
        }
    }

    /**
     * 从新设置圆角
     *
     * @param radius
     */
    public void setRadius(float[] radius) {
        this.mRadiusArr = radius;
        if (mRoundedView != null) {
            mRoundedView.postInvalidate();
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
        int width = mRoundedView.getWidth();
        int height = mRoundedView.getHeight();
        if (isDrawCircle) {
            canvas.drawCircle(width / 2f, height / 2f, mRadius, zonePaint);
        } else {
            if (roundPath == null) {
                roundPath = new Path();
            } else {
                roundPath.reset();
            }
            if (mRadiusArr != null && mRadiusArr.length == 8) {
                roundPath.addRoundRect(roundRect, mRadiusArr, Path.Direction.CCW);
            } else if (mLeftTopRadius > 0 || mLeftBottomRadius > 0 || mRightTopRadius > 0 || mRightBottomRadius > 0) {
                roundPath.addRoundRect(roundRect,
                        new float[]{mLeftTopRadius, mLeftTopRadius,
                                mRightTopRadius, mRightTopRadius,
                                mRightBottomRadius, mRightBottomRadius,
                                mLeftBottomRadius, mLeftBottomRadius}, Path.Direction.CCW);
            } else {
                roundPath.addRoundRect(roundRect,
                        new float[]{mRadius, mRadius,
                                mRadius, mRadius,
                                mRadius, mRadius,
                                mRadius, mRadius}, Path.Direction.CCW);
            }
            canvas.drawPath(roundPath, zonePaint);
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
                path.addCircle(mRoundedView.getWidth() / 2.0f, mRoundedView.getHeight() / 2.0f,
                        mRadius, Path.Direction.CCW);
            } else if (mRadiusArr != null && mRadiusArr.length == 8) {
                path.addRoundRect(roundRect, mRadiusArr, Path.Direction.CCW);
            } else if (mLeftTopRadius > 0 || mLeftBottomRadius > 0 || mRightTopRadius > 0 || mRightBottomRadius > 0) {
                path.addRoundRect(roundRect, new float[]{mLeftTopRadius, mLeftTopRadius,
                                mRightTopRadius, mRightTopRadius, mRightBottomRadius, mRightBottomRadius, mLeftBottomRadius, mLeftBottomRadius,},
                        Path.Direction.CCW);
            } else {
                path.addRoundRect(roundRect, new float[]{mRadius, mRadius,
                                mRadius, mRadius, mRadius, mRadius, mRadius, mRadius},
                        Path.Direction.CCW);
            }
            canvas.drawPath(path, paint);
        }
    }
}
