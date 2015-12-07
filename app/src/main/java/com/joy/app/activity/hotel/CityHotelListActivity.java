package com.joy.app.activity.hotel;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TextUtil;
import com.android.library.view.ExBaseWidget;
import com.android.library.view.dialogplus.DialogPlus;
import com.joy.app.R;
import com.joy.app.activity.common.DayPickerActivity;
import com.joy.app.bean.hotel.HotelParams;
import com.joy.app.bean.hotel.HotelSearchFilters;
import com.joy.app.utils.Consts;
import com.joy.app.utils.hotel.HotelTimeUtil;
import com.joy.app.utils.http.HotelHtpUtil;

import java.util.Map;

/**
 * @author litong  <br>
 * @Description 城市酒店列表    <br>
 */
public class CityHotelListActivity extends BaseHttpUiActivity<HotelSearchFilters> implements ExBaseWidget.OnWidgetViewClickListener {
    HotelSearchFilters hotelSearchFilters;
    HotelParams params;
    HotelListFragment hotelListFragment;
    final int REQ_DAY_PICK = 101;
    final int REQ_FILTER = 102;
    FooterWidget footer;

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
        startActivity(activity, cityId, cityName, fromKey, days[0], days[1]);
    }

    public static void startActivity(Activity activity, String cityId, String cityName, String fromKey, long startDay, long endDay) {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQ_DAY_PICK == requestCode) {
            long start = data.getLongExtra(DayPickerActivity.REQ_EXTRA_KEY_BEGIN_DATE, 0);
            long end = data.getLongExtra(DayPickerActivity.REQ_EXTRA_KEY_END_DATE, 0);
            footer.initDate(start, end);
        } else if (REQ_FILTER == requestCode) {
            String facilities = data.getStringExtra(HotelSearchFilterActivity.EX_KEY_HOTEL__FACILITIES_TYPE_STR);
            String price[] = data.getStringArrayExtra(HotelSearchFilterActivity.EX_KEY_HOTEL_PRICES_TYPE);
            String star = data.getStringExtra(HotelSearchFilterActivity.EX_KEY_HOTEL_STAR_TYPE_STR);
            params.setFacilities_ids(facilities);
            params.setStar_ids(star);
            //TODO
//            params.setPrice_rangs();
        }
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
        params.setCheckIn(getIntent().getLongExtra("startDay", System.currentTimeMillis()));
        params.setCheckOut(getIntent().getLongExtra("endDay", System.currentTimeMillis()));
    }

    @Override
    protected void initTitleView() {
        addTitleLeftBackView();
        addTitleMiddleView(getIntent().getStringExtra("cityName") + "酒店");
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        footer = new FooterWidget(this);
        footer.setContentView(findViewById(R.id.llFooterView));
        footer.initDate(params.getCheckInMills(), params.getCheckOutMills());
        footer.setOnWidgetViewClickListener(this);
        hotelListFragment = HotelListFragment.instantiate(this, params);
        addFragment(R.id.fl_content, hotelListFragment);
    }


    private void showDialog() {

//        final DialogPlus mDialog = new DialogPlus.newDialog(this);
//        mDialog.setTitleText(getString(R.string.sort));
//        mDialog.setTitleColor(getResources().getColor(R.color.hotel_search_line));
//        HotelListSortTypeAdapter adapter = new HotelListSortTypeAdapter(mSortTypes, mLastSelectedOrder);
//
//        // 设置list最大高度
//        if (adapter.getCount() > 6)
//            mDialog.setListHeight(DensityUtil.dip2px(6 * 46 + 14));
//
//        mDialog.setAdapter(adapter);
//        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                mIsShowDialog = false;
//            }
//        });
//
//        mDialog.setListOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //排序刷新列表
//                if(mSortTypes == null)
//                    return;
//
//                mLastSelectedOrder = mSortTypes.get(position);
//                mDialog.dismiss();
//                executeFrameRefresh();
//            }
//        });
//
//        mDialog.show();
//        mIsShowDialog = true;
    }

    @Override
    protected boolean invalidateContent(HotelSearchFilters hotelSearchFilters) {
        this.hotelSearchFilters = hotelSearchFilters;
        return true;
    }

    @Override
    protected ObjectRequest getObjectRequest() {

        return HotelHtpUtil.getFilterRequest(HotelSearchFilters.class);
    }

    @Override
    public void onWidgetViewClick(View v) {
        switch (v.getId()) {
            case R.id.rl_select_time:
                DayPickerActivity.startHotelDayPickerForResult(this, true, params.getCheckInMills(), params.getCheckOutMills(), REQ_DAY_PICK);
                break;
            case R.id.ll_hotel_filter:
                //TODO
//                HotelSearchFilterActivity.startActivityForResult(this,REQ_FILTER,hotelSearchFilters.getFacilities(),hotelSearchFilters.getStars(),params.ge);
            case R.id.ll_hotel_list_sort:

            default:

        }

    }
}
