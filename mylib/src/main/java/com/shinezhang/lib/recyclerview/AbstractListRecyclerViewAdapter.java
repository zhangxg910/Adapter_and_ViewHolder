package com.shinezhang.lib.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by ShineZhang on 2016/11/29.
 */

public abstract class AbstractListRecyclerViewAdapter<T, VH extends AbstractRecyclerViewHolder<T>> extends RecyclerView.Adapter<VH> {

    private static final String TAG = "RecyclerViewAdapter";

    private List<? extends T> mListData;

    private final LayoutInflater mLayoutInflater;

    public AbstractListRecyclerViewAdapter(Context context) {
        if (context == null) {
            Log.w(TAG, "context is null, so LayoutInflater is null");
            mLayoutInflater = null;
            return;
        }

        mLayoutInflater = LayoutInflater.from(context);
    }

    public final void setDataList(List<? extends T> listData) {
        mListData = listData;
    }

    public final List<? extends T> getDataList() {
        return mListData;
    }

    protected final LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    protected final T getItem(int position) {
        try {
            return mListData.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public final int getItemCount() {
        if (mListData != null) {
            return mListData.size();
        }
        return 0;
    }

    @Override
    public final void onBindViewHolder(VH holder, int position) {
        T data = getItem(position);
        holder.bind(data);
    }
}
