package com.joy.app.activity.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.library.ui.fragment.BaseHttpRvFragment2;
import com.android.library.ui.webview.BaseWebViewActivity;
import com.android.library.view.recyclerview.ItemAddAnimator;
import com.android.library.view.recyclerview.ItemDecoration;
import com.joy.app.adapter.sample.RvLoadMoreAdapter;

import javax.inject.Inject;

/**
 * Created by Daisw on 16/6/8.
 */
public class MvpTestRvFragment extends BaseHttpRvFragment2 implements MvpTestRvView {

    @Inject
    MvpTestRvPresenter mPresenter;

    public static MvpTestRvFragment instantiate(Context context) {

        return (MvpTestRvFragment) Fragment.instantiate(context, MvpTestRvFragment.class.getName(), new Bundle());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        ((TabTestActivity) getActivity()).component().inject(this);
        super.onActivityCreated(savedInstanceState);
        mPresenter.launchRefreshOnly();
    }

    @Override
    protected void initContentView() {

        mPresenter.setPageLimit(10);
        RvLoadMoreAdapter adapter = new RvLoadMoreAdapter();
        adapter.setOnItemClickListener((p, v, t) -> BaseWebViewActivity.startActivity(getActivity(), t.getUrl(), t.getTitle()));
        setAdapter(adapter);
        getRecyclerView().setItemAnimator(new ItemAddAnimator(mPresenter.getPageLimit()));
        getRecyclerView().addItemDecoration(
                new ItemDecoration.Builder(getActivity())
                        .dividerSize(0)
                        .verticalSpace(DP_1_PX * 10)
                        .paddingParent(DP_1_PX * 10)
                        .build());
    }

    @Override
    protected void doOnRetry() {

        mPresenter.launchRefreshOnly();
    }
}
