package com.joy.app.utils.http.sample;

import com.joy.app.utils.http.BaseHtpUtil;

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
        params.put(KEY_COUNT, "200");// 默认200个
        return createUrl(URL_GET_SEARCH_HOT_CITY, params);
    }

    /**
     * 城市详情
     *
     * @param cityId
     * @return
     */
    public static String getCityInfoUrl(String cityId) {

        Map<String, String> params = getBaseParams();
        params.put("city_id", cityId);
        return createUrl(URL_GET_CITY_INFO, params);
    }

    /**
     * 获取专题列表
     *
     * @param pageIndex
     * @param pageLimit
     * @return
     */
    public static String getSpecialListUrl(int pageIndex, int pageLimit) {

        Map<String, String> params = getBaseParams();
        params.put(KEY_PAGE, pageIndex+"");
        params.put(KEY_COUNT, pageLimit+"");
        return createUrl(URL_GET_SPECIAL_LIST, params);
    }
}