package com.joy.app.utils.http;

import android.util.Base64;

import com.android.library.arithmetic.HmacUtils;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.ParamsUtil;
import com.android.library.utils.SortComparator;
import com.joy.app.JoyApplication;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by KEVIN.DAI on 15/11/27.
 */
public class ReqFactory {

    private static final String PRIVATE_KEY = "AD7A1775466A6A137E9F2322A330598AE51A922AB9308919AA8CA0B3154F9EDD";
    private static final String KEY_API_AUTH = "api-auth";
    private static final String KEY_USER_TOKEN = "user-token";

    public static ObjectRequest newGet(String fullUrl, Class clazz) {

        return ObjectRequest.get(fullUrl, clazz);
    }

    public static ObjectRequest newPost(String baseUrl, Class clazz, Map<String, String> params) {

        String data = Base64.encodeToString(ParamsUtil.createUrl(params).getBytes(), Base64.NO_WRAP);
        String auth = HmacUtils.SHA256(data, PRIVATE_KEY);
        Map<String, String> headers = new TreeMap<>(new SortComparator());
        headers.put(KEY_API_AUTH, auth);
        headers.put(KEY_USER_TOKEN, JoyApplication.getUserToken());

        ObjectRequest req = ObjectRequest.post(baseUrl, clazz);
        req.setHeaders(headers);
        req.setParams(params);
        return req;
    }
}