package com.shinezhang.lib.listview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ShineZhang on 2016/11/29.
 */

public abstract class AbstractListViewAdapter<T, VH extends AbstractListViewHolder> extends BaseAdapter {

    private static final String LOG_TAG = "ListViewAdapter";

    /**
     * the value must larger than 0x7f000000,
     * refer to the source code of {@link View#setTag(int, Object)}
     */
    private static final int TAG_KEY = 0x7faabbcc;

    private LayoutInflater mLayoutInflater;
    private List<T> mDataList;

    public AbstractListViewAdapter(Context context) {
        if (context == null) {
            Log.w(LOG_TAG, "context is null, so layout inflater in null");
            return;
        }

        mLayoutInflater = LayoutInflater.from(context);
    }

    public final List<T> getDataList() {
        return mDataList;
    }

    public final void setDataList(List<T> list) {
        mDataList = list;
        if (mDataList == null) {
            Log.e(LOG_TAG, "list is null");
        }

        super.notifyDataSetChanged();
    }

    /**
     * in most of time, we use xml to inflate a view, so add extra method to provide a LayoutInflater
     * @return refer to {@link LayoutInflater}
     */
    protected final LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    @Override
    public final int getCount() {
        if (mDataList != null) {
            return mDataList.size();
        }
        return 0;
    }

    @Override
    public final T getItem(int position) {
        if (mDataList != null) {
            try {
                return mDataList.get(position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        int itemViewType = getItemViewType(position);

        if (view == null) {
            view = onCreateItemView(itemViewType, parent);
            if (view == null) {
                throw new NullPointerException("onCreateItemView return null, check code pls");
            }

            VH viewHolder = onCreateViewHolder(itemViewType, view);
            if (viewHolder == null) {
                Log.w(LOG_TAG, "onCreateViewHolder return null");
            }
            view.setTag(TAG_KEY, viewHolder);
        }

        Object obj = view.getTag(TAG_KEY);
        VH viewHolder = null;
        if (obj == null) {
            Log.w(LOG_TAG, "getTag return null");
        } else {
            //if ClassCastException occurs, just let it throw to make us know it
            viewHolder = (VH) obj;
        }

        T itemData = getItem(position);

        onBindItemView(position, itemData, viewHolder);

        return view;
    }

    /**
     * create the content view if adapter need crate new view
     *
     * @param itemViewType refer to {{@link #getItemViewType(int)}}
     * @return the list view item
     */
    protected abstract View onCreateItemView(int itemViewType, ViewGroup parent);

    /**
     * create a new view holder associate view list item view
     * @param itemViewType refer to {{@link #getItemViewType(int)}}
     * @param itemView the view from {@link #onCreateViewHolder(int, View)}
     * @return
     */
    protected abstract VH onCreateViewHolder(int itemViewType, View itemView);

    /**
     * when the list view is scroll down or up, it should update the current display item
     * @param position which item position need to bind
     * @param itemData the associate item need to bind to list view item
     * @param viewHolder the view holder
     */
    protected abstract void onBindItemView(int position, T itemData, VH viewHolder);
}
