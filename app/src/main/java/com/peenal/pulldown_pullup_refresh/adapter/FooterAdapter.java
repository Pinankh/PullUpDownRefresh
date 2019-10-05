package com.peenal.pulldown_pullup_refresh.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.peenal.pulldown_pullup_refresh.R;
import com.peenal.refreshview_lib.adapter.BaseFooterAdapter;

// TODO: 10/5/2019  Created by peenalkumar

public class FooterAdapter extends BaseFooterAdapter {
    private ImageView loading;
    private Context mContext;

    public FooterAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getFooterView() {
        View footerView = mInflater.inflate(R.layout.footer_refresh_layout, null, false);
        loading = (ImageView) footerView.findViewById(R.id.loading);
        return footerView;
    }

    @Override
    public void pullViewToRefresh(int deltaY) {
        Glide.with(mContext).load(R.drawable.jd_loading_logo).into(loading);
    }

    @Override
    public void releaseViewToRefresh(int deltaY) {

    }

    @Override
    public void footerRefreshing() {

    }

    @Override
    public void footerRefreshComplete() {
        loading.setImageDrawable(null);
    }
}
