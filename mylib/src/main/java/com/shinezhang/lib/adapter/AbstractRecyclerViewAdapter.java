package com.shinezhang.lib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by ShineZhang on 2016/12/7.
 */

public abstract class AbstractRecyclerViewAdapter<T, VH extends AbstractRecyclerViewHolder<T>> extends RecyclerView.Adapter<VH> {

    private final IAdapterHelper<T> mAdapterHelper;

    /**
     * constructor of this adapter
     * @param context <b>if null is passed, the method {@link #getContext()}
     *                and {@link #getLayoutInflater()} will return null.</b>
     *                so pay attention at this.
     */
    public AbstractRecyclerViewAdapter(Context context) {
        mAdapterHelper = new AdapterHelperImpl<T>(context);
    }

    /**
     * set the data list need to show
     * @param list the data list
     */
    public final void setDataList(List<? extends T> list) {
        mAdapterHelper.setDataList(list);

        //need notify here, I think it will be better to notify in client
//        super.notifyDataSetChanged();
    }

    /**
     * get the data list
     * @return if you did not call {@link #setDataList(List)} before, it will be null
     * @see #setDataList(List)
     */
    public final List<? extends T> getDataList() {
        return mAdapterHelper.getDataList();
    }

    /**
     * the assigned position item in your data list, if position is invalid, null will be return
     * @param position the assigned item position
     * @return the item in list, null return if position is invalid
     */
    protected final T getItem(int position) {
        return mAdapterHelper.getItem(position);
    }

    /**
     * get the context, you can use this context to create view
     * @return the context of you have been set before
     */
    protected final Context getContext() {
        return mAdapterHelper.getContext();
    }

    /**
     * if you use xml to create view, it will be useful
     * @return the LayoutInflater you need to inflate xml
     */
    protected final LayoutInflater getLayoutInflater() {
        return mAdapterHelper.getLayoutInflater();
    }

    @Override
    public final void onBindViewHolder(VH holder, int position) {
        T itemData = getItem(position);
        holder.bind(position, itemData);
    }

    @Override
    public final int getItemCount() {
        return mAdapterHelper.getItemCount();
    }
}
