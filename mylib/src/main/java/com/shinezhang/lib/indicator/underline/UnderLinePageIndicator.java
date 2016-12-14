package com.shinezhang.lib.indicator.underline;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.shinezhang.lib.indicator.AbstractPageIndicator;
import com.shinezhang.lib.indicator.Utils;


/**
 * Created by ShineZhang on 2016/12/9.
 */

public final class UnderLinePageIndicator extends AbstractPageIndicator {

    private int mIndicatorLength;

    private Drawable mIndicatorDrawable;

    private int mIndicatorTop = -1;
    private int mIndicatorBottom = -1;

    public UnderLinePageIndicator(View parentView) {
        super(parentView);

        // set as default values
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF33B5E5"));
        setIndicatorDrawable(colorDrawable);

        setIndicatorLength(Utils.dp2px(mParentView.getContext(), 80));
    }

    public void setIndicatorDrawable(Drawable drawable) {
        mIndicatorDrawable = drawable;
        invalidate();
    }

    public void setIndicatorLength(int length) {
        mIndicatorLength = length;
        invalidate();
    }

    public void setIndicatorTop(int top) {
        mIndicatorTop = top;
        invalidate();
    }

    public void setIndicatorBottom(int bottom) {
        mIndicatorBottom = bottom;
        invalidate();
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

        int paddingLeft = mParentView.getPaddingLeft();
        int paddingTop = mParentView.getPaddingTop();
        int paddingRight = mParentView.getPaddingRight();
        int paddingBottom = mParentView.getPaddingBottom();

        int parentViewHeight = mParentView.getHeight();
        int maxHeight = parentViewHeight - paddingTop - paddingBottom;
        if (maxHeight == 0) {
            return;
        }

        if (mIndicatorTop < paddingTop || mIndicatorTop >= (paddingTop + maxHeight)) {
            mIndicatorTop = paddingTop;
        }

        if (mIndicatorBottom <= paddingTop || mIndicatorBottom > (paddingTop + maxHeight)) {
            mIndicatorBottom = (paddingTop + maxHeight);
        }

        if (mIndicatorTop == mIndicatorBottom) {
            Log.e(TAG, "indicator top equals indicator bottom, value is " + mIndicatorTop);
            return;
        }

        int parentViewWidth = mParentView.getWidth();
        int maxWidth = parentViewWidth - paddingLeft - paddingRight;
        int maxIndicatorWidth = maxWidth / pageCount;
        if (mIndicatorLength <= 0 || mIndicatorLength > maxIndicatorWidth) {
            mIndicatorLength = maxIndicatorWidth;
        }

        int left = (int) (mPosition * maxIndicatorWidth + (maxIndicatorWidth - mIndicatorLength) / 2
                + maxIndicatorWidth * mPositionOffset) + paddingLeft;
        int right = left + mIndicatorLength;

        mIndicatorDrawable.setBounds(left, mIndicatorTop, right, mIndicatorBottom);

        mIndicatorDrawable.draw(canvas);
    }

    @Override
    public void invalidate() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            mParentView.invalidate(0, mIndicatorTop, mParentView.getWidth(), mIndicatorBottom);
        } else {
            mParentView.post(new Runnable() {

                @Override
                public void run() {
                    mParentView.invalidate(0, mIndicatorTop, mParentView.getWidth(), mIndicatorBottom);
                }
            });
        }
    }

}
