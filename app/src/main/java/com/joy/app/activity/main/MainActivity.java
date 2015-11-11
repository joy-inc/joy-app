package com.joy.app.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.joy.app.bc.main.MainActivityBC;
import com.joy.app.bc.main.MainActivityHelperBC;
import com.joy.library.activity.frame.BaseUiActivity;
import com.joy.library.httptask.frame.ObjectRequest;

/**
 * 专题列表
 * 推送.或则外部应用打开,在这里处理
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-09
 */
public class MainActivity extends BaseUiActivity {

    private MainActivityBC mMainActivityBC;
    private MainActivityHelperBC mMainActivityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mMainActivityBC = new MainActivityBC(this);
        mMainActivityHelper = new MainActivityHelperBC(this);
        mMainActivityHelper.onCreate(savedInstanceState);
    }

    @Override
    protected void onRestart() {

        super.onRestart();
        mMainActivityHelper.onRestart();
    }

    @Override
    protected void onPause() {

        super.onPause();
        mMainActivityHelper.onPause();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        mMainActivityHelper.onDestroy();

    }

    @Override

    protected void initData() {

        mMainActivityBC.initData();
    }

    @Override
    protected void initTitleView() {

        mMainActivityBC.initTitleView();
    }

    @Override
    protected void initContentView() {

        mMainActivityBC.initContentView();
    }



    public static void startActivity(Context context) {

        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }
}
