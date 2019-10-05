package com.peenal.refreshview_lib.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import com.peenal.refreshview_lib.R;
import com.peenal.refreshview_lib.util.MeasureTools;

// TODO: 10/1/2019
// Created By Pinalkumar

public class MeiTuanHeaderAdapter extends BaseHeaderAdapter {

    private ImageView loading;
    private int viewHeight;
    private float pull_distance=0;

    public MeiTuanHeaderAdapter(Context context) {
        super(context);
    }

    @Override
    public View getHeaderView() {
        View mView = mInflater.inflate(R.layout.meituan_header_refresh_layout, null, false);
        loading = (ImageView) mView.findViewById(R.id.loading);
        MeasureTools.measureView(mView);
        viewHeight = mView.getMeasuredHeight();
        return mView;
    }

    @Override
    public void pullViewToRefresh(int deltaY) {

        pull_distance=pull_distance+deltaY*0.3f;
        float scale = pull_distance / viewHeight;
        loading.setScaleX(scale);
        loading.setScaleY(scale);

    }


    @Override
    public void releaseViewToRefresh(int deltaY) {
        loading.setImageResource(R.drawable.mei_tuan_loading_pre);
        AnimationDrawable mAnimationDrawable= (AnimationDrawable) loading.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void headerRefreshing() {
        loading.setImageResource(R.drawable.mei_tuan_loading);
        AnimationDrawable mAnimationDrawable= (AnimationDrawable) loading.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void headerRefreshComplete() {
        loading.setImageResource(R.drawable.pull_image);
        loading.setScaleX(0);
        loading.setScaleY(0);
        pull_distance=0;
    }
}
