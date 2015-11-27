package com.joy.app.activity.poi;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.TextUtil;
import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.bean.poi.OrderContacts;
import com.joy.app.bean.poi.OrderDetail;
import com.joy.app.utils.http.OrderHtpUtil;

/**
 * 预订
 * Created by xiaoyu.chen on 15/11/26.
 */
public class OrderBookProfileActivity extends BaseHttpUiActivity<OrderContacts> {

    private String mPhotoUrl;
    private String mTitle;
    private String mTotalPrice;
    private String mOrderItem;

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
        executeRefresh();
    }

    @Override
    protected void initData() {

        mPhotoUrl = TextUtil.filterNull(getIntent().getStringExtra("photoUrl"));
        mTitle = TextUtil.filterNull(getIntent().getStringExtra("title"));
        mTotalPrice = TextUtil.filterNull(getIntent().getStringExtra("price"));
        mOrderItem = TextUtil.filterNull(getIntent().getStringExtra("item"));
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
    }

    @Override
    protected boolean invalidateContent(OrderContacts data) {

        mContact = data;

        mSdvPhoto.setImageURI(Uri.parse(mPhotoUrl));
        mTvTitle.setText(mTitle);

        if (!data.isEmpty()) {

            mEtName.setText(data.getName());
            mEtPhone.setText(data.getPhone());
            mEtEmail.setText(data.getEmail());
        }

        mTvPrice.setText(mTotalPrice);

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
            userinfo.setContact_id(mContact.getContact_id());
            userinfo.setEmail(email);
            userinfo.setPhone(phone);
            userinfo.setName(name);

            createOrder(userinfo);

        } while (false);
    }

    @Override
    protected ObjectRequest getObjectRequest() {

        return ObjectRequest.get(OrderHtpUtil.getContactUrl(), OrderContacts.class);
    }

    private void createOrder(OrderContacts userinfo) {

        ObjectRequest<OrderDetail> req = ObjectRequest.get(OrderHtpUtil.getCreateOrderUrl(mOrderItem, userinfo), OrderDetail.class);
        req.setResponseListener(new ObjectResponse<OrderDetail>() {

            @Override
            public void onSuccess(Object tag, OrderDetail data) {

                showToast("创建订单成功");
            }

            @Override
            public void onError(Object tag, VolleyError error) {

                showToast("创建订单失败啦～～"+error.getMessage());
            }
        });
        addRequest2QueueNoCache(req, req.getIdentifier());

    }
}
