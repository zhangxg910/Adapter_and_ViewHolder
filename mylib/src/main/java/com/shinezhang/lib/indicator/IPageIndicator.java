package com.shinezhang.lib.indicator;

import android.graphics.Canvas;
import android.support.v4.view.ViewPager;

/**
 * Created by ShineZhang on 2016/12/9.
 */

public interface IPageIndicator extends ViewPager.OnPageChangeListener {

    /**
     * Bind the indicator to a ViewPager.
     *
     * @param viewPager it can not be null
     * @param initPageChangeListener if true, we will set the view pager OnPageChangeListener as this
     */
    void setViewPager(ViewPager viewPager, boolean initPageChangeListener);

    /**
     * get bind ViewPager
     * @return
     */
    ViewPager getViewPager();

    /**
     * redraw the page indicator
     */
    void invalidate();

    /**
     * draw the page indicator, it must be in ui thread, usually it execute on {@link android.view.View#onDraw(Canvas canvas)}
     * 
     * @param canvas
     */
    void draw(Canvas canvas);
}
