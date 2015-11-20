package com.joy.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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

        }
    }
}
