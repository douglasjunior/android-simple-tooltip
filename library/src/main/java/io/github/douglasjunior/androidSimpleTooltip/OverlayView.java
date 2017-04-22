/*
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2016 Douglas Nassif Roma Junior
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.douglasjunior.androidSimpleTooltip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.github.douglasjunior.androidSimpleTooltip.R;

/**
 * View que faz o efeito de escurecer a tela e dar destaque no ponto de ancoragem.<br>
 * Implementação baseada na resposta: http://stackoverflow.com/a/34702884/2826279
 * <p/>
 * Created by douglas on 09/05/16.
 */
@SuppressLint("ViewConstructor")
public class OverlayView extends View {

    private static final int mDefaultOverlayCircleOffsetRes = R.dimen.simpletooltip_overlay_circle_offset;
    private static final int mDefaultOverlayAlphaRes = R.integer.simpletooltip_overlay_alpha;

    private RectF anchorRect;
    private Bitmap bitmap;
    private float offset = 0;
    private int alpha = 0;
    private boolean invalidated = true;
    @OverlayShape
    private int shape = RECTANGLE;

    OverlayView(Context context, RectF anchorRect, Float offset) {
        super(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(R.styleable.SimpleTooltipStyle);
        this.offset = offset == null
                ? a.getDimension(R.styleable.SimpleTooltipStyle_st_overlay_offset,
                context.getResources().getDimension(mDefaultOverlayCircleOffsetRes))
                : offset;
        alpha = a.getInt(R.styleable.SimpleTooltipStyle_st_overlay_alpha,
                context.getResources().getInteger(mDefaultOverlayAlphaRes));
        a.recycle();

        this.anchorRect = anchorRect;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (invalidated)
            createWindowFrame();

        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    private void createWindowFrame() {
        final int width = getMeasuredWidth(), height = getMeasuredHeight();
        if (width <= 0 || height <= 0)
            return;

        if (bitmap != null && !bitmap.isRecycled())
            bitmap.recycle();
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas osCanvas = new Canvas(bitmap);

        RectF outerRectangle = new RectF(0, 0, width, height);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setAlpha(alpha);
        osCanvas.drawRect(outerRectangle, paint);

        paint.setColor(Color.TRANSPARENT);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        RectF overlayRect = SimpleTooltipUtils.calculeRectInWindow(this);

        float left = anchorRect.left - overlayRect.left;
        float top = anchorRect.top - overlayRect.top;
        RectF shapeRect = new RectF(
                left - offset,
                top - offset,
                left + anchorRect.width() + offset,
                top + anchorRect.height() + offset);

        if (shape == RECTANGLE) {
            osCanvas.drawRoundRect(shapeRect, 5, 5, paint);
        } else if (shape == OVAL) {
            osCanvas.drawOval(shapeRect, paint);
        }

        invalidated = false;
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        invalidated = true;
    }

    public static class Builder {
        private final Context context;
        private int color = Color.BLACK;
        private float offset;
        private int shape;

        public Builder(Context context) {
            this.context = context;
        }

        public void color(int color) {
            this.color = color;
        }

        public void offset(float offset) {
            this.offset = offset;
        }

        public void shape(int shape) {
            this.shape = shape;
        }
    }

    public static final int OVAL = 0;
    public static final int RECTANGLE = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({OVAL, RECTANGLE})
    public @interface OverlayShape {}
}
