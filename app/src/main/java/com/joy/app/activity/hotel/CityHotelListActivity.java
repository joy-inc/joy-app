package com.joy.app.activity.hotel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.library.httptask.ObjectRequest;
import com.android.library.ui.activity.BaseHttpUiActivity;
import com.android.library.utils.TextUtil;
import com.android.library.view.ExBaseWidget;
import com.android.library.view.bottomsheet.JBottomSheetRvDialog;
import com.joy.app.R;
import com.joy.app.activity.common.DayPickerActivity;
import com.joy.app.adapter.hotel.HotelSortAdapter;
import com.joy.app.bean.hotel.HotelParams;
import com.joy.app.bean.hotel.HotelSearchFilters;
import com.joy.app.utils.JoyConstant;
import com.joy.app.utils.hotel.HotelTimeUtil;
import com.joy.app.utils.http.HotelHtpUtil;

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
    HotelFooterWidget footer;

    /**
     * @param activity
     * @param cityId
     * @param fromKey  只传入城市信息和Aid
     *                 默认时间为今天和明天
     *                 无onresult的返回
     */
    public static void startActivity(Context activity, String cityId, String fromKey) {

        long[] days = HotelTimeUtil.getLastOffsetDay(JoyConstant.MONTH_DAY_OFFSET);

        startActivity(activity, TextUtil.filterEmpty(cityId,"50"), TextUtil.filterEmpty(fromKey,JoyConstant.HOTEL_FROM_KEY), days[0], days[1]);
//        startActivity(activity, "50", fromKey, days[0], days[1]);
    }

    public static void startActivity(Context activity, String cityId, String fromKey, long startDay, long endDay) {

        Intent intent = new Intent(activity, CityHotelListActivity.class);
        Bundle bundle = new Bundle();
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
        if (resultCode != RESULT_OK)return;
        if ( REQ_DAY_PICK == requestCode ) {

            long start = data.getLongExtra(DayPickerActivity.REQ_EXTRA_KEY_BEGIN_DATE, 0);
            long end = data.getLongExtra(DayPickerActivity.REQ_EXTRA_KEY_END_DATE, 0);
            footer.initDate(start, end);
            params.setCheckInMills(start);
            params.setCheckOutMills(end);
            hotelListFragment.reLoadHotelList(params);
        }
//        if ( REQ_FILTER == requestCode ) {
//            String facilities = data.getStringExtra(HotelSearchFilterActivity.EX_KEY_HOTEL__FACILITIES_TYPE_STR);
//            String price[] = data.getStringArrayExtra(HotelSearchFilterActivity.EX_KEY_HOTEL_PRICES_TYPE);
//            String star = data.getStringExtra(HotelSearchFilterActivity.EX_KEY_HOTEL_STAR_TYPE_STR);
//            params.setFacilities_ids(hotelSearchFilters.getFacilitiesIds(facilities));
//            params.setStar_ids(hotelSearchFilters.getStarIds(star));
//            params.setStars(star);
//            params.setFacilities(facilities);
//            params.setPrice_rangs(price);
//        }
        hotelListFragment.reLoadHotelList(params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_hotel_list);
        executeRefreshOnly();
    }

    @Override
    protected void initData() {
        params = new HotelParams();
        params.setCityId(getIntent().getStringExtra("cityId"));
        params.setFrom_key(getIntent().getStringExtra("fromKey"));
        params.setCheckIn(getIntent().getLongExtra("startDay", System.currentTimeMillis()));
        params.setCheckOut(getIntent().getLongExtra("endDay", System.currentTimeMillis()));
        params.setOrderby(1);
    }
    TextView title ;
    @Override
    protected void initTitleView() {
        addTitleLeftBackView();
        title = addTitleMiddleView("");
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        footer = new HotelFooterWidget(this);
        footer.setContentView(findViewById(R.id.llFooterView));
        footer.initDate(params.getCheckInMills(), params.getCheckOutMills());
        footer.setOnWidgetViewClickListener(this);
        hotelListFragment = HotelListFragment.instantiate(this, params);
//        hotelListFragment.setListener(new RecyclerView.OnScrollListener(){
//            boolean isDragging = false;
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                isDragging = (RecyclerView.SCROLL_STATE_DRAGGING== newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (!isDragging)return;
//                if (dy >0){
//                    //TODO
//                    LogMgr.i("down");
//                }else{
//                    LogMgr.i("up");
//                }
//
//            }
//        });
        addFragment(R.id.fl_content, hotelListFragment);
    }

    public void showtitle(String str){
        title.setText(String.format("%s酒店",str));
    }

    private void showDialog() {

        JBottomSheetRvDialog jbsDialog = new JBottomSheetRvDialog(this);

        HotelSortAdapter adapter = new HotelSortAdapter();
        adapter.setData(hotelSearchFilters.getOrderby());
        adapter.setSelect(params.getOrderby());
        adapter.setOnItemViewClickListener((position, clickView, filterItems) -> {
            jbsDialog.dismiss();
            params.setOrderby(filterItems.getValue());
            hotelListFragment.reLoadHotelList(params);
        });

        jbsDialog.setAdapter(adapter);
        jbsDialog.show();
    }

    @Override
    protected boolean invalidateContent(HotelSearchFilters hotelSearchFilters) {
        this.hotelSearchFilters = hotelSearchFilters;
//        hotelListFragment.reLoadHotelList(params);
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
//                HotelSearchFilterActivity.startActivityForResult(this,REQ_FILTER,hotelSearchFilters.getFacilities(),hotelSearchFilters.getStars(),params.getFacilities(),params.getStars(),params.getPrice());
                HotelFilterBottomSheetDialog dialog = new HotelFilterBottomSheetDialog(this);
                dialog.setDatas(hotelSearchFilters.getFacilities(), hotelSearchFilters.getStars(), params.getPrice(), params.getFacilities(), params.getStars());
                dialog.setCallback((facilities, prices, star) -> {
                    params.setFacilities_ids(hotelSearchFilters.getFacilitiesIds(facilities));
                    params.setStar_ids(hotelSearchFilters.getStarIds(star));
                    params.setStars(star);
                    params.setFacilities(facilities);
                    params.setPrice_rangs(prices);
                    hotelListFragment.reLoadHotelList(params);
                });
                dialog.show();
                break;
            case R.id.ll_hotel_list_sort:
                showDialog();
            default:
                break;
        }
    }
}
