package com.peenal.refreshview_lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by engineer on 2017/4/29.
 */

public abstract class BaseFooterAdapter {
    protected LayoutInflater mInflater;

    public BaseFooterAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    public abstract View getFooterView();


    public abstract void pullViewToRefresh(int deltaY);


    public abstract void releaseViewToRefresh(int deltaY);


    public abstract void footerRefreshing();


    public abstract void footerRefreshComplete();
}
