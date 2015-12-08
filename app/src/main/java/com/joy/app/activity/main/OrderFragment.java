package com.joy.app.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.Gravity;
import android.view.View;

import com.android.library.activity.BaseHttpRvFragment;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.view.dialogplus.DialogPlus;
import com.android.library.view.dialogplus.ViewHolder;
import com.joy.app.R;
import com.joy.app.activity.poi.OrderPayActivity;
import com.joy.app.activity.poi.PoiDetailActivity;
import com.joy.app.adapter.MainOrderRvAdapter;
import com.joy.app.bean.MainOrder;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;

import java.util.List;

/**
 * 我的订单
 * 如果外部刷新,会通过eventbus来进行通知
 * 这里在加载的时候,需要判断是否已经登录了.没登录的界面逻辑和登录的界面逻辑不一样
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-16
 */
public class OrderFragment extends BaseHttpRvFragment<List<MainOrder>> {

    private DialogPlus mCommentonDialog;

    public static OrderFragment instantiate(Context context) {

        return (OrderFragment) Fragment.instantiate(context, OrderFragment.class.getName(), new Bundle());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        executeCacheAndRefresh();
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

                    showToast(data.getOrder_id() + " To Order Detail");
                    PoiDetailActivity.startActivity(getActivity(), data.getProduct_id());
                }
            }
        });
        setAdapter(adapter);
    }

    @Override
    protected ObjectRequest<List<MainOrder>> getObjectRequest(int pageIndex, int pageLimit) {

        ObjectRequest obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_ORDER_LIST, MainOrder.class, OrderHtpUtil.getOrderListUrl(pageIndex, pageLimit, "0"));

        return obj;
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        super.onHttpFailed(tag, msg);
        showToast(msg);
    }

    private void showCommentonDialog(final String productId) {

        mCommentonDialog = DialogPlus.newDialog(getActivity())
                .setContentHolder(new ViewHolder(R.layout.view_order_commenton))
                .setGravity(Gravity.CENTER)
                .create();

        final AppCompatRatingBar ratingBar = (AppCompatRatingBar) mCommentonDialog.findViewById(R.id.acRatingBar);
        final AppCompatEditText editText = (AppCompatEditText) mCommentonDialog.findViewById(R.id.acetContent);

        mCommentonDialog.findViewById(R.id.tvSubmit).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                addComment(productId, String.valueOf(ratingBar.getRating()), editText.getText().toString());
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
}
