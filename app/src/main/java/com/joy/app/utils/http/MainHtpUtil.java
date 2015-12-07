package com.joy.app.utils.http;

import java.util.Map;

/**
 * 首页的一些网络请求
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-16
 */
public class MainHtpUtil extends BaseHtpUtil {

    /**
     * 获取首页的目标列表
     *
     * @return
     */
    public static Map<String, String> getMainRouteList(int page, int count) {

        Map<String, String> params = getBaseParams();
        params.put(KEY_PAGE, page+"");
        params.put(KEY_COUNT, count+"");
        return params;
    }
}