package com.joy.app.activity.poi;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.LogMgr;
import com.android.library.utils.TextUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.BuildConfig;
import com.joy.app.R;
import com.joy.app.bean.poi.OrderContacts;
import com.joy.app.bean.poi.OrderDetail;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;

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

    private SimpleDraweeView mSdvPhoto;
    private TextView mTvTitle;
    private AppCompatEditText mEtName, mEtPhone, mEtEmail;
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
    protected void initData() {

        mPhotoUrl = TextUtil.filterNull(getIntent().getStringExtra("photoUrl"));
        mTitle = TextUtil.filterNull(getIntent().getStringExtra("title"));
        mTotalPrice = TextUtil.filterNull(getIntent().getStringExtra("price"));
        mOrderItem = TextUtil.filterNull(getIntent().getStringExtra("item"));
        mDateTime = TextUtil.filterNull(getIntent().getStringExtra("time"));

        LogMgr.w("itemids====" + mOrderItem);
    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView();
    }

    @Override
    protected void initContentView() {

        mSdvPhoto = (SimpleDraweeView) findViewById(R.id.sdvPhoto);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);

        mEtName = (AppCompatEditText) findViewById(R.id.etName);
        mEtPhone = (AppCompatEditText) findViewById(R.id.etPhone);
        mEtEmail = (AppCompatEditText) findViewById(R.id.etEmail);

        mTvPrice = (TextView) findViewById(R.id.tvTotalPrice);
        mAcbNext = (AppCompatButton) findViewById(R.id.acbNext);
        mAcbNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                checkProfileThenSubmit();
            }
        });

        mSdvPhoto.setImageURI(Uri.parse(mPhotoUrl));
        mTvTitle.setText(mTitle);
        mTvPrice.setText(mTotalPrice);
    }

    @Override
    protected void showNoContentTip() {
    }

    @Override
    protected void showFailedTip() {

//        super.showFailedTip();
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

        if (BuildConfig.DEBUG) {

            OrderContacts data = new OrderContacts();
            data.setContact_id("11");
            data.setName("plmkk");
            data.setPhone("18611111111");
            data.setEmail("plmkk@test.com");
            obj.setData(data);
        }

        return obj;
    }

    private void createOrder(OrderContacts userinfo) {

        ObjectRequest<OrderDetail> req = ReqFactory.newPost(OrderHtpUtil.URL_POST_ORDER_CREATE, OrderDetail.class, OrderHtpUtil.getCreateOrderUrl(mOrderItem, mDateTime, userinfo));
        req.setResponseListener(new ObjectResponse<OrderDetail>() {

            @Override
            public void onSuccess(Object tag, OrderDetail data) {

                showToast("创建订单成功");
                OrderPayActivity.startActivity(OrderBookProfileActivity.this, data.getOrder_id(), data);
            }

            @Override
            public void onError(Object tag, String msg) {

                super.onError(tag, msg);
                showToast(msg);
            }
        });
        addRequestNoCache(req);

    }

    private void addContact(OrderContacts data) {

        ObjectRequest<OrderContacts> request = ReqFactory.newPost(OrderHtpUtil.URL_POST_CONTACT_ADD, OrderContacts.class, OrderHtpUtil.getContactAddUrl(data));
        request.setResponseListener(new ObjectResponse<OrderContacts>() {

            @Override
            public void onSuccess(Object tag, OrderContacts orderContacts) {

            }

            @Override
            public void onError(Object tag, String msg) {

                super.onError(tag, msg);
                showToast(msg);
            }
        });
        addRequestNoCache(request);
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(act, view, view.getTransitionName());
            act.startActivity(intent, options.toBundle());
        } else {

            act.startActivity(intent);
        }
    }
}
