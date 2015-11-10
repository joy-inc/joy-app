package com.joy.app.activity.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.joy.app.R;
import com.joy.app.adapter.sample.CityAdapter;
import com.joy.app.bean.sample.HotCityItem;
import com.joy.app.httptask.sample.TestHtpUtil;
import com.joy.library.activity.frame.BaseHttpLvFragment;
import com.joy.library.httptask.frame.ObjectRequest;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/11/10.
 */
public class ListTestFragment extends BaseHttpLvFragment<List<HotCityItem>> {

    public static ListTestFragment instantiate(Context context) {

        return (ListTestFragment) Fragment.instantiate(context, ListTestFragment.class.getName(), new Bundle());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        executeRefresh();
    }

    @Override
    protected void initContentView() {

        setAdapter(new CityAdapter());
        setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                View v = view.findViewById(R.id.sdvPhoto);
                String url = ((CityAdapter) getAdapter()).getItem(position).getPhoto();
                DetailTestActivity.startActivity(getActivity(), v, url);
            }
        });
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