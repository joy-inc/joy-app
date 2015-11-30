package com.joy.app.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.joy.app.BuildConfig;
import com.joy.app.activity.common.WebViewActivity;
import com.joy.app.adapter.MainRouteRvAdapter;
import com.joy.app.bean.MainRoute;
import com.joy.app.bean.sample.HotCityItem;
import com.joy.app.utils.http.MainHttpUtil;
import com.android.library.activity.BaseHttpRvFragment;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.ToastUtil;
import com.joy.app.utils.http.ReqFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页列表,旅行目的地规划
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-16
 */
public class MainFragment extends BaseHttpRvFragment<List<MainRoute>> {

    public static MainFragment instantiate(Context context) {

        return (MainFragment) Fragment.instantiate(context, MainFragment.class.getName(), new Bundle());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        executeCacheAndRefresh();
    }

    @Override
    protected void initContentView() {

        MainRouteRvAdapter adapter = new MainRouteRvAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<MainRoute>() {

            @Override
            public void onItemViewClick(int position, View clickView, MainRoute hotCityItem) {

                ToastUtil.showToast("open deatil" + hotCityItem.getCn_name());
                WebViewActivity.startActivity(MainFragment.this.getContext(), hotCityItem.getPlace_url(), hotCityItem.getCn_name());
            }
        });
        setAdapter(adapter);
    }

    @Override
    protected ObjectRequest<List<MainRoute>> getObjectRequest() {


        ObjectRequest req = ReqFactory.newPost(MainHttpUtil.URL_POST_MAIN_ROUTE_LIST, MainRoute.class, MainHttpUtil.getMainRouteList(getPageIndex(), getPageLimit()));
        if (BuildConfig.DEBUG) {
            List<MainRoute> list = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                MainRoute route = new MainRoute();
                route.setPic_url("http://pic.qyer.com/album/user/495/23/RUBQQBkGYw/index/300x200");
                route.setCn_name("第一名称" + i);
                route.setEn_name("name" + i);
                route.setTags("日式,美食,旅游,日式,美食,旅游" + i);
                route.setType((i + 1) / 2 == 0 ? 1 : 2);
                route.setPlace_url("http://www.qq.com");
                list.add(route);
            }
            req.setData(list);
        }
        return req;
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        showToast("error: " + msg);
    }
}
