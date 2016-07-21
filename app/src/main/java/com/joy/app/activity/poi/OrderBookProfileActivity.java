package com.joy.app.activity.poi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.library.ui.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.TextUtil;
import com.android.library.widget.FrescoImageView;
import com.joy.app.R;
import com.joy.app.bean.poi.OrderContacts;
import com.joy.app.bean.poi.OrderDetail;
import com.joy.app.eventbus.OrderStatusEvent;
import com.joy.app.utils.JTextSpanUtil;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;

import de.greenrobot.event.EventBus;

/**
 * 预订
 * Created by xiaoyu.chen on 15/11/26.
 */
public class OrderBookProfileActivity extends BaseHttpUiActivity<OrderContacts> {

    private String mPhotoUrl;
    private String mTitle;
    private String mTotalPrice;
    private String mOrderItem;
    private String mDateTime;

    private FrescoImageView mSdvPhoto;
    private TextView mTvTitle;
    private EditText mEtName, mEtPhone, mEtEmail;
    private TextView mTvPrice;
    private AppCompatButton mAcbNext;
    private OrderContacts mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order_book_profile);
        executeRefreshOnly();
    }

    @Override
    protected void onResume() {

        super.onResume();
        if (mAcbNext != null)
            mAcbNext.setClickable(true);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {

        EventBus.getDefault().register(this);
        mPhotoUrl = TextUtil.filterNull(getIntent().getStringExtra("photoUrl"));
        mTitle = TextUtil.filterNull(getIntent().getStringExtra("title"));
        mTotalPrice = TextUtil.filterNull(getIntent().getStringExtra("price"));
        mOrderItem = TextUtil.filterNull(getIntent().getStringExtra("item"));
        mDateTime = TextUtil.filterNull(getIntent().getStringExtra("time"));

    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView();
        addTitleMiddleView(R.string.booking);
    }

    @Override
    protected void initContentView() {

        mSdvPhoto = (FrescoImageView) findViewById(R.id.sdvPhoto);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);

        mEtName = (EditText) findViewById(R.id.etName);
        mEtPhone = (EditText) findViewById(R.id.etPhone);
        mEtEmail = (EditText) findViewById(R.id.etEmail);

        mTvPrice = (TextView) findViewById(R.id.tvTotalPrice);
        mAcbNext = (AppCompatButton) findViewById(R.id.acbNext);
        mAcbNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                checkProfileThenSubmit();
            }
        });

        mSdvPhoto.setImageURI(mPhotoUrl);
        mTvTitle.setText(mTitle);
        mTvPrice.setText(JTextSpanUtil.getFormatUnitStr(mTotalPrice));
    }

    @Override
    protected void showEmptyTip() {
    }

    @Override
    protected void showErrorTip() {
    }

    @Override
    protected boolean invalidateContent(OrderContacts data) {

        mContact = data;
        if (!data.isEmpty()) {

            mEtName.setText(data.getName());
            mEtPhone.setText(data.getPhone());
            mEtEmail.setText(data.getEmail());
        }

        return true;
    }

    private void checkProfileThenSubmit() {

        String name = mEtName.getText().toString();

        String phone = mEtPhone.getText().toString();
        boolean isphone = TextUtil.isMobile(phone);

        String email = mEtEmail.getText().toString();
        boolean iserrormail = TextUtil.checkMailFormat(email);


        do {

            if (TextUtils.isEmpty(name)) {
                showToast(R.string.toast_input_name);
                break;
            }

            if (TextUtils.isEmpty(phone)) {
                showToast(R.string.toast_input_phone);
                break;
            }

            if (!isphone) {
                showToast(R.string.toast_phone_error);
                break;
            }

            if (TextUtils.isEmpty(email)) {
                showToast(R.string.toast_input_email);
                break;
            }

            if (!iserrormail) {
                showToast(R.string.toast_email_error);
                break;
            }

            OrderContacts userinfo = new OrderContacts();
            userinfo.setContact_id(mContact == null ? "0" : mContact.getContact_id());
            userinfo.setEmail(email);
            userinfo.setPhone(phone);
            userinfo.setName(name);

            addContact(userinfo);
            createOrder(userinfo);

        } while (false);
    }

    @Override
    protected ObjectRequest getObjectRequest() {

        ObjectRequest<OrderContacts> obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_CONTACT_GET, OrderContacts.class, OrderHtpUtil.getContactUrl());

        return obj;
    }

    private void createOrder(OrderContacts userinfo) {

        ObjectRequest<OrderDetail> req = ReqFactory.newPost(OrderHtpUtil.URL_POST_ORDER_CREATE, OrderDetail.class, OrderHtpUtil.getCreateOrderUrl(mOrderItem, mDateTime, userinfo));
        mAcbNext.setClickable(false);
        showLoading();
        req.setResponseListener(new ObjectResponse<OrderDetail>() {

//            @Override
//            public void onPre() {
//
//                mAcbNext.setClickable(false);
//                showLoading();
//            }

            @Override
            public void onSuccess(Object tag, OrderDetail data) {

                hideLoading();
                showToast("创建订单成功");
                EventBus.getDefault().post(new OrderStatusEvent(OrderStatusEvent.EnumOrderStatus.ORDER_CREATE_SUCCESS));
                OrderPayActivity.startActivity(OrderBookProfileActivity.this, data.getOrder_id(), data);
                mAcbNext.setClickable(true);
            }

            @Override
            public void onError(Object tag, String msg) {

                hideLoading();
                showToast(msg);
                mAcbNext.setClickable(true);
            }
        });
        addRequestNoCache(req);

    }

    private void addContact(OrderContacts data) {

        ObjectRequest<OrderContacts> request = ReqFactory.newPost(OrderHtpUtil.URL_POST_CONTACT_ADD, OrderContacts.class, OrderHtpUtil.getContactAddUrl(data));
        addRequestNoCache(request);
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

    /**
     * @param act
     * @param view The view which starts the transition
     */
    public static void startActivity(Activity act, View view, String... params) {

        if (act == null || view == null || params == null || params.length < 5)
            return;

        Intent intent = new Intent(act, OrderBookProfileActivity.class);
        intent.putExtra("photoUrl", params[0]);
        intent.putExtra("title", params[1]);
        intent.putExtra("price", params[2]);
        intent.putExtra("item", params[3]);
        intent.putExtra("time", params[4]);

        act.startActivity(intent);
    }
}
