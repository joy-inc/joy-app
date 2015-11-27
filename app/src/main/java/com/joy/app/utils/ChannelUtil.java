package com.joy.app.utils;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.preference.PreferenceManager;

import com.android.library.utils.TextUtil;
import com.joy.app.BuildConfig;
import com.joy.app.JoyApplication;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by KEVIN.DAI on 15/11/25.
 */
public class ChannelUtil {

    private static final String CHANNEL_KEY = "cztchannel";
    private static final String CHANNEL_VERSION_KEY = "cztchannel_version";
    private static String mChannel;

    public static String getChannel() {

        return getChannel(BuildConfig.FLAVOR);
    }

    private static String getChannel(String defaultChannel) {

        if (TextUtil.isNotEmpty(mChannel))
            return mChannel;

        mChannel = getChannelBySharedPreferences();
        if (TextUtil.isNotEmpty(mChannel))
            return mChannel;

        mChannel = getChannelFromApk(CHANNEL_KEY);
        if (TextUtil.isNotEmpty(mChannel)) {
            saveChannelBySharedPreferences(mChannel);
            return mChannel;
        }

        return defaultChannel;
    }

    private static String getChannelFromApk(String channelKey) {

        ApplicationInfo appinfo = JoyApplication.getContext().getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        String key = "META-INF/" + channelKey;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith(key)) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String[] split = ret.split("_");
        String channel = "";
        if (split.length >= 2)
            channel = ret.substring(split[0].length() + 1);

        return channel;
    }

    private static void saveChannelBySharedPreferences(String channel) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(JoyApplication.getContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CHANNEL_KEY, channel);
        editor.putInt(CHANNEL_VERSION_KEY, BuildConfig.VERSION_CODE);
        editor.apply();
    }

    private static String getChannelBySharedPreferences() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(JoyApplication.getContext());

        int versionCodeSaved = sp.getInt(CHANNEL_VERSION_KEY, -1);
        if (versionCodeSaved == -1 || versionCodeSaved != BuildConfig.VERSION_CODE)
            return "";

        return sp.getString(CHANNEL_KEY, "");
    }
}