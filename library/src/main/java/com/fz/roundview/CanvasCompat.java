package com.fz.roundview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;

import androidx.annotation.Nullable;

/**
 * Canvas 兼容处理
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2019/12/19 18:52
 */
public class CanvasCompat {
    private CanvasCompat() {
    }

    public static int saveLayerAlpha(Canvas canvas, RectF bounds, int alpha) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? canvas.saveLayerAlpha(bounds, alpha)
                : canvas.saveLayerAlpha(bounds, alpha, Canvas.ALL_SAVE_FLAG);
    }

    public static int saveLayerAlpha(Canvas canvas, float left, float top, float right, float bottom, int alpha) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? canvas.saveLayerAlpha(left, top, right, bottom, alpha)
                : canvas.saveLayerAlpha(left, top, right, bottom, alpha, Canvas.ALL_SAVE_FLAG);
    }

    public static int saveLayer(Canvas canvas, @Nullable RectF bounds, @Nullable Paint paint) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? canvas.saveLayer(bounds, paint)
                : canvas.saveLayer(bounds, paint, Canvas.ALL_SAVE_FLAG);
    }
}
