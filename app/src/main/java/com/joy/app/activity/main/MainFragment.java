package com.joy.app.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.joy.app.BuildConfig;
import com.joy.app.activity.sample.DetailTestActivity;
import com.joy.app.adapter.MainRouteRvAdapter;
import com.joy.app.adapter.sample.CityRvAdapter;
import com.joy.app.bean.MainRoute;
import com.joy.app.bean.sample.HotCityItem;
import com.joy.app.httptask.MainHtpUtil;
import com.joy.app.httptask.sample.TestHtpUtil;
import com.joy.library.activity.frame.BaseHttpRvFragment;
import com.joy.library.activity.frame.BaseUiFragment;
import com.joy.library.adapter.frame.OnItemViewClickListener;
import com.joy.library.httptask.frame.ObjectRequest;
import com.joy.library.utils.ToastUtil;

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
        executeRefresh();
    }

    @Override
    protected void initContentView() {

        MainRouteRvAdapter adapter = new MainRouteRvAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<MainRoute>() {

            @Override
            public void onItemViewClick(int position, View clickView, MainRoute hotCityItem) {

                ToastUtil.showToast("open deatil" + hotCityItem.getCn_name());
            }
        });
        setAdapter(adapter);
    }

    @Override
    protected ObjectRequest<List<MainRoute>> getObjectRequest() {

        ObjectRequest obj = new ObjectRequest(MainHtpUtil.getMainRouteList(1, 10), HotCityItem.class);
        if (BuildConfig.DEBUG) {
            List<MainRoute> list = new ArrayList<MainRoute>();
            for(int i=0;i<20;i++){
                MainRoute route=new MainRoute();
                route.setPic_url("http://pic.qyer.com/album/user/495/23/RUBQQBkGYw/index/300x200");
                route.setCn_name("第一名称" + i);
                route.setEn_name("name" + i);
                route.setPlace_url("http://www.qyer.com");
                list.add(route);
            }
            obj.setData(list);
        }
        return obj;
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        showToast("error: " + msg);
    }
}
