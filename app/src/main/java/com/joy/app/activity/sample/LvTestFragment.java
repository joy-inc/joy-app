package com.joy.app.activity.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.joy.app.R;
import com.joy.app.adapter.sample.CityLvAdapter;
import com.joy.app.bean.sample.HotCityItem;
import com.joy.app.httptask.sample.TestHtpUtil;
import com.joy.library.activity.frame.BaseHttpLvFragment;
import com.joy.library.adapter.frame.OnItemViewClickListener;
import com.joy.library.httptask.frame.ObjectRequest;

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
        executeRefresh();
    }

    @Override
    protected void initContentView() {

        CityLvAdapter adapter = new CityLvAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<HotCityItem>() {

            @Override
            public void onItemViewClick(int position, View clickView, HotCityItem hotCityItem) {

                DetailTestActivity.startActivity(getActivity(), clickView.findViewById(R.id.sdvPhoto), hotCityItem.getId(), hotCityItem.getPhoto());
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