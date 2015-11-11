package com.joy.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 *移动以前的webview
 */
public class BaseWebView extends WebView {

    private OnScrollListener mOnScrollLisn;

    public BaseWebView(Context context) {

        super(context);
    }

    public BaseWebView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {

        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollLisn != null)
            mOnScrollLisn.onScroll(l, t, oldl, oldt);
    }

    public void setOnScrollListener(OnScrollListener lisn){

        mOnScrollLisn = lisn;
    }

    public static interface OnScrollListener{

        /***
         *
         * Parameters:
         *
         * @param currentX
         *            Current horizontal scroll origin.当前横向偏移量(距origin坐标)
         * @param currentY
         *            Current vertical scroll origin.当前纵向偏移量(距origin坐标)
         * @param oldX
         *            Previous horizontal scroll origin.上一次横向偏移量(距origin坐标)
         * @param oldY
         *            Previous vertical scroll origin.上一个纵向偏移量(距origin坐标)
         */
        public void onScroll(int currentX, int currentY, int oldX, int oldY);
    }
}
