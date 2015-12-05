package com.joy.app.bean.poi;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import com.android.library.utils.TextUtil;

import java.io.Serializable;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class OrderDetail implements Serializable {

//            "order_id":"201511120001",
//            "travel_date":"2015年11月12日",
//            "product_title":"米尔福德峡湾一日游（观光游轮+自助午餐+皮划艇）",
//            "selected_item":"7:30 皇后镇出发 中文导游",
//            "count":2,
//            "contact_name":"张路",
//            "contact_phone":"13901243393",
//            "contact_email":"lu.zhang945@gmail.com",
//            "total_price":1076,
//            "product_photo":"http://pic.qyer.com/album/user/1363/58/QEpTQR8PZEg/index/680x"
//            "order_status":0,
//    0:待付款
//    1:订单过期或被取消
//    2:支付成功 订单处理中
//    3:订单已确认
    private String order_id = TextUtil.TEXT_EMPTY;
    private String travel_date = TextUtil.TEXT_EMPTY;
    private String product_title = TextUtil.TEXT_EMPTY;
    private String selected_item = TextUtil.TEXT_EMPTY;
    private String contact_name = TextUtil.TEXT_EMPTY;
    private String contact_phone = TextUtil.TEXT_EMPTY;
    private String contact_email = TextUtil.TEXT_EMPTY;
    private int count, order_status;
    private float total_price;
    private String product_photo = TextUtil.TEXT_EMPTY;

    private String orderInfor = TextUtil.TEXT_EMPTY;
    private String orderTitle = TextUtil.TEXT_EMPTY;

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

    public String getSelected_item() {
        return selected_item;
    }

    public void setSelected_item(String selected_item) {
        this.selected_item = selected_item;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public String getTotal_price_Str() {
        return "￥" + total_price;
    }

    public boolean isUnpayState() {
        return getOrder_status() == 0;
    }

    public String getOrderInfor() {
        return orderInfor;
    }

    public void setOrderInfor(String orderInfor) {
        this.orderInfor = orderInfor;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }

    public String getTravel_date() {
        return travel_date;
    }

    public void setTravel_date(String travel_date) {
        this.travel_date = travel_date;
    }

    public CharSequence getFormatCountStr() {

        SpannableString spannableString = new SpannableString("x" + getCount());
        spannableString.setSpan(new RelativeSizeSpan(0.50f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
