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
import com.android.library.utils.LogMgr;
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
        addTitleMiddleView(R.string.booking);
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

        return obj;
    }

    private void startPay(String channel) {

        ObjectRequest<OrderCharge> obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_ORDER_PAY_CREATE_CHARGE, OrderCharge.class, OrderHtpUtil.getOrderPayCreateCharge(mId, channel));

        obj.setResponseListener(new ObjectResponse<OrderCharge>() {

            @Override
            public void onSuccess(Object tag, OrderCharge orderCharge) {

                startPaymentActivity("服务器返回的charge json 串");// todo json 字符串
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
