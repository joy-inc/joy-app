package com.joy.app.utils.http;

import com.joy.app.JoyApplication;

import java.util.Map;

/**
 * 商品及订单相关网络请求
 * <p/>
 * Created by xiaoyu.chen on 15/11/11.
 */
public class OrderHtpUtil extends BaseHtpUtil {

    /**
     * 获取商品详情
     *
     * @return
     */
    public static String getOrderDetailUrl(String order_id) {

        Map<String, String> params = getBaseParams();
        params.put("order_id", order_id);
        params.put("user_token", JoyApplication.getUserToken());
        return createUrl(URL_POST_ORDER_DETAIL, params);
    }
    /**
     * 删除订单
     *
     * @return
     */
    public static String getCancelOrderUrl(String order_id) {

        Map<String, String> params = getBaseParams();
        params.put("order_id", order_id);
        params.put("user_token", JoyApplication.getUserToken());
        return createUrl(URL_POST_ORDER_CANCEL, params);
    }

    /**
     * 获取商品详情
     *
     * @return
     */
    public static String getProductDetailUrl(String product_id) {

        Map<String, String> params = getBaseParams();
        params.put("product_id", product_id);
        return createUrl(URL_POST_PRODUCT_DETAIL, params);
    }

    /**
     * 获取商品评论列表
     *
     * @return
     */
    public static String getProductCommentListUrl(String product_id, int count, int page) {

        Map<String, String> params = getBaseParams();
        params.put("product_id", product_id);
        params.put(KEY_COUNT, count+"");
        params.put(KEY_PAGE, page+"");
        return createUrl(URL_POST_COMMENTS, params);
    }

    /**
     * 获取商品项目列表
     *
     * @return
     */
    public static String getProductOptionListUrl(String product_id) {

        Map<String, String> params = getBaseParams();
        params.put("product_id", product_id);
        return createUrl(URL_POST_OPTIONS, params);
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

        Map<String, String> params = getBaseParams();
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());
        params.put(KEY_PAGE, page);
        params.put(KEY_COUNT, count);
        params.put("order_status", order_status);
        return createUrl(URL_POST_ORDERS, params);
    }
}
