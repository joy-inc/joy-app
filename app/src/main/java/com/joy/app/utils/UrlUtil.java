package com.joy.app.utils;

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

        return "";//HtpApi.URL_LOGIN_COOKIE + "?client_id=" + HtpApi.CLIENT_ID + "&client_secret=" + HtpApi.CLIENT_SECRET + "&oauth_token=" + token;
        //// TODO: 15/11/10 把url组合成
    }
}
