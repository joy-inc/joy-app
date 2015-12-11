package com.joy.app;

import com.android.library.BaseApplication;
import com.android.library.utils.LogMgr;
import com.android.library.utils.TextUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.joy.app.bean.User;
import com.joy.app.utils.XiaomiUtil;
import com.joy.app.utils.prefs.CommonPrefs;
import com.joy.library.share.ShareConstant;

/**
 * Created by KEVIN.DAI on 15/7/8.
 */
public class JoyApplication extends BaseApplication {

    private static User mUser;

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
        }
//        return "";
        //测试不要提交
                return "e4f6ed7c3acb5bbcd17f62f82a0effb22bc3c1b319b50f825a95dc3891af0aeb"; // test
//                return "41dd399909650830414ae7b0276d8dc2ae777fa2d552c5a4dd93fcd0040bce1e"; // 4awork
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
        ShareConstant.getIns().setWeiboScope("email," + "follow_app_official_microblog" );
        ShareConstant.getIns().setWeixinAppid("wx4f5cdafa5eaf4ca0");
        ShareConstant.getIns().setWeixinSecret("d4624c36b6795d1d99dcf0547af5443d");
    }
}