/***********************************************************************************
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
 ***********************************************************************************/
package com.joy.app.view.dateView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.joy.app.R;


//日期选择器的控件view，用于组装adapter，
public class DayPickerView extends RecyclerView {
    protected Context mContext;
    protected SimpleMonthAdapter mAdapter;
    private DatePickerController mController;
    protected int mCurrentScrollState = 0;
    protected long mPreviousScrollPosition;
    protected int mPreviousScrollState = 0;
    private TypedArray typedArray;
    private OnScrollListener onScrollListener;
    int mNowShowIndex = -1;// 当前显示的标题索引


    public DayPickerView(Context context) {

        this(context, null);
    }

    public DayPickerView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public DayPickerView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.DayPickerView);
            setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            init(context);
        }
    }


    public void setController(DatePickerController mController) {

        this.mController = mController;
        setUpAdapter();
        setAdapter(mAdapter);

    }

    public void setStartTime(int year, int month, int day) {

        mAdapter.setStartTime(year, month, day);
    }

    /**
     * 设置可选的开始和结束的时间区间
     * @param startTime
     * @param endtime
     */
    public void setSectionDay(long startTime,long endtime) {

        mAdapter.setSectionDay(startTime, endtime);
    }

    public void setEndTime(int year, int month, int day) {

        mAdapter.setEndTime(year, month, day);
    }

    public void setSelectBackgroundColor(int color) {
        mAdapter.setSelectBackgroundColor(color);

    }

    public void setSameDayString(String str) {

        mAdapter.setSameDayString(str);
    }

    //设置开始和结束的日期下得文字
    public void setChoseText(String start, String end) {

        mAdapter.setChoseText(start, end);
    }

    public void setStartChose(boolean isStart) {

        mAdapter.setStartChose(isStart);
    }

    public void init(Context paramContext) {

        setLayoutManager(new LinearLayoutManager(paramContext));
        mContext = paramContext;


        onScrollListener = new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);
                int firstItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (firstItem != mNowShowIndex) {
                    mController.onMonthShowChange(mAdapter.getItemDayTitle(firstItem));
                    mNowShowIndex = firstItem;
                }

                final SimpleMonthView child = (SimpleMonthView) recyclerView.getChildAt(0);
                if (child == null) {
                    return;
                }

                mPreviousScrollPosition = dy;
                mPreviousScrollState = mCurrentScrollState;
            }
        };

        setUpListView();
    }


    protected void setUpAdapter() {

        if (mAdapter == null) {
            mAdapter = new SimpleMonthAdapter(getContext(), mController, typedArray);
        }
        mAdapter.notifyDataSetChanged();
    }

    protected void setUpListView() {

        setVerticalScrollBarEnabled(false);
        setOnScrollListener(onScrollListener);
        setFadingEdgeLength(0);
    }

    public SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> getSelectedDays() {

        return mAdapter.getSelectedDays();
    }

    protected DatePickerController getController() {

        return mController;
    }

    protected TypedArray getTypedArray() {

        return typedArray;
    }


}