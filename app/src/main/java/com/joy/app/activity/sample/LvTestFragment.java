package com.joy.app.activity.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.library.ui.fragment.BaseHttpLvFragment;
import com.android.library.listener.OnItemClickListener;
import com.android.library.httptask.ObjectRequest;
import com.joy.app.R;
import com.joy.app.adapter.sample.CityLvAdapter;
import com.joy.app.bean.sample.HotCityItem;
import com.joy.app.utils.http.sample.TestHtpUtil;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/11/10.
 */
public class LvTestFragment extends BaseHttpLvFragment<List<HotCityItem>> {

    public static LvTestFragment instantiate(Context context) {

        return (LvTestFragment) Fragment.instantiate(context, LvTestFragment.class.getName(), new Bundle());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setLoadMoreEnable(false);
        executeRefreshOnly();
    }

    @Override
    protected void initContentView() {

        CityLvAdapter adapter = new CityLvAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener<HotCityItem>() {

            @Override
            public void onItemClick(int position, View clickView, HotCityItem hotCityItem) {

                DetailTestActivity.startActivity(getActivity(), clickView.findViewById(R.id.sdvPhoto), hotCityItem.getId(), hotCityItem.getPhoto());
            }
        });
        setAdapter(adapter);
    }

    @Override
    protected ObjectRequest<List<HotCityItem>> getObjectRequest(int pageIndex, int pageLimit) {

        return ObjectRequest.get(TestHtpUtil.getHotCityListUrl(), HotCityItem.class);
    }
}