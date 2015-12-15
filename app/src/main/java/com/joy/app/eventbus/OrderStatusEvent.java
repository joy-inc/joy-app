package com.joy.app.eventbus;

/**
 * 支付状态的通知：成功
 * Created by xiaoyu.chen on 15/12/9.
 */
public class OrderStatusEvent {

    public enum EnumOrderStatus {

        ORDER_CREATE_SUCCESS,

        ORDER_PAY_SUCCESS;
    }

    private EnumOrderStatus status;

    public OrderStatusEvent(EnumOrderStatus status) {

        this.status = status;
    }

    public EnumOrderStatus getStatus() {
        return status;
    }

    public void setStatus(EnumOrderStatus status) {
        this.status = status;
    }
}
