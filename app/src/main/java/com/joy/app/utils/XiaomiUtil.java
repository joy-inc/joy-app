package com.joy.app.utils;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Process;
import android.support.v4.app.NotificationCompat;

import com.joy.app.BuildConfig;
import com.joy.app.R;
import com.joy.app.activity.main.MainActivityHelperBC;
import com.joy.app.bean.PushMessageBean;
import com.joy.app.receiver.PushStartACtivityReceiver;
import com.android.library.utils.DeviceUtil;
import com.android.library.utils.LogMgr;
import com.android.library.utils.TextUtil;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushMessage;

import org.json.JSONObject;

import java.util.List;

/**
 * 小米的相关的操作类
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-19
 */
public class XiaomiUtil {


    /**
     * 注册小米推送
     *
     * @param context
     */
    public static void registerMiPush(Context context) {

        if (shouldInit(context)) {
            MiPushClient.registerPush(context, JoyConstant.XIAOMI_APPID, JoyConstant.XIAOMI_KEY);
        }
        if (BuildConfig.DEBUG) {
            LoggerInterface newLogger = new LoggerInterface() {

                @Override
                public void setTag(String tag) {
                    // ignore
                }

                @Override
                public void log(String content, Throwable t) {
                    LogMgr.d(JoyConstant.XIAOMI_MITAG, content);
                }

                @Override
                public void log(String content) {
                }
            };
            Logger.setLogger(context.getApplicationContext(), newLogger);
        }
    }

    /**
     * 关闭推送
     *
     * @param context
     */
    public static void unRegisterMiPush(Context context) {

        MiPushClient.unregisterPush(context);
    }

    /**
     * 是否已经注册了.
     *
     * @param context
     * @return
     */
    private static boolean shouldInit(Context context) {

        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 这里可能需要根据不同的版本返回不同的,因为6.0禁止获取imei
     *
     * @return
     */
    public static String getAliasName() {
        //// TODO: 15/11/19 6.0的适配
        return DeviceUtil.getIMEI();
    }


    /**
     * 添加小米推送的标签和别名
     */
    public static void registerMiPushInfo(Context context) {

        try {

            MiPushClient.subscribe(context, "all", null);
            MiPushClient.setAlias(context, XiaomiUtil.getAliasName(), null);
        } catch (Exception e) {

            if (LogMgr.isDebug())
                LogMgr.e(getTag(), "onCommandResult error: " + e.getMessage());
        }
    }

    /**
     * 解析小米推送接受的消息
     *
     * @param message
     * @return
     */
    public static PushMessageBean praseMiPushMessage(MiPushMessage message) {

        try {

            PushMessageBean pushMessage = new PushMessageBean();
            JSONObject jsonObj = new JSONObject(message.getContent());
            pushMessage.setTitle(jsonObj.getString("title"));
            pushMessage.setMessage(jsonObj.getString("message"));
            pushMessage.setType(jsonObj.getString("type"));
            pushMessage.setUri(jsonObj.getString("uri"));
            pushMessage.setMessageId(message.getMessageId());
            return pushMessage;

        } catch (Exception e) {

            if (LogMgr.isDebug())
                LogMgr.e(getTag(), "praseMiPushMessage error: " + e.getMessage());
        }

        return null;
    }

    /**
     * 发送通知到系统通知栏
     * umeng错误统计中，manager.notify的调用在有些三星的机器上会抛出无该权限： android.permission.UPDATE_APP_OPS_STATS 异常
     * 可是清单文件中已经声明该权限，所以这里tryCatch住，做个容错处理。
     *
     * @param pushMessage
     */
    public static void sendPushNotification(Context context, PushMessageBean pushMessage) {

        try {

            if (pushMessage == null)
                return;

            Notification notify = buildPushNotify(context, pushMessage);
            if (notify != null) {

                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, notify);
            }

        } catch (Exception e) {

            if (LogMgr.isDebug())
                LogMgr.e(getTag(), "sendPushNotification error: " + e.getMessage());
        }
    }

    private static Notification buildPushNotify(Context context, PushMessageBean pushMessage) {

        String uri = pushMessage.getUri();
        String messageId = pushMessage.getMessageId();
        if (LogMgr.isDebug())
            LogMgr.d(getTag(), "sendPushNotification uri = " + uri + ", msg id = " + messageId);

        if (TextUtil.isEmptyTrim(uri) || TextUtil.isEmpty(messageId))
            return null;

        if (!uri.startsWith("http://") && !uri.startsWith("qyer://"))
            return null;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent intent = getIntentByPush(context, uri, messageId);
        builder.setContentIntent(PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_tran_white_logo));
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker(pushMessage.getTitle());
        builder.setContentTitle(pushMessage.getTitle());
        builder.setContentText(pushMessage.getMessage());
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        return builder.build();
    }

    /**
     * 反馈给小米推送了点击
     *
     * @param context
     * @param msgId
     */
    public static void reportMessageClicked(Context context, String msgId) {

        msgId = TextUtil.filterNull(msgId);
        MiPushClient.reportMessageClicked(context, msgId);
    }

    /**
     * 获取推送跳转Intent
     *
     * @param context
     * @param pushUrl
     * @param messageId
     * @return
     */
    public static Intent getIntentByPush(Context context, String pushUrl, String messageId) {

        Intent intent = new Intent();
        intent.setAction(PushStartACtivityReceiver.ACTION);
        intent.putExtra(MainActivityHelperBC.EXTRA_INTEGER_INTENT_TYPE, MainActivityHelperBC.VAL_INTEGER_INTENT_TYPE_PUSH);
        intent.putExtra(MainActivityHelperBC.EXTRA_STRING_INTENT_MSG_URL, pushUrl);
        intent.putExtra(MainActivityHelperBC.EXTRA_STRING_INTENT_MSG_ID, messageId);
        return intent;
    }

    public static String getTag() {

        return JoyConstant.XIAOMI_MITAG;
    }
}
