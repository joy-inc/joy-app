package com.joy.app.activity.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.android.library.activity.BaseUiActivity;
import com.android.library.utils.ViewUtil;
import com.joy.app.R;
import com.joy.app.view.dateView.DatePickerController;
import com.joy.app.view.dateView.DayPickerView;
import com.joy.app.view.dateView.SimpleMonthAdapter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期选择器,给酒店使用的.<br>
 * 1.自动保存最后一次选择的日期<br>
 * 2.返回值为时间戳<br>
 * 3.用户选择的日期返回关键字为 REQ_EXTRA_KEY_BEGIN_DATE,REQ_EXTRA_KEY_END_DATE<br>
 * 4.请传入背景颜色值
 * Created by Eric on 15/4/7.
 */
public class DayPickerActivity extends BaseUiActivity implements DatePickerController {
    //用户选择的开始日期
    public static final String REQ_EXTRA_KEY_BEGIN_DATE = "beginDate";
    //用户结束的日期

    public static final String REQ_EXTRA_KEY_END_DATE = "endDate";
    //默认开始时间
    private static final String EXTRA_KEY_LONG_INTIME = "intime";
    //默认结束时间
    private static final String EXTRA_KEY_LONG_OUTTIME = "outtime";
    //是否开始按钮点击
    private static final String EXTRA_KEY_BOOLEAN_START = "start";
    //标题
    private static final String EXTRA_KEY_INTEGER_TITLE = "title";
    //开始的toast描述
    private static final String EXTRA_KEY_INTEGER_START_TOAST = "start_toast";
    //借宿的taost描述
    private static final String EXTRA_KEY_INTEGER_END_TOAST = "end_toast";
    //在开始日期下得文字
    private static final String EXTRA_KEY_INTEGER_START_TIP = "start_tip";
    //再借宿日期下得文字
    private static final String EXTRA_KEY_INTEGER_END_TIP = "end_tip";
    //标题栏的颜色
    private static final String EXTRA_KEY_INTEGER_TITLE_COOR = "color";
    //status bar 的颜色
    private static final String EXTRA_KEY_INTEGER_STATUS_BAR_COLOR = "status_bar_color";
    //日期选中得颜色
    private static final String EXTRA_KEY_INTEGER_DAY_SELECT_COLOR = "day_select_color";
    //同时选择入住和离开的日期的文字描述
    private static final String EXTRA_KEY_STRING_SELECT_SAME_DAY = "day_select_same_day";
    //开始时间的区间
    private static final String EXTRA_KEY_LONG_SECTION_START_TIME = "section_start_time";
    //结束时间的区间
    private static final String EXTRA_KEY_LONG_SECTION_END_TIME = "section_end_time";


    //传入的颜色

    private int mColor = -1;
    //是否是开始点击的，在酒店上使用
    private boolean isStartClick = false;
    private DayPickerView mDayPickerView;
    private TextView mChoseTip;
    private TextView mDayShow;//当前的拉动的月份信息
    private boolean isOrderType = false;
    private Map<String, String> mDayInfo = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_day_picker);
    }

    @Override
    protected void initData() {

        mColor = getIntent().getIntExtra(EXTRA_KEY_INTEGER_TITLE_COOR, 0);
        isStartClick = getIntent().getBooleanExtra(EXTRA_KEY_BOOLEAN_START, true);
    }

    @Override
    protected void initTitleView() {
        setTitle("日期选择");
        addTitleLeftBackView();

        //        addTitleMiddleTextView(getIntent().getIntExtra(EXTRA_KEY_INTEGER_TITLE, 0));
        //        int iconResId = R.drawable.ic_back_white;
        //        if (getIntent().getBooleanExtra("isClose", true)) {
        //
        //            iconResId = R.drawable.ic_close;
        //        }
        //        addTitleLeftImageView(iconResId, new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //
        //                finish();
        //            }
        //        });
        //        setStatusBarColor(getResources().getColor(getIntent().getIntExtra(EXTRA_KEY_INTEGER_STATUS_BAR_COLOR, 0)));
        //        getTitleView().setBackgroundResource(mColor);

    }

    @Override
    protected void initContentView() {

        findViewById(R.id.div_ll_week).setBackgroundResource(mColor);
        mChoseTip = (TextView) findViewById(R.id.qtvChoseTip);

        mDayPickerView = (DayPickerView) findViewById(R.id.dyPickerView);
        mDayShow = (TextView) findViewById(R.id.qtvDay);
        String startTip = getResources().getString(getIntent().getIntExtra(EXTRA_KEY_INTEGER_START_TIP, 0));
        String endTip = getResources().getString(getIntent().getIntExtra(EXTRA_KEY_INTEGER_END_TIP, 0));
        mDayPickerView.setController(this);
        //        mDayPickerView.setChoseText(startTip, endTip);
        mDayPickerView.setSelectBackgroundColor(getIntent().getIntExtra(EXTRA_KEY_INTEGER_DAY_SELECT_COLOR, 0));
        mDayPickerView.setStartChose(isStartClick);

        //设置默认的月份值
        long begin = getIntent().getLongExtra(EXTRA_KEY_LONG_INTIME, 0);
        long end = getIntent().getLongExtra(EXTRA_KEY_LONG_OUTTIME, 0);
        Calendar calendarBegin = null;
        int startMonth = 0;
        if (begin > 0) {
            calendarBegin = Calendar.getInstance();
            calendarBegin.setTimeInMillis(begin);
            startMonth = calendarBegin.get(Calendar.MONTH);
            mDayPickerView.setStartTime(calendarBegin.get(Calendar.YEAR), startMonth, calendarBegin.get(Calendar.DAY_OF_MONTH));

        }
        if (end > 0) {
            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTimeInMillis(end);
            mDayPickerView.setEndTime(calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH));
        }
        long sectionStart = getIntent().getLongExtra(EXTRA_KEY_LONG_SECTION_START_TIME, 0);
        long sectionEnd = getIntent().getLongExtra(EXTRA_KEY_LONG_SECTION_END_TIME, 0);
        if (sectionStart > 0 && sectionEnd > 0) {
            isOrderType = true;
            Calendar sectionStartTimeCalendar = Calendar.getInstance();
            sectionStartTimeCalendar.setTimeInMillis(sectionStart);

            Calendar sectionEndTimeCalendar = Calendar.getInstance();
            sectionEndTimeCalendar.setTimeInMillis(sectionEnd);

            mDayPickerView.setSectionDay(sectionStartTimeCalendar.getTimeInMillis(), sectionEndTimeCalendar.getTimeInMillis());
            ViewUtil.hideView(mChoseTip);
        }

        //设置当前显示的月份
        Calendar today = Calendar.getInstance();
        int position = 0;
        if (calendarBegin != null) {
            if (calendarBegin.get(Calendar.YEAR) > today.get(Calendar.YEAR)) {
                position = (12 - today.get(Calendar.MONTH)) + startMonth;
            } else {
                position = startMonth - today.get(Calendar.MONTH);
            }
            if (position > 0 && position < mDayPickerView.getAdapter().getItemCount()) {
                mDayPickerView.scrollToPosition(position);
            }
        }
        //设置toas的显示内容

        if (isStartClick) {
            mChoseTip.setText(getIntent().getIntExtra(EXTRA_KEY_INTEGER_START_TOAST, 0));
        } else {
            mChoseTip.setText(getIntent().getIntExtra(EXTRA_KEY_INTEGER_END_TOAST, 0));
        }

        int sameDayResId = getIntent().getIntExtra(EXTRA_KEY_STRING_SELECT_SAME_DAY, 0);
        if (sameDayResId > 0) {
            mDayPickerView.setSameDayString(getResources().getString(sameDayResId));
        }

    }


    @Override
    public int getMaxYear() {

        return Calendar.getInstance().get(Calendar.YEAR) + 1;
    }

    @Override
    public String getDayInfo(int year, int month, int day) {

        if (mDayInfo != null && mDayInfo.size() > 0) {
            String dayKey = Integer.toString(year) + Integer.toString(month) + Integer.toString(day);
            if (mDayInfo.containsKey(dayKey)) {
                return mDayInfo.get(dayKey);
            }
        }
        return null;
    }

    @Override
    public void onDayOfMonthSelected(SimpleMonthAdapter.CalendarDay calendarDay) {

        if (calendarDay != null && !isOrderType) {
            mChoseTip.setText(getIntent().getIntExtra(EXTRA_KEY_INTEGER_END_TOAST, 0));
        }
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
        if (isOrderType) {
            if (selectedDays != null && selectedDays.getFirst() != null) {
                Intent intent = new Intent();
                long begin = selectedDays.getFirst().getDate().getTime();
                intent.putExtra(REQ_EXTRA_KEY_BEGIN_DATE, begin);
                intent.putExtra(REQ_EXTRA_KEY_END_DATE, 0);
                setResult(RESULT_OK, intent);
                time2Finish();
            }
        } else {
            if (selectedDays != null && selectedDays.getFirst() != null && selectedDays.getLast() != null) {
                Intent intent = new Intent();
                long begin = selectedDays.getFirst().getDate().getTime();
                long end = selectedDays.getLast().getMaxDayDate().getTime();
                intent.putExtra(REQ_EXTRA_KEY_BEGIN_DATE, begin);
                intent.putExtra(REQ_EXTRA_KEY_END_DATE, end);
                setResult(RESULT_OK, intent);
            }
            time2Finish();
        }
    }

    @Override
    public void onMonthShowChange(String title) {

        if (mDayShow.getVisibility() != View.VISIBLE) {

            ViewUtil.showView(mDayShow);
            //            ViewUtil.showView(findViewById(R.id.vLink));
        }

        mDayShow.setText(title);
    }


    //定时关闭界面
    private void time2Finish() {

        ((SimpleMonthAdapter) mDayPickerView.getAdapter()).setCanSelect(false);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        handler.postDelayed(runnable, 500);
    }

    /*
    * 根据自定义的需求，打开对应的选额器
    * @param activity act
    * @param color 对应的颜色值
    * @param startClick 是否是开启按钮点击了，主要用来告诉选择器用户点击的类型是开始还是结束
    * @param inTime 默认的开始时间
    * @param outTime 默认的离开时间
    * @param startToastResId 底部提示用户选择开始的描述
    * @param endToastResId 底部提示用户选额离开的描述
    * @param startTipResId 开始的日期下得文字描述
    * @param endTipResId 结束的日期下的文字描述2
    * @param titleResId 标题的资源ID
    * @param titleColorResid 任务栏颜色
    * @param requestCode 回调code
    * */
    public static void startActivityForResult(Activity activity, boolean startClick, long inTime, long outTime, int startToastResId, int endToastResId, int startTipResId, int endTipResId, int titleResId, int titleColorResid, int statusColorResId, int selectRecColorResId, int requestCode, int sameDayResId, boolean isClose, long sectionStartTime, long sectionEndTime) {

        Intent intent = new Intent();
        intent.setClass(activity, DayPickerActivity.class);
        intent.putExtra(EXTRA_KEY_BOOLEAN_START, startClick);
        intent.putExtra(EXTRA_KEY_LONG_INTIME, inTime);
        intent.putExtra(EXTRA_KEY_LONG_OUTTIME, outTime);
        intent.putExtra(EXTRA_KEY_INTEGER_START_TOAST, startToastResId);
        intent.putExtra(EXTRA_KEY_INTEGER_END_TOAST, endToastResId);
        intent.putExtra(EXTRA_KEY_INTEGER_END_TOAST, endToastResId);
        intent.putExtra(EXTRA_KEY_INTEGER_START_TIP, startTipResId);
        intent.putExtra(EXTRA_KEY_INTEGER_END_TIP, endTipResId);
        intent.putExtra(EXTRA_KEY_INTEGER_TITLE, titleResId);
        intent.putExtra(EXTRA_KEY_INTEGER_TITLE_COOR, titleColorResid);
        intent.putExtra(EXTRA_KEY_INTEGER_STATUS_BAR_COLOR, statusColorResId);
        intent.putExtra(EXTRA_KEY_INTEGER_DAY_SELECT_COLOR, selectRecColorResId);
        intent.putExtra(EXTRA_KEY_STRING_SELECT_SAME_DAY, sameDayResId);
        intent.putExtra(EXTRA_KEY_LONG_SECTION_START_TIME, sectionStartTime);
        intent.putExtra(EXTRA_KEY_LONG_SECTION_END_TIME, sectionEndTime);
        intent.putExtra("isClose", isClose);

        activity.startActivityForResult(intent, requestCode);
    }

    //酒店的打开activity的默认参数
    public static void startHotelDayPickerForResult(Activity activity, boolean startClick, long inTime, long outTime, int requestCode) {

        startActivityForResult(activity, startClick, inTime, outTime, R.string.calendar_chose_satrt, R.string.calendar_chose_out, R.string.calendar_in, R.string.calendar_out, R.string.day_picker_title, R.color.qa_bg_title_bar_main_hotel, R.color.qa_bg_status_bar_main_hotel, R.color.selected_day_background, requestCode, 0, true, 0, 0);
    }

    public static void startOrderDayPickerForResult(Activity activity, long beginTime, long endTime, long selectTime, int requestCode) {

        startActivityForResult(activity, true, selectTime, 0, R.string.calendar_chose_satrt, R.string.calendar_chose_out, R.string.calendar_in, R.string.calendar_out, R.string.day_picker_title, R.color.qa_bg_title_bar_main_hotel, R.color.qa_bg_status_bar_main_hotel, R.color.selected_day_background, requestCode, 0, true, beginTime, endTime);
    }

}
