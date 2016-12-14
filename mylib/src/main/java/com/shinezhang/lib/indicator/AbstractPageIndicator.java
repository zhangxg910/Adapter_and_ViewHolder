package com.shinezhang.lib.indicator;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by ShineZhang on 2016/12/9.
 */

public abstract class AbstractPageIndicator implements IPageIndicator {

    protected static final String TAG = "PageIndicator";

    private static final boolean DEBUG = false;

    protected View mParentView;
    protected ViewPager mViewPager;

    protected int mPosition;
    protected float mPositionOffset;

    public AbstractPageIndicator(View parentView) {
        mParentView = parentView;
        if (mParentView == null) {
            throw new IllegalArgumentException("parentView can not be null");
        }
    }

    @Override
    public final void setViewPager(ViewPager viewPager, boolean initPageChangeListener) {
        mViewPager = viewPager;
        if (mViewPager == null) {
            throw new IllegalArgumentException("viewPager can not be null");
        }

        if (initPageChangeListener) {
            mViewPager.addOnPageChangeListener(this);
        }

        invalidate();
    }

    @Override
    public final ViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public final void onPageSelected(int position) {
        mPosition = position;
        mPositionOffset = 0F;

        invalidate();
    }

    @Override
    public final void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mPosition = position;
        mPositionOffset = positionOffset;

        invalidate();
    }

    protected final boolean prepare() {
        if (mViewPager == null) {
            if (DEBUG) {
                throw new RuntimeException("you must set ViewPager first.");
            }

            Log.w(TAG, "you must set ViewPager first.");
            return false;
        }

        if (mViewPager.getAdapter() == null) {
            if (DEBUG) {
                throw new RuntimeException("you must set ViewPager adapter first.");
            }

            Log.w(TAG, "you must set ViewPager adapter first.");
            return false;
        }

        return true;
    }

}
