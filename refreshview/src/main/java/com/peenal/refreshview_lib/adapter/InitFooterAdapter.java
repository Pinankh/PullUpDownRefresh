package com.peenal.refreshview_lib.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.peenal.refreshview_lib.R;



/**
 * Created by engineer on 2017/4/29.
 */

public class InitFooterAdapter extends BaseFooterAdapter {
    private TextView headerText;
    private ProgressBar mProgressBar;

    public InitFooterAdapter(Context context) {
        super(context);
    }

    @Override
    public View getFooterView() {
        View mFooterView = mInflater.inflate(R.layout.ulti_footer_layout, null, false);
        headerText = (TextView) mFooterView.findViewById(R.id.footer_text);
        mProgressBar = (ProgressBar) mFooterView.findViewById(R.id.progressBar);
        return mFooterView;
    }

    @Override
    public void pullViewToRefresh(int deltaY) {
        headerText.setText("Loading...");
    }


    @Override
    public void releaseViewToRefresh(int deltaY) {
        headerText.setText("Loading more...");
    }

    @Override
    public void footerRefreshing() {
        mProgressBar.setVisibility(View.VISIBLE);
        headerText.setText("Loading...");
    }

    @Override
    public void footerRefreshComplete() {
        mProgressBar.setVisibility(View.INVISIBLE);
        headerText.setVisibility(View.VISIBLE);
        headerText.setText("Done");
    }
}
