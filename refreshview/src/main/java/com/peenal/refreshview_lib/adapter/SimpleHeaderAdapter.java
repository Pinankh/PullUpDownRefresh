package com.peenal.refreshview_lib.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import com.peenal.refreshview_lib.R;

// TODO: 10/1/2019
// Created by pinalkumar

public class SimpleHeaderAdapter extends BaseHeaderAdapter {
    private ImageView loading;

    public SimpleHeaderAdapter(Context context) {
        super(context);
    }

    @Override
    public View getHeaderView() {
        View mView = mInflater.inflate(R.layout.simple_header_refresh_layout, null, false);
        loading = (ImageView) mView.findViewById(R.id.loading);
        return mView;
    }

    @Override
    public void pullViewToRefresh(int deltaY) {
        loading.setImageResource(R.drawable.simple_loading);
        AnimationDrawable mAnimationDrawable= (AnimationDrawable) loading.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void releaseViewToRefresh(int deltaY) {

    }

    @Override
    public void headerRefreshing() {

    }

    @Override
    public void headerRefreshComplete() {
    }

}
