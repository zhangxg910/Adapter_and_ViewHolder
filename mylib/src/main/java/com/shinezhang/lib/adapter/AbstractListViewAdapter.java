package com.shinezhang.lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ShineZhang on 2016/12/7.
 */

public abstract class AbstractListViewAdapter<T, VH extends AbstractListViewHolder<T>> extends BaseAdapter {

    /**
     * the value must larger than 0x7f000000,
     * refer to the source code of {@link View#setTag(int, Object)}
     */
    private static final int TAG_KEY = 0x7faabbcc;

    private final IAdapterHelper<T> mAdapterHelper;

    /**
     * constructor of this adapter
     * @param context <b>if null is passed, the method {@link #getContext()}
     *                and {@link #getLayoutInflater()} will return null.</b>
     *                so pay attention at this.
     */
    public AbstractListViewAdapter(Context context) {
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
    public final int getCount() {
        return mAdapterHelper.getItemCount();
    }

    @Override
    public final T getItem(int position) {
        return mAdapterHelper.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            int itemViewType = getItemViewType(position);
            VH viewHolder = onCreateViewHolder(parent, itemViewType);
            if (viewHolder == null) {
                throw new NullPointerException("onCreateViewHolder return null, check code pls, position = " + position);
            }

            view = viewHolder.getItemView();
            view.setTag(TAG_KEY, viewHolder);
        }

        Object obj = view.getTag(TAG_KEY);
        if (obj == null) {
            throw new NullPointerException("try get view holder from view at position " + position
                    +", but null return, have you call View.setTag() with key param " + TAG_KEY + " before?" );
        }

        //if ClassCastException occurs, just let it throw to make us know it
        VH viewHolder = (VH) obj;

        T itemData = getItem(position);
        viewHolder.bind(position, itemData);

        return view;
    }

    /**
     * create a new view holder associate view list item view
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param itemViewType refer to {@link #getItemViewType(int)}
     * @return the created view holder, exception will throw it null return
     */
    protected abstract VH onCreateViewHolder(ViewGroup parent, int itemViewType);
}
