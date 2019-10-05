package com.peenal.refreshview_lib;

import android.animation.ValueAnimator;
import android.content.Context;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.LinearLayout;



import com.peenal.refreshview_lib.adapter.BaseFooterAdapter;
import com.peenal.refreshview_lib.adapter.BaseHeaderAdapter;
import com.peenal.refreshview_lib.adapter.InitFooterAdapter;
import com.peenal.refreshview_lib.adapter.InitHeaderAdapter;
import com.peenal.refreshview_lib.interfaces.OnFooterRefreshListener;
import com.peenal.refreshview_lib.interfaces.OnHeaderRefreshListener;
import com.peenal.refreshview_lib.util.MeasureTools;

// TODO: 10/1/2019
//Created by Pinalkumar

public class RefreshLayout extends LinearLayout {

    private static final String TAG = RefreshLayout.class.getSimpleName();

    private static final int PULL_TO_REFRESH = 2;
    private static final int RELEASE_TO_REFRESH = 3;
    private static final int REFRESHING = 4;
    // pull state
    private static final int PULL_UP_STATE = 0;
    private static final int PULL_DOWN_STATE = 1;

    private int mPullState;

    private int animDuration = 300;//头、尾 部回弹动画执行时间

    /**
     * list or grid
     */
    private AdapterView<?> mAdapterView;
    /**
     * RecyclerView
     */
    private RecyclerView mRecyclerView;
    /**
     * NestedScrollView
     */
    private NestedScrollView mScrollView;
    /**
     * WebView
     */
    private WebView mWebView;


    //Header
    private int mHeaderState;
    private View mHeaderView;
    private int mHeadViewHeight;
    //Footer
    private int mFooterState;
    private View mFooterView;
    private int mFooterViewHeight;
    //action
    private int lastY;

    private BaseHeaderAdapter mBaseHeaderAdapter;
    private BaseFooterAdapter mBaseFooterAdapter;
    private OnHeaderRefreshListener mOnHeaderRefreshListener;
    private OnFooterRefreshListener mOnFooterRefreshListener;

    private Context mContext;


    public RefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        setOrientation(VERTICAL);
        mContext = context;

    }


    public void setBaseHeaderAdapter(BaseHeaderAdapter baseHeaderAdapter) {
        mBaseHeaderAdapter = baseHeaderAdapter;
        initHeaderView();
        initSubViewType();
    }

    public void setBaseHeaderAdapter() {
        mBaseHeaderAdapter = new InitHeaderAdapter(mContext);
        initHeaderView();
        initSubViewType();
    }

    public void setBaseFooterAdapter(BaseFooterAdapter baseFooterAdapter) {
        mBaseFooterAdapter = baseFooterAdapter;
        initFooterView();
    }

    public void setBaseFooterAdapter() {
        mBaseFooterAdapter = new InitFooterAdapter(mContext);
        initFooterView();
    }


    private void initHeaderView() {
        mHeaderView = mBaseHeaderAdapter.getHeaderView();
        MeasureTools.measureView(mHeaderView);
        mHeadViewHeight = mHeaderView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mHeadViewHeight);
        params.topMargin = -mHeadViewHeight;
        addView(mHeaderView, 0, params);

    }

    private void initFooterView() {
        mFooterView = mBaseFooterAdapter.getFooterView();
        MeasureTools.measureView(mFooterView);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                mFooterViewHeight);
        addView(mFooterView, params);
    }



    private void initSubViewType() {
        int count = getChildCount();
        if (count < 2) {
            return;
        }

        View view = getChildAt(1);

        if (view instanceof AdapterView<?>) {
            mAdapterView = (AdapterView<?>) view;
        }

        if (view instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) view;
        }

        if (view instanceof NestedScrollView) {
            mScrollView = (NestedScrollView) view;
        }

        if (view instanceof WebView) {
            mWebView = (WebView) view;
        }

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - lastY;
                if (isParentViewScroll(deltaY)) {
                    Log.e(TAG, "onInterceptTouchEvent: belong to ParentView");
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }


    private boolean isParentViewScroll(int deltaY) {
        boolean belongToParentView = false;
        if (mHeaderState == REFRESHING) {
            belongToParentView = false;
        }

        if (mAdapterView != null) {

            if (deltaY > 0) {
                View child = mAdapterView.getChildAt(0);
                if (child == null) {
                    belongToParentView = false;
                } else if (mAdapterView.getFirstVisiblePosition() == 0 && child.getTop() == 0) {
                    mPullState = PULL_DOWN_STATE;
                    belongToParentView = true;
                }
            } else if (deltaY < 0) {
                View lastChild = mAdapterView.getChildAt(mAdapterView.getChildCount() - 1);
                if (lastChild == null) {

                    belongToParentView = false;
                }

                else if (lastChild.getBottom() <= getHeight()
                        && mAdapterView.getLastVisiblePosition() == mAdapterView
                        .getCount() - 1) {
                    mPullState = PULL_UP_STATE;
                    belongToParentView = true;
                }
            }
        }

        else if (mRecyclerView != null) {
            if (deltaY > 0) {
                View child = mRecyclerView.getChildAt(0);
                if (child == null) {
                    belongToParentView = false;
                }
                LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int firstPosition = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();

                if (firstPosition == 0) {
                    mPullState = PULL_DOWN_STATE;
                    belongToParentView = true;
                }
            } else if (deltaY < 0) {
                View child = mRecyclerView.getChildAt(0);
                if (child == null) {
                    belongToParentView = false;
                }
                if (mRecyclerView.computeVerticalScrollExtent() + mRecyclerView.computeVerticalScrollOffset()
                        >= mRecyclerView.computeVerticalScrollRange()) {
                    belongToParentView = true;
                    mPullState = PULL_UP_STATE;
                } else {
                    belongToParentView = false;
                }
            }
        }

        else if (mScrollView != null) {
            View child = mScrollView.getChildAt(0);
            if (deltaY > 0) {

                if (child == null) {
                    belongToParentView = false;
                }

                int distance = mScrollView.getScrollY();
                if (distance == 0) {
                    mPullState = PULL_DOWN_STATE;
                    belongToParentView = true;
                }
            } else if (deltaY < 0
                    && child.getMeasuredHeight() <= getHeight()
                    + mScrollView.getScrollY()) {
                mPullState = PULL_UP_STATE;
                belongToParentView = true;

            }
        }

        else if (mWebView != null) {
            View child = mWebView.getChildAt(0);
            if (deltaY > 0) {

                if (child == null) {
                    belongToParentView = false;
                }

                int distance = mWebView.getScrollY();
                if (distance == 0) {
                    mPullState = PULL_DOWN_STATE;
                    belongToParentView = true;
                }
            }
        }


        return belongToParentView;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - lastY;
                if (mPullState == PULL_DOWN_STATE) {
                    Log.e(TAG, "onTouchEvent: pull down begin-->" + deltaY);
                    initHeaderViewToRefresh(deltaY);
                } else if (mPullState == PULL_UP_STATE) {
                    initFooterViewToRefresh(deltaY);
                }
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int topMargin = getHeaderTopMargin();
                Log.e(TAG, "onTouchEvent: topMargin==" + topMargin);
                if (mPullState == PULL_DOWN_STATE) {
                    if (topMargin >= 0) {
                        headerRefreshing();
                    } else {
                        reSetHeaderTopMargin(-mHeadViewHeight);
                    }
                } else if (mPullState == PULL_UP_STATE) {
                    if (Math.abs(topMargin) >= mHeadViewHeight
                            + mFooterViewHeight) {

                        footerRefreshing();
                    } else {

                        reSetHeaderTopMargin(-mHeadViewHeight);
                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }



    private void initHeaderViewToRefresh(int deltaY) {
        if (mBaseHeaderAdapter == null) {
            return;
        }
        int topDistance = UpdateHeadViewMarginTop(deltaY);
        if (topDistance < 0 && topDistance > -mHeadViewHeight) {
            mBaseHeaderAdapter.pullViewToRefresh(deltaY);
            mHeaderState = PULL_TO_REFRESH;
        } else if (topDistance > 0 && mHeaderState != RELEASE_TO_REFRESH) {
            mBaseHeaderAdapter.releaseViewToRefresh(deltaY);
            mHeaderState = RELEASE_TO_REFRESH;
        }

    }


    private int UpdateHeadViewMarginTop(int deltaY) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        float topMargin = params.topMargin + deltaY * 0.3f;
        params.topMargin = (int) topMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
        return params.topMargin;
    }


    public void headerRefreshing() {
        if (mBaseHeaderAdapter == null) {
            return;
        }

        mHeaderState = REFRESHING;
        setHeaderTopMargin(0);
        mBaseHeaderAdapter.headerRefreshing();
        if (mOnHeaderRefreshListener != null) {
            mOnHeaderRefreshListener.onHeaderRefresh(this);
        }
    }


    private void initFooterViewToRefresh(int deltaY) {
        if (mBaseFooterAdapter == null) {
            return;
        }

        int topDistance = UpdateHeadViewMarginTop(deltaY);

        Log.e("zzz", "the distance  is " + topDistance);


        if (Math.abs(topDistance) >= (mHeadViewHeight + mFooterViewHeight) / 4
                && mFooterState != RELEASE_TO_REFRESH) {
            mBaseFooterAdapter.pullViewToRefresh(deltaY);
            mFooterState = RELEASE_TO_REFRESH;
        } else if (Math.abs(topDistance) < (mHeadViewHeight + mFooterViewHeight) / 4) {
            mBaseFooterAdapter.releaseViewToRefresh(deltaY);
            mFooterState = PULL_TO_REFRESH;
        }
    }

    private void footerRefreshing() {
        if (mBaseFooterAdapter == null) {
            return;
        }

        mFooterState = REFRESHING;
        int top = mHeadViewHeight + mFooterViewHeight;
        setHeaderTopMargin(-top);
        mBaseFooterAdapter.footerRefreshing();
        if (mOnFooterRefreshListener != null) {
            mOnFooterRefreshListener.onFooterRefresh(this);
        }
    }

    public void onHeaderRefreshComplete() {
        if (mBaseHeaderAdapter == null) {
            return;
        }
        setHeaderTopMargin(-mHeadViewHeight);
        mBaseHeaderAdapter.headerRefreshComplete();
        mHeaderState = PULL_TO_REFRESH;
    }

    public void onFooterRefreshComplete() {
        if (mBaseFooterAdapter == null) {
            return;
        }
        setHeaderTopMargin(-mHeadViewHeight);
        mBaseFooterAdapter.footerRefreshComplete();
        mFooterState = PULL_TO_REFRESH;
    }




    private int getHeaderTopMargin() {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        return params.topMargin;
    }


    private void setHeaderTopMargin(int topMargin) {

        smoothMargin(topMargin);
    }



    private void reSetHeaderTopMargin(int topMargin) {

        if (mBaseHeaderAdapter != null) {
            mBaseHeaderAdapter.headerRefreshComplete();
        }

        if (mBaseFooterAdapter != null) {
            mBaseFooterAdapter.footerRefreshComplete();
        }

        smoothMargin(topMargin);
    }



    private void smoothMargin(int topMargin) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(params.topMargin, topMargin);
        animator.setDuration(animDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeadViewHeight);
                lp.topMargin = (int) animation.getAnimatedValue();
                mHeaderView.setLayoutParams(lp);
            }
        });
        animator.start();
    }

    public void setOnHeaderRefreshListener(OnHeaderRefreshListener onHeaderRefreshListener) {
        mOnHeaderRefreshListener = onHeaderRefreshListener;
    }

    public void setOnFooterRefreshListener(OnFooterRefreshListener onFooterRefreshListener) {
        mOnFooterRefreshListener = onFooterRefreshListener;
    }
}
