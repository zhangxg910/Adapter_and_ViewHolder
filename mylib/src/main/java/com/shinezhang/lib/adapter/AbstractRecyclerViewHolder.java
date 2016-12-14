package com.shinezhang.lib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by ShineZhang on 2016/12/7.
 */

public abstract class AbstractRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    private final IViewHolderHelper<T> mViewHolderHelper;

    public AbstractRecyclerViewHolder(View itemView) {
        super(itemView);

        mViewHolderHelper = new ViewHolderHelperImpl<T>();
        ButterKnife.bind(this, itemView);
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
     * get the current item bind data
     * @return the current bind data, it is for list view item ui use in most of case
     */
    protected final T getBindData() {
        return mViewHolderHelper.getBindData();
    }

    /**
     * get the current item bind position
     * @return the current list view item position, it is for list view item ui use in most of case
     */
    protected final int getBindPosition() {
        return mViewHolderHelper.getBindPosition();
    }

    /**
     * do actual bind work in this method, e.g. mTvName.setTag(Integer.value(position));
     * mTvName.setText(t.xxx);
     * @param t the current bind data
     */
    protected abstract void onBind(T t);
}
