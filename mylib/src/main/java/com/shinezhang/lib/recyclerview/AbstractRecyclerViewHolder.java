package com.shinezhang.lib.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by ShineZhang on 2016/11/29.
 */

public abstract class AbstractRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    private T bindData;

    public AbstractRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public final void bind(T t) {
        bindData = t;
        onBind(t);
    }

    protected final T getBindData() {
        return bindData;
    }

    /**
     * do bind work,
     * @param t
     */
    protected abstract void onBind(T t);
}
