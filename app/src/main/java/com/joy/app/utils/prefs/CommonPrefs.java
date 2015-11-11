package com.joy.app.utils.prefs;

import android.content.Context;

import com.joy.library.utils.ExSharedPrefs;

/**
 * app内关于app的一些信息记录
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-09
 */
public class CommonPrefs {

    private final String KEY_VERSION_CODE = "version_code";//版本号


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

    public static void releaseInstance(){

        if(mSettingPrefs != null)
            mSettingPrefs = null;
    }

    /**
     * 获取存储的版本号，如果没有返回0
     * @return
     */
    public int getVersionCode(){

        return mExSharedPrefs.getInt(KEY_VERSION_CODE, 0);
    }

    /**
     * 判断存储的版本号是否小于versionCode
     * @param versionCode
     * @return
     */
    public boolean isVersionCodeLessThan(int versionCode){

        return getVersionCode() < versionCode;
    }

    /**
     * 如果存储的版本号小于versionCode，则存储新的versionCode
     * @param versionCode
     */
    public void saveVersionCodeIfLessThan(int versionCode){

        if(isVersionCodeLessThan(versionCode))
            mExSharedPrefs.putInt(KEY_VERSION_CODE, versionCode);
    }
}
