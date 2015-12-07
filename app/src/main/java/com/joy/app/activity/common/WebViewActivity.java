package com.joy.app.activity.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.joy.app.R;
import com.joy.app.eventbus.LoginStatusEvent;
import com.joy.app.utils.share.WebViewShare;
import com.joy.app.view.dialog.ShareDialog;
import com.joy.app.view.webview.BaseWebView;
import com.joy.app.view.webview.WebViewBaseWidget;
import com.joy.app.view.webview.WebViewNativeWidget;
import com.android.library.activity.BaseUiActivity;
import com.android.library.utils.LogMgr;

import de.greenrobot.event.EventBus;

/**
 * 统一处理webview的展现
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-10
 */
public class WebViewActivity extends BaseUiActivity implements WebViewBaseWidget.WebViewListener {

    private WebViewBaseWidget mWebViewWidget;
    private ShareDialog mShareDialog;
    private WebViewShare mWebViewShare;

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
        mWebViewShare.setInfo(getIntent().getStringExtra("url"));
        EventBus.getDefault().register(this);
        mWebViewWidget.loadUrl(getIntent().getStringExtra("url"));

    }

    @Override
    protected void initTitleView() {

        setTitle(getIntent().getStringExtra("title"));
        addTitleLeftBackView();
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
        loadUrl(url);
        //        ActivityUrlUtil.startActivityByHttpUrl(this, url);
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


    public static void startActivity(Context context, String url, String title) {

        Intent intent = new Intent();
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.setClass(context, WebViewActivity.class);
        context.startActivity(intent);

    }
}
