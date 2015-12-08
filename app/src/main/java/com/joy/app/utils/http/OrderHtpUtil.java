package com.joy.app.utils.http;

import com.joy.app.JoyApplication;
import com.joy.app.bean.poi.OrderContacts;

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
    public static Map<String, String> getOrderDetailUrl(String order_id) {

        Map<String, String> params = getBaseParams();
        params.put("order_id", order_id);
        params.put("user_token", JoyApplication.getUserToken());
        return params;
    }

    /**
     * 删除订单
     *
     * @return
     */
    public static Map<String, String> getCancelOrderUrl(String order_id) {

        Map<String, String> params = getBaseParams();
        params.put("order_id", order_id);
        params.put("user_token", JoyApplication.getUserToken());
        return params;
    }

    /**
     * 获取商品详情
     *
     * @return
     */
    public static Map<String, String> getProductDetailUrl(String product_id) {

        Map<String, String> params = getBaseParams();
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());
        params.put("product_id", product_id);
        return params;
    }

    /**
     * 获取商品评论列表
     *
     * @return
     */
    public static Map<String, String> getProductCommentListUrl(String product_id, int count, int page) {

        Map<String, String> params = getBaseParams();
        params.put("product_id", product_id);
        params.put(KEY_COUNT, count + "");
        params.put(KEY_PAGE, page + "");
        return params;
    }

    /**
     * 获取商品项目列表
     *
     * @return
     */
    public static Map<String, String> getProductOptionListUrl(String product_id) {

        Map<String, String> params = getBaseParams();
        params.put("product_id", product_id);
        return params;
    }

    /**
     * 通过用户ID获取用户订单列表
     *
     * @param page         页数	默认1
     * @param count        返回数据件数	默认5
     * @param order_status 不传时返回全部，0:待支付 1:处理中 2:已完成
     * @return
     */
    public static Map<String, String> getOrderListUrl(int page, int count, String order_status) {

        Map<String, String> params = getBaseParams();
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());
        params.put(KEY_PAGE, page+"");
        params.put(KEY_COUNT, count+"");
        params.put("order_status", order_status);
        return params;
    }

    /**
     * 创建订单
     *
     * @param item 用户选择的商品项目
     *             按照itemid-count,itemid-count的规则拼接.例item=1-2,2-1
     * @param data 联系人信息
     * @return
     */
    public static Map<String, String> getCreateOrderUrl(String item, String dateTime, OrderContacts data) {

        Map<String, String> params = getBaseParams();
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());
        params.put("travel_date", dateTime);
        params.put("item", item);
        params.put("contact_id", data.getContact_id());
        params.put("contact_name", data.getName());
        params.put("contact_phone", data.getPhone());
        params.put("contact_email", data.getEmail());
        return params;
    }

    /**
     * 通过用户ID获取联系人信息
     *
     * @return
     */
    public static Map<String, String> getContactUrl() {

        Map<String, String> params = getBaseParams();
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());
        return params;
    }

    /**
     * 添加联系人信息
     *
     * @return
     */
    public static Map<String, String> getContactAddUrl(OrderContacts data) {

        Map<String, String> params = getBaseParams();
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());
        params.put("contact_id", data.getContact_id());
        params.put("name", data.getName());
        params.put("phone", data.getPhone());
        params.put("email", data.getEmail());
        return params;
    }

    /**
     * 生成ping++ 支付凭证
     *
     * @return
     */
    public static Map<String, String> getOrderPayCreateCharge(String order_id, String channel) {

        Map<String, String> params = getBaseParams();
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());
        params.put("order_id", order_id);
        params.put("channel", channel);
        return params;
    }

    /**
     * 发布点评
     *
     * @return
     */
    public static Map<String, String> getCommentAdd(String product_id, String comment_level, String comment) {

        Map<String, String> params = getBaseParams();
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());
        params.put("product_id", product_id);
        params.put("comment_level", comment_level);
        params.put("comment", comment);
        return params;
    }
}
