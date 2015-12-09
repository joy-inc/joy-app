package com.joy.app.activity.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.android.library.activity.BaseUiActivity;
import com.android.library.utils.LogMgr;
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
public class WebViewActivity extends BaseUiActivity implements WebViewBaseWidget.WebViewListener {

    //--大于100有分享的意思
    public static final int TYPE_CITY = 101;//城市详情

    public static final int TYPE_ABOUT = 1;//关于界面

    private WebViewBaseWidget mWebViewWidget;
    private ShareDialog mShareDialog;
    private WebViewShare mWebViewShare;
    private int mType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentFullScreenWebView(true);
    }

    @Override
    protected void onPause() {

        super.onPause();
        mWebViewWidget.onPause();
    }

    @Override
    protected void initData() {
        mWebViewShare = new WebViewShare();
        mWebViewShare.setInfo("http://www.qq.com");
        EventBus.getDefault().register(this);
        mWebViewWidget.loadUrl(getIntent().getStringExtra("url"));
        mType = getIntent().getIntExtra("type", 0);
    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView();
        addTitleMiddleView(getIntent().getStringExtra("title"));

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
        setContentView(getWebWidget().getContentView());
    }

    protected void setWebWidget(WebViewBaseWidget widget) {

        mWebViewWidget = widget;
        mWebViewWidget.setWebViewListener(this);
    }

    public WebViewBaseWidget getWebWidget() {

        return mWebViewWidget;
    }

    /*
        webview方法回调区
     */

    @Override
    public void onWebViewPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onWebViewReceiveTitle(String title) {

    }

    @Override
    public void onWebViewProgressChanged(int newProgress) {

    }

    @Override
    public void onWebViewReceivedError(int errorCode, String description, String failingUrl) {

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
        if (!ActivityUrlUtil.startActivityByHttpUrl(this, url)) {
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

    /**
     * 不需要分享的调这
     */
    public static void startActivity(Context context, String url, String title) {

        startActivity(context, url, title, 0);

    }

    /**
     * 有分享的调用这
     *
     * @param context
     * @param url
     * @param title
     * @param type
     */
    public static void startActivity(Context context, String url, String title, int type) {

        Intent intent = new Intent();
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        intent.setClass(context, WebViewActivity.class);
        context.startActivity(intent);

    }
}
