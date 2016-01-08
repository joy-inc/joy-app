package com.joy.app.utils.http;

import android.os.Build;

import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.AppUtil;
import com.android.library.utils.DeviceUtil;
import com.android.library.utils.LogMgr;
import com.android.library.utils.ParamsUtil;
import com.android.library.utils.SortComparator;
import com.joy.app.BuildConfig;
import com.joy.app.JoyApplication;
import com.joy.app.utils.ChannelUtil;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by KEVIN.DAI on 15/7/10.
 */
public class BaseHtpUtil implements HtpApi {

    protected static final String KEY_PAGE = "page";
    protected static final String KEY_COUNT = "count";
    protected static final String KEY_USER_TOKEN = "user_token";

    protected static Map<String, String> getBaseParams() {

        Map<String, String> params = new TreeMap<>(new SortComparator());
        addDefaultParams(params);
        return params;
    }

    private static void addDefaultParams(Map<String, String> params) {

        params.put("track_client_id", "android");
        params.put("track_device_id", DeviceUtil.getIMEI());
        params.put("track_app_version", BuildConfig.VERSION_NAME);
        params.put("track_app_channel", ChannelUtil.getChannel());
        params.put("track_device_info", Build.DEVICE);
        params.put("track_os", "Android" + Build.VERSION.RELEASE);
        params.put("app_installtime", AppUtil.getInstallTime() + "");
        params.put("lat", JoyApplication.getLocationLatitude());// 纬度
        params.put("lon", JoyApplication.getLocationLongitude());// 经度
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());

        //-----------------------------------------------------------------------
        // TODO joy接口ok就干掉
        params.put("client_id", "qyer_android");
        params.put("client_secret", "9fcaae8aefc4f9ac4915");
        //-----------------------------------------------------------------------
    }

    protected static String createUrl(String url, Map<String, String> params) {

        String requestUrl = new StringBuilder(url).append('?').append(ParamsUtil.createUrl(params)).toString();

        if (LogMgr.isDebug())
            LogMgr.d("BaseHtpUtil", "~~get: " + requestUrl);

        return requestUrl;
    }

    protected  static ObjectRequest createPostRequest(String url, Map<String, String> params,Class calss){

       return ReqFactory.newPost(url, calss,params);
    }
    protected  static ObjectRequest createGetRequest(String url, Map<String, String> params,Class calss){

       return ReqFactory.newGet(createUrl(url,params), calss);
    }
}