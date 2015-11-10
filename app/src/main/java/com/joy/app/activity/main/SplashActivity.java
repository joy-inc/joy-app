package com.joy.app.activity.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.app.activity.sample.ListTestActivity;
import com.joy.library.utils.AppUtil;


/**
 * 闪屏处理,是否要打开引导页
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-09
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        delayStartMainActivity();
    }

    private void delayStartMainActivity() {


        new Handler() {

            @Override
            public void handleMessage(Message msg) {

                if (!isFinishing()) {

                    finishToEnterActivity();
                }
            }
        }.sendEmptyMessageDelayed(0, 2000);
    }

    private void finishToEnterActivity() {

        if (JoyApplication.getCommonPrefs().isVersionCodeLessThan(AppUtil.getVersionCode())) {
            GuideSplashActivity.startActivity(this);
        } else {
            MainActivity.startActivity(this);
//            ListTestActivity.startActivity(this);
        }
        finish();
    }
}
