package com.joy.app.utils;

import android.net.Uri;

import com.joy.app.utils.http.HtpApi;

/**
 * 处理url相关的, 处理url分析,提取,组合
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-10
 */
public class UrlUtil {

    /**
     * 获取WebView CookieUrl 的链接
     *
     * @param token
     * @return
     */
    public static String getWebViewCookieUrl(String token) {

        return "";// HtpApi.URL_LOGIN_COOKIE + "?client_id=" + HtpApi.CLIENT_ID + "&client_secret=" + HtpApi.CLIENT_SECRET + "&oauth_token=" + token;
        //// TODO: 15/11/10 把url组合成
    }

    /**
     * 获取url对应的key数值
     *
     * @param url
     * @param key
     * @return
     */
    public static String getQueryParameter(String url, String key) {

        try {
            Uri uri = Uri.parse(url);
            String value = uri.getQueryParameter(key);
            return value;
        } catch (Exception ex) {
            return "";
        }
    }


    /**
     * 获取url最后的参数值
     *
     * @param url
     * @return
     */
    public static String getLastParameter(String url) {

        int index = url.lastIndexOf("/");
        if (index > 0) {
            return url.substring(index + 1).trim();
        }
        return "";
    }


}
