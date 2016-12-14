package com.shinezhang.lib.indicator.circle;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.shinezhang.lib.indicator.IPageIndicatorGetter;
import com.shinezhang.lib.indicator.Utils;


/**
 * Created by ShineZhang on 2016/12/9.
 */

public class CirclePageIndicatorView extends View implements IPageIndicatorGetter {

    private CirclePageIndicator mCirclePageIndicator;

    public CirclePageIndicatorView(Context context) {
        super(context);
        init();
    }

    public CirclePageIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CirclePageIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCirclePageIndicator = new CirclePageIndicator(this);
        int radius = Utils.dp2px(getContext(), 3);
        mCirclePageIndicator.setRadius(radius);
        mCirclePageIndicator.setGapWidth(radius);
    }

    @Override
    public CirclePageIndicator getPageIndicator() {
        return mCirclePageIndicator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCirclePageIndicator.draw(canvas);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
    }
    
    /**
     * Determines the width of this view
     *
     * @param measureSpec
     *            A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureLong(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY) || (mCirclePageIndicator.getViewPager() == null)) {
            // We were told how big to be
            result = specSize;
        } else {
            // Calculate the width according the views count
            final int count = mCirclePageIndicator.getViewPager().getAdapter().getCount();
            result = (int) (getPaddingLeft() + getPaddingRight() + (count * 2 * mCirclePageIndicator.getRadius()) + (count - 1) * mCirclePageIndicator.getRadius() + 1);
            // Respect AT_MOST value if that was what is called for by
            // measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec
     *            A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureShort(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the height
            result = (int) (2 * mCirclePageIndicator.getRadius() + getPaddingTop() + getPaddingBottom() + 1);
            // Respect AT_MOST value if that was what is called for by
            // measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

}
