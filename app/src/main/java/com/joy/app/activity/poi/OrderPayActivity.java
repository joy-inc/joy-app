package com.joy.app.activity.poi;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.TextUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.BuildConfig;
import com.joy.app.R;
import com.joy.app.bean.poi.OrderDetail;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;
import com.pingplusplus.android.PaymentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单支付页
 * <p/>
 * Created by xiaoyu.chen on 15/12/4.
 */
public class OrderPayActivity extends BaseHttpUiActivity<OrderDetail> {

    private static final int REQUEST_CODE_PAYMENT = 1;

    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";

    private String mId;
    private OrderDetail mOrderDetail;

    @Bind(R.id.sdvPhoto)
    SimpleDraweeView sdvPhoto;

    @Bind(R.id.tvTitle)
    TextView tvTitle;

    @Bind(R.id.jtv_order_id)
    TextView jtvOrderId;

    @Bind(R.id.jtv_order_item)
    TextView jtvOrderItem;

    @Bind(R.id.jtv_order_count)
    TextView jtvOrderCount;

    @Bind(R.id.jtv_order_name)
    TextView jtvOrderName;

    @Bind(R.id.jtv_order_phone)
    TextView jtvOrderPhone;

    @Bind(R.id.jtv_order_email)
    TextView jtvOrderEmail;

    @Bind(R.id.jtv_order_total)
    TextView jtvOrderTotal;

    @Bind(R.id.rlWeChatDiv)
    RelativeLayout rlWeChatDiv;

    @Bind(R.id.accbWechat)
    AppCompatCheckBox accbWechat;

    @Bind(R.id.rlAlipayDiv)
    RelativeLayout rlAlipayDiv;

    @Bind(R.id.accbAlipay)
    AppCompatCheckBox accbAlipay;

    @Bind(R.id.tvTotalPrice)
    TextView tvTotalPrice;

    @Bind(R.id.acbNext)
    AppCompatButton acbNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order_pay);

        if (mOrderDetail == null)
            executeRefreshOnly();
        else
            invalidateContent(mOrderDetail);
    }

    @Override
    protected void initData() {

        super.initData();
        mId = TextUtil.filterNull(getIntent().getStringExtra("id"));
        if (getIntent().getSerializableExtra("data") instanceof OrderDetail)
            mOrderDetail = (OrderDetail) getIntent().getSerializableExtra("data");
    }

    @Override
    protected void initTitleView() {

        super.initTitleView();
        addTitleLeftBackView();
        setTitleText(R.string.booking);
    }

    @Override
    protected void initContentView() {

        super.initContentView();
        ButterKnife.bind(this);
        acbNext.setText(R.string.pay);
        acbNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (accbWechat.isChecked()) {
                    startPay(CHANNEL_WECHAT);
                } else if (accbAlipay.isChecked()) {
                    startPay(CHANNEL_ALIPAY);
                } else {
                    showToast("请选择支付方式");
                }
            }
        });
        rlWeChatDiv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                accbWechat.setChecked(!accbWechat.isChecked());
            }
        });
        rlAlipayDiv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                accbAlipay.setChecked(!accbAlipay.isChecked());
            }
        });
        accbWechat.setChecked(true);
        accbWechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    accbAlipay.setChecked(false);
            }
        });
        accbAlipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    accbWechat.setChecked(false);
            }
        });
    }

    @Override
    protected boolean invalidateContent(OrderDetail data) {

        if (data == null)
            return false;

        sdvPhoto.setImageURI(Uri.parse(data.getProduct_photo()));
        tvTitle.setText(data.getProduct_title());
        jtvOrderId.setText(data.getOrder_id());
        jtvOrderItem.setText(data.getSelected_item());
        jtvOrderCount.setText(data.getFormatCountStr());
        jtvOrderName.setText(data.getContact_name());
        jtvOrderPhone.setText(data.getContact_phone());
        jtvOrderEmail.setText(data.getContact_email());
        jtvOrderTotal.setText(data.getTotal_price_Str());

        tvTotalPrice.setText(data.getTotal_price_Str());

        return true;
    }

    @Override
    protected ObjectRequest<OrderDetail> getObjectRequest() {

        ObjectRequest<OrderDetail> obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_ORDER_DETAIL, OrderDetail.class, OrderHtpUtil.getOrderDetailUrl(mId));

        if (BuildConfig.DEBUG) {

            OrderDetail data = new OrderDetail();
            data.setOrder_id("201511120001");
            data.setTravel_date("2015年11月12日");
            data.setProduct_title("米尔福德峡湾一日游（观光游轮+自助午餐+皮划艇）");
            data.setSelected_item("7:30 皇后镇出发 中文导游");
            data.setCount(2);
            data.setContact_name("plmkk");
            data.setContact_phone("18611111111");
            data.setContact_email("plmkk@test.com");
            data.setTotal_price(1097);
            data.setOrder_status(0);
            data.setProduct_photo("http://pic.qyer.com/album/user/1363/58/QEpTQR8PZEg/index/680x");

            obj.setData(data);
        }

        return obj;
    }

    String ccc = "{\n" +
            "                                                                \"id\": \"ch_SmXX188C0qvTSebXXLeT48S4\",\n" +
            "                                                                \"object\": \"charge\",\n" +
            "                                                                \"created\": 1449382303,\n" +
            "                                                                \"livemode\": false,\n" +
            "                                                                \"paid\": false,\n" +
            "                                                                \"refunded\": false,\n" +
            "                                                                \"app\": \"app_1Gqj58ynP0mHeX1q\",\n" +
            "                                                                \"channel\": \"alipay\",\n" +
            "                                                                \"order_no\": \"f7267df2762313ac\",\n" +
            "                                                                \"client_ip\": \"222.130.61.80\",\n" +
            "                                                                \"amount\": 100,\n" +
            "                                                                \"amount_settle\": 0,\n" +
            "                                                                \"currency\": \"cny\",\n" +
            "                                                                \"subject\": \"Your Subject\",\n" +
            "                                                                \"body\": \"Your Body\",\n" +
            "                                                                \"extra\": {},\n" +
            "                                                                \"time_paid\": null,\n" +
            "                                                                \"time_expire\": 1449468703,\n" +
            "                                                                \"time_settle\": null,\n" +
            "                                                                \"transaction_no\": null,\n" +
            "                                                                \"refunds\": {\n" +
            "                                                                    \"object\": \"list\",\n" +
            "                                                                    \"url\": \"\\/v1\\/charges\\/ch_SmXX188C0qvTSebXXLeT48S4\\/refunds\",\n" +
            "                                                                    \"has_more\": false,\n" +
            "                                                                    \"data\": []\n" +
            "                                                                },\n" +
            "                                                                \"amount_refunded\": 0,\n" +
            "                                                                \"failure_code\": null,\n" +
            "                                                                \"failure_msg\": null,\n" +
            "                                                                \"metadata\": {},\n" +
            "                                                                \"credential\": {\n" +
            "                                                                    \"object\": \"credential\",\n" +
            "                                                                    \"alipay\": {\n" +
            "                                                                        \"orderInfo\": \"_input_charset=\\\"utf-8\\\"&body=\\\"Your Body\\\"&it_b_pay=\\\"2015-12-07 14:11:43\\\"&notify_url=\\\"https%3A%2F%2Fapi.pingxx.com%2Fnotify%2Fcharges%2Fch_SmXX188C0qvTSebXXLeT48S4\\\"&out_trade_no=\\\"f7267df2762313ac\\\"&partner=\\\"2008744092999923\\\"&payment_type=\\\"1\\\"&seller_id=\\\"2008744092999923\\\"&service=\\\"mobile.securitypay.pay\\\"&subject=\\\"Your Subject\\\"&total_fee=\\\"1\\\"&sign=\\\"aVBtYmJMMXVUdWIxamI1MEtDMWlqekQw\\\"&sign_type=\\\"RSA\\\"\"\n" +
            "                                                                    }\n" +
            "                                                                },\n" +
            "                                                                \"description\": null\n" +
            "                                                            }";

    private void startPay(String channel) {

        ObjectRequest<OrderCharge> obj = ReqFactory.newPost("http://218.244.151.190/demo/charge", OrderCharge.class, OrderHtpUtil.getOrderPayCreateCharge(mId, channel));

        if (BuildConfig.DEBUG) {
            obj.setData(new OrderCharge());
        }

        obj.setResponseListener(new ObjectResponse<OrderCharge>() {

            @Override
            public void onSuccess(Object tag, OrderCharge orderCharge) {

                showToast("支付成功？");
                startPaymentActivity(ccc);// todo json 字符串
            }

            @Override
            public void onError(Object tag, String msg) {

                super.onError(tag, msg);
                showToast(msg);
            }
        });
        addRequestNoCache(obj);
    }

    private void startPaymentActivity(String charge) {

        Intent intent = new Intent();
        String packageName = getPackageName();
        ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
        intent.setComponent(componentName);
        intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {

                String result = data.getExtras().getString("pay_result");
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                showMsg(result, errorMsg, extraMsg);

                takePayResult(result, errorMsg, extraMsg);
            }
        }
    }

    private void takePayResult(String result, String errorMsg, String extraMsg) {
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */

        if ("success".equals(result)) {

            // todo open order detail activity
            callbackPaySuccess();
        } else if ("fail".equals(result)) {
            showToast(R.string.toast_pay_failed);
        } else if ("invalid".equals(result)) {
            showToast(errorMsg);
        }
    }

    private void callbackPaySuccess() {

        // todo 参数是什么
        ObjectRequest obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_ORDER_PAY_CALLBACK, Object.class, OrderHtpUtil.getOrderPayCallback());
        addRequestNoCache(obj);
    }

    public void showMsg(String title, String msg1, String msg2) {
        String str = title;
        if (null != msg1 && msg1.length() != 0) {
            str += "\n" + msg1;
        }
        if (null != msg2 && msg2.length() != 0) {
            str += "\n" + msg2;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(str);
        builder.setTitle("提示");
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    public static void startActivity(Activity act, String order_id, OrderDetail data) {

        Intent intent = new Intent(act, OrderPayActivity.class);
        intent.putExtra("id", order_id);
        intent.putExtra("data", data);
        act.startActivity(intent);
    }
}
