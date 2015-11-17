package com.joy.app.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.joy.app.activity.sample.DetailTestActivity;
import com.joy.app.adapter.MainRouteRvAdapter;
import com.joy.app.adapter.sample.CityRvAdapter;
import com.joy.app.bean.MainRoute;
import com.joy.app.bean.sample.HotCityItem;
import com.joy.app.httptask.sample.TestHtpUtil;
import com.joy.library.activity.frame.BaseHttpRvFragment;
import com.joy.library.activity.frame.BaseUiFragment;
import com.joy.library.adapter.frame.OnItemViewClickListener;
import com.joy.library.httptask.frame.ObjectRequest;
import com.joy.library.utils.ToastUtil;

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

                ToastUtil.showToast("open deatil"+hotCityItem.getName());
            }
        });
        setAdapter(adapter);
    }

    @Override
    protected ObjectRequest<List<MainRoute>> getObjectRequest() {

        return new ObjectRequest(TestHtpUtil.getHotCityListUrl(), HotCityItem.class);
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        showToast("error: " + msg);
    }
}
