package com.joy.app.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.joy.app.adapter.MainOrderRvAdapter;
import com.joy.app.adapter.plan.UserPlanAdapter;
import com.joy.app.bean.MainOrder;
import com.joy.app.bean.MainRoute;
import com.joy.app.bean.plan.PlanFolder;
import com.joy.app.httptask.OrderHtpUtil;
import com.joy.app.httptask.PlanHttpUtil;
import com.joy.library.activity.frame.BaseHttpRvFragment;
import com.joy.library.activity.frame.BaseUiFragment;
import com.joy.library.adapter.frame.OnItemViewClickListener;
import com.joy.library.httptask.frame.ObjectRequest;

import java.util.List;

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
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        executeRefresh();
    }

    @Override
    protected void initContentView() {

        super.initContentView();
        UserPlanAdapter adapter = new UserPlanAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<PlanFolder>() {

            @Override
            public void onItemViewClick(int position, View clickView, PlanFolder planFolder) {

            }
        });
        setAdapter(adapter);
    }

    @Override
    protected ObjectRequest<List<PlanFolder>> getObjectRequest() {

        return new ObjectRequest(PlanHttpUtil.getUserPlanFolderUrl(getCurrentPageIndex(), 5), PlanFolder.class);
    }
}
