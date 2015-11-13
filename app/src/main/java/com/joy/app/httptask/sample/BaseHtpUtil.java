package com.joy.app.httptask.sample;

import android.os.Build;

import com.joy.app.BuildConfig;
import com.joy.library.utils.AppUtil;
import com.joy.library.utils.DeviceUtil;
import com.joy.library.utils.LogMgr;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KEVIN.DAI on 15/7/10.
 */
public class BaseHtpUtil implements HtpApi {

    protected static Map<String, String> getBaseParams() {

        Map<String, String> params = new HashMap<>();
        addDefaultParams(params);
        return params;
    }

    protected static void addDefaultParams(Map<String, String> params) {

        params.put("client_id", "qyer_android");
        params.put("client_secret", "9fcaae8aefc4f9ac4915");
        params.put("v", "1");
        params.put("track_user_id", "");
        params.put("track_deviceid", DeviceUtil.getIMEI());
        params.put("track_app_version", BuildConfig.VERSION_NAME);
        params.put("track_app_channel", "");
        params.put("track_device_info", Build.DEVICE);
        params.put("track_os", "Android" + Build.VERSION.RELEASE);
        params.put("app_installtime", AppUtil.getInstallAppTime() + "");
    }

    protected static String createGetUrl(String url, Map<String, String> params) {

        if (params == null || params.size() == 0)
            return url;

        StringBuilder sb = new StringBuilder(url).append('?');

        for (Map.Entry<String, String> entry : params.entrySet()) {

            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append('&');
        }
        sb.deleteCharAt(sb.length() - 1);

        String requestUrl = sb.toString();
        if (LogMgr.isDebug())
            LogMgr.d("BaseHtpUtil", "~~get: " + requestUrl);

        return requestUrl;
    }
}