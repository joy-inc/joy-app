package com.joy.app.activity.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.library.activity.BaseHttpRvFragment;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.joy.app.JoyApplication;
import com.joy.app.activity.plan.UserPlanListActivity;
import com.joy.app.activity.poi.OrderDetailActivity;
import com.joy.app.adapter.plan.UserPlanAdapter;
import com.joy.app.bean.plan.PlanFolder;
import com.joy.app.eventbus.LoginStatusEvent;
import com.joy.app.utils.http.PlanHtpUtil;
import com.joy.app.view.LoginTipView;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 我的旅行计划
 * 如果外部刷新,会通过eventbus来进行通知
 * 这里在加载的时候,需要判断是否已经登录了.没登录的界面逻辑和登录的界面逻辑不一样
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-16
 */
public class TravelPlanFragment extends BaseHttpRvFragment<List<PlanFolder>> {

    public static TravelPlanFragment instantiate(Context context) {

        return (TravelPlanFragment) Fragment.instantiate(context, TravelPlanFragment.class.getName(), new Bundle());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_OK) {
            executeRefreshOnly();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);

        initViewLoad();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    /**
     * 处理界面的加载状态还是显示登录界面
     */
    private void initViewLoad() {
        if (JoyApplication.isLogin()) {
            executeRefreshOnly();
        } else {
            //设置界面为提示登录
            setNotLoginView();
        }
    }

    /**
     * 设置没有登录的界面提示
     */
    private void setNotLoginView() {

        LoginTipView loginTipView = new LoginTipView(this.getActivity());

    }


    /**
     * 登录的回掉
     *
     * @param event
     */
    public void onEventMainThread(LoginStatusEvent event) {

        initViewLoad();
    }

    @Override
    protected void initContentView() {

        super.initContentView();
        final UserPlanAdapter adapter = new UserPlanAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<PlanFolder>() {
            @Override
            public void onItemViewClick(int position, View clickView, PlanFolder planFolder) {

                if (planFolder != null)
                    UserPlanListActivity.startActivityById(getActivity(), planFolder.getFolder_id(), planFolder.getFolder_name(), 1);
            }
        });
        setAdapter(adapter);
    }

    @Override
    protected ObjectRequest<List<PlanFolder>> getObjectRequest(int pageIndex, int pageLimit) {

        ObjectRequest obj = PlanHtpUtil.getUserPlanFolderRequest(PlanFolder.class, pageLimit, pageIndex);
        return obj;
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        showToast("TravelPlanFragment error: " + msg);
    }
}
