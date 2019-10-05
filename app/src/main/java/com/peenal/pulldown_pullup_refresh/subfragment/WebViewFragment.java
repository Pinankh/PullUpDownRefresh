package com.peenal.pulldown_pullup_refresh.subfragment;

import android.os.Bundle;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.fragment.app.Fragment;

import com.peenal.pulldown_pullup_refresh.R;
import com.peenal.pulldown_pullup_refresh.adapter.SimpleHeaderAdapter;
import com.peenal.refreshview_lib.RefreshLayout;
import com.peenal.refreshview_lib.interfaces.OnHeaderRefreshListener;

// TODO: 10/5/2019  created by peenalkumar

public class WebViewFragment extends Fragment {
    private RefreshLayout mUltimateRefreshView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_web_view, container, false);
        WebView mWebView= (WebView) view.findViewById(R.id.webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl("https://mobile.bk.com/");
        mUltimateRefreshView = (RefreshLayout) view.findViewById(R.id.refreshView);
        mUltimateRefreshView.setBaseHeaderAdapter(new SimpleHeaderAdapter(getContext()));
        mUltimateRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(RefreshLayout view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mUltimateRefreshView.onHeaderRefreshComplete();
                    }
                },2000);
            }
        });
        return view;

    }


}
