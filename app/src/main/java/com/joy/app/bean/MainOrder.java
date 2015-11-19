package com.joy.app.bean;

import com.joy.library.utils.TextUtil;

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
     * 订购产品
     */
    private String product_title = TextUtil.TEXT_EMPTY;

    /**
     * 产品类型
     */
    private String product_type = TextUtil.TEXT_EMPTY;

    /**
     * 数量
     */
    private String count = TextUtil.TEXT_EMPTY;

    /**
     * 总价 含单位 例:￥1076
     */
    private String total_price = TextUtil.TEXT_EMPTY;

    /**
     * 0:待付款
     * 1:订单过期或被取消
     * 2:支付成功 订单处理中
     * 3:订单已确认
     */
    private String status;

    public String getOrder_id() {

        return order_id;
    }

    public void setOrder_id(String order_id) {

        this.order_id = order_id;
    }

    public String getProduct_title() {

        return product_title;
    }

    public void setProduct_title(String product_title) {

        this.product_title = product_title;
    }

    public String getProduct_type() {

        return product_type;
    }

    public void setProduct_type(String product_type) {

        this.product_type = product_type;
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

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }
}
