package com.joy.app.httptask.sample;

import java.util.Map;

/**
 * Created by KEVIN.DAI on 15/7/10.
 */
public class TestHtpUtil extends BaseHtpUtil {

    /**
     * 200个热门城市列表
     *
     * @return
     */
    public static String getHotCityListUrl() {

        Map<String, String> params = getBaseParams();
        params.put("count", "200");// 默认200个
        return createGetUrl(URL_GET_SEARCH_HOT_CITY, params);
    }

    /**
     * 城市详情
     *
     * @return
     */
    public static String getCityInfoUrl(String cityId) {

        Map<String, String> params = getBaseParams();
        params.put("city_id", cityId);
        return createGetUrl(URL_GET_CITY_INFO, params);
    }
}