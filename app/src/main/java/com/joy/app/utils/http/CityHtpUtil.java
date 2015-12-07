package com.joy.app.utils.http;

import java.util.Map;

/**
 * Created by KEVIN.DAI on 15/12/5.
 */
public class CityHtpUtil extends BaseHtpUtil {

    public static Map<String, String> getCityParams(String placeId) {

        Map<String, String> params = getBaseParams();
        params.put("place_id", placeId);
        return params;
    }

    /**
     * @param placeId 城市id
     * @param type    类型: 1游玩 2住宿 3美食 4购物
     * @param count   请求数量
     * @param page    页码
     * @return
     */
    public static Map<String, String> getCityFunParams(String placeId, int type, int count, int page) {

        Map<String, String> params = getBaseParams();
        params.put("place_id", placeId);
        params.put("type", type + "");
        params.put(KEY_COUNT, count + "");
        params.put(KEY_PAGE, page + "");
        return params;
    }
}