package com.joy.app.activity.hotel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TimeUtil;
import com.joy.app.R;
import com.joy.app.bean.hotel.HotelParams;
import com.joy.app.bean.hotel.HotelSearchFilters;
import com.joy.app.utils.Consts;
import com.joy.app.utils.hotel.HotelTimeUtil;
import com.joy.app.utils.http.HotelHttpUtil;

/**
 * @author litong  <br>
 * @Description 城市酒店列表    <br>
 */
public class CityHotelListActivity extends BaseHttpUiActivity<HotelSearchFilters> {
    HotelSearchFilters hotelSearchFilters;
    HotelParams params;
    /**
     * @param activity
     * @param cityId
     * @param cityName
     * @param fromKey  只传入城市信息和Aid
     *                 默认时间为今天和明天
     *                 无onresult的返回
     */
    public static void startActivity(Activity activity, String cityId, String cityName, String fromKey) {

        long[] days = HotelTimeUtil.getLastOffsetDay(Consts.MONTH_DAY_OFFSET);
        startActivity(activity,cityId,cityName,fromKey,days[0],days[1]);
    }
    public static void startActivity(Activity activity, String cityId, String cityName, String fromKey,long startDay,long endDay) {

        Intent intent = new Intent(activity, CityHotelListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("cityName", cityName);
        bundle.putString("cityId", cityId);
        bundle.putLong("startDay", startDay);
        bundle.putLong("endDay", endDay);
        bundle.putString("fromKey", fromKey);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_hotel_list);
    }

    @Override
    protected void initData() {
        params = new HotelParams();
        params.setCityId(getIntent().getStringExtra("cityId"));
        params.setFrom_key(getIntent().getStringExtra("fromKey"));
        params.setCheckIn(getIntent().getLongExtra("startDay",System.currentTimeMillis()));
        params.setCheckOut(getIntent().getLongExtra("endDay",System.currentTimeMillis()));
    }

    @Override
    protected boolean invalidateContent(HotelSearchFilters hotelSearchFilters) {
        this.hotelSearchFilters = hotelSearchFilters;
        return true;
    }

    @Override
    protected ObjectRequest getObjectRequest() {

        return HotelHttpUtil.getFilterRequest(HotelSearchFilters.class);
    }
}
