package com.shinezhang.lib.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by ShineZhang on 2016/12/9.
 */

public abstract class AbstractViewPagerAdapter<T> extends PagerAdapter {

    private IAdapterHelper<T> mAdapterHelper;

    public AbstractViewPagerAdapter(Context context) {
        mAdapterHelper = new AdapterHelperImpl<T>(context);
    }

    public final List<? extends T> getDataList() {
        return mAdapterHelper.getDataList();
    }

    public final void setDataList(List<? extends T> list) {
        mAdapterHelper.setDataList(list);
    }

    protected final T getItem(int position) {
        return mAdapterHelper.getItem(position);
    }

    protected final LayoutInflater getLayoutInflater() {
        return mAdapterHelper.getLayoutInflater();
    }

    protected final Context getContext() {
        return mAdapterHelper.getContext();
    }

    @Override
    public final int getCount() {
        return mAdapterHelper.getItemCount();
    }
}
