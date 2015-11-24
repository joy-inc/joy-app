package com.joy.app.receiver;

import android.content.Context;

import com.joy.app.utils.XiaomiUtil;
import com.android.library.utils.DeviceUtil;
import com.android.library.utils.LogMgr;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

/**
 * 小米推送的接受类
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-19
 */
public class XiaomiPushMessageReceiver extends PushMessageReceiver {


    @Override
    public void onReceiveMessage(Context context, MiPushMessage message) {

        if (LogMgr.isDebug())
            LogMgr.i(XiaomiUtil.getTag(), "onReceiveMessage is called. " + message.toString());

        XiaomiUtil.sendPushNotification(context, XiaomiUtil.praseMiPushMessage(message));
    }


    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {

        if (LogMgr.isDebug())
            LogMgr.i(XiaomiUtil.getTag(), "onCommandResult is called. " + message.toString());

        if (message == null)
            return;

        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {

            if (message.getResultCode() == ErrorCode.SUCCESS) {

                XiaomiUtil.registerMiPushInfo(context);
                if (LogMgr.isDebug())
                    LogMgr.i("QaPushMessageReceiver", "rid = " + arguments.get(0));
            }
        }
    }


    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        if (LogMgr.isDebug())
            LogMgr.v(XiaomiUtil.getTag(), "onNotificationMessageClicked is called. " + message.toString());
    }

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        if (LogMgr.isDebug())
            LogMgr.v(XiaomiUtil.getTag(), "onReceivePassThroughMessage is called. " + message.toString());
    }


    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        if (LogMgr.isDebug())
            LogMgr.v(XiaomiUtil.getTag(), "onNotificationMessageArrived is called. " + message.toString());
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        if (LogMgr.isDebug())
            LogMgr.v(XiaomiUtil.getTag(), "onReceiveRegisterResult is called. " + message.toString());
    }
}
