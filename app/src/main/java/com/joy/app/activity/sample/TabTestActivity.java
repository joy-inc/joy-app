package com.joy.app.activity.sample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.library.ui.activity.BaseTabActivity;
import com.android.library.ui.fragment.BaseUiFragment;
import com.android.library.utils.CollectionUtil;
import com.android.library.widget.FrescoImageView;
import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KEVIN.DAI on 15/11/10.
 */
public class TabTestActivity extends BaseTabActivity {

    public static void startActivity(Activity act) {

        if (act != null)
            act.startActivity(new Intent(act, TabTestActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        if (savedInstanceState != null) {
//
//            final FragmentManager fm = getSupportFragmentManager();
//            final Fragment f1 = fm.getFragment(savedInstanceState, MvpTestFragment.TAG);
//            final Fragment f2 = fm.getFragment(savedInstanceState, MvpTestRvFragment.TAG);
//            final Fragment f3 = fm.getFragment(savedInstanceState, RvTestFragment.TAG);
//
//            LogMgr.d("daisw", "onCreate\n" +
//                    f1 + "\n" +
//                    f2 + "\n" +
//                    f3);
//
//            initActComponent(f1, f2);
//        } else {

        initActComponent(getFragment(0), getFragment(1));
//        }

        RxPermissions.getInstance(this)
                .request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(granted -> {
                    if (granted) {// Always true pre-M

                        showToast("granted");
                    } else {

                        showToast("not granted");
                    }
                });
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//
//        super.onSaveInstanceState(outState);
//
//        final FragmentManager fm = getSupportFragmentManager();
//        final BaseUiFragment f1 = getFragment(0);
//        final BaseUiFragment f2 = getFragment(1);
//        final BaseUiFragment f3 = getFragment(2);
//
//        LogMgr.i("daisw", "onSaveInstanceState\n" +
//                f1.isAdded() + " - " + f1 + "\n" +
//                f2.isAdded() + " - " + f2 + "\n" +
//                f3.isAdded() + " - " + f3);
//
//        if (f1.isAdded())
//            fm.putFragment(outState, MvpTestFragment.TAG, f1);
//        if (f2.isAdded())
//            fm.putFragment(outState, MvpTestRvFragment.TAG, f2);
//        if (f3.isAdded())
//            fm.putFragment(outState, RvTestFragment.TAG, f3);
//    }

    @Override
    protected void initTitleView() {

        super.initTitleView();

        setTitleLogo(R.drawable.ic_logo);

        View v = inflateLayout(R.layout.view_avatar);
        FrescoImageView sdvAvatar = (FrescoImageView) v.findViewById(R.id.sdvAvatar);
        sdvAvatar.setImageURI("http://static.qyer.com/data/avatar/000/66/51/28_avatar_big.jpg?v=1423838207");
        addTitleRightView(v, v1 -> showToast("Avatar click"));
    }

    @Override
    protected void initContentView() {

        super.initContentView();
        setTabTextColors(getResources().getColor(R.color.white_trans26), getResources().getColor(R.color.white));
        setTabIndicatorColor(getResources().getColor(R.color.transparent));
        setFloatActionBtnEnable(R.drawable.abc_ic_search_api_mtrl_alpha, v -> showToast("FloatActionBtn click"));
    }

    @Override
    protected List<? extends BaseUiFragment> getFragments() {

        List<BaseUiFragment> fragments = new ArrayList<>();

        List<Fragment> fs = getSupportFragmentManager().getFragments();
        if (CollectionUtil.isNotEmpty(fs)) {

            for (Fragment f : fs)
                if (f instanceof MvpTestFragment) {
                    fragments.add(((MvpTestFragment) f).setLableText("目的地"));
                    break;
                }
            for (Fragment f : fs)
                if (f instanceof MvpTestRvFragment) {
                    fragments.add(((MvpTestRvFragment) f).setLableText("旅行规划"));
                    break;
                }
            for (Fragment f : fs)
                if (f instanceof RvTestFragment) {
                    fragments.add(((RvTestFragment) f).setLableText("订单"));
                    break;
                }
        }

        if (fragments.size() == 0)
            fragments.add(MvpTestFragment.instantiate(this).setLableText("目的地"));
        if (fragments.size() == 1)
            fragments.add(MvpTestRvFragment.instantiate(this).setLableText("旅行规划"));
        if (fragments.size() == 2)
            fragments.add(RvTestFragment.instantiate(this).setLableText("订单"));

        return fragments;
    }

    private long mLastPressedTime;

    @Override
    public void onBackPressed() {

        long currentPressedTime = System.currentTimeMillis();
        if (currentPressedTime - mLastPressedTime > 2000) {

            mLastPressedTime = currentPressedTime;
            showToast(R.string.toast_exit_tip);
        } else {

            super.onBackPressed();
            JoyApplication.releaseForExitApp();
        }
    }

    private TabTestComponent mComponent;

    private void initActComponent(Fragment... fs) {

        mComponent = DaggerTabTestComponent.builder()
                .appComponent(((JoyApplication) getApplication()).component())
                .tabTestModule(new TabTestModule(this))
                .mvpTestModule(new MvpTestModule((MvpTestFragment) fs[0]))
                .mvpTestRvModule(new MvpTestRvModule((MvpTestRvFragment) fs[1]))
                .build();
    }

    public TabTestComponent component() {

        return mComponent;
    }
}
