package com.joy.app.utils.http;

import android.os.Build;

import com.android.library.utils.AppUtil;
import com.android.library.utils.DeviceUtil;
import com.android.library.utils.LogMgr;
import com.joy.app.BuildConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KEVIN.DAI on 15/7/10.
 */
public class BaseHtpUtil implements HtpApi {

    protected static final String KEY_PAGE = "page";
    protected static final String KEY_COUNT = "count";
    protected static final String KEY_USER_TOKEN = "user_token";

    protected static Map<String, Object> getBaseParams() {

        Map<String, Object> params = new HashMap<>();
        addDefaultParams(params);
        return params;
    }

    protected static void addDefaultParams(Map<String, Object> params) {

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

    protected static String createGetUrl(String url, Map<String, Object> params) {

        if (params == null || params.size() == 0)
            return url;

        StringBuilder sb = new StringBuilder(url).append('?');

        for (Map.Entry<String, Object> entry : params.entrySet()) {

            sb.append(entry.getKey());
            sb.append('=');
            sb.append(String.valueOf(entry.getValue()));
            sb.append('&');
        }
        sb.deleteCharAt(sb.length() - 1);

        String requestUrl = sb.toString();
        if (LogMgr.isDebug())
            LogMgr.d("BaseHtpUtil", "~~get: " + requestUrl);

        return requestUrl;
    }
}