package com.joy.app.bean.poi;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import java.io.Serializable;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class OrderDetail implements Serializable {
//    "order_id": "201511120001",
//            "product_title": "米尔福德峡湾一日游（观光游轮+自助午餐+皮划艇）",
//            "selected_item": "7:30 皇后镇出发 中文导游",
//            "count": 2,
//            "contact_name": "张路",
//            "contact_phone": "13901243393",
//            "contact_email": "lu.zhang945@gmail.com",
//            "total_price": 1076,
//            "status": 0,
//    0:待付款
//    1:订单过期或被取消
//    2:支付成功 订单处理中
//    3:订单已确认
    String order_id,product_title,selected_item,contact_name,contact_phone,contact_email;
    int count,status;
    float total_price;
    String orderInfor,orderTitle;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public String getTotal_price_Str() {
        return "￥"+total_price;
    }

    public boolean isUnpayState(){
        return getStatus() == 0;
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

    public CharSequence getFormatCountStr(){

        SpannableString spannableString = new SpannableString("x"+getCount());
        spannableString.setSpan(new RelativeSizeSpan(0.50f), 0 , 1 ,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
