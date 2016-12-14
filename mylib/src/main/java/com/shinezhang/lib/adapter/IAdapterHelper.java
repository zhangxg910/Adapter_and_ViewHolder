package com.shinezhang.lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by ShineZhang on 2016/12/7.
 * <p/>
 * helper class for adapter
 */

/* package */ interface IAdapterHelper<T> {

    /**
     * set the data list need to show
     * @param list the data list
     */
    void setDataList(List<? extends T> list);

    /**
     * get the data list
     * @return if you did not call {@link #setDataList(List)} before, it will be null
     * @see #setDataList(List)
     */
    List<? extends T> getDataList();

    /**
     * get the context, you can use this context to create view
     * @return the context of you have been set before
     */
    Context getContext();

    /**
     * if you use xml to create view, it will be useful
     * @return the LayoutInflater you need to inflate xml
     */
    LayoutInflater getLayoutInflater();

    /**
     * the assigned position item in your data list, if position is invalid, null will be return
     * @param position the assigned item position
     * @return the item in list, null return if position is invalid
     */
    T getItem(int position);

    /**
     * get the list size
     * @return the list size, 0 return if list is null
     */
    int getItemCount();
}
