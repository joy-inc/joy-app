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
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;


import com.android.library.utils.TextUtil;
import com.joy.app.R;

import java.security.InvalidParameterException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * 整体思路是:
 * 1.外部传入一些参数,setMonthParams里面,
 * 2.一行一行的绘制图片,根据逻辑,主要逻辑在drawMonthNums这
 * 3.onDayClick处理点事事件
 */
class SimpleMonthView extends View {

    public static final int START_CLICK = 1;
    public static final int END_CLICK = 2;
    //    public static final String VIEW_PARAMS_HEIGHT = "height";
    public static final String VIEW_PARAMS_MONTH = "month";
    public static final String VIEW_PARAMS_YEAR = "year";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_DAY = "selected_begin_day";
    public static final String VIEW_PARAMS_SELECTED_LAST_DAY = "selected_last_day";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_MONTH = "selected_begin_month";
    public static final String VIEW_PARAMS_SELECTED_LAST_MONTH = "selected_last_month";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_YEAR = "selected_begin_year";
    public static final String VIEW_PARAMS_SELECTED_LAST_YEAR = "selected_last_year";

    public static final String VIEW_PARAMS_WEEK_START = "week_start";
    public static final String VIEW_LASTDAY_YEAR = "lastDay_year";//昨天的日期
    public static final String VIEW_LASTDAY_MONTH = "lastDay_month";//昨天的日期
    public static final String VIEW_LASTDAY_DAY = "lastDay_day";//昨天的日期
    public static final int LINK_SIZE = 2;

    //    private static final int SELECTED_CIRCLE_ALPHA = 128;
    protected static int DEFAULT_HEIGHT = 32;
    protected static final int DEFAULT_NUM_ROWS = 6;
    protected static int DAY_SELECTED_CIRCLE_SIZE;
    protected static int DAY_SEPARATOR_WIDTH = 1;
    protected static int MINI_DAY_NUMBER_TEXT_SIZE;
    protected static int MINI_INFO_TEXT_SIZE;
    protected static int MIN_HEIGHT = 10;
    protected static int MONTH_DAY_LABEL_TEXT_SIZE;
    protected static int MONTH_HEADER_SIZE;
    protected static int MONTH_LABEL_TEXT_SIZE;
    protected static int SELECT_REC_PADDING;//选择框的上下间距
    protected int mPadding = 0;

    private String mDayOfWeekTypeface;
    private String mMonthTitleTypeface;

    protected Paint mMonthDayLabelPaint;
    protected Paint mMonthNumPaint;
    protected Paint mMonthTitleBGPaint;
    protected Paint mMonthTitlePaint;
    protected Paint mSelectedCirclePaint;
    protected Paint mTodayStrokePaint;//今天的空心圆
    protected Paint mLinkColorPaint;
    protected Paint mMonthDayStringPaint;
    protected Paint mSelectPoint;
    protected Paint mSelectLink;
    protected int mCurrentDayTextColor;
    protected int mMonthTextColor;
    protected int mDayTextColor;
    protected int mDayNumColor;
    protected int mMonthTitleBGColor;
    protected int mPreviousDayColor;
    protected int mSelectedDaysColor;

    //是否需要画选中的线,先不添加外部设置方法,以免UI再变
    protected boolean mDrawSelectedLink = false;

    private final StringBuilder mStringBuilder;

    protected boolean mHasToday = false;
    protected boolean mIsPrev = false;
    protected int mSelectedBeginDay = -1;
    protected int mSelectedLastDay = -1;
    protected int mSelectedBeginMonth = -1;
    protected int mSelectedLastMonth = -1;
    protected int mSelectedBeginYear = -1;
    protected int mSelectedLastYear = -1;
    protected int mToday = -1;
    protected int mWeekStart = 1;
    protected int mNumDays = 7;
    protected int mNumCells = mNumDays;
    private int mDayOfWeekStart = 0;
    protected int mMonth;
    protected Boolean mDrawRect;
    protected int mRowHeight = DEFAULT_HEIGHT;
    protected int mWidth;
    protected int mYear;
    final Time today;
    protected int mLastYear;
    protected int mLastMonth;
    protected int mLastDay;

    protected long mStartSectionTime = 0;//开始区间的时间
    protected int mStartSectionYear;
    protected int mStartSectionMonth;
    protected int mStartSectionDay;
    protected long mEndSectionTime = 0;//结束的时间
    protected int mEndSectionYear;
    protected int mEndSectionMonth;
    protected int mEndSectionDay;
    private final Calendar mCalendar;
    private final Calendar mDayLabelCalendar;
    private final Boolean isPrevDayEnabled;

    private int mNumRows = DEFAULT_NUM_ROWS;

    private DateFormatSymbols mDateFormatSymbols = new DateFormatSymbols();

    private OnDayClickListener mOnDayClickListener;

    //--刘龙加
    protected int mLinkColor = 0;
    protected String mWeekendText = "";
    protected int mSelectDayTextColor;
    protected int mWeekendColor = 0;
    protected String mStartText = null; //第一个选择的文字
    protected String mEndText = null;//第二哥选择的文字
    protected int mSelectTextToTop = 0;
    protected boolean mCanSelectBeforeOneDay = false;
    protected SimpleMonthAdapter mSimpleMonthAdapter;
    protected DatePickerController mDatePickerController;

    protected int mSelectPointR = 0;//选择点的半径
    protected int mSelectPointColor = 0; //选择点得颜色
    protected int mSelectLinkSize = 0; //选择的线的大小
    protected int mSelectLinkColor = 0;//选择的线的颜色
    protected String mSameDay = TextUtil.TEXT_EMPTY;//同一天的字符串

    //用户点击进入的模式
    protected int mInType = -1;
    protected boolean mCanSelect = true;//用于如果用户选了结束日期,就不能进行再次选择

    public SimpleMonthView(Context context, TypedArray typedArray, SimpleMonthAdapter simpleMonthAdapter, DatePickerController datePickerController) {
        super(context);

        mDatePickerController = datePickerController;
        mSimpleMonthAdapter = simpleMonthAdapter;
        Resources resources = context.getResources();
        mDayLabelCalendar = Calendar.getInstance();
        mCalendar = Calendar.getInstance();
        today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        mDayOfWeekTypeface = resources.getString(R.string.sans_serif);
        mMonthTitleTypeface = resources.getString(R.string.sans_serif);
        mCurrentDayTextColor = typedArray.getColor(R.styleable.DayPickerView_colorCurrentDay, resources.getColor(R.color.normal_day));
        mMonthTextColor = typedArray.getColor(R.styleable.DayPickerView_colorMonthName, resources.getColor(R.color.normal_day));
        mDayTextColor = typedArray.getColor(R.styleable.DayPickerView_colorDayName, resources.getColor(R.color.normal_day));
        mDayNumColor = typedArray.getColor(R.styleable.DayPickerView_colorNormalDay, resources.getColor(R.color.normal_day));
        mPreviousDayColor = typedArray.getColor(R.styleable.DayPickerView_colorPreviousDay, resources.getColor(R.color.normal_day));
        mSelectedDaysColor = typedArray.getColor(R.styleable.DayPickerView_colorSelectedDayBackground, resources.getColor(R.color.selected_day_background));
        mMonthTitleBGColor = typedArray.getColor(R.styleable.DayPickerView_colorMonthBackground, resources.getColor(R.color.selected_day_text));
        mSelectDayTextColor = typedArray.getColor(R.styleable.DayPickerView_colorSelectedDayText, resources.getColor(R.color.selected_day_text));
        mWeekendColor = typedArray.getColor(R.styleable.DayPickerView_colorWeekend, resources.getColor(R.color.calendar_weekend_color));
        mLinkColor = typedArray.getColor(R.styleable.DayPickerView_colorLink, -1);

        mDrawRect = typedArray.getBoolean(R.styleable.DayPickerView_drawRoundRect, false);

        mStringBuilder = new StringBuilder(50);

        MINI_DAY_NUMBER_TEXT_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeDay, resources.getDimensionPixelSize(R.dimen.text_size_day));
        MINI_INFO_TEXT_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeInfo, resources.getDimensionPixelSize(R.dimen.text_size_day_name));
        mSelectTextToTop = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_selectTextToTop, 0);
        MONTH_LABEL_TEXT_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeMonth, resources.getDimensionPixelSize(R.dimen.text_size_month));
        MONTH_DAY_LABEL_TEXT_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeDayName, resources.getDimensionPixelSize(R.dimen.text_size_day_name));
        MONTH_HEADER_SIZE = typedArray.getDimensionPixelOffset(R.styleable.DayPickerView_headerMonthHeight, resources.getDimensionPixelOffset(R.dimen.header_month_height));
        DAY_SELECTED_CIRCLE_SIZE = mRowHeight = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_calendarDayHeight, MIN_HEIGHT);
        SELECT_REC_PADDING = typedArray.getDimensionPixelOffset(R.styleable.DayPickerView_selectPadding, 0);
        //        DAY_SELECTED_CIRCLE_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_selectedDayRadius, resources.getDimensionPixelOffset(R.dimen.selected_day_radius));
        //        mRowHeight = ((typedArray.getDimensionPixelSize(R.styleable.DayPickerView_calendarHeight, resources.getDimensionPixelOffset(R.dimen.calendar_height)) - MONTH_HEADER_SIZE) / 6);

        mSelectPointR = typedArray.getDimensionPixelOffset(R.styleable.DayPickerView_selectPointR, resources.getDimensionPixelOffset(R.dimen.select_point_r));
        mSelectPointColor = typedArray.getColor(R.styleable.DayPickerView_colorSelectPoint, resources.getColor(R.color.select_point));
        mSelectLinkSize = typedArray.getDimensionPixelOffset(R.styleable.DayPickerView_selectLinkSize, resources.getDimensionPixelOffset(R.dimen.select_link_size));
        mSelectLinkColor = typedArray.getColor(R.styleable.DayPickerView_colorSelectLink, resources.getColor(R.color.select_link));


        mStartText = typedArray.getString(R.styleable.DayPickerView_textStartStr);
        mEndText = typedArray.getString(R.styleable.DayPickerView_textEndStr);
        mWeekendText = typedArray.getString(R.styleable.DayPickerView_textWeekend);
        isPrevDayEnabled = typedArray.getBoolean(R.styleable.DayPickerView_enablePreviousDay, true);
        mCanSelectBeforeOneDay = typedArray.getBoolean(R.styleable.DayPickerView_canSelectBeforeOneToday, false);
        initView();

    }

    //设置开始和结束的日期下得文字
    public void setChoseText(String start, String end) {
        mStartText = start;
        mEndText = end;
    }

    public void setSelectBackgroundColor(int colorResId) {
        mSelectedDaysColor = getResources().getColor(colorResId);
        mSelectedCirclePaint.setColor(mSelectedDaysColor);
    }

    //type START_CLICK开始  END_CLICK 结束
    public void setInType(int type) {
        mInType = type;
    }

    private int calculateNumRows() {
        int offset = findDayOffset();
        int dividend = (offset + mNumCells) / mNumDays;
        int remainder = (offset + mNumCells) % mNumDays;
        return (dividend + (remainder > 0 ? 1 : 0));
    }

    private void drawMonthDayLabels(Canvas canvas) {
        int y = MONTH_HEADER_SIZE - (MONTH_DAY_LABEL_TEXT_SIZE / 2);
        int dayWidthHalf = (mWidth - mPadding * 2) / (mNumDays * 2);

        for (int i = 0; i < mNumDays; i++) {
            int calendarDay = (i + mWeekStart) % mNumDays;
            int x = (2 * i + 1) * dayWidthHalf + mPadding;
            mDayLabelCalendar.set(Calendar.DAY_OF_WEEK, calendarDay);
            canvas.drawText(mDateFormatSymbols.getShortWeekdays()[mDayLabelCalendar.get(Calendar.DAY_OF_WEEK)].toUpperCase(Locale.getDefault()), x, y, mMonthDayLabelPaint);
        }
    }

    private void drawMonthTitle(Canvas canvas) {

        RectF rectF = new RectF(0, 0, mWidth, MONTH_HEADER_SIZE);
        canvas.drawRoundRect(rectF, 0, 0, mMonthTitleBGPaint);


        int x = (mWidth + 2 * mPadding) / 2;
        int y = MONTH_HEADER_SIZE / 2 + MONTH_LABEL_TEXT_SIZE / 2;
        StringBuilder stringBuilder = new StringBuilder(getMonthAndYearString().toLowerCase());
        stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));

        canvas.drawText(stringBuilder.toString(), x, y, mMonthTitlePaint);
        drawLink(canvas, MONTH_HEADER_SIZE);
    }

    private void drawLink(Canvas canvas, float drawY) {
        if (mLinkColorPaint != null) {
            canvas.drawLine(0, drawY, mWidth, drawY, mLinkColorPaint);
        }
    }

    private int findDayOffset() {
        return (mDayOfWeekStart < mWeekStart ? (mDayOfWeekStart + mNumDays) : mDayOfWeekStart) - mWeekStart;
    }

    private String getMonthAndYearString() {
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NO_MONTH_DAY;
        mStringBuilder.setLength(0);
        long millis = mCalendar.getTimeInMillis();
        return DateUtils.formatDateRange(getContext(), millis, millis, flags);
    }

    private void onDayClick(SimpleMonthAdapter.CalendarDay calendarDay) {
        if (mOnDayClickListener != null && mCanSelect) {
            //        if (mOnDayClickListener != null && (isPrevDayEnabled || checkDay(calendarDay.day, today))) {
            if (hasChoseStart() && hasChoseEnd()) {
                if (mInType == START_CLICK || mInType == END_CLICK) {
                    mSelectedLastDay = -1;
                    mSelectedLastMonth = -1;
                    mSelectedLastYear = -1;
                    mSimpleMonthAdapter.clearLastChose();
                }
                if (mInType == START_CLICK) {
                    mSelectedBeginYear = -1;
                    mSelectedBeginDay = -1;
                    mSelectedBeginMonth = -1;
                    mSimpleMonthAdapter.clearFirstChose();
                }
            }
            if (hasChoseStart() == false) {
                if (mStartSectionTime > 0) {
                    if (checkDay(calendarDay.day, calendarDay.year, calendarDay.month, calendarDay.day)) {
                        mOnDayClickListener.onDayClick(this, calendarDay);
                    }
                } else {
                    if (isPrevDayEnabled || !checkDay(calendarDay.day, today) || isLastCanChoseDay(calendarDay.day)) {
                        mOnDayClickListener.onDayClick(this, calendarDay);
                    }
                }
            } else {
                if (mStartSectionTime <= 0) {
                    int day = mSelectedBeginDay;
                    if (!TextUtil.isEmpty(mSameDay)) {
                        day--;
                    }
                    if (isPrevDayEnabled || !pervThatDay(calendarDay, mSelectedBeginYear, mSelectedBeginMonth, day)) { //如果大于开始时间
                        mOnDayClickListener.onDayClick(this, calendarDay);
                    }
                }
            }

        }
    }

    private boolean sameDay(int monthDay, Time time) {
        return (mYear == time.year) && (mMonth == time.month) && (monthDay == time.monthDay);
    }

    private boolean checkDay(int monthDay, Time time) {
        return checkDay(monthDay, time.year, time.month, time.monthDay);
    }

    private boolean checkDay(int monthDay, int toYear, int toMonth, int toDay) {
        if (mEndSectionTime > 0 && mStartSectionTime > 0) {
            Calendar cNow = Calendar.getInstance();
            cNow.set(toYear, toMonth, toDay);
            if (cNow.getTimeInMillis() >= mStartSectionTime && cNow.getTimeInMillis() <= mEndSectionTime) {
                return true;
            } else
                return false;
        }
        return ((mYear < toYear)) || (mYear == toYear && mMonth < toMonth) || (mMonth == toMonth && monthDay < toDay);
    }

    //当设置了可以选择昨天的日期,这里就是判断是否昨天的可选
    private boolean isLastCanChoseDay(int monthDay) {
        if (mCanSelectBeforeOneDay) {
            if (mLastYear == mYear && mLastMonth == mMonth && mLastDay == monthDay) {
                return true;
            }
        }
        return false;
    }

    private boolean pervThatDay(SimpleMonthAdapter.CalendarDay day, int toYear, int toMonth, int toDay) {
        return ((day.year < toYear)) || (day.year == toYear && day.month < toMonth) || (day.month == toMonth && day.day <= toDay);
    }

    private void drawSelRec(Canvas canvas, int dayOffset, int linkCount, int oneWidth, int type, int paddingHeight, int paddingBootm,float top) {
        float itemWidth = mWidth / 7;
        float startWidth = itemWidth * dayOffset;
        oneWidth += 2;
        int oWidth = oneWidth;
        if (type != 2) {
            oWidth /= 2;
            if (type == 0) {
                startWidth += oneWidth;
            }
        }
        float startHeight = DAY_SELECTED_CIRCLE_SIZE * (linkCount - 1) + MONTH_HEADER_SIZE + LINK_SIZE * (linkCount - 1) + paddingHeight; //因为从从link_size下开始画所以这里不进行-1
        RectF rectF = new RectF(startWidth, startHeight-top, startWidth + oWidth * 2, startHeight + DAY_SELECTED_CIRCLE_SIZE - paddingHeight * 2 - paddingBootm);
        canvas.drawRoundRect(rectF, 0, 0, mSelectedCirclePaint);

        if (mDrawSelectedLink) {
            //--画选择的线
            float linkY = (rectF.bottom - rectF.height() / 5) - LINK_SIZE * linkCount;//1/3的未知
            float linkStartX = rectF.left;
            float drawWidth = rectF.right - rectF.left;
            int pointSize = 0;
            if (type == 0) {
                linkStartX += (drawWidth / 2 + pointSize);
            }
            float linkEndX = rectF.right;
            if (type == 1) {
                linkEndX -= (drawWidth / 2 + pointSize);
            }
            canvas.drawLine(linkStartX, linkY, linkEndX, linkY, mSelectLink);
            //--画选择的线

            //--画圆
            if (type == 0 || type == 1) {
                canvas.drawCircle(type == 0 ? linkStartX - pointSize : linkEndX - pointSize, linkY, mSelectPointR, mSelectPoint);
            }
        }
    }


    private void drawSelText(Canvas canvas, String text, int x, int y, int paintColor) {
        mMonthDayStringPaint.setColor(paintColor);
        canvas.drawText(text, x, y, mMonthDayStringPaint);
    }

    protected void drawMonthNums(Canvas canvas) {

        int linkCount = 1;
        int y = (mRowHeight + MINI_DAY_NUMBER_TEXT_SIZE) / 2 + LINK_SIZE + MONTH_HEADER_SIZE; //获得当前数字的Y坐标
        int paddingDay = (mWidth - 2 * mPadding) / (2 * mNumDays);
        int dayOffset = findDayOffset();
        int day = 1;

        int oneWidth = paddingDay + mPadding;

        while (day <= mNumCells) {
            int x = paddingDay * (1 + dayOffset * 2) + mPadding;
            boolean isHasBegin = mMonth == mSelectedBeginMonth && mSelectedBeginDay == day && mSelectedBeginYear == mYear;
            boolean isHasEnd = mMonth == mSelectedLastMonth && mSelectedLastDay == day && mSelectedLastYear == mYear;
            boolean isInSelecDay = false;

            boolean isToday = (mMonth == today.month && mYear == today.year && day == today.monthDay);
            int paddintBottom = 0;
            float topOffset=0;
            if(linkCount==5){
                paddintBottom = SELECT_REC_PADDING+LINK_SIZE;
            }else if(linkCount>5){
                paddintBottom = SELECT_REC_PADDING+LINK_SIZE*2;

                topOffset=SELECT_REC_PADDING/2 ;
//                paddintBottom = SELECT_REC_PADDING+LINK_SIZE*2;
            }
            if (isToday) { //画空心圆
                int dyOffset = 0;
                float drOffset = 0;
                if (linkCount == 1) {
                    dyOffset = -LINK_SIZE;
                } else if (linkCount == 2) {
                    //                    dyOffset = LINK_SIZE * (linkCount - 1);

                } else if (linkCount == 3) {
                    dyOffset = LINK_SIZE;
                    //                    drOffset += (LINK_SIZE / 2);

                } else if (linkCount == 4) {
                    dyOffset = LINK_SIZE * 2;
                    //                    drOffset += LINK_SIZE;
                } else   {
                    dyOffset = LINK_SIZE;
                    drOffset = LINK_SIZE * 2;
                }//我也不想这样加的了.~~~为啥会偏移..在最后一行会少了原本的高度距离,但整个view的高度是对的.所以咯.
                float dy = y - (MINI_DAY_NUMBER_TEXT_SIZE / 2) + dyOffset;
                //                float dr = (MONTH_HEADER_SIZE - LINK_SIZE * linkCount) / 2 + drOffset;
                float dr = DAY_SELECTED_CIRCLE_SIZE / 2 - drOffset - SELECT_REC_PADDING - SELECT_REC_PADDING;
                canvas.drawCircle(x, dy, dr, mTodayStrokePaint);
            }
            //画了选中的日期
            if (isHasBegin || isHasEnd) {
                isInSelecDay = true;
                int drawSeY = 0;
                if (!TextUtils.isEmpty(mStartText) || !TextUtils.isEmpty(mEndText)) {
                    drawSeY = y + mSelectTextToTop + MINI_DAY_NUMBER_TEXT_SIZE / 2;
                    //                    drawSeY = DAY_SELECTED_CIRCLE_SIZE * (linkCount - 1) + MONTH_HEADER_SIZE + LINK_SIZE * (linkCount - 1) + mSelectTextToTop + MINI_DAY_NUMBER_TEXT_SIZE;
                }
                //                if (mDrawRect) {
                //                    drawSelRec(canvas, dayOffset, linkCount, oneWidth, isHasBegin ? 0 : 1);
                //                } else
                int dyOffset = 0;
                float drOffset = 0;
                if (linkCount == 1) {
                    dyOffset = -LINK_SIZE;
                } else if (linkCount == 2) {
                    //                    dyOffset = LINK_SIZE * (linkCount - 1);

                } else if (linkCount == 3) {
                    dyOffset = LINK_SIZE;
                    //                    drOffset += (LINK_SIZE / 2);

                } else if (linkCount == 4) {
                    dyOffset = LINK_SIZE * 2;
                    //                    drOffset += LINK_SIZE;
                } else {

                    dyOffset = LINK_SIZE;
                    drOffset = LINK_SIZE * 2;
                } //我也不想这样加的了.~~~为啥会偏移..在最后一行会少了原本的高度距离,但整个view的高度是对的.所以咯.
                float dy = y - (MINI_DAY_NUMBER_TEXT_SIZE / 2) + dyOffset;
                //                float dr = (MONTH_HEADER_SIZE - LINK_SIZE * linkCount) / 2 + drOffset;
                float dr = DAY_SELECTED_CIRCLE_SIZE / 2 - drOffset - SELECT_REC_PADDING;
                canvas.drawCircle(x, dy, dr, mSelectedCirclePaint);
                //                drawSelRec(canvas, dayOffset, linkCount, oneWidth/2, isHasBegin ? 0 : 1);

                if (mStartSectionTime <= 0) {
                    //画区间
                    drawSelRec(canvas, dayOffset, linkCount, oneWidth, isHasBegin ? 0 : 1, SELECT_REC_PADDING, paddintBottom,topOffset);

                }

                if (isHasBegin && isHasEnd && !TextUtil.isEmpty(mSameDay) && mSelectedBeginYear == mSelectedLastYear && mSelectedBeginMonth == mSelectedLastMonth && mSelectedBeginDay == mSelectedLastDay) {
                    drawSelText(canvas, mSameDay, x, drawSeY, mSelectDayTextColor);

                } else if (isHasBegin) {
                    if (!TextUtils.isEmpty(mStartText)) {
                        drawSelText(canvas, mStartText, x, drawSeY, mSelectDayTextColor);
                    }
                } else if (isHasEnd) {
                    if (!TextUtils.isEmpty(mEndText)) {
                        drawSelText(canvas, mEndText, x, drawSeY, mSelectDayTextColor);
                    }
                }
            }
            //画了选中的日期

            //确定日期的颜色

            String dayInfo = mDatePickerController.getDayInfo(mYear, mMonth + 1, day); //获取节假日信息

            int paintColor = 0;
            //确定颜色的基本颜色
            if ((dayOffset == 0 || dayOffset == 6) && !TextUtil.isEmpty(dayInfo) && dayInfo.equals(mWeekendText)) {
                paintColor = mDayNumColor;
            } else if ((dayOffset == 0 || dayOffset == 6) || !TextUtil.isEmpty(dayInfo)) {
                paintColor = mWeekendColor;
            } else if (mHasToday && (mToday == day)) {
                paintColor = mCurrentDayTextColor;
            } else {
                paintColor = mDayNumColor;
            }
            mMonthNumPaint.setColor(paintColor);
            int drawSelType = 2;

            //画选中的日期颜色和线
            if ((mMonth == mSelectedBeginMonth && mSelectedBeginDay == day && mSelectedBeginYear == mYear) || (mMonth == mSelectedLastMonth && mSelectedLastDay == day && mSelectedLastYear == mYear))
                mMonthNumPaint.setColor(mSelectDayTextColor);

            if ((mSelectedBeginDay != -1 && mSelectedLastDay != -1 && mSelectedBeginYear == mSelectedLastYear &&
                    mSelectedBeginMonth == mSelectedLastMonth &&
                    mSelectedBeginDay == mSelectedLastDay &&
                    day == mSelectedBeginDay &&
                    mMonth == mSelectedBeginMonth &&
                    mYear == mSelectedBeginYear)) {
                //                if (mDrawRect) {
                //                    drawSelRec(canvas, dayOffset, linkCount, oneWidth);
                //                }
                mMonthNumPaint.setColor(mSelectDayTextColor);
            }
            if ((mSelectedBeginDay != -1 && mSelectedLastDay != -1 && mSelectedBeginYear == mSelectedLastYear && mSelectedBeginYear == mYear) && (((mMonth == mSelectedBeginMonth && mSelectedLastMonth == mSelectedBeginMonth) && ((mSelectedBeginDay < mSelectedLastDay && day > mSelectedBeginDay && day < mSelectedLastDay) || (mSelectedBeginDay > mSelectedLastDay && day < mSelectedBeginDay && day > mSelectedLastDay))) ||
                    ((mSelectedBeginMonth < mSelectedLastMonth && mMonth == mSelectedBeginMonth && day > mSelectedBeginDay) || (mSelectedBeginMonth < mSelectedLastMonth && mMonth == mSelectedLastMonth && day < mSelectedLastDay)) ||
                    ((mSelectedBeginMonth > mSelectedLastMonth && mMonth == mSelectedBeginMonth && day < mSelectedBeginDay) || (mSelectedBeginMonth > mSelectedLastMonth && mMonth == mSelectedLastMonth && day > mSelectedLastDay)))) {
                if (mDrawRect) {
                    isInSelecDay = true;
                    drawSelRec(canvas, dayOffset, linkCount, oneWidth, drawSelType, SELECT_REC_PADDING, paddintBottom,topOffset);
                }
                mMonthNumPaint.setColor(mSelectDayTextColor);
            }

            if ((mSelectedBeginDay != -1 && mSelectedLastDay != -1 && mSelectedBeginYear != mSelectedLastYear && ((mSelectedBeginYear == mYear && mMonth == mSelectedBeginMonth) || (mSelectedLastYear == mYear && mMonth == mSelectedLastMonth)) &&
                    (((mSelectedBeginMonth < mSelectedLastMonth && mMonth == mSelectedBeginMonth && day < mSelectedBeginDay) || (mSelectedBeginMonth < mSelectedLastMonth && mMonth == mSelectedLastMonth && day > mSelectedLastDay)) || ((mSelectedBeginMonth > mSelectedLastMonth && mMonth == mSelectedBeginMonth && day > mSelectedBeginDay) || (mSelectedBeginMonth > mSelectedLastMonth && mMonth == mSelectedLastMonth && day < mSelectedLastDay))))) {
                if (mDrawRect) {
                    isInSelecDay = true;
                    drawSelRec(canvas, dayOffset, linkCount, oneWidth, drawSelType, SELECT_REC_PADDING, paddintBottom,topOffset);
                }
                mMonthNumPaint.setColor(mSelectDayTextColor);
            }

            if ((mSelectedBeginDay != -1 && mSelectedLastDay != -1 && mSelectedBeginYear == mSelectedLastYear && mYear == mSelectedBeginYear) && ((mMonth > mSelectedBeginMonth && mMonth < mSelectedLastMonth && mSelectedBeginMonth < mSelectedLastMonth) || (mMonth < mSelectedBeginMonth && mMonth > mSelectedLastMonth && mSelectedBeginMonth > mSelectedLastMonth))) {
                if (mDrawRect) {
                    isInSelecDay = true;
                    drawSelRec(canvas, dayOffset, linkCount, oneWidth, drawSelType, SELECT_REC_PADDING, paddintBottom,topOffset);
                }
                mMonthNumPaint.setColor(mSelectDayTextColor);

            }

            if ((mSelectedBeginDay != -1 && mSelectedLastDay != -1 && mSelectedBeginYear != mSelectedLastYear) && ((mSelectedBeginYear < mSelectedLastYear && ((mMonth > mSelectedBeginMonth && mYear == mSelectedBeginYear) || (mMonth < mSelectedLastMonth && mYear == mSelectedLastYear))) || (mSelectedBeginYear > mSelectedLastYear && ((mMonth < mSelectedBeginMonth && mYear == mSelectedBeginYear) || (mMonth > mSelectedLastMonth && mYear == mSelectedLastYear))))) {
                if (mDrawRect) {
                    isInSelecDay = true;
                    drawSelRec(canvas, dayOffset, linkCount, oneWidth, drawSelType, SELECT_REC_PADDING, paddintBottom,topOffset);
                }
                mMonthNumPaint.setColor(mSelectDayTextColor);
            }


            //判断日期是否不可选颜色,1.小于今天的是否可用 2.是否在开始的时间前
            if (hasChoseStart()) { //分开写判断,好维护
                if (mStartSectionTime > 0) {
                    if (!checkDay(day, mYear, mMonth, day)) {
                        mMonthNumPaint.setColor(mPreviousDayColor);
                    }
                } else {
                    if (mInType == END_CLICK && checkDay(day, mSelectedBeginYear, mSelectedBeginMonth, mSelectedBeginDay)) {
                        mMonthNumPaint.setColor(mPreviousDayColor);
                    } else if (mInType == START_CLICK && hasChoseEnd() &&
                            checkDay(day, today.year, today.month, today.monthDay) && !isLastCanChoseDay(day)) {
                        mMonthNumPaint.setColor(mPreviousDayColor);
                    } else if (!hasChoseEnd() && checkDay(day, mSelectedBeginYear, mSelectedBeginMonth, mSelectedBeginDay)) {
                        mMonthNumPaint.setColor(mPreviousDayColor);
                    }
                }
            } else if (!isPrevDayEnabled && checkDay(day, today.year, today.month, today.monthDay) && !isLastCanChoseDay(day)) {
                //                else if (!isPrevDayEnabled && today.month >= mMonth && today.year >= mYear && checkDay(day, today.year, today.month, today.monthDay) && !isLastCanChoseDay(day)) {
                mMonthNumPaint.setColor(mPreviousDayColor); //前天的都置灰
            } else if (mStartSectionTime > 0 && !checkDay(day, mYear, mMonth, day)) {
                mMonthNumPaint.setColor(mPreviousDayColor);
            }


            if (!TextUtil.isEmpty(dayInfo)) {

                //                int drawSeY = DAY_SELECTED_CIRCLE_SIZE * (linkCount - 1) + MONTH_HEADER_SIZE + LINK_SIZE * (linkCount - 1) + mSelectTextToTop + MINI_DAY_NUMBER_TEXT_SIZE;
                int drawSeY = y - MINI_DAY_NUMBER_TEXT_SIZE / 2 - mSelectTextToTop;
                if (isInSelecDay) { //这说明在选中的日期范围内,但不是开始喝结束日
                    mMonthNumPaint.setColor(mSelectDayTextColor);
                }
                drawSelText(canvas, dayInfo, x, drawSeY, mMonthNumPaint.getColor());

            }
            canvas.drawText(Integer.toString(day), x, y, mMonthNumPaint);

            dayOffset++;
            if (dayOffset == mNumDays) {
                dayOffset = 0;
                drawLink(canvas, mRowHeight * linkCount + MONTH_HEADER_SIZE + LINK_SIZE * linkCount);
                linkCount++;
                y += mRowHeight;//-1是因为月份开始是没有线
            }
            day++;
        }
        drawLink(canvas, mRowHeight * linkCount + MONTH_HEADER_SIZE);
    }

    private boolean hasChoseEnd() {
        return mSelectedLastDay != -1 && mSelectedLastMonth != -1 && mSelectedLastYear != -1;
    }

    private boolean hasChoseStart() {
        return mSelectedBeginDay != -1 && mSelectedBeginMonth != -1 && mSelectedBeginYear != -1;
    }

    public SimpleMonthAdapter.CalendarDay getDayFromLocation(float x, float y) {
        int padding = mPadding;
        if ((x < padding) || (x > mWidth - mPadding)) {
            return null;
        }

        int yDay = (int) (y - MONTH_HEADER_SIZE) / mRowHeight;
        int day = 1 + ((int) ((x - padding) * mNumDays / (mWidth - padding - mPadding)) - findDayOffset()) + yDay * mNumDays;

        if (mMonth > 11 || mMonth < 0 || CalendarUtils.getDaysInMonth(mMonth, mYear) < day || day < 1)
            return null;

        return new SimpleMonthAdapter.CalendarDay(mYear, mMonth, day);
    }

    protected void initView() {
        mMonthTitlePaint = new Paint();
        //        mMonthTitlePaint.setFakeBoldText(true);
        mMonthTitlePaint.setAntiAlias(true);
        mMonthTitlePaint.setTextSize(MONTH_LABEL_TEXT_SIZE);
        //        mMonthTitlePaint.setTypeface(Typeface.create(mMonthTitleTypeface, Typeface.BOLD));
        mMonthTitlePaint.setColor(mMonthTextColor);
        mMonthTitlePaint.setTextAlign(Align.CENTER);
        mMonthTitlePaint.setStyle(Style.FILL);

        mMonthTitleBGPaint = new Paint();
        mMonthTitleBGPaint.setFakeBoldText(true);
        mMonthTitleBGPaint.setAntiAlias(true);
        mMonthTitleBGPaint.setColor(mMonthTitleBGColor);
        mMonthTitleBGPaint.setTextAlign(Align.CENTER);
        mMonthTitleBGPaint.setStyle(Style.FILL);

        mSelectedCirclePaint = new Paint();
        mSelectedCirclePaint.setFakeBoldText(true);
        mSelectedCirclePaint.setAntiAlias(true);
        mSelectedCirclePaint.setColor(mSelectedDaysColor);
        mSelectedCirclePaint.setTextAlign(Align.CENTER);
        mSelectedCirclePaint.setStyle(Style.FILL);

        mTodayStrokePaint = new Paint();
        mTodayStrokePaint.setFakeBoldText(true);
        mTodayStrokePaint.setAntiAlias(true);
        mTodayStrokePaint.setColor(mSelectedDaysColor);
        mTodayStrokePaint.setTextAlign(Align.CENTER);
        mTodayStrokePaint.setStyle(Style.STROKE);
        mTodayStrokePaint.setStrokeWidth((float) 2.0); //線寬

        mMonthDayLabelPaint = new Paint();
        mMonthDayLabelPaint.setAntiAlias(true);
        mMonthDayLabelPaint.setTextSize(MONTH_DAY_LABEL_TEXT_SIZE);
        mMonthDayLabelPaint.setColor(mDayTextColor);
        mMonthDayLabelPaint.setStyle(Style.FILL);
        mMonthDayLabelPaint.setTextAlign(Align.CENTER);
        mMonthDayLabelPaint.setFakeBoldText(true);

        mMonthNumPaint = new Paint();
        mMonthNumPaint.setAntiAlias(true);
        mMonthNumPaint.setTextSize(MINI_DAY_NUMBER_TEXT_SIZE);
        mMonthNumPaint.setStyle(Style.FILL);
        mMonthNumPaint.setTextAlign(Align.CENTER);
        mMonthNumPaint.setFakeBoldText(false);

        mMonthDayStringPaint = new Paint();
        mMonthDayStringPaint.setAntiAlias(true);
        mMonthDayStringPaint.setColor(mSelectDayTextColor);
        mMonthDayStringPaint.setTextSize(MINI_INFO_TEXT_SIZE);
        mMonthDayStringPaint.setStyle(Style.FILL);
        mMonthDayStringPaint.setTextAlign(Align.CENTER);
        mMonthDayStringPaint.setFakeBoldText(false);

        mSelectPoint = new Paint();
        mSelectPoint.setAntiAlias(true);
        mSelectPoint.setColor(mSelectPointColor);

        mSelectLink = new Paint();
        mSelectLink.setAntiAlias(true);
        mSelectLink.setStrokeWidth(mSelectLinkSize);
        mSelectLink.setStyle(Style.STROKE);
        mSelectLink.setColor(mSelectLinkColor);

        if (mLinkColor != 0) {
            mLinkColorPaint = new Paint();
            mLinkColorPaint.setStrokeWidth(LINK_SIZE);
            mLinkColorPaint.setStyle(Style.STROKE);
            mLinkColorPaint.setAntiAlias(true);
            mLinkColorPaint.setColor(mLinkColor);
        }
    }

    protected void onDraw(Canvas canvas) {
        drawMonthTitle(canvas);
        drawMonthNums(canvas);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mRowHeight * mNumRows + MONTH_HEADER_SIZE);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
    }

    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            SimpleMonthAdapter.CalendarDay calendarDay = getDayFromLocation(event.getX(), event.getY());
            if (calendarDay != null) {
                onDayClick(calendarDay);
            }
        }
        return true;
    }

    public void setSameDay(String str) {

        mSameDay = str;
    }

    public void reuse() {

        mNumRows = DEFAULT_NUM_ROWS;
        requestLayout();
    }

    //设置view是否可以点击
    public void setCanSelect(boolean canSelect) {

        mCanSelect = canSelect;
    }

    public void setMonthParams(HashMap<String, Integer> params) {

        if (!params.containsKey(VIEW_PARAMS_MONTH) && !params.containsKey(VIEW_PARAMS_YEAR)) {
            throw new InvalidParameterException("You must specify month and year for this view");
        }
        setTag(params);

        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_DAY)) {
            mSelectedBeginDay = params.get(VIEW_PARAMS_SELECTED_BEGIN_DAY);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_DAY)) {
            mSelectedLastDay = params.get(VIEW_PARAMS_SELECTED_LAST_DAY);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_MONTH)) {
            mSelectedBeginMonth = params.get(VIEW_PARAMS_SELECTED_BEGIN_MONTH);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_MONTH)) {
            mSelectedLastMonth = params.get(VIEW_PARAMS_SELECTED_LAST_MONTH);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_YEAR)) {
            mSelectedBeginYear = params.get(VIEW_PARAMS_SELECTED_BEGIN_YEAR);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_YEAR)) {
            mSelectedLastYear = params.get(VIEW_PARAMS_SELECTED_LAST_YEAR);
        }


        mLastYear = params.get(VIEW_LASTDAY_YEAR);
        mLastMonth = params.get(VIEW_LASTDAY_MONTH);
        mLastDay = params.get(VIEW_LASTDAY_DAY);

        mMonth = params.get(VIEW_PARAMS_MONTH);
        mYear = params.get(VIEW_PARAMS_YEAR);

        mHasToday = false;
        mToday = -1;

        mCalendar.set(Calendar.MONTH, mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        mDayOfWeekStart = mCalendar.get(Calendar.DAY_OF_WEEK);

        if (params.containsKey(VIEW_PARAMS_WEEK_START)) {
            mWeekStart = params.get(VIEW_PARAMS_WEEK_START);
        } else {
            mWeekStart = mCalendar.getFirstDayOfWeek();
        }

        mNumCells = CalendarUtils.getDaysInMonth(mMonth, mYear);
        for (int i = 0; i < mNumCells; i++) {
            final int day = i + 1;
            if (sameDay(day, today)) {
                mHasToday = true;
                mToday = day;
            }

            //            mIsPrev = checkDay(day, today);
        }

        mNumRows = calculateNumRows();
    }

    /**
     * 设置可选的开始和结束的时间区间
     *
     * @param startTime
     * @param endtime
     */
    public void setSectionDay(long startTime, long endtime) {

        mEndSectionTime = endtime;
        if (mEndSectionTime > 0) {
            Calendar tmp = Calendar.getInstance();
            tmp.setTimeInMillis(mEndSectionTime);
            mEndSectionYear = tmp.get(Calendar.YEAR);
            mEndSectionMonth = tmp.get(Calendar.MONTH);
            mEndSectionDay = tmp.get(Calendar.DAY_OF_MONTH);
        }
        mStartSectionTime = startTime;
        if (mStartSectionTime > 0) {
            Calendar tmp = Calendar.getInstance();

            tmp.setTimeInMillis(mStartSectionTime);
            mStartSectionYear = tmp.get(Calendar.YEAR);
            mStartSectionMonth = tmp.get(Calendar.MONTH);
            mStartSectionDay = tmp.get(Calendar.DAY_OF_MONTH);
        }

    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }

    public static abstract interface OnDayClickListener {
        public abstract void onDayClick(SimpleMonthView simpleMonthView, SimpleMonthAdapter.CalendarDay calendarDay);
    }
}