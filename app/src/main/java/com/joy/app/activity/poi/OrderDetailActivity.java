package com.joy.app.activity.poi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.ViewUtil;
import com.android.library.widget.JTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.bean.poi.OrderDetail;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description 订单详情页    <br>
 */
public class OrderDetailActivity extends BaseHttpUiActivity<OrderDetail> implements View.OnClickListener {
    String order_id;
    OrderDetail detail;
    @Bind(R.id.jtv_title)
    JTextView jtvTitle;
    @Bind(R.id.jtv_infor)
    JTextView jtvInfor;
    @Bind(R.id.sdv_order_photo)
    SimpleDraweeView sdvOrderPhoto;
    @Bind(R.id.jtv_order_title)
    JTextView jtvOrderTitle;
    @Bind(R.id.jtv_order_id)
    JTextView jtvOrderId;
    @Bind(R.id.jtv_order_item)
    JTextView jtvOrderItem;
    @Bind(R.id.jtv_order_count)
    JTextView jtvOrderCount;
    @Bind(R.id.jtv_order_name)
    JTextView jtvOrderName;
    @Bind(R.id.jtv_order_phone)
    JTextView jtvOrderPhone;
    @Bind(R.id.jtv_order_email)
    JTextView jtvOrderEmail;
    @Bind(R.id.jtv_order_total)
    JTextView jtvOrderTotal;
    @Bind(R.id.v_empty)
    View vEmpty;
    @Bind(R.id.jtv_pay)
    JTextView jtvPay;
    @Bind(R.id.iv_finsh)
    ImageView ivFinsh;

    public static void startActivity(Activity act, String orderId) {
        Intent intent = new Intent(act, OrderDetailActivity.class);
//        intent.putExtra("DATA",orderId);
        intent.putExtra("DATA", "20151208_66084");
        act.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_order_view);
        ButterKnife.bind(this);
        executeRefreshOnly();
    }

    @Override
    protected void initData() {
        super.initData();
        order_id = getIntent().getStringExtra("DATA");
    }

    @Override
    protected void initTitleView() {
        super.initTitleView();
        addTitleLeftBackView();
        addTitleMiddleView(R.string.order_detail_title);
        //TODO 右边的点点点图标
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        ButterKnife.bind(this);
        jtvPay.setOnClickListener(this);
    }

    private void showPayButton() {
        ViewUtil.showView(jtvPay);
        ViewUtil.showView(vEmpty);
    }

    private void removePayButton() {
        if (jtvPay == null) return;
        ViewUtil.goneView(jtvPay);
        ViewUtil.goneView(vEmpty);
    }

    private void showFinishIcon() {
        ViewUtil.showView(ivFinsh);
    }

    private void hideFinishIcon() {
        ViewUtil.goneView(ivFinsh);
    }

    @Override
    protected boolean invalidateContent(OrderDetail orderDetail) {
        if (orderDetail == null) return false;
        else {
            detail = orderDetail;
            if (orderDetail.isUnpayState()) {
                showPayButton();
            } else {
                removePayButton();
            }
            switch (orderDetail.getOrder_status()) {
                case 0://待支付
                    orderDetail.setOrderInfor(getString(R.string.order_detail_create_infor));
                    orderDetail.setOrderTitle(getString(R.string.order_detail_create_title));
                    showFinishIcon();
                    break;
                case 1://订单过期或被取消
                    orderDetail.setOrderInfor(getString(R.string.order_detail_create_infor));
                    orderDetail.setOrderTitle(getString(R.string.order_detail_create_title));
                    hideFinishIcon();
                    break;
                case 2://支付成功 订单处理中
                    orderDetail.setOrderInfor(getString(R.string.order_detail_paied_infor));
                    orderDetail.setOrderTitle(getString(R.string.order_detail_paied_title));
                    hideFinishIcon();

                    break;
                case 3://订单已确认
                    orderDetail.setOrderInfor(getString(R.string.order_detail_create_infor));
                    orderDetail.setOrderTitle(getString(R.string.order_detail_create_title));
                    showFinishIcon();
                    break;
                default://默认
                    orderDetail.setOrderInfor(getString(R.string.order_detail_finish_infor));
                    orderDetail.setOrderTitle(getString(R.string.order_detail_finish_title));
                    hideFinishIcon();


            }
        }
        jtvTitle.setText(orderDetail.getOrderTitle());
        jtvInfor.setText(orderDetail.getOrderInfor());
        sdvOrderPhoto.setImageURI(Uri.parse(orderDetail.getProduct_photo()));
        jtvOrderName.setText(orderDetail.getContact_name());
        jtvOrderId.setText(orderDetail.getOrder_id());
        jtvOrderTitle.setText(orderDetail.getProduct_title());
        jtvOrderItem.setText(orderDetail.getSelected_item());
        jtvOrderEmail.setText(orderDetail.getContact_email());
        jtvOrderPhone.setText(orderDetail.getContact_phone());
        jtvOrderTotal.setText(orderDetail.getTotal_price_Str());
        jtvOrderCount.setText(orderDetail.getFormatCountStr());
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jtv_pay:
                SendPayMassage();
                break;
            default:
        }
    }

    private void SendPayMassage() {
        OrderPayActivity.startActivity(this,order_id,detail);
    }

    @Override
    protected ObjectRequest<OrderDetail> getObjectRequest() {

        return ReqFactory.newPost(OrderHtpUtil.URL_POST_ORDER_DETAIL, OrderDetail.class, OrderHtpUtil.getOrderDetailUrl(order_id));
    }
}
