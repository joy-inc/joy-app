package com.joy.app.httptask.sample;

/**
 * 网络请求 api
 */
public interface HtpApi {

    String URL_BASE = "http://open.qyer.com";// base url

    String URL_GET_SEARCH_HOT_CITY = URL_BASE + "/qyer/hotel/hot_city_list";// 200热门城市
    String URL_GET_CITY_INFO = URL_BASE + "/qyer/footprint/city_detail";// 获取城市详情

    // poi折扣====================================
    String URL_GET_POI_INFO = URL_BASE + "/qyer/footprint/poi_detail";// 获取poi详情 //---临时
    String URL_GET_POI_DISCOUNT = URL_BASE + "/poi/discount_detail";// 获取poi折扣详情接口
    String URL_GET_ORDERS = URL_BASE + "/order/get_order_list";// 获取订单列表
}
