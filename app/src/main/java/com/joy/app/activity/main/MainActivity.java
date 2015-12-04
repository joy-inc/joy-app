package com.joy.app.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.android.library.activity.BaseTabActivity;
import com.android.library.activity.BaseUiFragment;
import com.joy.app.activity.common.DayPickerActivity;
import com.joy.app.activity.setting.SettingActivity;
import com.joy.app.eventbus.LoginStatusEvent;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 专题列表
 * 推送.或则外部应用打开,在这里处理
 * 模块控制油mMainActivityBC来处理
 * 推送等其他和ui唔关的由mMainActivityHelper 处理
 * 本activity相关的ui.直接在这里进行处理
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-09
 */
public class MainActivity extends BaseTabActivity {

    private MainActivityBC mMainActivityBC;
    private MainActivityHelperBC mMainActivityHelper;
    private long mLastPressedTime; //最后一次按返回的按钮
    private SimpleDraweeView mSimpleDraweeView;

    public static void startActivity(Context context) {

        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mMainActivityBC = new MainActivityBC(this);
        mMainActivityHelper = new MainActivityHelperBC(this);
        super.onCreate(savedInstanceState);

        mMainActivityHelper.onCreate(savedInstanceState);
        mMainActivityBC.onCreate(savedInstanceState);

    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        mMainActivityHelper.onNewIntent(intent);
    }

    @Override
    protected void onRestart() {

        super.onRestart();
        mMainActivityHelper.onRestart();
        mMainActivityBC.onRestart();
    }

    @Override
    protected void onPause() {

        super.onPause();
        mMainActivityHelper.onPause();
        mMainActivityBC.onPause();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mMainActivityHelper.onDestroy();
        mMainActivityBC.onDestroy();
    }

    @Override
    protected void initData() {

        super.initData();
        EventBus.getDefault().register(this);
        mMainActivityBC.initData();
    }

    @Override
    protected void initTitleView() {

        super.initTitleView();

        setTitle(null);
        setTitleLogo(R.drawable.ic_logo);

        View v = inflateLayout(R.layout.view_avatar);
        mSimpleDraweeView = (SimpleDraweeView) v.findViewById(R.id.sdvAvatar);
        handleUserLogin();
        addTitleRightView(v, new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SettingActivity.startActivity(MainActivity.this);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                //                calendar.add(Calendar.DAY_OF_MONTH, 2);
                //                Calendar calendar1 = Calendar.getInstance();
                //                calendar1.setTime(new Date());
                //                calendar1.add(Calendar.MONTH, 3);
                //                DayPickerActivity.startHotelDayPickerForResult(MainActivity.this,true,0,0,1);
//                DayPickerActivity.startOrderDayPickerForResult(MainActivity.this, 1446351132l * 1000, 1477973532l * 1000, 0, 1);
            }
        });
        mMainActivityBC.initTitleView();
    }


    @Override
    protected void initContentView() {

        super.initContentView();
        setTabIndicatorHeight(DP_1_PX * 3);
        mMainActivityBC.initContentView();
    }

    @Override
    protected List<? extends BaseUiFragment> getFragments() {

        return mMainActivityBC.getFragments();
    }

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

    /**
     * 登录的回掉
     *
     * @param event
     */
    public void onEventMainThread(LoginStatusEvent event) {
        handleUserLogin();
    }

    /**
     *
     */
    private void handleUserLogin() {

        if (JoyApplication.isLogin()) {
            mSimpleDraweeView.setImageResource(R.drawable.ic_joy_login);
        } else {
            mSimpleDraweeView.setImageResource(R.drawable.ic_main_def_user_head);

        }
    }


}