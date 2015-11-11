package com.joy.app.utils;

import android.os.Environment;

import java.io.File;

/**
 * 一些目录相关,缓存目录的记录
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-10
 */
public class StorageUtil {

    private static final String WEBVIEW_DIR = "/joy/webview_cache/";

    public static String getWebViewCachePath() {
        final String path = new StringBuilder(Environment.getExternalStorageDirectory().toString()).append(WEBVIEW_DIR)
                .toString();
        final File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return path;
    }
}
