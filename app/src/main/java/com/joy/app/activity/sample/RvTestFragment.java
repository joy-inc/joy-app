package com.joy.app.activity.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.joy.app.adapter.sample.CityRvAdapter;
import com.joy.app.bean.sample.HotCityItem;
import com.joy.app.httptask.sample.TestHtpUtil;
import com.joy.library.activity.frame.BaseHttpRvFragment;
import com.joy.library.adapter.frame.OnItemViewClickListener;
import com.joy.library.adapter.frame.OnItemViewLongClickListener;
import com.joy.library.httptask.frame.ObjectRequest;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/11/10.
 */
public class RvTestFragment extends BaseHttpRvFragment<List<HotCityItem>> {

    public static RvTestFragment instantiate(Context context) {

        return (RvTestFragment) Fragment.instantiate(context, RvTestFragment.class.getName(), new Bundle());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        executeRefresh();
    }

    @Override
    protected void initContentView() {

        CityRvAdapter adapter = new CityRvAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<HotCityItem>() {

            @Override
            public void onItemViewClick(int position, View clickView, HotCityItem hotCityItem) {

                DetailTestActivity.startActivity(getActivity(), clickView, hotCityItem.getPhoto());
            }
        });
        adapter.setOnItemViewLongClickListener(new OnItemViewLongClickListener<HotCityItem>() {

            @Override
            public void onItemViewLongClick(int position, View clickView, HotCityItem hotCityItem) {

                showToast(hotCityItem.getCnname());
            }
        });
        setAdapter(adapter);
    }

    @Override
    protected ObjectRequest<List<HotCityItem>> getObjectRequest() {

        return new ObjectRequest(TestHtpUtil.getHotCityListUrl(), HotCityItem.class);
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        showToast("error: " + msg);
    }
}