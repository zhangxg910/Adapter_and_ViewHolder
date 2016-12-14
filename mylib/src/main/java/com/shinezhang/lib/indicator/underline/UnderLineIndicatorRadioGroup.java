package com.shinezhang.lib.indicator.underline;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.shinezhang.lib.indicator.IPageIndicatorGetter;
import com.shinezhang.lib.indicator.Utils;

/**
 * Created by ShineZhang on 2016/12/9.
 */

public class UnderLineIndicatorRadioGroup extends RadioGroup implements IPageIndicatorGetter {

    private int mDefaultIndicatorHeight;

    private UnderLinePageIndicator mUnderLinePageIndicator;
    private int mIndicatorHeight;
    private float mIndicatorPositionRelateParent;

    public UnderLineIndicatorRadioGroup(Context context) {
        super(context);
        init();
    }

    public UnderLineIndicatorRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.setOrientation(LinearLayout.HORIZONTAL);

        mUnderLinePageIndicator = new UnderLinePageIndicator(this);

        mDefaultIndicatorHeight = Utils.dp2px(getContext(), 1);
        mIndicatorHeight = mDefaultIndicatorHeight;
        mIndicatorPositionRelateParent = 1F;
    }

    @Override
    public UnderLinePageIndicator getPageIndicator() {
        return mUnderLinePageIndicator;
    }

    public void setIndicatorHeightAndPosition(int height, float postionRelateParent) {
        mIndicatorHeight = height;
        mIndicatorPositionRelateParent = postionRelateParent;
        if (mIndicatorPositionRelateParent < 0f || mIndicatorPositionRelateParent > 1f) {
            throw new IllegalArgumentException("you must set postionRelateParent in [0, 1], but the value is " + postionRelateParent);
        }

        if (mIndicatorHeight < 0) {
            mIndicatorHeight = mDefaultIndicatorHeight;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int top, bottom;
        if (h <= mIndicatorHeight) {
            top = 0;
            bottom = h;
        } else {
            int range = h - mIndicatorHeight;
            top = (int) (range * mIndicatorPositionRelateParent);
            bottom = top + mIndicatorHeight;
        }

        mUnderLinePageIndicator.setIndicatorTop(top);
        mUnderLinePageIndicator.setIndicatorBottom(bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mUnderLinePageIndicator.draw(canvas);
    }

}
