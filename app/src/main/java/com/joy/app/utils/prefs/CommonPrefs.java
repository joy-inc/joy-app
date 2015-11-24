package com.joy.app.utils.prefs;

import android.content.Context;

import com.joy.app.BuildConfig;
import com.android.library.utils.ExSharedPrefs;

/**
 * app内关于app的一些信息记录
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-09
 */
public class CommonPrefs {

    private static final String KEY_VERSION_CODE = "version_code";//版本号
    private static CommonPrefs mSettingPrefs;
    private ExSharedPrefs mExSharedPrefs;

    private CommonPrefs(Context context) {

        mExSharedPrefs = new ExSharedPrefs(context, "joy_app_android");
    }

    public static CommonPrefs getInstance(Context context) {

        if (mSettingPrefs == null)
            mSettingPrefs = new CommonPrefs(context);

        return mSettingPrefs;
    }

    public static void releaseInstance() {

        if (mSettingPrefs != null)
            mSettingPrefs = null;
    }

    /**
     * 获取存储的版本号，如果没有返回1
     *
     * @return
     */
    public int getVersionCode() {

        return mExSharedPrefs.getInt(KEY_VERSION_CODE, 1);
    }

    /**
     * 判断是否为新安装版本
     *
     * @return
     */
    public boolean isNewVersion() {

        return getVersionCode() < BuildConfig.VERSION_CODE;
    }

    /**
     * 如果存储的版本号小于versionCode，则存储新的versionCode
     */
    public void saveVersionCodeIfNeed() {

        if (isNewVersion())
            mExSharedPrefs.putInt(KEY_VERSION_CODE, BuildConfig.VERSION_CODE);
    }
}