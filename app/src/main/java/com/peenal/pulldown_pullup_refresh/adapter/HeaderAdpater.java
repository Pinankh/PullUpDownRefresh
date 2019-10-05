package com.peenal.pulldown_pullup_refresh.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peenal.pulldown_pullup_refresh.R;
import com.peenal.refreshview_lib.adapter.BaseHeaderAdapter;
import com.peenal.refreshview_lib.util.MeasureTools;

// TODO: 10/5/2019  Created by peenalkumar
public class HeaderAdpater extends BaseHeaderAdapter {
    private TextView headerText;
    private ImageView loading;
    private LinearLayout loading_pre;
    private float pull_distance=0;
    private int viewHeight;

    public HeaderAdpater(Context context) {
        super(context);
    }


    @Override
    public View getHeaderView() {
        View headerView = mInflater.inflate(R.layout.header_refresh_layout, null, false);
        headerText = (TextView) headerView.findViewById(R.id.header_text);
        loading = (ImageView) headerView.findViewById(R.id.loading);
        loading_pre = (LinearLayout) headerView.findViewById(R.id.loading_pre);
        MeasureTools.measureView(headerView);
        viewHeight = headerView.getMeasuredHeight();
        return headerView;
    }

    @Override
    public void pullViewToRefresh(int deltaY) {
        headerText.setText("AppName…");
        pull_distance=pull_distance+deltaY*0.3f;
        float scale = pull_distance / viewHeight;
        loading_pre.setScaleY(scale);
        loading_pre.setScaleX(scale);
    }

    @Override
    public void releaseViewToRefresh(int deltaY) {
        headerText.setText("Release…");
        loading_pre.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void headerRefreshing() {
        headerText.setText("Loading…");
        loading.setImageResource(R.drawable.jd_loading);
        AnimationDrawable mAnimationDrawable= (AnimationDrawable) loading.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void headerRefreshComplete() {
        headerText.setText("Done…");
        loading.clearAnimation();
        loading.setImageResource(R.drawable.x_);
        loading.setVisibility(View.GONE);
        loading_pre.setVisibility(View.VISIBLE);
        loading_pre.setScaleY(0);
        loading_pre.setScaleX(0);
        pull_distance=0;
    }
}
