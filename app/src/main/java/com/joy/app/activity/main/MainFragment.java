package com.joy.app.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.library.httptask.ObjectRequest;
import com.android.library.ui.fragment.BaseHttpRvFragment;
import com.android.library.utils.TextUtil;
import com.android.library.view.recyclerview.ItemAddAnimator;
import com.joy.app.activity.city.CityActivity;
import com.joy.app.activity.common.WebViewActivity;
import com.joy.app.adapter.MainRouteRvAdapter;
import com.joy.app.bean.MainRoute;
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
        setPageLimit(5);
        executeCacheAndRefresh();
    }

    @Override
    protected void initContentView() {

        MainRouteRvAdapter adapter = new MainRouteRvAdapter();
        adapter.setOnItemViewClickListener((position, clickView, mainRoute) -> {

            if (mainRoute != null) {
                if (mainRoute.isCity()) {
                    String cityId = mainRoute.getCityId();
                    if (!TextUtil.isEmpty(cityId)) {
                        CityActivity.startActivity(getActivity(), cityId);
                    }
                } else {
                    WebViewActivity.startActivityNoTitle(getActivity(), mainRoute.getPlace_url(),WebViewActivity.TYPE_CITY_TOPIC);
                }
            }
        });
        setAdapter(adapter);
        getRecyclerView().setItemAnimator(new ItemAddAnimator());
    }

    @Override
    protected ObjectRequest<List<MainRoute>> getObjectRequest(int pageIndex, int pageLimit) {

        return ReqFactory.newPost(MainHtpUtil.URL_POST_MAIN_ROUTE_LIST, MainRoute.class, MainHtpUtil.getMainRouteList(pageIndex, pageLimit));
    }
}