package com.joy.app.activity.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.joy.app.utils.ActivityUrlUtil;
import com.joy.app.view.webview.BaseWebView;
import com.joy.app.view.webview.WebViewBaseWidget;
import com.joy.app.view.webview.WebViewNativeWidget;
import com.joy.library.activity.frame.BaseUiActivity;
import com.joy.library.utils.LogMgr;

/**
 * 统一处理webview的展现
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-10
 */
public class WebViewActivity extends BaseUiActivity  implements WebViewBaseWidget.WebViewListener {

    private WebViewBaseWidget mWebViewWidget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        registerUserLoginReceiver();
    }

    @Override
    protected void onPause() {

        super.onPause();
        mWebViewWidget.onPause();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        unregisterReceiver(mUserLoginReceiver);
    }

    protected void setContentFullScreenWebView(boolean isNativeMode){

//        if(isNativeMode){

            setWebWidget(new WebViewNativeWidget(this));
//        }else{
//
//            setWebWidget(new WebViewBaseWidget(this));
//        }

        setContentView(getWebWidget().getContentView());
    }

    protected void setWebWidget(WebViewBaseWidget widget){

        mWebViewWidget = widget;
        mWebViewWidget.setWebViewListener(this);
    }

    public WebViewBaseWidget getWebWidget(){

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

        if(LogMgr.isDebug())
            LogMgr.d("webviewActivity", "onWebViewShouldOverrideUrlLoading url  = " + url);

        ActivityUrlUtil.startActivityByHttpUrl(this, url);
        return true;
    }

    /*
        子类调用方法区--------------------------------------------------------------------------------
     */

    public FrameLayout getFrameView(){

        return mWebViewWidget.getContentView();
    }

    public void setWebViewHtmlSourceEnable(boolean enable){

        mWebViewWidget.setWebViewHtmlSourceEnable(enable);
    }

    public void setWebViewCacheMode(int cacheMode){

        mWebViewWidget.setWebViewCacheMode(cacheMode);
    }

    public void setWebViewBuiltInZoomControls(boolean enabled){

        mWebViewWidget.setWebViewBuiltInZoomControls(enabled);
    }

    public void setWebViewOnTouchListener(View.OnTouchListener lisn){

        mWebViewWidget.setWebViewOnTouchListener(lisn);
    }

    public void setWebViewOnScrollListener(BaseWebView.OnScrollListener lisn){

        mWebViewWidget.setWebViewOnScrollListener(lisn);
    }

    public void reloadUrl(){

        mWebViewWidget.reloadUrl();
    }

    public void loadUrl(String url){

        mWebViewWidget.loadUrl(url);
    }

    public void onUserLoginStatusChanged(boolean isLogin) {

        if(!isFinishing())
            mWebViewWidget.reloadUrlByLoginStateChanged();
    }

    protected void registerUserLoginReceiver(){

//        IntentFilter filter = new IntentFilter();
//        filter.addAction(UserManager.INTENT_ACTION_USER_LOGIN);
//        filter.addAction(UserManager.INTENT_ACTION_USER_LOGIN_OUT);
//        registerReceiver(mUserLoginReceiver, filter);
    }
    private BroadcastReceiver mUserLoginReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

//            if(UserManager.INTENT_ACTION_USER_LOGIN.equals(intent.getAction())){
//
//                onUserLoginStatusChanged(true);
//
//            }else if(UserManager.INTENT_ACTION_USER_LOGIN_OUT.equals(intent.getAction())){
//
//                onUserLoginStatusChanged(false);
//            }
        }
    };
}
