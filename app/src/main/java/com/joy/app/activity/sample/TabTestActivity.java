package com.joy.app.activity.sample;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.library.activity.frame.BaseTabActivity;
import com.joy.library.activity.frame.BaseUiFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KEVIN.DAI on 15/11/10.
 */
public class TabTestActivity extends BaseTabActivity {

    public static void startActivity(Activity act) {

        if (act != null)
            act.startActivity(new Intent(act, TabTestActivity.class));
    }

    @Override
    protected void initTitleView() {

        super.initTitleView();
        setTitleTextColor(getResources().getColor(R.color.color_accent));
    }

    @Override
    protected void initContentView() {

        super.initContentView();
        setTabIndicatorHeight(DP_1_PX * 3);
    }

    @Override
    protected List<? extends BaseUiFragment> getFragments() {

        List<BaseUiFragment> fragments = new ArrayList<>();
        fragments.add(RvTestFragment.instantiate(this).setLableText("目的地"));
        fragments.add(RvTestFragment.instantiate(this).setLableText("旅行规划"));
        fragments.add(RvTestFragment.instantiate(this).setLableText("订单"));
        return fragments;
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

        return super.onOptionsItemSelected(item);
    }
}