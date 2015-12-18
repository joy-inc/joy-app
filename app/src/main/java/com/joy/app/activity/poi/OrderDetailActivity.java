package com.joy.app.activity.poi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.ToastUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.widget.JTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.bean.poi.OrderDetail;
import com.joy.app.eventbus.OrderStatusEvent;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;
import com.joy.app.utils.plan.DialogUtil;
import com.joy.app.utils.plan.FolderRequestListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * @author litong  <br>
 * @Description 订单详情页    <br>
 */
public class OrderDetailActivity extends BaseHttpUiActivity<OrderDetail> implements View.OnClickListener, FolderRequestListener {
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

    DialogUtil dialogUtil;
    @Bind(R.id.jtv_order_day)
    JTextView jtvOrderDay;
    @Bind(R.id.ll_selected_item)
    LinearLayout llSelectedItem;
    @Bind(R.id.ll_day_item)
    LinearLayout llDayItem;
    @Bind(R.id.v_day_spilt)
    View vDaySpilt;
    @Bind(R.id.v_item_spilt)
    View vItemSpilt;

    public static void startActivity(Activity act, String orderId, int requestCode) {
        Intent intent = new Intent(act, OrderDetailActivity.class);
        intent.putExtra("DATA", orderId);
        act.startActivityForResult(intent, requestCode);
    }

    public static void startActivity(Activity act, String orderId) {
        Intent intent = new Intent(act, OrderDetailActivity.class);
        intent.putExtra("DATA", orderId);
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
    protected void onDestroy() {

        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {

        super.initData();
        EventBus.getDefault().register(this);
        order_id = getIntent().getStringExtra("DATA");
    }

    @Override
    protected void initTitleView() {
        super.initTitleView();
        addTitleLeftBackView();
        addTitleMiddleView(R.string.order_detail_title);
        addTitleRightView(R.drawable.ic_plan_more, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRequest) return;
                dialogUtil.showDeleteOrderDialog(order_id);
            }
        });
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        ButterKnife.bind(this);
        jtvPay.setOnClickListener(this);
        dialogUtil = new DialogUtil(this, this);
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
                    orderDetail.setOrderInfor(getString(R.string.order_detail_cancel_infor));
                    orderDetail.setOrderTitle(getString(R.string.order_detail_cancel_title));
                    hideFinishIcon();
                    break;
                case 2://支付成功 订单处理中
                    orderDetail.setOrderInfor(getString(R.string.order_detail_paied_infor));
                    orderDetail.setOrderTitle(getString(R.string.order_detail_paied_title));
                    hideFinishIcon();

                    break;
                case 3://订单已确认
                    orderDetail.setOrderInfor(getString(R.string.order_detail_finish_infor));
                    orderDetail.setOrderTitle(getString(R.string.order_detail_finish_title));
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
        if (orderDetail.isEmptyItem()) {
            goneView(llSelectedItem);
            goneView(vItemSpilt);
        } else {
            showView(llSelectedItem);
            showView(vItemSpilt);
            jtvOrderItem.setText(orderDetail.getSelected_item());
        }
        if(orderDetail.hasTravelDate()){
            showView(llDayItem);
            showView(vDaySpilt);
            jtvOrderDay.setText(orderDetail.getTravel_date());
        }else{
            goneView(llDayItem);
            goneView(vDaySpilt);
        }
        jtvOrderEmail.setText(orderDetail.getContact_email());
        jtvOrderPhone.setText(orderDetail.getContact_phone());
        jtvOrderTotal.setText(orderDetail.getTotal_price_Str());
        jtvOrderCount.setText(orderDetail.getItem_count());
        return true;
    }

    boolean isRequest = false;

    @Override
    public void onRequest(dialog_category category, Object obj) {
        isRequest = true;
        showLoading();
    }

    @Override
    public void onSuccess(dialog_category category, Object obj) {
        isRequest = false;
        hideLoading();
        ToastUtil.showToast("删除成功");
        setResult(RESULT_OK);
        EventBus.getDefault().post(new OrderStatusEvent(OrderStatusEvent.EnumOrderStatus.ORDER_CANCEL_SUCESS));
        finish();
    }

    @Override
    public void onfaild(dialog_category category, String msg) {
        isRequest = false;
        hideLoading();
        ToastUtil.showToast(msg);
    }

    @Override
    public void onClick(View v) {
        if (isRequest) return;
        switch (v.getId()) {
            case R.id.jtv_pay:
                SendPayMassage();
                break;
            default:
        }
    }

    private void SendPayMassage() {
        OrderPayActivity.startActivity(this, order_id, detail);
    }

    @Override
    protected ObjectRequest<OrderDetail> getObjectRequest() {

        return ReqFactory.newPost(OrderHtpUtil.URL_POST_ORDER_DETAIL, OrderDetail.class, OrderHtpUtil.getOrderDetailUrl(order_id));
    }

    /**
     * 支付状态的回调 关闭支付页之前的预订流程页面
     *
     * @param event
     */
    public void onEventMainThread(OrderStatusEvent event) {

        if (event.getStatus() == OrderStatusEvent.EnumOrderStatus.ORDER_PAY_SUCCESS)
            finish();
    }
}
