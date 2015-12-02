package com.joy.app.activity.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.library.activity.BaseHttpLvActivity;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.app.adapter.sample.CityLvAdapter;
import com.joy.app.bean.sample.HotCityItem;
import com.joy.app.utils.http.sample.TestHtpUtil;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/8.
 */
public class LvTestActivity extends BaseHttpLvActivity<List<HotCityItem>> {

    public static void startActivity(Activity act) {

        if (act != null)
            act.startActivity(new Intent(act, LvTestActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        executeCacheAndRefresh();
    }

    @Override
    protected void initTitleView() {

        setTitle("列表页");
    }

    @Override
    protected void initContentView() {

        CityLvAdapter adapter = new CityLvAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<HotCityItem>() {

            @Override
            public void onItemViewClick(int position, View clickView, HotCityItem hotCityItem) {

                DetailTestActivity.startActivity(LvTestActivity.this, clickView.findViewById(R.id.sdvPhoto), hotCityItem.getId(), hotCityItem.getPhoto());
            }
        });
        setAdapter(adapter);
    }

    @Override
    protected ObjectRequest<List<HotCityItem>> getObjectRequest(int pageIndex, int pageLimit) {

        return ObjectRequest.get(TestHtpUtil.getHotCityListUrl(), HotCityItem.class);
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        showToast("error: " + msg);
    }

    private long mLastPressedTime;

    @Override
    public void onBackPressed() {

        long currentPressedTime = System.currentTimeMillis();
        if (currentPressedTime - mLastPressedTime > 2000) {

            mLastPressedTime = currentPressedTime;
            showToast(R.string.toast_exit_tip);
        } else {

            super.onBackPressed();
            JoyApplication.releaseForExitApp();
        }
    }
}