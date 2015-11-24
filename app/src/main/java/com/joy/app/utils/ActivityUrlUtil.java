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

    private static final int TYPE_COUNTRY_TOPIC = 1;//国家路线专题
    private static final String URL_COUNTRY_TOPIC = "topic.joy.com/country/";//国家路线专题

    private static final int TYPE_CITY_TOPIC = 2;//城市详情
    private static final String URL_CITY_TOPIC = "place.joy.com/city/";//城市详情

    private static final int TYPE_POI_DETAIL = 3;//POI详情
    private static final String URL_POI_DETAIL = "place.joy.com/poi/";//POI详情

    private static final int TYPE_HOLTER_DETAIL = 4;//酒店详情
    private static final String URL_HOLTER_DETAIL = "hotel.joy.com/";//酒店详情

    private static final int TYPE_TICKETS = 5;//景点门票
    private static final String URL_TICKETS = "topic.joy.com/tickets/";//景点门票

    private static final int TYPE_VISA = 6;//签证
    private static final String URL_VISA = "topic.joy.com/visa/";//签证

    private static final int TYPE_TRANSPORT = 7;//交通
    private static final String URL_TRANSPORT = "topic.joy.com/transport/";//交通

    private static final int TYPE_WIFI = 8;//wifi
    private static final String URL_WIFI = "topic.joy.com/communication/";//wifi

    private static final int TYPE_JOY = 9;//玩乐
    private static final String URL_JOY = "topic.joy.com/joy/";//玩乐

    private static final int TYPE_HOLTER = 10;//酒店
    private static final String URL_HOLTER = "topic.joy.com/hotel/";//酒店

    private static final int TYPE_FOOD = 11;// 美食
    private static final String URL_FOOD = "topic.joy.com/food/";// 美食

    private static final int TYPE_SHOPPING = 12;//购物
    private static final String URL_SHOPPING = "topic.joy.com/shopping/";//购物

    private static final int TYPE_ROUTE = 13;//行程
    private static final String URL_ROUTE = "topic.joy.com/route/";//行程

    public static boolean startUriActivity(Context context, String uriStr, boolean newTask) {

        try {

            Uri uri = Uri.parse(uriStr);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (newTask)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
            return true;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    public static boolean startActivityByHttpUrl(Context context, String url) {
        return true;
    }


}
