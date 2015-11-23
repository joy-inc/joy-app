package com.joy.app.activity.main;

import android.content.Intent;
import android.os.Bundle;

import com.joy.app.activity.main.MainActivity;
import com.joy.library.utils.TextUtil;
import com.joy.library.utils.ToastUtil;
import com.umeng.update.UmengUpdateAgent;

/**
 * 用于处理mainactivity的除了列表外的事件处理
 * 包括生命周期的各个相关处理
 * 以后的推送 外部应用打开处理
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-10
 */
public class MainActivityHelperBC {

    public static final String EXTRA_INTEGER_INTENT_TYPE = "intent_type";
    public static final int VAL_INTEGER_INTENT_TYPE_NONE = 0;
    public static final int VAL_INTEGER_INTENT_TYPE_PUSH = 1;
    public static final int VAL_INTEGER_INTENT_TYPE_CHAT_SESSION = 2;
    public static final int VAL_INTEGER_INTENT_TYPE_CHAT_ROOM = 3;
    public static final int VAL_INTEGER_INTENT_TYPE_DISCUSS = 4;

    public static final String EXTRA_STRING_INTENT_MSG_URL = "intent_url";
    public static final String EXTRA_STRING_INTENT_MSG_ID = "intent_id";

    MainActivity mMainActivity;

    public MainActivityHelperBC(MainActivity mainActivity) {

        mMainActivity = mainActivity;
    }

    //--生命周期
    public void onCreate(Bundle savedInstanceState) {

        //处理推送
        delayStartActivityByPushIntent(mMainActivity.getIntent());
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(mMainActivity);

    }

    public void onNewIntent(Intent intent) {

        delayStartActivityByPushIntent(intent);
    }

    /**
     * 统一处理广播或者饿推送的事件
     *
     * @param intent
     */
    private void delayStartActivityByPushIntent(Intent intent) {

        String id = intent.getStringExtra(EXTRA_STRING_INTENT_MSG_ID);
        String url = intent.getStringExtra(EXTRA_STRING_INTENT_MSG_URL);
        if(!TextUtil.isEmpty(url)) {
            ToastUtil.showToast("push message id=" + id + " url= " + url);
        }
    }

    public void onRestart() {
    }

    public void onPause() {
    }

    public void onDestroy() {
        mMainActivity = null;
    }

    //--生命周期
}
