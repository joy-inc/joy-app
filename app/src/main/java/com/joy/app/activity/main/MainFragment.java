package com.joy.app.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.library.activity.BaseHttpRvFragment;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TextUtil;
import com.joy.app.activity.city.CityActivity;
import com.joy.app.activity.common.WebViewActivity;
import com.joy.app.activity.hotel.CityHotelListActivity;
import com.joy.app.activity.poi.OrderDetailActivity;
import com.joy.app.activity.poi.PoiDetailActivity;
import com.joy.app.adapter.MainRouteRvAdapter;
import com.joy.app.bean.MainRoute;
import com.joy.app.bean.sample.PoiDetail;
import com.joy.app.utils.JoyConstant;
import com.joy.app.utils.http.MainHtpUtil;
import com.joy.app.utils.http.ReqFactory;

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
            public void onItemViewClick(int position, View clickView, MainRoute mainRoute) {

                if (mainRoute != null) {
                    if (mainRoute.isCity()) {
                        String cityId = mainRoute.getCityId();
                        if (!TextUtil.isEmpty(cityId)) {
                            CityActivity.startActivity(getActivity(), cityId);
                        }
                    } else {
                        WebViewActivity.startActivity(getActivity(), mainRoute.getPlace_url(), mainRoute.getCn_name());
                    }
                }
            }
        });
        setAdapter(adapter);
//        CityHotelListActivity.startActivity(getActivity(),"50", JoyConstant.HOTEL_FROM_KEY);
    }

    @Override
    protected ObjectRequest<List<MainRoute>> getObjectRequest(int pageIndex, int pageLimit) {

        ObjectRequest req = ReqFactory.newPost(MainHtpUtil.URL_POST_MAIN_ROUTE_LIST, MainRoute.class, MainHtpUtil.getMainRouteList(pageIndex, pageLimit));
        return req;
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        showToast("MainFragment error: " + msg);
    }
}
