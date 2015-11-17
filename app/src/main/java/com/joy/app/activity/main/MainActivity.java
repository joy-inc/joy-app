package com.joy.app.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.library.activity.frame.BaseTabActivity;
import com.joy.library.activity.frame.BaseUiActivity;
import com.joy.library.activity.frame.BaseUiFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 专题列表
 * 推送.或则外部应用打开,在这里处理
 * 模块控制油mMainActivityBC来处理
 * 推送等其他和ui唔关的由mMainActivityHelper 处理
 * 本activity相关的ui.直接在这里进行处理
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-09
 */
public class MainActivity extends BaseTabActivity {

    private MainActivityBC mMainActivityBC;
    private MainActivityHelperBC mMainActivityHelper;
    private long mLastPressedTime; //最后一次按返回的按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mMainActivityBC = new MainActivityBC(this);
        mMainActivityHelper = new MainActivityHelperBC(this);
        mMainActivityHelper.onCreate(savedInstanceState);
        mMainActivityBC.onCreate(savedInstanceState);

    }

    @Override
    protected void onRestart() {

        super.onRestart();
        mMainActivityHelper.onRestart();
        mMainActivityBC.onRestart();
    }

    @Override
    protected void onPause() {

        super.onPause();
        mMainActivityHelper.onPause();
        mMainActivityBC.onPause();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        mMainActivityHelper.onDestroy();
        mMainActivityBC.onDestroy();

    }

    @Override

    protected void initData() {

        mMainActivityBC.initData();
    }

    @Override
    protected void initTitleView() {

        setTitleTextColor(getResources().getColor(R.color.color_accent));
        mMainActivityBC.initTitleView();
    }

    @Override
    protected void initContentView() {

        setTabIndicatorHeight(DP_1_PX * 3);
        mMainActivityBC.initContentView();
    }

    @Override
    protected List<? extends BaseUiFragment> getFragments() {

        List<BaseUiFragment> fragments = new ArrayList<>(3);
        fragments.add(MainFragment.instantiate(this).setLableText(R.string.route));
        fragments.add(TravelPlanFragment.instantiate(this).setLableText(R.string.travel_plan));
        fragments.add(OrderFragment.instantiate(this).setLableText(R.string.order));
        return fragments;
    }

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


    public static void startActivity(Context context) {

        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }
}
