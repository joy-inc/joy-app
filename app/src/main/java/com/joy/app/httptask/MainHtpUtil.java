package com.joy.app.httptask;

import com.joy.app.httptask.sample.BaseHtpUtil;

import java.util.Map;

/**
 * 首页的一些网络请求
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-16
 */
public class MainHtpUtil  extends BaseHtpUtil {

    /**
     * 获取首页的目标列表
     * @return
     */
    public static String getMainRouteList(String token,int page,int index) {

        Map<String, Object> params = getBaseParams();
        params.put("token", token);
        params.put("page", page);
        params.put("index", index);
        return createGetUrl(URL_GET_CITY_INFO, params);
    }
}
