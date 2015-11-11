package com.joy.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 处理url打开对应的activity或者处理事件
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-10
 */
public class ActivityUrlUtil {
    public static boolean startUriActivity(Context context, String uriStr, boolean newTask) {

        try {

            Uri uri = Uri.parse(uriStr);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if(newTask)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
            return true;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    public static boolean startActivityByHttpUrl(Context context,String url){
        return true;
    }

}
