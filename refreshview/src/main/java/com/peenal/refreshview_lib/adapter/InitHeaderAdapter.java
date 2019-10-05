package com.peenal.refreshview_lib.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.peenal.refreshview_lib.R;

import static android.view.View.VISIBLE;

/**
 * Created by engineer on 2017/4/26.
 */

public class InitHeaderAdapter extends BaseHeaderAdapter {

    //
    private TextView headerText;
    private ProgressBar mProgressBar;

    public InitHeaderAdapter(Context context) {
        super(context);
    }


    @Override
    public View getHeaderView() {
        View mHeaderView = mInflater.inflate(R.layout.ulti_header_layout, null, false);
        headerText = (TextView) mHeaderView.findViewById(R.id.header_text);
        mProgressBar = (ProgressBar) mHeaderView.findViewById(R.id.progressBar);
        return mHeaderView;
    }

    @Override
    public void pullViewToRefresh(int deltaY) {
        headerText.setText("");
    }


    @Override
    public void releaseViewToRefresh(int deltaY) {
        headerText.setText("");
    }

    @Override
    public void headerRefreshing() {
        mProgressBar.setVisibility(VISIBLE);
        headerText.setText("AppName...");
    }

    @Override
    public void headerRefreshComplete() {
        mProgressBar.setVisibility(View.INVISIBLE);
        headerText.setVisibility(VISIBLE);
        headerText.setText("Done");
    }
}
