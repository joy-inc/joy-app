package com.joy.app.activity.poi;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.DensityUtil;
import com.android.library.widget.JTextView;
import com.joy.app.R;
import com.joy.app.bean.plan.PlanFolder;
import com.joy.app.bean.poi.OrderDetail;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.PlanHttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author litong  <br>
 * @Description 订单详情页    <br>
 */
public class OrderDetailActivity extends BaseHttpRvActivity<OrderDetail> {
    String Url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showPayButton();
    }
    private void showPayButton(){
        FrameLayout root = getRootView();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout layout = new RelativeLayout(this);
        JTextView tv = new JTextView(this);
        tv.setBackgroundResource(R.drawable.selector_bg_rectangle_accent);
        RelativeLayout.LayoutParams tvpaParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(50));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendPayMassage();
            }
        });
        layout.addView(tv,tvpaParams);
        root.addView(layout,params);
    }

    @Override
    protected List<?> getListInvalidateContent(OrderDetail orderDetail) {
        ArrayList<OrderDetail> list= new ArrayList<OrderDetail>();
        list.add(orderDetail);
        return super.getListInvalidateContent(orderDetail);
    }

    private void SendPayMassage(){

    }

    @Override
    protected ObjectRequest<OrderDetail> getObjectRequest() {
        ObjectRequest obj = new ObjectRequest(OrderHtpUtil.getOrderDetailUrl(Url), OrderDetail.class);
        return obj ;
    }
}
