package com.joy.app.bc.main;

import android.os.Bundle;

import com.joy.app.activity.main.MainActivity;

/**
 * 用于处理mainactivity的除了列表外的事件处理
 * 包括生命周期的各个相关处理
 * 以后的推送 外部应用打开处理
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-10
 */
public class MainActivityHelperBC {

    MainActivity mMainActivity;

    public MainActivityHelperBC(MainActivity mainActivity) {

        mMainActivity = mainActivity;
    }

    public void onCreate(Bundle savedInstanceState) {

    }

    public void onRestart() {
    }

    public void onPause() {
    }

    public void onDestroy() {
    }
}
