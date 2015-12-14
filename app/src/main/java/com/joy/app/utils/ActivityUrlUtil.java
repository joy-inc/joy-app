package com.joy.app.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.android.library.utils.TextUtil;
import com.joy.app.activity.city.CityActivity;
import com.joy.app.activity.city.CityFunActivity;
import com.joy.app.activity.common.WebViewActivity;
import com.joy.app.activity.hotel.CityHotelListActivity;
import com.joy.app.activity.hotel.HotelSearchFilterActivity;
import com.joy.app.activity.plan.UserPlanListActivity;
import com.joy.app.activity.poi.PoiDetailActivity;
import com.joy.app.utils.http.HtpApi;

/**
 * 处理url打开对应的activity或者处理事件
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-10
 */
public class ActivityUrlUtil {

    private static final String BASE_HOST = "joy.4awork.com/";
    private static final int TYPE_CITY_TOPIC = 1;//城市详情
    private static final String URL_CITY_TOPIC = BASE_HOST + "place.joy.com/city/";//城市详情

    private static final int TYPE_POI_DETAIL = 2;//POI详情
    private static final String URL_POI_DETAIL = BASE_HOST + "place.joy.com/poi/";//POI详情

    private static final int TYPE_PRODUCT_DETAIL =3 ;//POI详情
    private static final String URL_PRODUCT_DETAIL = BASE_HOST + "topic/product/detail/";//POI详情

    //    private static final int TYPE_COUNTRY_TOPIC = 1;//国家路线专题
    //
    //    private static final String URL_COUNTRY_TOPIC = "topic.joy.com/country/";//国家路线专题
    //    private static final int TYPE_HOLTER_DETAIL = 4;//酒店详情
    //    private static final String URL_HOLTER_DETAIL = "hotel.joy.com/";//酒店详情
    //
    //    private static final int TYPE_TICKETS = 5;//景点门票
    //    private static final String URL_TICKETS = "topic.joy.com/tickets/";//景点门票
    //
    //    private static final int TYPE_VISA = 6;//签证
    //    private static final String URL_VISA = "topic.joy.com/visa/";//签证
    //
    //    private static final int TYPE_TRANSPORT = 7;//交通
    //    private static final String URL_TRANSPORT = "topic.joy.com/transport/";//交通
    //
    //    private static final int TYPE_WIFI = 8;//wifi
    //    private static final String URL_WIFI = "topic.joy.com/communication/";//wifi
    //
    //    private static final int TYPE_JOY = 9;//玩乐
    //    private static final String URL_JOY = "topic.joy.com/joy/";//玩乐
    //
    //    private static final int TYPE_HOLTER = 10;//酒店
    //    private static final String URL_HOLTER = "topic.joy.com/hotel/";//酒店
    //
    //    private static final int TYPE_FOOD = 11;// 美食
    //    private static final String URL_FOOD = "topic.joy.com/food/";// 美食
    //
    //    private static final int TYPE_SHOPPING = 12;//购物
    //    private static final String URL_SHOPPING = "topic.joy.com/shopping/";//购物
    //
    //    private static final int TYPE_ROUTE = 13;//行程
    //    private static final String URL_ROUTE = "topic.joy.com/route/";//行程专题

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

    public static int getUrlType(String url) {
        if (TextUtil.isEmpty(url)) {
            return -1;
        }
        if (checkUrl(url, URL_CITY_TOPIC)) {
            return TYPE_CITY_TOPIC;
        } else if (checkUrl(url, URL_POI_DETAIL)) {
            return TYPE_POI_DETAIL;
        }else if (checkUrl(url, URL_PRODUCT_DETAIL)) {
            return TYPE_PRODUCT_DETAIL;
        }
        //        if (checkUrl(url, URL_COUNTRY_TOPIC)) {
        //            return TYPE_COUNTRY_TOPIC;
        //        } else if (checkUrl(url, URL_CITY_TOPIC)) {
        //            return TYPE_CITY_TOPIC;
        //        } else if (checkUrl(url, URL_POI_DETAIL)) {
        //            return TYPE_POI_DETAIL;
        //        } else if (checkUrl(url, URL_HOLTER_DETAIL)) {
        //            return TYPE_HOLTER_DETAIL;
        //        } else if (checkUrl(url, URL_TICKETS)) {
        //            return TYPE_TICKETS;
        //        } else if (checkUrl(url, URL_VISA)) {
        //            return TYPE_VISA;
        //        } else if (checkUrl(url, URL_TRANSPORT)) {
        //            return TYPE_TRANSPORT;
        //        } else if (checkUrl(url, URL_WIFI)) {
        //            return TYPE_WIFI;
        //        } else if (checkUrl(url, URL_JOY)) {
        //            return TYPE_JOY;
        //        } else if (checkUrl(url, URL_HOLTER)) {
        //            return TYPE_HOLTER;
        //        } else if (checkUrl(url, URL_FOOD)) {
        //            return TYPE_FOOD;
        //        } else if (checkUrl(url, URL_SHOPPING)) {
        //            return TYPE_SHOPPING;
        //        } else if (checkUrl(url, URL_ROUTE)) {
        //            return TYPE_ROUTE;
        //        }
        return -1;
    }

    public static boolean startActivityByHttpUrl(Context context, String url) {

        int urlType = getUrlType(url);
        switch (urlType) {

            //--打开原生页
            case TYPE_CITY_TOPIC:
                CityActivity.startActivity(context, UrlUtil.getLastParameter(url));
                return true;
            case TYPE_POI_DETAIL:
                PoiDetailActivity.startActivity(context, UrlUtil.getLastParameter(url));
                return true;
            case TYPE_PRODUCT_DETAIL:
                PoiDetailActivity.startActivity(context, UrlUtil.getLastParameter(url));
                return true;


        }
        return false;
    }

    private static boolean checkUrl(String url, String url1) {
        if (url.startsWith("http") || url.startsWith("https")) {
            if (url.indexOf(url1) > 0) {
                return true;
            }
        }
        return false;
    }


}
