package com.peenal.pulldown_pullup_refresh;

import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.peenal.pulldown_pullup_refresh.subfragment.ListViewFragment;
import com.peenal.pulldown_pullup_refresh.subfragment.RecyclerViewFragment;
import com.peenal.pulldown_pullup_refresh.subfragment.ScrollViewFragment;
import com.peenal.pulldown_pullup_refresh.subfragment.WebViewFragment;

import java.util.ArrayList;
import java.util.List;

// TODO: 10/5/2019  crated by peenalkumar

public class MainActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private List<Fragment> mFragments = new ArrayList<>();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utril_view);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mFragments.add(new RecyclerViewFragment());
        mFragments.add(new ListViewFragment());
        mFragments.add(new ScrollViewFragment());
        mFragments.add(new WebViewFragment());

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mFragments);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragmentList;

        public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            mFragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "ListView";
                case 1:
                    return "RecyclerView";
                case 2:
                    return "ScrollView";
                case 3:
                    return "WebView";
            }
            return null;
        }
    }
}
