package com.joy.app.activity.main;

import android.os.Bundle;
import android.support.annotation.StringRes;

import com.joy.app.R;
import com.joy.app.activity.main.MainActivity;
import com.joy.app.activity.sample.RvTestFragment;
import com.joy.library.activity.frame.BaseUiFragment;
import com.joy.library.httptask.frame.ObjectRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 这里是做代码分离,完成首页列表的列表展现等操作
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-09
 */
public class MainActivityBC {

    MainActivity mMainActivity;

    public MainActivityBC(MainActivity mainActivity) {

        mMainActivity = mainActivity;
    }

    //---activty的生命周期
    public void onCreate(Bundle savedInstanceState) {

    }

    public void onRestart() {
    }

    public void onPause() {
    }

    public void onDestroy() {
    }
    //---activty的生命周期
    //--activity的页面处理
    public void initData() {

    }

    public void initTitleView() {

    }

    public void initContentView() {

    }
    //--activity的页面处理




}
