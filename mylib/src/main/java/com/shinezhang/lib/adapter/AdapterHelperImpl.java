package com.shinezhang.lib.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by ShineZhang on 2016/12/7.
 */

/* package */ final class AdapterHelperImpl<T> implements IAdapterHelper<T> {

    private static final String LOG_TAG = "AdapterHelperImpl";

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    private List<? extends T> mListData;

    public AdapterHelperImpl(Context context) {
        mContext = context;

        if (context == null) {
            Log.w(LOG_TAG, "context is null, so LayoutInflater is null");
            mLayoutInflater = null;
            return;
        }

        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void setDataList(List<? extends T> list) {
        mListData = list;
    }

    @Override
    public List<? extends T> getDataList() {
        return mListData;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    @Override
    public T getItem(int position) {
        int count = getItemCount();

        if (count == 0) {
            return null;
        }

        if (position >= 0 && position < count) {
            return mListData.get(position);
        }

        return null;
    }

    @Override
    public int getItemCount() {
        if (mListData == null) {
            return 0;
        }

        return mListData.size();
    }
}
