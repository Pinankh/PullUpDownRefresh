# PullUpDownRefresh
This is an amazing Refresh View for the Android .
 
You can easily Scroll Up Down  with your custom layout, and there are many kinds of amazing animated Loading you can add.

# Demo

<img src="/01.gif" width="200">    <img src="/02.gif" width="200">

Copy & Past this line in to your app level Gradle

```groovy
     implementation 'com.github.PatelPinal:pullUpDownRefresh:1.0.0'
```

Copy & Past this line in to your project Gradle

```groovy
     allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```




# Integration guide

First put the RefreshLayout view in your layout xml :
# With ListView

```xml
         <com.peenal.refreshview_lib.RefreshLayout
        android:id="@+id/refreshView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/white"
        android:orientation="vertical">

        <ListView
            android:id="@+id/listView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:scrollbars="none"/>

    </com.peenal.refreshview_lib.RefreshLayout>
```
# With RecycerlView
```
<com.peenal.refreshview_lib.RefreshLayout
        android:id="@+id/refreshView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#3c3f41"
        android:orientation="vertical">


        <androidx.recyclerview.widget.RecyclerView
            android:background="@color/white"
            android:id="@+id/recyclerview"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>



    </com.peenal.refreshview_lib.RefreshLayout>
 ```   
# With ScrollView
```
    <com.peenal.refreshview_lib.RefreshLayout
        android:id="@+id/refreshView"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.core.widget.NestedScrollView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:fillViewport="true">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical" >


                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:scaleType="fitStart"
                    android:src="@drawable/bg_burger"/>

                <ListView
                    android:id="@+id/list"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    />

            </LinearLayout>
        </androidx.core.widget.NestedScrollView>
    </com.peenal.refreshview_lib.RefreshLayout>
 ```   

     
# Next step 

Set Your Created Diffrent Adapter to "RefreshLayout"
```
 ListView listView = (ListView) view.findViewById(R.id.listView);
        final List<String> datas = new ArrayList<>();
        for (int i = 0; i < PER_PAGE_NUM; i++) {
            datas.add("this is item " + i);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);
        listView.addHeaderView(headview);
        mUltimateRefreshView = (RefreshLayout) view.findViewById(R.id.refreshView);


        mBaseHeaderAdapter = new AnimatedHeaderAdapter(getContext());
        mUltimateRefreshView.setBaseHeaderAdapter(mBaseHeaderAdapter);
        mUltimateRefreshView.setBaseFooterAdapter();


        mUltimateRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(RefreshLayout view) {
                page = 0;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        datas.clear();
                        for (int i = page * PER_PAGE_NUM; i < PER_PAGE_NUM; i++) {
                            datas.add("this is item " + i);
                        }
                        adapter.notifyDataSetChanged();
                        mUltimateRefreshView.onHeaderRefreshComplete();
                    }
                }, 2000);
            }
        });

        mUltimateRefreshView.setOnFooterRefreshListener(new OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(RefreshLayout view) {
                page++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = page * PER_PAGE_NUM; i < (page + 1) * PER_PAGE_NUM; i++) {
                            datas.add("this is item " + i);
                        }
                        adapter.notifyDataSetChanged();
                        mUltimateRefreshView.onFooterRefreshComplete();
                    }
                }, 200);
            }
        });

        mUltimateRefreshView.post(new Runnable() {
            @Override
            public void run() {
                mUltimateRefreshView.headerRefreshing();
            }
        });

```
# Contribute

Suggestions and pull requests are always welcome.
