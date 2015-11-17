package com.joy.app.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.joy.app.bean.MainRoute;
import com.joy.library.activity.frame.BaseHttpRvFragment;
import com.joy.library.activity.frame.BaseUiFragment;
import com.joy.library.httptask.frame.ObjectRequest;

import java.util.List;

/**
 * 我的订单
 * 如果外部刷新,会通过eventbus来进行通知
 * 这里在加载的时候,需要判断是否已经登录了.没登录的界面逻辑和登录的界面逻辑不一样
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-16
 */
public class OrderFragment extends BaseHttpRvFragment<List<MainRoute>> {

    public static OrderFragment instantiate(Context context) {

        return (OrderFragment) Fragment.instantiate(context, OrderFragment.class.getName(), new Bundle());
    }

    @Override
    protected ObjectRequest<List<MainRoute>> getObjectRequest() {
        return null;
    }
}
