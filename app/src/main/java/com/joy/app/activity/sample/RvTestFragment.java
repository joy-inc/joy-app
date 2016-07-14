package com.joy.app.activity.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.library.httptask.ObjectRequest;
import com.android.library.ui.fragment.BaseHttpRvFragment;
import com.joy.app.adapter.sample.CityRvAdapter;
import com.joy.app.bean.sample.HotCityItem;
import com.joy.app.utils.http.ReqFactory;
import com.joy.app.utils.http.sample.TestHtpUtil;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/11/10.
 */
public class RvTestFragment extends BaseHttpRvFragment<List<HotCityItem>> {

    public static final String TAG = "RvTestFragment";

    public static RvTestFragment instantiate(Context context) {

        return (RvTestFragment) Fragment.instantiate(context, RvTestFragment.class.getName(), new Bundle());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        executeCacheAndRefresh();
    }

    @Override
    protected void initContentView() {

        CityRvAdapter adapter = new CityRvAdapter();
        adapter.setOnItemViewClickListener((position, clickView, hotCityItem) -> {

//            DetailTestActivity.startActivity(getActivity(), clickView, hotCityItem.getId(), hotCityItem.getPhoto(), hotCityItem.getCnname(), hotCityItem.getEnname());
//            DetailTestActivity2.startActivity(getActivity(), clickView, hotCityItem.getId(), hotCityItem.getPhoto(), hotCityItem.getCnname(), hotCityItem.getEnname());
            DetailTestActivity3.startActivity(getActivity(), clickView, hotCityItem.getId(), hotCityItem.getPhoto(), hotCityItem.getCnname(), hotCityItem.getEnname());
        });
        setAdapter(adapter);
    }

    @Override
    protected ObjectRequest<List<HotCityItem>> getObjectRequest(int pageIndex, int pageLimit) {

        return ReqFactory.newGet(TestHtpUtil.getHotCityListUrl(), HotCityItem.class);
    }
}
