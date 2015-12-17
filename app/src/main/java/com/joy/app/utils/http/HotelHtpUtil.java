package com.joy.app.utils.http;

import android.os.Bundle;

import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TextUtil;
import com.joy.app.bean.hotel.HotelParams;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author litong  <br>
 * @Description 酒店    <br>
 */
public class HotelHtpUtil extends BaseHtpUtil {

    public static ObjectRequest getHotelListRequest(HotelParams hotelParams, int page, int count, Class clazz) {

        Map<String, String> params = getBaseParams();
        //必传参数
        params.put("city_id",hotelParams.getCityId());//城市id
        params.put("checkin",hotelParams.getCheckIn());//入住时间
        params.put("checkout", hotelParams.getCheckOut());//离开时间
        params.put("from_key", hotelParams.getFrom_key());//aid
        //可选参数
        params.put("orderby", String.valueOf(hotelParams.getOrderby()));
        if (TextUtil.isNotEmpty(hotelParams.getHotel())){
            params.put("hotel", URLEncoder.encode(hotelParams.getHotel()));
        }
        if (hotelParams.getArea_id() != 0){
            params.put("area_id",String.valueOf(hotelParams.getArea_id()));
        }
        if (TextUtil.isNotEmpty(hotelParams.getFacilities_ids())){
            params.put("facilities_ids", hotelParams.getFacilities_ids());
        }

        if (TextUtil.isNotEmpty(hotelParams.getStar_ids())){
            params.put("star_ids",hotelParams.getStar_ids());
        }
        if (TextUtil.isNotEmpty(hotelParams.getPrice_rangs())){
            params.put("price_rangs",hotelParams.getPrice_rangs().replace(",不限",""));
        }
        params.put(KEY_COUNT, count + "");
        params.put(KEY_PAGE, page + "");

        return createGetRequest(URL_GET_HOTEL_LIST, params, clazz);
    }

    /**
     *
     *
     * @return
     */
    public static ObjectRequest getFilterRequest(Class clazz) {

        Map<String, String> params = getBaseParams();

        return createGetRequest(URL_GET_SEARCH_FILTERS, params, clazz);

    }

    /**
     *
     *
     * @return
     */
    public static ObjectRequest getAutoCompleteRequest(String city_id ,String keyword,Class clazz) {

        Map<String, String> params = getBaseParams();
        params.put("city_id", city_id);
        params.put("keyword", keyword);
        return createGetRequest(URL_GET_SEARCH_HOTEL_AUTOCOMPLATE, params, clazz);

    }
}
