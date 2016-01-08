package com.joy.app;

import android.location.Location;

import com.android.library.BaseApplication;
import com.android.library.utils.LogMgr;
import com.android.library.utils.TextUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.joy.app.bean.User;
import com.joy.app.utils.LocationUtil;
import com.joy.app.utils.XiaomiUtil;
import com.joy.app.utils.prefs.CommonPrefs;
import com.joy.library.share.ShareConstant;

/**
 * Created by KEVIN.DAI on 15/7/8.
 */
public class JoyApplication extends BaseApplication {

    private static User mUser;
    private static LocationUtil locationUtil;

    @Override
    public void onCreate() {

        super.onCreate();
        try {
            initApplication();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initApplication() {

        Fresco.initialize(getContext());
        initShareInfo();
        locationUtil = new LocationUtil(this);
//        locationUtil.getOnceLocation();//获取一次位置
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

    public static Location getLocation() {
        if (locationUtil != null) {
            return locationUtil.getLastKnownLocation();
        } else
            return null;
    }

    public static LocationUtil getLocationUtil() {
        return locationUtil;
    }
    public static String getLocationLatitude() {
        if (getLocation() == null){
            return TextUtil.TEXT_EMPTY;
        }else{
            return String.valueOf(getLocation().getLatitude());
        }
    }
    public static String getLocationLongitude() {
        if (getLocation() == null){
            return TextUtil.TEXT_EMPTY;
        }else{
            return String.valueOf(getLocation().getLongitude());
        }
    }

    /**
     * 直接获取用户token
     *
     * @return
     */
    public static String getUserToken() {

        if (mUser != null) {
            return mUser.getToken();
        }
        return "";
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
     * 直接获取显示的用户名
     *
     * @return
     */
    public static String getNickname() {

        if (mUser != null) {
            return mUser.getNickname();
        }
        return "";
    }

    /**
     * 初始化分享的常量信息
     */
    private void initShareInfo() {

        ShareConstant.getIns().setWeiboAppid("1720673958");
        ShareConstant.getIns().setWeiboSecret("121aa6378e89dba7d6fc290fdb40e669");
        ShareConstant.getIns().setWeiboUrl("http://sns.whalecloud.com/sina2/callback");
        //        ShareConstant.getIns().setWeiboScope("email,direct_messages_read,direct_messages_write," + "friendships_groups_read,friendships_groups_write,statuses_to_me_read," + "follow_app_official_microblog," + "invitation_write");
        ShareConstant.getIns().setWeiboScope("email," + "follow_app_official_microblog");

        ShareConstant.getIns().setWeixinAppid("wx4f5cdafa5eaf4ca0");
        ShareConstant.getIns().setWeixinSecret("d4624c36b6795d1d99dcf0547af5443d");
    }
}