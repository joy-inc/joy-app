package com.joy.app.utils;

import android.os.Environment;

import java.io.File;

/**
 * 一些目录相关,缓存目录的记录
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-10
 */
public class StorageUtil {

    private static final String HOME_DIR = "/joy/";// 大应用主目录

    private static final String WEBVIEW_DIR = "/joy/webview_cache/";
    private static final String QYER_TEMP = HOME_DIR + "tmp";// 临时的目录,存放其他临时东西

    public static File getExStorageDir() {

        return Environment.getExternalStorageDirectory();
    }

    public static String getWebViewCachePath() {
        final String path = new StringBuilder(Environment.getExternalStorageDirectory().toString()).append(WEBVIEW_DIR)
                .toString();
        final File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return path;
    }

    public static File getQyerTempDir() {

        File qyerTemp = new File(getExStorageDir(), QYER_TEMP);
        if (!qyerTemp.exists()) {
            qyerTemp.mkdirs();
        }

        return qyerTemp;
    }
}
