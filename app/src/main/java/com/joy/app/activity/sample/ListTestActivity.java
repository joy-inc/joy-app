package com.joy.app.activity.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.app.adapter.sample.CityAdapter;
import com.joy.app.bean.sample.HotCityItem;
import com.joy.app.httptask.sample.TestHtpUtil;
import com.joy.library.activity.frame.BaseHttpLvActivity;
import com.joy.library.httptask.frame.ObjectRequest;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/8.
 */
public class ListTestActivity extends BaseHttpLvActivity<List<HotCityItem>> {

    public static void startActivity(Activity act) {

        if (act != null)
            act.startActivity(new Intent(act, ListTestActivity.class));
    }

    @Override
    protected void initTitleView() {

//        setTitleText("列表页");
        setTitle("列表页");
    }

    @Override
    protected void initContentView() {

        setAdapter(new CityAdapter());
        setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                View v = view.findViewById(R.id.sdvPhoto);
                String url = ((CityAdapter) getAdapter()).getItem(position).getPhoto();
                DetailTestActivity.startActivity(ListTestActivity.this, v, url);
            }
        });
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}