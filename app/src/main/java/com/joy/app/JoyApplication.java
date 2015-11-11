package com.joy.app;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.joy.app.utils.prefs.CommonPrefs;
import com.joy.library.BaseApplication;
import com.joy.library.utils.LogMgr;

/**
 * Created by KEVIN.DAI on 15/7/8.
 */
public class JoyApplication extends BaseApplication {

    @Override
    public void onCreate() {

        super.onCreate();
        initApplication();
    }

    private void initApplication() {

        Fresco.initialize(getContext());
        if (BuildConfig.DEBUG) {

            LogMgr.turnOn();
        } else {

            LogMgr.turnOff();
        }
    }

    public static void releaseForExitApp() {

//        Fresco.shutDown();
        release();
        getCommonPrefs().releaseInstance();
    }

    public static CommonPrefs getCommonPrefs() {

        return CommonPrefs.getInstance(getContext());
    }

    /**
     * 用户是否登录
     *
     * @return
     */
    public static boolean isLogin() {

        return false;
    }

    /**
     * 直接获取用户token
     *
     * @return
     */
    public static String getUserToken() {

        return "";
    }
}