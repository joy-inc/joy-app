package com.joy.app.activity.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.library.ui.fragment.BaseHttpRvFragment2;
import com.android.library.view.recyclerview.ItemDecoration;
import com.joy.app.adapter.sample.RvLoadMoreAdapter;

import javax.inject.Inject;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

/**
 * Created by Daisw on 16/6/8.
 */
public class MvpTestRvFragment extends BaseHttpRvFragment2 implements MvpTestRvView {

    public static final String TAG = "MvpTestRvFragment";

    @Inject
    MvpTestRvPresenter mPresenter;

    public static MvpTestRvFragment instantiate(Context context) {

        return (MvpTestRvFragment) Fragment.instantiate(context, MvpTestRvFragment.class.getName(), new Bundle());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        ((TabTestActivity) getActivity()).component().inject(this);
        mPresenter.launchRefreshOnly();
    }

    @Override
    protected void initContentView() {

        setAdapter(new RvLoadMoreAdapter());
        getRecyclerView().addItemDecoration(
                new ItemDecoration.Builder(getActivity())
                        .orientation(HORIZONTAL)
                        .marginTop(DP_1_PX * 10)
                        .marginBottom(DP_1_PX * 10)
                        .marginLeft(DP_1_PX * 16)
//                        .margin(DP_1_PX * 10)
//                        .paddingParent(DP_1_PX * 16)
                        .paddingParentTop(DP_1_PX * 16)
                        .paddingParentBottom(DP_1_PX * 16)
                        .paddingParentLeft(DP_1_PX * 16)
                        .build());
    }

    @Override
    protected void doOnRetry() {

        mPresenter.launchRefreshOnly();
    }
}
