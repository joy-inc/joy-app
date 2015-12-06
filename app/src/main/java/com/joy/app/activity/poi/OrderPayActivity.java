package com.joy.app.activity.poi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.TextView;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TextUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.bean.poi.OrderDetail;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单支付页
 * <p/>
 * Created by xiaoyu.chen on 15/12/4.
 */
public class OrderPayActivity extends BaseHttpUiActivity<OrderDetail> {

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

    @Bind(R.id.accbWechat)
    AppCompatCheckBox accbWechat;

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
                // todo ping++ pay
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

    public static void startActivity(Activity act, String order_id, OrderDetail data) {

        Intent intent = new Intent(act, OrderPayActivity.class);
        intent.putExtra("id", order_id);
        intent.putExtra("data", data);
        act.startActivity(intent);
    }
}
