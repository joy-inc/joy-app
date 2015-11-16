package com.joy.app.activity.sample;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.joy.app.adapter.sample.CityDetailRvAdapter;
import com.joy.app.bean.sample.CityDetail;
import com.joy.app.httptask.sample.TestHtpUtil;
import com.joy.library.activity.frame.BaseHttpRvActivity;
import com.joy.library.httptask.frame.ObjectRequest;

import java.util.ArrayList;

/**
 * Created by KEVIN.DAI on 15/7/11.
 */
public class DetailTestActivity extends BaseHttpRvActivity<CityDetail> {

    public static void startActivity(Activity act, String... params) {

        if (act == null || params == null || params.length < 4)
            return;

        Intent intent = new Intent(act, DetailTestActivity.class);
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
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startActivity(Activity act, View view, String... params) {

        if (act == null || view == null || params == null || params.length < 4)
            return;

        Intent intent = new Intent(act, DetailTestActivity.class);
        intent.putExtra("cityId", params[0]);
        intent.putExtra("photoUrl", params[1]);
        intent.putExtra("cnname", params[2]);
        intent.putExtra("enname", params[3]);

        if (isLollipopOrUpper()) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(act, view, view.getTransitionName());
            act.startActivity(intent, options.toBundle());
        } else {

            act.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        executeRefresh();
    }

    @Override
    protected void initTitleView() {

        setTitle(null);
        addTitleLeftBackView();
        getToolbarLp().topMargin = STATUS_BAR_HEIGHT;
    }

    @Override
    protected void initContentView() {

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
    protected ObjectRequest<CityDetail> getObjectRequest() {

        return new ObjectRequest(TestHtpUtil.getCityInfoUrl(getIntent().getStringExtra("cityId")), CityDetail.class);
    }

    @Override
    protected void invalidateContent(CityDetail datas) {

        CityDetailRvAdapter adapter = ((CityDetailRvAdapter) getAdapter());
        adapter.setData(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        showToast(msg);
    }
}