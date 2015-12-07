package com.joy.app.activity.poi;

import java.util.List;

/**
 * ping++ charge
 * Created by xiaoyu.chen on 15/12/6.
 */
public class OrderCharge {


    /**
     * id : ch_Hm5uTSifDOuTy9iLeLPSurrD
     * object : charge
     * created : 1425095528
     * livemode : true
     * paid : false
     * refunded : false
     * app : app_1Gqj58ynP0mHeX1q
     * channel : alipay
     * order_no : 123456789
     * client_ip : 127.0.0.1
     * amount : 100
     * amount_settle : 0
     * currency : cny
     * subject : Your Subject
     * body : Your Body
     * extra : {}
     * time_paid : null
     * time_expire : 1425181928
     * time_settle : null
     * transaction_no : null
     * refunds : {"object":"list","url":"/v1/charges/ch_Hm5uTSifDOuTy9iLeLPSurrD/refunds","has_more":false,"data":[]}
     * amount_refunded : 0
     * failure_code : null
     * failure_msg : null
     * credential : {"object":"credential","alipay":{"orderInfo":"_input_charset=\"utf-8\"&body=\"tsttest\"&it_b_pay=\"1440m\"¬ify_url=\"https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_jH8uD0aLyzHG9Oiz5OKOeHu9\"&out_trade_no=\"1234dsf7uyttbj\"&partner=\"2008451959385940\"&payment_type=\"1\"&seller_id=\"2008451959385940\"&service=\"mobile.securitypay.pay\"&subject=\"test\"&total_fee=\"1.23\"&sign=\"dkxTeVhMMHV2dlRPNWl6WHI5cm56THVI\"&sign_type=\"RSA\""}}
     * description : null
     */

    private String id;
    private String object;
    private int created;
    private boolean livemode;
    private boolean paid;
    private boolean refunded;
    private String app;
    private String channel;
    private String order_no;
    private String client_ip;
    private int amount;
    private int amount_settle;
    private String currency;
    private String subject;
    private String body;
    private Object time_paid;
    private int time_expire;
    private Object time_settle;
    private Object transaction_no;
    /**
     * object : list
     * url : /v1/charges/ch_Hm5uTSifDOuTy9iLeLPSurrD/refunds
     * has_more : false
     * data : []
     */

    private RefundsEntity refunds;
    private int amount_refunded;
    private Object failure_code;
    private Object failure_msg;
    /**
     * object : credential
     * alipay : {"orderInfo":"_input_charset=\"utf-8\"&body=\"tsttest\"&it_b_pay=\"1440m\"¬ify_url=\"https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_jH8uD0aLyzHG9Oiz5OKOeHu9\"&out_trade_no=\"1234dsf7uyttbj\"&partner=\"2008451959385940\"&payment_type=\"1\"&seller_id=\"2008451959385940\"&service=\"mobile.securitypay.pay\"&subject=\"test\"&total_fee=\"1.23\"&sign=\"dkxTeVhMMHV2dlRPNWl6WHI5cm56THVI\"&sign_type=\"RSA\""}
     */

    private CredentialEntity credential;
    private Object description;

    public void setId(String id) {
        this.id = id;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public void setLivemode(boolean livemode) {
        this.livemode = livemode;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAmount_settle(int amount_settle) {
        this.amount_settle = amount_settle;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTime_paid(Object time_paid) {
        this.time_paid = time_paid;
    }

    public void setTime_expire(int time_expire) {
        this.time_expire = time_expire;
    }

    public void setTime_settle(Object time_settle) {
        this.time_settle = time_settle;
    }

    public void setTransaction_no(Object transaction_no) {
        this.transaction_no = transaction_no;
    }

    public void setRefunds(RefundsEntity refunds) {
        this.refunds = refunds;
    }

    public void setAmount_refunded(int amount_refunded) {
        this.amount_refunded = amount_refunded;
    }

    public void setFailure_code(Object failure_code) {
        this.failure_code = failure_code;
    }

    public void setFailure_msg(Object failure_msg) {
        this.failure_msg = failure_msg;
    }

    public void setCredential(CredentialEntity credential) {
        this.credential = credential;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getObject() {
        return object;
    }

    public int getCreated() {
        return created;
    }

    public boolean isLivemode() {
        return livemode;
    }

    public boolean isPaid() {
        return paid;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public String getApp() {
        return app;
    }

    public String getChannel() {
        return channel;
    }

    public String getOrder_no() {
        return order_no;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public int getAmount() {
        return amount;
    }

    public int getAmount_settle() {
        return amount_settle;
    }

    public String getCurrency() {
        return currency;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public Object getTime_paid() {
        return time_paid;
    }

    public int getTime_expire() {
        return time_expire;
    }

    public Object getTime_settle() {
        return time_settle;
    }

    public Object getTransaction_no() {
        return transaction_no;
    }

    public RefundsEntity getRefunds() {
        return refunds;
    }

    public int getAmount_refunded() {
        return amount_refunded;
    }

    public Object getFailure_code() {
        return failure_code;
    }

    public Object getFailure_msg() {
        return failure_msg;
    }

    public CredentialEntity getCredential() {
        return credential;
    }

    public Object getDescription() {
        return description;
    }

    public static class RefundsEntity {
        private String object;
        private String url;
        private boolean has_more;
        private List<?> data;

        public void setObject(String object) {
            this.object = object;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setHas_more(boolean has_more) {
            this.has_more = has_more;
        }

        public void setData(List<?> data) {
            this.data = data;
        }

        public String getObject() {
            return object;
        }

        public String getUrl() {
            return url;
        }

        public boolean isHas_more() {
            return has_more;
        }

        public List<?> getData() {
            return data;
        }
    }

    public static class CredentialEntity {
        private String object;
        /**
         * orderInfo : _input_charset="utf-8"&body="tsttest"&it_b_pay="1440m"¬ify_url="https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_jH8uD0aLyzHG9Oiz5OKOeHu9"&out_trade_no="1234dsf7uyttbj"&partner="2008451959385940"&payment_type="1"&seller_id="2008451959385940"&service="mobile.securitypay.pay"&subject="test"&total_fee="1.23"&sign="dkxTeVhMMHV2dlRPNWl6WHI5cm56THVI"&sign_type="RSA"
         */

        private AlipayEntity alipay;

        public void setObject(String object) {
            this.object = object;
        }

        public void setAlipay(AlipayEntity alipay) {
            this.alipay = alipay;
        }

        public String getObject() {
            return object;
        }

        public AlipayEntity getAlipay() {
            return alipay;
        }

        public static class AlipayEntity {
            private String orderInfo;

            public void setOrderInfo(String orderInfo) {
                this.orderInfo = orderInfo;
            }

            public String getOrderInfo() {
                return orderInfo;
            }
        }
    }
}
