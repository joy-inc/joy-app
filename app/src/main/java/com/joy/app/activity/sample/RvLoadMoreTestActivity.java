package com.joy.app.activity.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.library.ui.activity.BaseHttpRvActivity;
import com.android.library.httptask.ObjectRequest;
import com.joy.app.adapter.sample.RvLoadMoreAdapter;
import com.joy.app.bean.sample.Special;
import com.joy.app.utils.http.sample.TestHtpUtil;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/12/2.
 */
public class RvLoadMoreTestActivity extends BaseHttpRvActivity<List<Special>> {

    public static void startActivity(Activity act) {

        if (act != null)
            act.startActivity(new Intent(act, RvLoadMoreTestActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setPageLimit(10);
        executeCacheAndRefresh();
    }

    @Override
    protected void initTitleView() {

        setTitle("rv loadmore sample");
    }

    @Override
    protected void initContentView() {

        setAdapter(new RvLoadMoreAdapter());
    }

    @Override
    protected ObjectRequest<List<Special>> getObjectRequest(int pageIndex, int pageLimit) {

        return ObjectRequest.get(TestHtpUtil.getSpecialListUrl(pageIndex, pageLimit), Special.class);
    }
}