/**
 * ********************************************************************************
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2014 Robin Chutaux
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * *********************************************************************************
 */
package com.joy.app.view.dateView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;


import com.android.library.utils.LogMgr;
import com.android.library.utils.TextUtil;
import com.joy.app.R;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

//日期控件适配，每个月都是一个item，控制每个item之间的数据控制，如跨月的选择，数据传输
public class SimpleMonthAdapter extends RecyclerView.Adapter<SimpleMonthAdapter.ViewHolder> implements SimpleMonthView.OnDayClickListener {
    protected static final int MONTHS_IN_YEAR = 12;
    private final TypedArray typedArray;
    private final Context mContext;
    private final DatePickerController mController;
    private final Calendar calendar;
    private final SelectedDays<CalendarDay> selectedDays;
    private Integer firstMonth;

    private String mStartText = null; //第一个选择的文字
    private String mEndText = null;//第二哥选择的文字
    private int mShowMonthCount = -1;

    private long mStartTime;//开始时间
    private long mEndTime;//结束时间的区间
    private String mSameDay = TextUtil.TEXT_EMPTY;
    private int mSelectBackgroundColor = -1;
    private boolean isStart = false;
    protected boolean mCanSelect = true;//用于如果用户选了结束日期,就不能进行再次选择
    HashMap<Integer, String> mDays = new HashMap<Integer, String>();

    public SimpleMonthAdapter(Context context, DatePickerController datePickerController, TypedArray typedArray) {

        this.typedArray = typedArray;
        calendar = Calendar.getInstance();
        firstMonth = typedArray.getInt(R.styleable.DayPickerView_firstMonth, calendar.get(Calendar.MONTH));
        if (typedArray.getBoolean(R.styleable.DayPickerView_canSelectBeforeOneToday, false)) {
            if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
                firstMonth -= 1;
            }
        }
        mShowMonthCount = typedArray.getInt(R.styleable.DayPickerView_showMonthCount, 1);

        selectedDays = new SelectedDays<>();
        mContext = context;
        mController = datePickerController;
        init();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        final SimpleMonthView simpleMonthView = new SimpleMonthView(mContext, typedArray, this, mController);
        if (!TextUtil.isEmpty(mStartText) && !TextUtil.isEmpty(mEndText)) {
            simpleMonthView.setChoseText(mStartText, mEndText);
        }
        simpleMonthView.setSelectBackgroundColor(mSelectBackgroundColor);
        simpleMonthView.setSameDay(mSameDay);
        return new ViewHolder(simpleMonthView, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final SimpleMonthView v = viewHolder.simpleMonthView;
        v.setInType(isStart ? SimpleMonthView.START_CLICK : SimpleMonthView.END_CLICK);
        final HashMap<String, Integer> drawingParams = new HashMap<String, Integer>();
        int month;
        int year = calendar.get(Calendar.YEAR);
        int tmpFirstMonth = firstMonth;
        if (firstMonth < 0) {
            tmpFirstMonth = 11;
            year -= 1;
        }
        month = (tmpFirstMonth + (position % MONTHS_IN_YEAR)) % MONTHS_IN_YEAR;
        //        if (mStartTime > 0) {
        //            month--;
        //        }
        year = position / MONTHS_IN_YEAR + year + ((tmpFirstMonth + (position % MONTHS_IN_YEAR)) / MONTHS_IN_YEAR);

        int selectedFirstDay = -1;
        int selectedLastDay = -1;
        int selectedFirstMonth = -1;
        int selectedLastMonth = -1;
        int selectedFirstYear = -1;
        int selectedLastYear = -1;

        if (selectedDays.getFirst() != null) {
            selectedFirstDay = selectedDays.getFirst().day;
            selectedFirstMonth = selectedDays.getFirst().month;
            selectedFirstYear = selectedDays.getFirst().year;
        }

        if (selectedDays.getLast() != null) {
            selectedLastDay = selectedDays.getLast().day;
            selectedLastMonth = selectedDays.getLast().month;
            selectedLastYear = selectedDays.getLast().year;
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH, -1);

        v.reuse();

        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_BEGIN_YEAR, selectedFirstYear);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_LAST_YEAR, selectedLastYear);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_BEGIN_MONTH, selectedFirstMonth);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_LAST_MONTH, selectedLastMonth);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_BEGIN_DAY, selectedFirstDay);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_LAST_DAY, selectedLastDay);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_YEAR, year);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_MONTH, month);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_WEEK_START, calendar.getFirstDayOfWeek());
        drawingParams.put(SimpleMonthView.VIEW_LASTDAY_YEAR, calendar1.get(Calendar.YEAR));
        drawingParams.put(SimpleMonthView.VIEW_LASTDAY_MONTH, calendar1.get(Calendar.MONTH));
        drawingParams.put(SimpleMonthView.VIEW_LASTDAY_DAY, calendar1.get(Calendar.DAY_OF_MONTH));
        v.setSectionDay(mStartTime, mEndTime);
        v.setMonthParams(drawingParams);
        v.setCanSelect(mCanSelect);
        v.invalidate();

        if (!mDays.containsKey(position)) {

            //保存每个项的标题
            Calendar tmpCalendar = Calendar.getInstance();
            tmpCalendar.set(Calendar.MONTH, month);
            tmpCalendar.set(Calendar.YEAR, year);
            tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
            int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NO_MONTH_DAY;
            long millis = tmpCalendar.getTimeInMillis();
            mDays.put(position, DateUtils.formatDateRange(mContext, millis, millis, flags));

        }
    }

    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getItemCount() {

        return mShowMonthCount;
    }

    public void setItemCount(int count) {
        mShowMonthCount = count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final SimpleMonthView simpleMonthView;

        public ViewHolder(View itemView, SimpleMonthView.OnDayClickListener onDayClickListener) {
            super(itemView);
            simpleMonthView = (SimpleMonthView) itemView;
            simpleMonthView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            simpleMonthView.setClickable(true);
            simpleMonthView.setOnDayClickListener(onDayClickListener);
        }
    }

    protected void init() {

        if (typedArray.getBoolean(R.styleable.DayPickerView_currentDaySelected, false))
            onDayTapped(new CalendarDay(System.currentTimeMillis()));
    }

    public void setStartTime(int year, int month, int day) {

        selectedDays.setFirst(new CalendarDay(year, month, day));
    }

    public void setEndTime(int year, int month, int day) {

        selectedDays.setLast(new CalendarDay(year, month, day));
    }

    public void setSelectBackgroundColor(int color) {

        mSelectBackgroundColor = color;

    }

    //设置是入住进来的还是离店进来的
    public void setStartChose(boolean isStart) {

        this.isStart = isStart;
    }

    public void onDayClick(SimpleMonthView simpleMonthView, CalendarDay calendarDay) {

        if (calendarDay != null) {
            onDayTapped(calendarDay);
        }
    }

    protected void onDayTapped(CalendarDay calendarDay) {

        mController.onDayOfMonthSelected(calendarDay);
        setSelectedDay(calendarDay);
    }

    //设置view是否可以点击
    public void setCanSelect(boolean canSelect) {

        mCanSelect = canSelect;
    }

    /**
     * 设置可选的开始和结束的时间区间
     *
     * @param startTime
     * @param endtime
     */
    public void setSectionDay(long startTime, long endtime) {
        mStartTime = startTime;
        mEndTime = endtime;

        //        calendar.setTimeInMillis(mStartTime);
        //        firstMonth = calendar.get(Calendar.MONTH);
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(mEndTime);

        int showCount = 1;
        int year = c1.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        if (year > 0) {
            showCount = c1.get(Calendar.MONTH) + (12 - calendar.get(Calendar.MONTH)) + (year - 1) * 12;
        }
        //          showCount = ((int)(mEndTime / 1000) - (int)(calendar.getTimeInMillis() / 1000)) / 3600 / (24 * 30);
        //        int showCount1 = (int) ((mEndTime - calendar.getTimeInMillis()) / (1000 * 3600 * 24 * 30));
        //        LogMgr.d("showCount="+showCount+"   showCount1="+showCount1);
        if (showCount < 1)
            showCount = 1;
        else {
            showCount++;
        }
        setItemCount(showCount);
    }

    public void setSelectedDay(CalendarDay calendarDay) {

        if (mStartTime > 0 && mEndTime > 0) {
            selectedDays.setFirst(calendarDay);
            mController.onDateRangeSelected(selectedDays);

        } else {
            if (selectedDays.getFirst() != null && selectedDays.getLast() == null) {
                selectedDays.setLast(calendarDay);
                isStart = false;//改变这值是为了告诉控件画得类型不一样了.
                if (selectedDays.getFirst().month < calendarDay.month) {
                    for (int i = 0; i < selectedDays.getFirst().month - calendarDay.month - 1; ++i)
                        mController.onDayOfMonthSelected(new CalendarDay(selectedDays.getFirst().year, selectedDays.getFirst().month + i, selectedDays.getFirst().day));
                }
                mController.onDateRangeSelected(selectedDays);
            } else if (selectedDays.getLast() != null) {
                selectedDays.setFirst(calendarDay);
                selectedDays.setLast(null);
            } else
                selectedDays.setFirst(calendarDay);
        }
        notifyDataSetChanged();
    }

    public void clearLastChose() {

        selectedDays.setLast(null);
    }

    public void clearFirstChose() {

        selectedDays.setFirst(null);
    }

    //设置开始和结束的日期下得文字
    public void setChoseText(String start, String end) {

        mStartText = start;
        mEndText = end;
    }

    //获取索引对应的日期
    public String getItemDayTitle(int index) {

        if (mDays.containsKey(index)) {
            return mDays.get(index);
        }
        return TextUtil.TEXT_EMPTY;
    }


    public void setSameDayString(String str) {

        mSameDay = str;
    }

    public static class CalendarDay implements Serializable {

        private static final long serialVersionUID = -5456695978688356202L;
        //        public static final int BIG_START_DAY = -100; //大于开始的日期
        private Calendar calendar;

        int day;
        int month;
        int year;

        //        int errorCode;

        public CalendarDay() {

            setTime(System.currentTimeMillis());
        }

        public CalendarDay(int year, int month, int day) {

            setDay(year, month, day);
        }

        public CalendarDay(long timeInMillis) {

            setTime(timeInMillis);
        }

        public CalendarDay(Calendar calendar) {

            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        //        public CalendarDay(int errorCode) {
        //            this.errorCode = errorCode;
        //        }

        private void setTime(long timeInMillis) {

            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            calendar.setTimeInMillis(timeInMillis);
            month = this.calendar.get(Calendar.MONTH);
            year = this.calendar.get(Calendar.YEAR);
            day = this.calendar.get(Calendar.DAY_OF_MONTH);
        }

        public void set(CalendarDay calendarDay) {

            year = calendarDay.year;
            month = calendarDay.month;
            day = calendarDay.day;
        }

        public void setDay(int year, int month, int day) {

            this.year = year;
            this.month = month;
            this.day = day;
        }

        //        public int getErrorCode() {
        //            return errorCode;
        //        }

        public Date getDate() {

            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            calendar.set(year, month, day, 0, 0, 0);
            return calendar.getTime();
        }

        /**
         * 获取当天的23:59:59秒
         *
         * @return
         */
        public Date getMaxDayDate() {
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            calendar.set(year, month, day, 23, 59, 59);
            return calendar.getTime();
        }

        @Override
        public String toString() {

            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ year: ");
            stringBuilder.append(year);
            stringBuilder.append(", month: ");
            stringBuilder.append(month);
            stringBuilder.append(", day: ");
            stringBuilder.append(day);
            stringBuilder.append(" }");

            return stringBuilder.toString();
        }
    }

    public SelectedDays<CalendarDay> getSelectedDays() {

        return selectedDays;
    }

    public static class SelectedDays<K> implements Serializable {
        private static final long serialVersionUID = 3942549765282708376L;
        private K first;
        private K last;

        public K getFirst() {
            return first;
        }

        public void setFirst(K first) {
            this.first = first;
        }

        public K getLast() {
            return last;
        }

        public void setLast(K last) {
            this.last = last;
        }
    }

}