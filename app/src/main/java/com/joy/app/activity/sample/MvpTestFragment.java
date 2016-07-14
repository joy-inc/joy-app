package com.joy.app.activity.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.ui.fragment.BaseHttpUiFragment2;
import com.android.library.widget.FrescoImageView;
import com.joy.app.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by KEVIN.DAI on 16/1/23.
 */
public class MvpTestFragment extends BaseHttpUiFragment2 implements MvpTestView {

    public static final String TAG = "MvpTestFragment";
    public static final String KEY_CITY_ID = "KEY_CITY_ID";
    Unbinder unbinder;

    @Inject
    MvpTestPresenter mPresenter;

    @BindView(R.id.sdvPhoto)
    FrescoImageView mFivCover;

    @BindView(R.id.tvName)
    TextView mTvCoverTitle;

    public static MvpTestFragment instantiate(Context context) {

        Bundle bundle = new Bundle();
        bundle.putString(KEY_CITY_ID, "28");
        return (MvpTestFragment) Fragment.instantiate(context, MvpTestFragment.class.getName(), bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.t_frag_mvp);
        assert v != null;
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        ((TabTestActivity) getActivity()).component().inject(this);
        mPresenter.launchRefreshOnly(getArguments().getString(KEY_CITY_ID));
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void doOnRetry() {

        mPresenter.launchRefreshOnly(getArguments().getString(KEY_CITY_ID));
    }

    @Override
    public void setCoverImg(String imgUrl) {

        mFivCover.setImageURI(imgUrl);
    }

    @Override
    public void setCoverTitle(String title) {

        mTvCoverTitle.setText(title);
    }
}
