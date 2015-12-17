package com.joy.app.activity.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.android.library.activity.BaseHttpRvFragment;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.LogMgr;
import com.android.library.utils.MathUtil;
import com.android.library.utils.TextUtil;
import com.android.library.view.dialogplus.DialogPlus;
import com.android.library.view.dialogplus.ViewHolder;
import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.app.activity.poi.OrderDetailActivity;
import com.joy.app.activity.poi.OrderPayActivity;
import com.joy.app.adapter.MainOrderRvAdapter;
import com.joy.app.bean.MainOrder;
import com.joy.app.eventbus.DeleteEvent;
import com.joy.app.eventbus.LoginStatusEvent;
import com.joy.app.eventbus.OrderStatusEvent;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;
import com.joy.app.view.LoginTipView;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 我的订单
 * 如果外部刷新,会通过eventbus来进行通知
 * 这里在加载的时候,需要判断是否已经登录了.没登录的界面逻辑和登录的界面逻辑不一样
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-16
 */
public class OrderFragment extends BaseHttpRvFragment<List<MainOrder>> {

    LoginTipView mLoginTipView;

    private DialogPlus mCommentonDialog;
    private final int REQ_ORDER_DETAIL = 101;
    private boolean mNeedToRefresh = false;

    public static OrderFragment instantiate(Context context) {

        return (OrderFragment) Fragment.instantiate(context, OrderFragment.class.getName(), new Bundle());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        initViewLoad();
    }

    @Override
    public void onResume() {

        super.onResume();
        if (mNeedToRefresh) {
            mNeedToRefresh = false;
            executeSwipeRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initContentView() {

        super.initContentView();
        MainOrderRvAdapter adapter = new MainOrderRvAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<MainOrder>() {

            @Override
            public void onItemViewClick(int position, View clickView, MainOrder data) {

                if (clickView.getId() == R.id.acbPay) {
                    OrderPayActivity.startActivity(getActivity(), data.getOrder_id(), null);
                } else if (clickView.getId() == R.id.acbCommenton) {

                    showCommentonDialog(data.getProduct_id());
                } else {

                    OrderDetailActivity.startActivity(getActivity(), data.getOrder_id(), REQ_ORDER_DETAIL);
                }
            }
        });
        setAdapter(adapter);
    }

    @Override
    protected ObjectRequest<List<MainOrder>> getObjectRequest(int pageIndex, int pageLimit) {

        ObjectRequest obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_ORDER_LIST, MainOrder.class, OrderHtpUtil.getOrderListUrl(pageIndex, pageLimit, ""));
        return obj;
    }

    private void showCommentonDialog(final String productId) {

        mCommentonDialog = DialogPlus.newDialog(getActivity()).setContentHolder(new ViewHolder(R.layout.view_order_commenton)).setGravity(Gravity.CENTER).create();

        final AppCompatRatingBar ratingBar = (AppCompatRatingBar) mCommentonDialog.findViewById(R.id.acRatingBar);
        final EditText editText = (EditText) mCommentonDialog.findViewById(R.id.acetContent);

        mCommentonDialog.findViewById(R.id.tvSubmit).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String rating = ratingBar.getRating() > 0 ? String.valueOf(ratingBar.getRating()) : "";
                addComment(productId, rating, editText.getText().toString());
            }
        });

        mCommentonDialog.show();
    }


    private void addComment(String productId, String commentLevel, String content) {

        ObjectRequest obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_COMMENT_ADD, Object.class, OrderHtpUtil.getCommentAdd(productId, commentLevel, content));
        obj.setResponseListener(new ObjectResponse() {

            @Override
            public void onPre() {
                showLoading();
            }

            @Override
            public void onSuccess(Object tag, Object o) {

                hideLoading();
                if (mCommentonDialog != null)
                    mCommentonDialog.dismiss();

                showToast(R.string.commenton_success);
            }

            @Override
            public void onError(Object tag, String msg) {

                hideLoading();
                showToast(msg);
            }
        });

        addRequestNoCache(obj);
    }

    /**
     * 登录的回掉
     *
     * @param event
     */
    public void onEventMainThread(LoginStatusEvent event) {

        initViewLoad();
    }

    public void onEventMainThread(OrderStatusEvent event) {

        // 支付成功、下单成功、删除订单成功，返回该页面都需要刷新
        mNeedToRefresh = true;
    }

    /**
     * 处理界面的加载状态还是显示登录界面
     */
    private void initViewLoad() {
        if (JoyApplication.isLogin()) {
            setSwipeRefreshEnable(true);

            if (mLoginTipView != null)
                removeCustomView(mLoginTipView);

            executeCacheAndRefresh();
        } else {
            //设置界面为提示登录
            setNotLoginView();
        }
    }

    /**
     * 设置没有登录的界面提示
     */
    private void setNotLoginView() {

        setSwipeRefreshEnable(false);
        if (mLoginTipView == null)
            mLoginTipView = new LoginTipView(this.getActivity(), R.string.order_no_login, R.string.order_no_login_sub);
        if (getAdapter() != null && !getAdapter().isEmpty()) {
            getAdapter().clear();
            getAdapter().notifyDataSetChanged();
        }
        removeCustomView(mLoginTipView);
        addCustomView(mLoginTipView);
    }


}
