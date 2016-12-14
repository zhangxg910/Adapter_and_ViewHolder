package com.shinezhang.lib.indicator.underline;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.shinezhang.lib.indicator.IPageIndicatorGetter;

/**
 * Created by ShineZhang on 2016/12/9.
 */

public class UnderLinePageIndicatorView extends View implements IPageIndicatorGetter {

    private UnderLinePageIndicator mUnderLinePageIndicator;

    public UnderLinePageIndicatorView(Context context) {
        super(context);
        init();
    }

    public UnderLinePageIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UnderLinePageIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mUnderLinePageIndicator = new UnderLinePageIndicator(this);
    }

    @Override
    public UnderLinePageIndicator getPageIndicator() {
        return mUnderLinePageIndicator;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mUnderLinePageIndicator.setIndicatorTop(0);
        mUnderLinePageIndicator.setIndicatorBottom(h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mUnderLinePageIndicator.draw(canvas);
    }

}
