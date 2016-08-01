package com.joy.app.activity.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.library.httptask.ObjectRequest;
import com.android.library.ui.activity.BaseHttpRvActivity;
import com.android.library.view.recyclerview.ItemAddAnimator;
import com.android.library.view.recyclerview.ItemDecoration;
import com.joy.app.adapter.sample.RvLoadMoreAdapter;
import com.joy.app.bean.sample.Special;
import com.joy.app.utils.http.sample.TestHtpUtil;

import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

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
        executeRefreshOnly();
    }

    @Override
    protected void initTitleView() {

        setTitle("rv loadmore sample");
    }

    @Override
    protected void initContentView() {

        setPageLimit(10);
        setAdapter(new RvLoadMoreAdapter());
        getRecyclerView().setItemAnimator(new ItemAddAnimator(getPageLimit()));
        setOnItemClickListener((holder, position) -> showSnackbar("item: " + position));
        getRecyclerView().addItemDecoration(
                new ItemDecoration.Builder(this)
                        .orientation(HORIZONTAL)
                        .marginTop(DP_1_PX * 10)
                        .marginBottom(DP_1_PX * 10)
                        .marginLeft(DP_1_PX * 16)
                        .paddingParentTop(DP_1_PX * 16)
                        .paddingParentBottom(DP_1_PX * 16)
                        .paddingParentLeft(DP_1_PX * 16)
                        .build());
    }

    @Override
    protected ObjectRequest<List<Special>> getObjectRequest(int pageIndex, int pageLimit) {

        return ObjectRequest.get(TestHtpUtil.getSpecialListUrl(pageIndex, pageLimit), Special.class);
    }
}