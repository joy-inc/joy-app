package com.joy.app.activity.hotel;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.android.library.utils.TimeUtil;
import com.android.library.view.ExBaseWidget;
import com.android.library.widget.JTextView;
import com.joy.app.R;
import com.joy.app.utils.hotel.HotelTimeUtil;

import java.util.Calendar;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class HotelFooterWidget extends ExBaseWidget implements View.OnClickListener {
    View filter,sort,day;
    TextView checkIn,checkOut,count;
    public HotelFooterWidget(Activity activity) {
        super(activity);
    }

    @Override
    protected void setContentView(View v) {
        super.setContentView(v);
        filter = v.findViewById(R.id.ll_hotel_filter);
        filter.setOnClickListener(this);
        sort = v.findViewById(R.id.ll_hotel_list_sort);
        sort.setOnClickListener(this);
        day = v.findViewById(R.id.rl_select_time);
        day.setOnClickListener(this);
        checkIn = (JTextView) v.findViewById(R.id.qtv_start_date);
        checkOut = (JTextView) v.findViewById(R.id.qtv_end_date);
        count = (JTextView) v.findViewById(R.id.qtv_total_nights);
    }


    public void initDate(long startDate, long endDate){

        checkIn.setText(TimeUtil.getFormatMonthDay(startDate));

        checkOut.setText(TimeUtil.getFormatMonthDay(endDate));
        count.setText(String.format("共%d晚",HotelTimeUtil.getDaysBettweenCalendar(startDate, endDate)));
    }


    @Override
    public void onClick(View v) {

        callbackWidgetViewClickListener(v);
    }


}
