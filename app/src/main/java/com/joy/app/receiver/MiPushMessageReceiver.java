package com.joy.app.receiver;

import android.content.Context;
import android.content.Intent;

import com.android.library.utils.LogMgr;
import com.joy.app.activity.main.MainActivity;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;


/**
 * 3、QyerPushMessageReceiver 的onReceivePassThroughMessage方法用来接收服务器向客户端发送的透传消息
 * 4、QyerPushMessageReceiver 的onNotificationMessageClicked方法用来接收服务器向客户端发送的通知消息，这个回调方法会在用户手动点击通知后触发
 * 5、QyerPushMessageReceiver 的onNotificationMessageArrived方法用来接收服务器向客户端发送的通知消息，这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数
 * 6、QyerPushMessageReceiver 的onCommandResult方法用来接收客户端向服务器发送命令后的响应结果
 * 7、QyerPushMessageReceiver 的onReceiveRegisterResult方法用来接收客户端向服务器发送注册命令后的响应结果
 * 8、以上这些方法运行在非UI线程中
 */
public class MiPushMessageReceiver extends PushMessageReceiver {

    public static final String EXTRA_INTENT_STRING_CONTENT = "intent_content";
    public static final String EXTRA_INTENT_STRING_ID = "intent_messageid";
    public static final String EXTRA_INTENT_STRING_TITLE = "intent_title";
    public static final String EXTRA_INTENT_STRING_DESC = "intent_desc";

    /**
     * onNotificationMessageArrived方法用来接收服务器向客户端发送的通知消息，这个回调方法是在通知消息到达客户端时触发。
     * 另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数
     */
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        super.onNotificationMessageArrived(context, message);

    }

    /**
     * onNotificationMessageClicked方法用来接收服务器向客户端发送的通知消息，这个回调方法会在用户手动点击通知后触发
     */
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        super.onNotificationMessageClicked(context, message);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_INTENT_STRING_CONTENT, message.getContent());
        intent.putExtra(EXTRA_INTENT_STRING_ID, message.getMessageId());
        intent.putExtra(EXTRA_INTENT_STRING_TITLE, message.getTitle());
        intent.putExtra(EXTRA_INTENT_STRING_DESC, message.getDescription());

        intent.setClass(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * onReceiveRegisterResult方法用来接收客户端向服务器发送注册命令后的响应结果
     */
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onReceiveRegisterResult(context, miPushCommandMessage);

    }


    /**
     * onCommandResult方法用来接收客户端向服务器发送命令后的响应结果
     */
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {

        if (LogMgr.isDebug())
            LogMgr.i("  onCommandResult()  :     " + message.toString());
    }
}
