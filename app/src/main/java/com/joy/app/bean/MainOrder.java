package com.joy.app.bean;

import com.android.library.utils.TextUtil;

/**
 * 首页我的订单列表item
 * <p/>
 * Created by xiaoyu.chen on 15/11/17.
 */
public class MainOrder {

    /**
     * 订单编号
     */
    private String order_id = TextUtil.TEXT_EMPTY;

    /**
     * 订单编号
     */
    private String travel_date = TextUtil.TEXT_EMPTY;

    /**
     * 订购产品
     */
    private String product_title = TextUtil.TEXT_EMPTY;

    /**
     * 产品类型
     */
    private String selected_item = TextUtil.TEXT_EMPTY;

    /**
     * 产品头图
     */
    private String product_photo = TextUtil.TEXT_EMPTY;

    /**
     * 数量
     */
    private String count = TextUtil.TEXT_EMPTY;

    /**
     * 总价 不含单位 1076
     */
    private String total_price = TextUtil.TEXT_EMPTY;

    /**
     * 0:待付款
     * 1:订单过期或被取消
     * 2:支付成功 订单处理中
     * 3:订单已确认
     */
    private String order_status;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTravel_date() {
        return travel_date;
    }

    public void setTravel_date(String travel_date) {
        this.travel_date = travel_date;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getSelected_item() {
        return selected_item;
    }

    public void setSelected_item(String selected_item) {
        this.selected_item = selected_item;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }
}
