package com.peenal.pulldown_pullup_refresh.subfragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.peenal.pulldown_pullup_refresh.R;
import com.peenal.pulldown_pullup_refresh.adapter.FooterAdapter;
import com.peenal.pulldown_pullup_refresh.adapter.HeaderAdpater;
import com.peenal.refreshview_lib.RefreshLayout;
import com.peenal.refreshview_lib.interfaces.OnFooterRefreshListener;
import com.peenal.refreshview_lib.interfaces.OnHeaderRefreshListener;

// TODO: 10/5/2019  created by peenalkumar
public class ScrollViewFragment extends Fragment {

    private RefreshLayout mUltimateRefreshView;
    private Context mContext;

    private ListView mListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_scroller_view, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.list);
        String[] datas = getResources().getStringArray(R.array.flows);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, datas);
        mListView.setAdapter(mAdapter);


        mUltimateRefreshView = (RefreshLayout) view.findViewById(R.id.refreshView);
        mUltimateRefreshView.setBaseHeaderAdapter(new HeaderAdpater(mContext));
        mUltimateRefreshView.setBaseFooterAdapter(new FooterAdapter(mContext));
        mUltimateRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(RefreshLayout view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mUltimateRefreshView.onHeaderRefreshComplete();
                    }
                }, 2000);
            }
        });
        mUltimateRefreshView.setOnFooterRefreshListener(new OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(RefreshLayout view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mUltimateRefreshView.onFooterRefreshComplete();
                    }
                }, 800);
            }
        });
    }

}
