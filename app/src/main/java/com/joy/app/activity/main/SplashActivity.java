package com.joy.app.activity.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.joy.app.JoyApplication;
import com.joy.app.R;

/**
 * 闪屏处理,是否要打开引导页
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-09
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_act_splash);
        delayStartMainActivity();
        initUser();
    }

    /**
     * 这里把用户的数据进行提取
     */
    private void initUser() {

        if (JoyApplication.getUser() == null) {
            //这界面以后,就通过islogin来判断是否登录了.
            try {
                JoyApplication.setUser(JoyApplication.getCommonPrefs().getUser());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void delayStartMainActivity() {

        new Handler() {

            @Override
            public void handleMessage(Message msg) {

                if (!isFinishing())
                    finishToEnterActivity();
            }
        }.sendEmptyMessageDelayed(0, 2000);
    }

    private void finishToEnterActivity() {
        try {
            if (JoyApplication.getCommonPrefs().isNewVersion()) {

                GuideSplashActivity.startActivity(this);
            } else {

                MainActivity.startActivity(this);
                //            TabTestActivity.startActivity(this);
                //            LvTestActivity.startActivity(this);
                //            LvLoadMoreTestActivity.startActivity(this);
            }
            finish();
        } catch (Exception ex) {
            ex.printStackTrace();
            MainActivity.startActivity(this);
        }
    }
}