package com.peenal.pulldown_pullup_refresh.subfragment;

import android.os.Bundle;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.peenal.pulldown_pullup_refresh.R;
import com.peenal.pulldown_pullup_refresh.adapter.TraditionFooterAdapter;
import com.peenal.pulldown_pullup_refresh.adapter.TraditionHeaderAdapter;
import com.peenal.refreshview_lib.RefreshLayout;
import com.peenal.refreshview_lib.interfaces.OnFooterRefreshListener;
import com.peenal.refreshview_lib.interfaces.OnHeaderRefreshListener;

import java.util.ArrayList;
import java.util.List;

// TODO: 10/5/2019  created by peenalkumar
public class RecyclerViewFragment extends Fragment {

    private RefreshLayout mUltimateRefreshView;
    private List<String> datas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("this is item " + i);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),RecyclerView.VERTICAL));
        mRecyclerView.setAdapter(new MyAdapter());
        mUltimateRefreshView = (RefreshLayout) view.findViewById(R.id.refreshView);
        mUltimateRefreshView.setBaseHeaderAdapter(new TraditionHeaderAdapter(getContext()));
        mUltimateRefreshView.setBaseFooterAdapter(new TraditionFooterAdapter(getContext()));


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

        mUltimateRefreshView.setOnFooterRefreshListener(new OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(RefreshLayout view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mUltimateRefreshView.onFooterRefreshComplete();
                    }
                },800);
            }
        });


        mUltimateRefreshView.headerRefreshing();


        return view;
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(parent.getContext());
            View mView = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new MyHolder(mView);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.mTextView.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            public MyHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
