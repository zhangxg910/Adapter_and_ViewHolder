package com.shinezhang.lib.adapter;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by ShineZhang on 2016/12/7.
 */

public abstract class AbstractListViewHolder<T> {

    private final IViewHolderHelper<T> mViewHolderHelper;

    private final View mItemView;

    public AbstractListViewHolder(View itemView) {
        if (itemView == null) {
            throw new NullPointerException("item view can not be null");
        }

        mItemView = itemView;
        mViewHolderHelper = new ViewHolderHelperImpl<T>();

        ButterKnife.bind(this, itemView);
    }

    /**
     * get the list item view
     * @return the list item view
     */
    public final View getItemView() {
        return mItemView;
    }

    /**
     * bind the current item view with position and item data
     * @param position the current view item position, it is for view item ui use in most of case.
     * @param t the current bind data, it is for list view item ui use in most of case
     */
    public final void bind(int position, T t) {
        mViewHolderHelper.bind(position, t);
        onBind(t);
    }

    /**
     * get the current item bind position
     * @return the current list view item position, it is for list view item ui use in most of case
     */
    protected final int getBindPosition() {
        return mViewHolderHelper.getBindPosition();
    }

    /**
     * get the current item bind data
     * @return the current bind data, it is for list view item ui use in most of case
     */
    protected final T getBindData() {
        return mViewHolderHelper.getBindData();
    }

    /**
     * do actual bind work in this method, mTvName.setText(t.xxx);
     * @param t the current bind data
     */
    protected abstract void onBind(T t);
}
