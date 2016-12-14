package com.shinezhang.lib.indicator.circle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.shinezhang.lib.indicator.AbstractPageIndicator;

/**
 * Created by ShineZhang on 2016/12/9.
 */

public final class CirclePageIndicator extends AbstractPageIndicator {

    public enum Gravity {
        START,
        CENTER,
        END;
    }

    private float mRadius;

    private Paint mPaintPageFill;
    private Paint mPaintStroke;
    private Paint mPaintFill;

    private boolean mSnap;
    private Gravity mGravity;

    private int mGapWidth;

    public CirclePageIndicator(View parentView) {
        super(parentView);
        init();
    }

    private void init() {
        mPaintPageFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);

        initAsDefault();
    }

    public void initAsDefault() {
        // default color and circle size
        this.setStrokeColor(Color.LTGRAY);
        this.setStrokeWidth(2f);
        this.setFillColor(Color.WHITE);
        this.setPageColorAlpha(0);
        this.setRadius(5);
        this.setGapWidth(5);
        this.setGravity(Gravity.CENTER);
    }

    public void setPageColor(int pageColor) {
        mPaintPageFill.setColor(pageColor);
        invalidate();
    }

    public void setPageColorAlpha(int alpha) {
        if (alpha < 0 || alpha > 255) {
            throw new IllegalArgumentException("alpha should be in [0, 255], but the real value is " + alpha);
        }

        mPaintPageFill.setAlpha(alpha);
        invalidate();
    }

    public int getPageColor() {
        return mPaintPageFill.getColor();
    }

    public int getPageColorAlpha() {
        return mPaintPageFill.getAlpha();
    }

    public void setFillColor(int fillColor) {
        mPaintFill.setColor(fillColor);
        invalidate();
    }

    public int getFillColor() {
        return mPaintFill.getColor();
    }

    public void setStrokeColor(int strokeColor) {
        mPaintStroke.setColor(strokeColor);
        invalidate();
    }

    public int getStrokeColor() {
        return mPaintStroke.getColor();
    }

    public void setStrokeWidth(float strokeWidth) {
        mPaintStroke.setStrokeWidth(strokeWidth);
        if (mPaintStroke.getStyle() != Paint.Style.STROKE) {
            mPaintStroke.setStyle(Paint.Style.STROKE);
        }
        invalidate();
    }

    public float getStrokeWidth() {
        return mPaintStroke.getStrokeWidth();
    }

    public void setRadius(float radius) {
        mRadius = radius;
        setParentMinDimen();
        invalidate();
    }

    private void setParentMinDimen() {
        int paddingLeft = mParentView.getPaddingLeft();
        int paddingTop = mParentView.getPaddingLeft();
        int paddingRight = mParentView.getPaddingLeft();
        int paddingBottom = mParentView.getPaddingLeft();

        mParentView.setMinimumHeight((int) (mRadius * 4) + paddingTop + paddingBottom);

        int pageCount = 0;
        try {
            pageCount = mViewPager.getAdapter().getCount();
        } catch (Exception e) {
        }
        if (pageCount == 0) {
            return;
        }

        int minWidth = (int) (pageCount * mRadius * 2 + (pageCount - 1) * mGapWidth + paddingLeft + paddingRight);
        mParentView.setMinimumWidth(minWidth);
    }

    public float getRadius() {
        return mRadius;
    }

    public void setSnap(boolean snap) {
        mSnap = snap;
        invalidate();
    }

    public boolean isSnap() {
        return mSnap;
    }

    public void setGravity(Gravity gravity) {
        mGravity = gravity;
        if (mGravity == null) {
            throw new IllegalArgumentException("gravity can not be null");
        }
    }

    public Gravity getGravity() {
        return mGravity;
    }
    
    public void setGapWidth(int gapWidth) {
        mGapWidth = gapWidth;
        setParentMinDimen();
        invalidate();
    }
    
    public int getGapWidth() {
        return mGapWidth;
    }

    @Override
    public void invalidate() {
        if (!super.prepare()) {
            return;
        }
        int pageCount = 0;
        try {
            pageCount = mViewPager.getAdapter().getCount();
        } catch (Exception e) {
        }
        if (pageCount == 0) {
            return;
        }

        int parentViewWidth = mParentView.getWidth();
        int parentViewHeight = mParentView.getHeight();

        int paddingLeft = mParentView.getPaddingLeft();
        int paddingTop = mParentView.getPaddingLeft();
        int paddingRight = mParentView.getPaddingLeft();
        int paddingBottom = mParentView.getPaddingLeft();

        float totalLength = pageCount * mRadius * 2 + (pageCount - 1) * mGapWidth;
        float startX = 0;
        switch (mGravity) {
            case START:
                startX = paddingLeft;
                break;
            case END:
                startX = parentViewWidth - paddingRight - totalLength;
                break;
            default:
                startX = paddingLeft + (parentViewWidth - paddingLeft - paddingRight - totalLength) / 2;
                break;
        }
        
        float pivotY = paddingTop + (parentViewHeight - paddingTop - paddingBottom ) / 2;

        int left = (int) (startX - 1);
        int top = (int) (pivotY - mRadius - 1);
        int right = (int) (startX + totalLength + 1);
        int bottom = (int) (pivotY + mRadius + 1);

        mParentView.invalidate(left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!super.prepare()) {
            return;
        }

        int pageCount = mViewPager.getAdapter().getCount();
        if (pageCount == 0) {
            return;
        }

        int parentViewWidth = mParentView.getWidth();
        int parentViewHeight = mParentView.getHeight();

        int paddingLeft = mParentView.getPaddingLeft();
        int paddingTop = mParentView.getPaddingLeft();
        int paddingRight = mParentView.getPaddingLeft();
        int paddingBottom = mParentView.getPaddingLeft();

        float totalLength = pageCount * mRadius * 2 + (pageCount - 1) * mGapWidth;
        float startX = 0;
        switch (mGravity) {
            case START:
                startX = paddingLeft;
                break;
            case END:
                startX = parentViewWidth - paddingRight - totalLength;
                break;
            default:
                startX = paddingLeft + (parentViewWidth - paddingLeft - paddingRight - totalLength) / 2;
                break;
        }

        float pageFillRadius = mRadius;
        if (mPaintStroke.getStrokeWidth() > 0) {
            pageFillRadius -= mPaintStroke.getStrokeWidth() / 2.0f;
        }

        float pivotY = paddingTop + (parentViewHeight - paddingTop - paddingBottom ) / 2;

        float pivotX = 0;
        for (int i = 0; i < pageCount; i++) {
            pivotX = startX + mRadius + (mRadius * 2 + mGapWidth) * i;

            // Only paint fill if not completely transparent
            if (mPaintPageFill.getAlpha() > 0) {
                canvas.drawCircle(pivotX, pivotY, pageFillRadius, mPaintPageFill);
            }

            // Only paint stroke if a stroke width was non-zero
            if (pageFillRadius != mRadius) {
                canvas.drawCircle(pivotX, pivotY, mRadius, mPaintStroke);
            }
        }

        pivotX = startX + mRadius + (mRadius * 2 + mGapWidth) * mPosition;
        if (!mSnap) {
            pivotX += (mRadius * 2 + mGapWidth) * mPositionOffset;
        }

        canvas.drawCircle(pivotX, pivotY, mRadius, mPaintFill);
    }

}
