package com.peenal.pulldown_pullup_refresh.adapter;

import android.content.Context;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.peenal.pulldown_pullup_refresh.R;
import com.peenal.refreshview_lib.adapter.BaseFooterAdapter;

// TODO: 10/5/2019  created by peenalkumar
public class TraditionFooterAdapter extends BaseFooterAdapter {
    private ProgressBar pull_to_load_progress;
    private ImageView pull_to_load_image;
    private TextView pull_to_load_text;

    private RotateAnimation mFlipAnimation;

    public TraditionFooterAdapter(Context context) {
        super(context);
        mFlipAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(250);
        mFlipAnimation.setFillAfter(true);
    }

    @Override
    public View getFooterView() {
        View mView = mInflater.inflate(R.layout.tradition_footer_refresh_layout, null, false);
        pull_to_load_progress = (ProgressBar) mView.findViewById(R.id.pull_to_load_progress);
        pull_to_load_image = (ImageView) mView.findViewById(R.id.pull_to_load_image);
        pull_to_load_text= (TextView) mView.findViewById(R.id.pull_to_load_text);
        return mView;
    }

    @Override
    public void pullViewToRefresh(int deltaY) {
        pull_to_load_text.setText("Pull…");
        pull_to_load_image.clearAnimation();
        pull_to_load_image.startAnimation(mFlipAnimation);
    }

    @Override
    public void releaseViewToRefresh(int deltaY) {
        pull_to_load_text.setText("AppName");
    }

    @Override
    public void footerRefreshing() {
        pull_to_load_text.setText("Loading...");
        pull_to_load_image.setImageDrawable(null);
        pull_to_load_image.clearAnimation();
        pull_to_load_image.setVisibility(View.GONE);
        pull_to_load_progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void footerRefreshComplete() {
        pull_to_load_progress.setVisibility(View.GONE);
        pull_to_load_image.setImageResource(R.drawable.ic_pulltorefresh_arrow_up);
        pull_to_load_image.setVisibility(View.VISIBLE);
        pull_to_load_text.setVisibility(View.VISIBLE);
        pull_to_load_text.setText("Done…");
    }
}
