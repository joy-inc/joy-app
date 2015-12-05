package com.joy.app.activity.sample;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.joy.app.R;
import com.joy.app.adapter.sample.CityDetailRvAdapter;
import com.joy.app.bean.sample.CityDetail;
import com.joy.app.utils.http.sample.TestHtpUtil;
import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.CollectionUtil;
import com.android.library.view.observablescrollview.ObservableRecyclerView;
import com.android.library.view.observablescrollview.ObservableScrollViewCallbacks;
import com.android.library.view.observablescrollview.ScrollState;
import com.android.library.view.observablescrollview.ScrollUtils;
import com.android.library.view.systembar.SystemBarTintManager;

import java.util.ArrayList;

/**
 * Created by KEVIN.DAI on 15/7/11.
 */
public class DetailTestActivity2 extends BaseHttpRvActivity<CityDetail> implements ObservableScrollViewCallbacks {

    private int mFlexibleHeight;
    private SystemBarTintManager mTintManager;

    public static void startActivity(Activity act, String... params) {

        if (act == null || params == null || params.length < 4)
            return;

        Intent intent = new Intent(act, DetailTestActivity2.class);
        intent.putExtra("cityId", params[0]);
        intent.putExtra("photoUrl", params[1]);
        intent.putExtra("cnname", params[2]);
        intent.putExtra("enname", params[3]);
        act.startActivity(intent);
    }

    /**
     * @param act
     * @param view The view which starts the transition
     */
    public static void startActivity(Activity act, View view, String... params) {

        if (act == null || view == null || params == null || params.length < 4)
            return;

        Intent intent = new Intent(act, DetailTestActivity2.class);
        intent.putExtra("cityId", params[0]);
        intent.putExtra("photoUrl", params[1]);
        intent.putExtra("cnname", params[2]);
        intent.putExtra("enname", params[3]);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(act, view, view.getTransitionName());
            act.startActivity(intent, options.toBundle());
        } else {

            act.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        executeRefreshOnly();
    }

    @Override
    protected void initData() {

        mFlexibleHeight = 260 * DP_1_PX;

        Intent it = getIntent();
        CityDetail cityDetail = new CityDetail();
        cityDetail.setChinesename(it.getStringExtra("cnname"));
        cityDetail.setEnglishname(it.getStringExtra("enname"));
        ArrayList<String> photos = new ArrayList<>(1);
        photos.add(it.getStringExtra("photoUrl"));
        cityDetail.setPhotos(photos);

        CityDetailRvAdapter adapter = new CityDetailRvAdapter(this);
        adapter.setAttachedView(getRecyclerView());
        adapter.setData(cityDetail);
        setAdapter(adapter);
    }

    @Override
    protected void initTitleView() {

        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintColor(R.color.black_trans54);

        addTitleLeftBackView();
        getToolbarLp().topMargin = STATUS_BAR_HEIGHT;
        setTitleBgColorResId(R.color.black_trans54);
    }

    @Override
    protected void initContentView() {

        setSwipeRefreshEnable(false);// 设置下拉刷新不可用
    }

    @Override
    protected RecyclerView getDefaultRecyclerView() {

        ObservableRecyclerView orv = (ObservableRecyclerView) inflateLayout(R.layout.lib_view_recycler_observable);
        orv.setScrollViewCallbacks(this);
        return orv;
    }

    @Override
    protected ObjectRequest<CityDetail> getObjectRequest(int pageIndex, int pageLimit) {

        return ObjectRequest.get(TestHtpUtil.getCityInfoUrl(getIntent().getStringExtra("cityId")), CityDetail.class);
    }

    @Override
    protected boolean invalidateContent(CityDetail datas) {

        if (datas == null || CollectionUtil.isEmpty(datas.getNew_trip()))
            return false;

        CityDetailRvAdapter adapter = (CityDetailRvAdapter) getAdapter();
        adapter.setData(datas);
        adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        showToast(msg);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

        float flexibleRange = mFlexibleHeight - STATUS_BAR_HEIGHT;
        float fraction = ScrollUtils.getFloat(scrollY / flexibleRange, 0f, 1f);
        mTintManager.setStatusBarAlpha(fraction);
        getToolbar().getBackground().setAlpha((int) (fraction * 255));
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}