package com.peenal.refreshview_lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by engineer on 2017/4/26.
 */

public abstract class BaseHeaderAdapter {

    protected LayoutInflater mInflater;


    public BaseHeaderAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public abstract View getHeaderView();

    public abstract void pullViewToRefresh(int deltaY);

    public abstract void releaseViewToRefresh(int deltaY);

    public abstract void headerRefreshing();

    public abstract void headerRefreshComplete();


}
