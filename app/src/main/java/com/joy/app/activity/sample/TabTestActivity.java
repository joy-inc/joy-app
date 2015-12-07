package com.joy.app.activity.sample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.android.library.activity.BaseTabActivity;
import com.android.library.activity.BaseUiFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.JoyApplication;
import com.joy.app.R;

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
    protected void initTitleView() {

        super.initTitleView();

        setTitleLogo(R.drawable.ic_logo);

        View v = inflateLayout(R.layout.view_avatar);
        SimpleDraweeView sdvAvatar = (SimpleDraweeView) v.findViewById(R.id.sdvAvatar);
        sdvAvatar.setImageURI(Uri.parse("http://static.qyer.com/data/avatar/000/66/51/28_avatar_big.jpg?v=1423838207"));
        addTitleRightView(v, new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initContentView() {

        super.initContentView();
        setTabTextColors(getResources().getColor(R.color.white_trans26), getResources().getColor(R.color.white));
        setTabIndicatorColor(getResources().getColor(R.color.transparent));
        setFloatActionBtnEnable(R.drawable.abc_ic_search_api_mtrl_alpha, new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showToast("FloatActionBtn click");
            }
        });
    }

    @Override
    protected List<? extends BaseUiFragment> getFragments() {

        List<BaseUiFragment> fragments = new ArrayList<>();
        fragments.add(RvTestFragment.instantiate(this).setLableText("目的地"));
        fragments.add(RvTestFragment.instantiate(this).setLableText("旅行规划"));
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
}