package com.joy.app.activity.hotel.frame;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;

import com.android.library.R;
import com.android.library.activity.BaseHttpUiFragment;
import com.android.library.adapter.ExRvAdapter;
import com.android.library.httptask.CacheMode;
import com.android.library.httptask.ObjectRequest;
import com.android.library.listener.OnLoadMoreListener;
import com.android.library.utils.CollectionUtil;
import com.android.library.view.recyclerview.RecyclerAdapter;
import com.android.library.view.recyclerview.RecyclerAdapter.OnItemClickListener;
import com.android.library.view.recyclerview.RecyclerAdapter.OnItemLongClickListener;
import com.android.library.widget.JLoadingView;
import com.android.library.widget.JRecyclerView;

import java.util.List;

/**
 * @author litong  <br>
 * @Description BaseHttpRvFragment 用不了所以单开一个    <br>
 */
public abstract class FrameHttpRvFragment<T> extends BaseHttpUiFragment<T> {

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private int mPageLimit = 20;
    private static final int PAGE_START_INDEX = 1;// 默认从第一页开始
    private int mPageIndex = PAGE_START_INDEX;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mRecyclerView = getDefaultRecyclerView();
        mRecyclerView.setLayoutManager(getDefaultLayoutManager());
        setContentView(wrapSwipeRefresh(mRecyclerView));
    }

    protected RecyclerView getDefaultRecyclerView() {

        JRecyclerView jrv = (JRecyclerView) inflateLayout(R.layout.lib_view_recycler);
        jrv.setLoadMoreView(JLoadingView.getLoadMore(getActivity()), JLoadingView.getLoadMoreLp());
        jrv.setLoadMoreListener(getDefaultLoadMoreLisn());
        return jrv;
    }

    /**
     * 子类可以复写此方法，为自己定制LayoutManager，默认为LinearLayoutManager
     * LinearLayoutManager (线性显示，类似于ListView)
     * GridLayoutManager (线性宫格显示，类似于GridView)
     * StaggeredGridLayoutManager(线性宫格显示，类似于瀑布流)
     *
     * @return
     */
    protected LayoutManager getDefaultLayoutManager() {

        return new LinearLayoutManager(getActivity());
    }

    private View wrapSwipeRefresh(View contentView) {

        mSwipeRefreshWidget = new SwipeRefreshLayout(getActivity());
//        mSwipeRefreshWidget.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light, R.color.holo_orange_light, R.color.holo_red_light);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.color_accent);
        mSwipeRefreshWidget.setOnRefreshListener(getDefaultRefreshLisn());
        mSwipeRefreshWidget.addView(contentView);
        return mSwipeRefreshWidget;
    }

    private OnRefreshListener getDefaultRefreshLisn() {

        return new OnRefreshListener() {

            @Override
            public void onRefresh() {

                if (isNetworkDisable()) {

                    hideSwipeRefresh();
                    showToast(R.string.toast_common_no_network);
                } else {

                    executeRefresh();
                }
            }
        };
    }

    private OnLoadMoreListener getDefaultLoadMoreLisn() {

        return new OnLoadMoreListener() {

            @Override
            public void onRefresh(boolean isAuto) {

                if (isNetworkDisable()) {

                    onLoadMoreFailed();
                    if (!isAuto)
                        showToast(R.string.toast_common_no_network);
                } else {

                    executeRefreshOnly();
                }
            }
        };
    }

    public void resetPageIndex() {
        mPageIndex = PAGE_START_INDEX;
    }

    protected void executeRefresh() {
        resetPageIndex();
        executeRefreshOnly();
    }

    public void clearData(){
        resetPageIndex();
        if (getAdapter() == null)return;
        getAdapter().clear();
        getAdapter().notifyDataSetChanged();
    }


    @Override
    protected final ObjectRequest<T> getObjectRequest() {

        return getObjectRequest(mPageIndex, mPageLimit);
    }

    protected abstract ObjectRequest<T> getObjectRequest(int pageIndex, int pageLimit);

    protected void executeSwipeRefresh() {

        showSwipeRefresh();
        mPageIndex = PAGE_START_INDEX;
        onRetryCallback();
    }

    /**
     * 设置分页大小
     *
     * @param pageLimit
     */
    protected void setPageLimit(int pageLimit) {

        mPageLimit = pageLimit;
    }

    protected RecyclerView getRecyclerView() {

        return mRecyclerView;
    }

    protected void addHeaderView(View v) {

        ((RecyclerAdapter) mRecyclerView.getAdapter()).addHeaderView(v);
    }

    protected void addFooterView(View v) {

        ((RecyclerAdapter) mRecyclerView.getAdapter()).addFooterView(v);
    }

    protected void setAdapter(ExRvAdapter adapter) {

        RecyclerAdapter ra = new RecyclerAdapter(adapter, getDefaultLayoutManager());
        getRecyclerView().setAdapter(ra);
    }

    protected ExRvAdapter getAdapter() {
        if (mRecyclerView == null) return null;
        Adapter adapter = mRecyclerView.getAdapter();
        if (adapter instanceof RecyclerAdapter)
            return (ExRvAdapter) ((RecyclerAdapter) adapter).getWrappedAdapter();
        else
            return (ExRvAdapter) adapter;
    }

    @Override
    protected boolean invalidateContent(T t) {

        List<?> datas = getListInvalidateContent(t);
        if (CollectionUtil.isEmpty(datas)) {

            clearData();
            return false;

        }
        if (mRecyclerView instanceof JRecyclerView && isLoadMoreEnable()) {

            JRecyclerView jrv = (JRecyclerView) mRecyclerView;
            jrv.setLoadMoreEnable(datas.size() >= mPageLimit);
            if (jrv.isLoadMoreEnable())
                jrv.stopLoadMore();
        }

        ExRvAdapter adapter = getAdapter();
        if (adapter != null) {

            if (mPageIndex == PAGE_START_INDEX)
                if (adapter.getData() == null) {
                    adapter.setData(datas);
                } else {
                    adapter.clear();
                    adapter.addAll(datas);
                }
            else
                adapter.addAll(datas);

            adapter.notifyDataSetChanged();

            if (!isRespIntermediate())
                mPageIndex++;
        }
        return true;
    }

    protected List<?> getListInvalidateContent(T t) {

        return (List<?>) t;
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        if (isRespIntermediate())
            mPageIndex++;

        onLoadMoreFailed();
    }

    @Override
    protected void showLoading() {

        if (getReqCacheMode() == CacheMode.CACHE_AND_REFRESH && isReqHasCache())
            showSwipeRefresh();
        else if (!isSwipeRefreshing() && !isLoadingMore())
            super.showLoading();
    }

    @Override
    protected void hideLoading() {

        if (isSwipeRefreshing())
            hideSwipeRefresh();
        else if (!isLoadingMore())
            super.hideLoading();
    }

    @Override
    protected void showFailedTip() {

        if (!isSwipeRefreshing() && !isLoadingMore() && getAdapter().isEmpty())
            super.showFailedTip();
    }

    @Override
    protected void hideContentView() {

        if (getAdapter() != null && getAdapter().isEmpty())
            super.showContentView();
    }

    protected void setSwipeRefreshEnable(boolean enable) {

        mSwipeRefreshWidget.setEnabled(enable);
    }

    protected void setColorSchemeResources(int... colorResIds) {

        mSwipeRefreshWidget.setColorSchemeResources(colorResIds);
    }

    protected void setOnRefreshListener(OnRefreshListener lisn) {

        mSwipeRefreshWidget.setOnRefreshListener(lisn);
    }

    protected boolean isSwipeRefreshing() {

        if (mSwipeRefreshWidget == null) return false;
        return mSwipeRefreshWidget.isRefreshing();
    }

    protected void showSwipeRefresh() {

        if (isSwipeRefreshing())
            return;

        mSwipeRefreshWidget.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshWidget.setRefreshing(true);
            }
        });
    }

    protected void hideSwipeRefresh() {

        if (!isSwipeRefreshing())
            return;

        mSwipeRefreshWidget.setRefreshing(false);
    }

    protected final boolean isLoadingMore() {

        if (mRecyclerView instanceof JRecyclerView)
            return isLoadMoreEnable() && ((JRecyclerView) mRecyclerView).isLoadingMore();
        return false;
    }

    protected final void onLoadMoreFailed() {

        if (mRecyclerView instanceof JRecyclerView && isLoadMoreEnable())
            ((JRecyclerView) mRecyclerView).stopLoadMoreFailed();
    }

    protected void setLoadMoreEnable(boolean enable) {

        if (mRecyclerView instanceof JRecyclerView)
            ((JRecyclerView) mRecyclerView).setLoadMoreEnable(enable);
    }

    private boolean isLoadMoreEnable() {

        return ((JRecyclerView) mRecyclerView).isLoadMoreEnable();
    }
}