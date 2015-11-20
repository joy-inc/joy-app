package com.joy.app.utils.http;

import com.joy.app.JoyApplication;
import com.joy.library.utils.DeviceUtil;

import java.util.Map;

/**
 * 订单相关网络请求
 * <p/>
 * Created by xiaoyu.chen on 15/11/11.
 */
public class OrderHtpUtil extends BaseHtpUtil {

    /**
     * 获取目的地折扣详情
     *
     * @return
     */
    public static String getPoiDiscountlUrl(String poiId) {

        Map<String, Object> params = getBaseParams();
        params.put("poi_id", poiId);
        params.put("oauth_token", JoyApplication.getUserToken());
        params.put("screensize", DeviceUtil.getScreenHeight() + "");

        return createGetUrl(URL_GET_POI_INFO, params);
    }

    /**
     * 通过用户ID获取用户订单列表
     *
     * @param page         页数	默认1
     * @param count        返回数据件数	默认5
     * @param order_status 不传时返回全部，0:待支付 1:处理中 2:已完成
     * @return
     */
    public static String getOrderListUrl(String page, String count, String order_status) {

        Map<String, Object> params = getBaseParams();
        params.put("oauth_token", JoyApplication.getUserToken());
        params.put("page", page);
        params.put("count", count);
        params.put("order_status", order_status);

        return createGetUrl(URL_GET_ORDERS, params);
    }
}
