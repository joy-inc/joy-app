package com.joy.app.activity.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.library.activity.BaseHttpRvFragment;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.joy.app.adapter.sample.CityRvAdapter;
import com.joy.app.bean.sample.HotCityItem;
import com.joy.app.utils.http.ReqFactory;
import com.joy.app.utils.http.sample.TestHtpUtil;

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
        executeCacheAndRefresh();

//        ObjectRequest<List<Special>> req = new ObjectRequest<>(TestHtpUtil.getSpecialListUrl(getPageIndex(), getPageLimit()), Special.class);
//        req.setResponseListener(new ObjectResponse<List<Special>>() {
//
//            @Override
//            public void onSuccess(Object tag, List<Special> specials) {
//
//            }
//        });
//        addRequestNoCache(req);
    }

    @Override
    protected void initContentView() {

        CityRvAdapter adapter = new CityRvAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<HotCityItem>() {

            @Override
            public void onItemViewClick(int position, View clickView, HotCityItem hotCityItem) {

                DetailTestActivity2.startActivity(getActivity(), clickView, hotCityItem.getId(), hotCityItem.getPhoto(), hotCityItem.getCnname(), hotCityItem.getEnname());
            }
        });
        setAdapter(adapter);
    }

    @Override
    protected ObjectRequest<List<HotCityItem>> getObjectRequest(int pageIndex, int pageLimit) {

        return ReqFactory.newGet(TestHtpUtil.getHotCityListUrl(), HotCityItem.class);
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        showToast("error: " + msg);
    }
}