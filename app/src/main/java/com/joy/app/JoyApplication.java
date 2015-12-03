package com.joy.app;

import com.android.library.utils.TextUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.joy.app.bean.User;
import com.joy.app.utils.XiaomiUtil;
import com.joy.app.utils.prefs.CommonPrefs;
import com.android.library.BaseApplication;
import com.joy.library.share.ShareConstant;
import com.android.library.utils.LogMgr;

/**
 * Created by KEVIN.DAI on 15/7/8.
 */
public class JoyApplication extends BaseApplication {

    private static User mUser;

    @Override
    public void onCreate() {

        super.onCreate();
        initApplication();
    }

    private void initApplication() {

        Fresco.initialize(getContext());
        initShareInfo();
        XiaomiUtil.registerMiPush(this);
        if (BuildConfig.DEBUG) {

            LogMgr.turnOn();
        } else {

            LogMgr.turnOff();
        }
    }

    public static void releaseForExitApp() {

        //        Fresco.shutDown();
        release();
        CommonPrefs.releaseInstance();
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

        return !TextUtil.isEmpty(getUserToken());
    }

    /**
     * 直接获取用户token
     *
     * @return
     */
    public static String getUserToken() {

        if (mUser != null) {
            return mUser.getToken();
        } return "";
    }

    /**
     * 获取用户对象
     */
    public static User getUser() {

        return mUser;
    }

    /**
     * 设置用户对象
     *
     * @param user
     */
    public static void setUser(User user) {
        mUser = user;
    }

    /**
     * 初始化分享的常量信息
     */
    private void initShareInfo() {

        ShareConstant.getIns().setQqZoneAppid("1");
        ShareConstant.getIns().setQqZoneKey("1");
        ShareConstant.getIns().setQqZoneUrl("http://www.qq.com");

        ShareConstant.getIns().setWeixinAppid("1");
        ShareConstant.getIns().setWeixinSecret("1");
    }
}