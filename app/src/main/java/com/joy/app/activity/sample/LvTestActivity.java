package com.joy.app.activity.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.app.adapter.sample.CityLvAdapter;
import com.joy.app.bean.sample.HotCityItem;
import com.joy.app.httptask.sample.TestHtpUtil;
import com.joy.library.activity.frame.BaseHttpLvActivity;
import com.joy.library.adapter.frame.OnItemViewClickListener;
import com.joy.library.httptask.frame.ObjectRequest;

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
    protected void initTitleView() {

        setTitle("列表页");
    }

    @Override
    protected void initContentView() {

        CityLvAdapter adapter = new CityLvAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<HotCityItem>() {

            @Override
            public void onItemViewClick(int position, View clickView, HotCityItem hotCityItem) {

                DetailTestActivity.startActivity(LvTestActivity.this, clickView.findViewById(R.id.sdvPhoto), hotCityItem.getPhoto());
            }
        });
        setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        executeRefresh();
//        executeRefreshAndCache();
//        executeCache();
        executeCacheAndRefresh();
    }

    @Override
    protected ObjectRequest<List<HotCityItem>> getObjectRequest() {

        return new ObjectRequest(TestHtpUtil.getHotCityListUrl(), HotCityItem.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}