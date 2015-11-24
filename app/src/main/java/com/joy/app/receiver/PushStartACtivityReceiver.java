package com.joy.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.joy.app.activity.main.MainActivity;
import com.android.library.utils.ToastUtil;

/**
 * 下拉点击打开的事件回掉
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-19
 */
public class PushStartACtivityReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.joy.app.receiver.PushStartACtivityReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null && ACTION.equalsIgnoreCase(intent.getAction())) {
            intent.setClass(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
