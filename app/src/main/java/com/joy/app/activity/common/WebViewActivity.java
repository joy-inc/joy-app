package com.joy.app.activity.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.activity.BaseUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.LogMgr;
import com.android.library.utils.TextUtil;
import com.android.library.utils.ToastUtil;
import com.android.library.utils.ViewUtil;
import com.joy.app.R;
import com.joy.app.eventbus.LoginStatusEvent;
import com.joy.app.utils.ActivityUrlUtil;
import com.joy.app.utils.share.WebViewShare;
import com.joy.app.view.dialog.ShareDialog;
import com.joy.app.view.webview.BaseWebView;
import com.joy.app.view.webview.WebViewBaseWidget;
import com.joy.app.view.webview.WebViewNativeWidget;

import de.greenrobot.event.EventBus;

/**
 * 统一处理webview的展现
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-10
 */
public class WebViewActivity extends BaseHttpUiActivity<String> implements WebViewBaseWidget.WebViewListener, View.OnClickListener {

    //--大于100有分享的意思
    public static final int TYPE_CITY_TOPIC = 102;//首页城市专题
    public static final int TYPE_CITY_RECMMMEND = 103;//城市页下面的推荐
    public static final int TYPE_CITY_PLAY = 104;//城市下的玩
    public static final int TYPE_CITY_FOOD = 105;//城市下的食物
    public static final int TYPE_CITY_SHOP = 106;//城市下的购物
    public static final int TYPE_CITY_HOTEL = 107;//城市下的酒店

    public static final int TYPE_ABOUT = 1;//关于界面
    public static final int TYPE_HOTEL = 2;//酒店Booking.com

    private WebViewBaseWidget mWebViewWidget;
    private ShareDialog mShareDialog;
    private WebViewShare mWebViewShare;
    private int mType = 0;
    private String mTitle;
    private boolean mUseBottomBanner = false;//使用底部的banner
    private String mUrl;
    private boolean mNoJump = false;//是否会进行打开新页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mType = getIntent().getIntExtra("type", 0);
        mTitle = getIntent().getStringExtra("title");

        if (!TextUtil.isEmpty(mTitle))
            setTheme(R.style.theme_app);
        else if (mType == TYPE_HOTEL)
            setTheme(R.style.theme_app_noTitle_hotel);

        super.onCreate(savedInstanceState);
        setContentFullScreenWebView(true);
    }

    @Override
    protected void onPause() {

        super.onPause();
        mWebViewWidget.onPause();
    }

    @Override
    protected boolean invalidateContent(String s) {
        return false;
    }

    @Override
    protected ObjectRequest<String> getObjectRequest() {
        return null;
    }

    @Override
    protected void initData() {

        mUrl = getIntent().getStringExtra("url");
        if (mUrl.indexOf("booking.com") > 0) {
            mNoJump = true;
        }

        if (mType > 100) {
            //再根据不同的来生成
            mWebViewShare = new WebViewShare();
            mWebViewShare.setInfo(mType, mUrl, mTitle);
        }

        EventBus.getDefault().register(this);


        mWebViewWidget.setUserCookie(false);
        mWebViewWidget.loadUrl(mUrl);

        mUseBottomBanner = getIntent().getBooleanExtra("usebootom", false);

    }

    @Override
    protected void initTitleView() {

        if (!mUseBottomBanner) {
            if (TextUtil.isEmpty(mTitle)) { //就是透明的
                addTitleLeftBackView(R.drawable.ic_back_gray);
            } else {
                addTitleMiddleView(mTitle);
                addTitleLeftBackView();
            }

            if (mType > 100) {
                addTitleRightView(R.drawable.ic_share_pink, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mShareDialog == null) {

                            mShareDialog = new ShareDialog(WebViewActivity.this, mWebViewShare);
                        }
                        mShareDialog.show();
                    }
                });
            }
        }
    }

    @Override
    protected void initContentView() {

        setBackgroundResource(R.color.white);
        if (mUseBottomBanner) {
            ViewUtil.showView(findViewById(R.id.llTool));
            findViewById(R.id.ivBack).setOnClickListener(this);
            findViewById(R.id.ivFoward).setOnClickListener(this);
            findViewById(R.id.ivClose).setOnClickListener(this);
            findViewById(R.id.ivRefresh).setOnClickListener(this);
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 登录的回掉
     *
     * @param event
     */

    public void onEventMainThread(LoginStatusEvent event) {

        onUserLoginStatusChanged(event.isLogin());
    }

    /**
     * 设置webview的实体类
     *
     * @param isNativeMode
     */
    protected void setContentFullScreenWebView(boolean isNativeMode) {

        setWebWidget(new WebViewNativeWidget(this));

        setContentView(R.layout.act_web_browser);

        RelativeLayout root = (RelativeLayout) getContentView();
        RelativeLayout.LayoutParams ls = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ls.addRule(RelativeLayout.ABOVE, R.id.llTool);
        root.addView(getWebWidget().getContentView(), ls);
    }

    protected void setWebWidget(WebViewBaseWidget widget) {

        mWebViewWidget = widget;
        mWebViewWidget.setWebViewListener(this);
    }

    public WebViewBaseWidget getWebWidget() {

        return mWebViewWidget;
    }

    @Override
    protected void onRetry() {
        showContentView();
        mWebViewWidget.loadUrl(mWebViewWidget.getUrl());

    }

    /*
        webview方法回调区
     */

    @Override
    public void onWebViewPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onWebViewReceiveTitle(String title) {

        //        if (TextUtil.isEmpty(mTitle) && !TextUtil.isEmpty(title) && !mUseBottomBanner) {
        //            setTitle(title);
        //        }
    }

    @Override
    public void onWebViewProgressChanged(int newProgress) {

    }

    @Override
    public void onWebViewReceivedError(int errorCode, String description, String failingUrl) {

        hideContentView();
        showFailedTip();
    }

    @Override
    public void onWebViewPageFinished(String url, boolean isUrlLoadError) {

    }

    @Override
    public void onWebViewHtmlSource(String html) {

    }

    @Override
    public boolean onWebViewShouldOverrideUrlLoading(String url) {

        if (LogMgr.isDebug())
            LogMgr.d("webviewActivity", "onWebViewShouldOverrideUrlLoading url  = " + url);
        if (mNoJump || mUrl.equals(url)) {
            loadUrl(url);
        } else if (!ActivityUrlUtil.startActivityByHttpUrl(this, url)) {
            loadUrl(url);

        }
        return true;
    }

    /*
        子类调用方法区--------------------------------------------------------------------------------
     */

    public FrameLayout getFrameView() {

        return mWebViewWidget.getContentView();
    }

    public void setWebViewHtmlSourceEnable(boolean enable) {

        mWebViewWidget.setWebViewHtmlSourceEnable(enable);
    }

    public void setWebViewCacheMode(int cacheMode) {

        mWebViewWidget.setWebViewCacheMode(cacheMode);
    }

    public void setWebViewBuiltInZoomControls(boolean enabled) {

        mWebViewWidget.setWebViewBuiltInZoomControls(enabled);
    }

    public void setWebViewOnTouchListener(View.OnTouchListener lisn) {

        mWebViewWidget.setWebViewOnTouchListener(lisn);
    }

    public void setWebViewOnScrollListener(BaseWebView.OnScrollListener lisn) {

        mWebViewWidget.setWebViewOnScrollListener(lisn);
    }

    public void reloadUrl() {

        mWebViewWidget.reloadUrl();
    }

    public void loadUrl(String url) {

        mWebViewWidget.loadUrl(url);
    }

    public void onUserLoginStatusChanged(boolean isLogin) {

        if (!isFinishing())
            mWebViewWidget.reloadUrlByLoginStateChanged();
    }

    private void onGoBackClick(boolean needFinish) {

        if (getWebWidget().canGoBack()) {

            if (getWebWidget().isCookieStatusNone()) {

                //如果没有种cookie，直接back
                getWebWidget().goBack();
            } else {

                //如果加载过cookie，则最早的历史纪录是cookieurl，不能back
                WebBackForwardList list = getWebWidget().copyBackForwardList();
                if (list != null && list.getCurrentIndex() > 1) {

                    if (mUseBottomBanner && list.getCurrentIndex() == 2) {// TODO 跳过loading页，直接关闭。暂时先这么写

                        finish();
                        return;
                    }

                    getWebWidget().goBack();
                } else {

                    if (needFinish)
                        finish();
                }
            }

        } else {

            if (needFinish)
                finish();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivBack:
                onGoBackClick(false);

                break;
            case R.id.ivFoward:
                getWebWidget().goForward();
                break;
            case R.id.ivClose:
                finish();
                break;
            case R.id.ivRefresh:
                getWebWidget().reloadUrl();

                break;

        }
    }

    /**
     * 透明标题,没有标题栏没有分享
     */
    public static void startActivityNoTitleShare(Context context, String url) {

        startActivity(context, url, "", 0, false);
    }

    /**
     * 透明标题,带分享的
     */
    public static void startActivityNoTitle(Context context, String url, int type) {

        startActivity(context, url, "", type, false);
    }

    /**
     * 标题栏不透明,有标题,不带分享
     *
     * @param context
     * @param url
     * @param title
     */
    public static void startActivityNoShare(Context context, String url, String title) {
        startActivity(context, url, title, 0, false);

    }

    /**
     * 酒店打开的方式
     *
     * @param context
     * @param url
     */
    public static void startHotelActivity(Context context, String url) {

        startActivity(context, url, "", 2, true);
    }


    /**
     * @param context
     * @param url
     * @param title
     * @param type
     * @param useBottomBanner 就不使用标题栏了,直接底部
     */
    private static void startActivity(Context context, String url, String title, int type, boolean useBottomBanner) {

        Intent intent = new Intent();
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        intent.putExtra("usebootom", useBottomBanner);
        intent.setClass(context, WebViewActivity.class);
        context.startActivity(intent);

    }

}