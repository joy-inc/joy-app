package com.joy.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.joy.app.JoyApplication;
import com.joy.app.utils.ActivityUrlUtil;
import com.joy.app.utils.UrlUtil;
import com.joy.library.utils.DimenCons;
import com.joy.library.utils.LogMgr;
import com.joy.library.utils.TextUtil;
import com.joy.library.view.ExLayoutWidget;
import com.joy.library.view.ExViewWidget;

/**
 * QyerApp webView逻辑的组件基类
 * 该类实现了大app相关的业务逻辑：同步登录等
 * @author yhb
 */
@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface"})
public abstract class WebViewBaseWidget extends ExLayoutWidget implements DimenCons{

    private final int MSG_WHAT_WEBVIEW_CUSTOM_TIMEOUT = 1;//webview自定义超时
    private final int WEBVIEW_CUSTOM_TIME_OUT_MILLIS = 1000 * 20;//webview自定义超时20秒

    private final int COOKIE_STATUS_NONE = 1;
    private final int COOKIE_STATUS_LOADING = 2;
    private final int COOKIE_STATUS_SUCCESS = 3;
    private final int COOKIE_STATUS_FAILED = 4;

    private BaseWebView mWebView;
    private WebViewListener mWebViewLisn;

    private boolean mIsGetHtmlSource;
    private boolean mIsLoadUrlError;

    private String mTempLoadUrl;
    private int mCookieStatus = COOKIE_STATUS_NONE;
    private int mCookieUrlTryCount;

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            WebViewBaseWidget.this.handleWebViewTimeOutMessage(msg);
        }
    };

    public WebViewBaseWidget(Activity activity){

        super(activity);
    }

    @Override
    public FrameLayout getContentView() {

        return (FrameLayout) super.getContentView();
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        mWebView = new BaseWebView(getActivity());
        mWebView.getSettings().setJavaScriptEnabled(true);
        View contentView = onInflateLayout(mWebView);
        setWebViewListener(mWebView);
        return contentView;
    }

    protected abstract View onInflateLayout(WebView webview);

    private void setWebViewListener(WebView webView){

        //禁止长按复制
        webView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {

                if (!isActivityFinishing()) {

                    super.onReceivedTitle(view, title);
                    if (canCallbackWebviewListener())
                        mWebViewLisn.onWebViewReceiveTitle(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (!isActivityFinishing()) {

                    super.onProgressChanged(view, newProgress);
                    onWebViewProgressChanged(newProgress);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, final String url, Bitmap favicon) {

                if (!isActivityFinishing()) {

                    super.onPageStarted(view, url, favicon);
                    onWebViewPageStarted(url, favicon);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                if (!isActivityFinishing()) {

                    super.onReceivedError(view, errorCode, description, failingUrl);
                    onWebViewReceivedError(errorCode, description, failingUrl);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                if (!isActivityFinishing()) {

                    super.onPageFinished(view, url);
                    onWebViewPageFinished(url, mIsLoadUrlError);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (isActivityFinishing())
                    return true;

                if (mWebViewLisn != null)
                    return mWebViewLisn.onWebViewShouldOverrideUrlLoading(url);

                return false;
            }

            /*暂未使用
            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {

                if(!isFinishing())
                    super.doUpdateVisitedHistory(view, url, isReload);
            }
            */

            /*暂未使用
            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                super.onScaleChanged(view, oldScale, newScale);
            }
            */
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {

                ActivityUrlUtil.startUriActivity(getActivity(), url, true);
            }
        });
    }

    @Override
    public void onPause() {

        if(isActivityFinishing()){

            mWebView.stopLoading();
            removeWebViewTimeOutMsg();
        }
    }

    /*
     * webview 回调方法区-----------------------------------------------------------------------------
     */

    private void onWebViewPageStarted(String url, Bitmap favicon){

        if(LogMgr.isDebug())
            LogMgr.d(simpleTag(), "onWebViewPageStarted url = "+url);

        mIsLoadUrlError = false;
        sendWebViewTimeOutMsg();

        switchViewOnPageStarted();

        //callback
        if(canCallbackWebviewListener())
            mWebViewLisn.onWebViewPageStarted(url, favicon);
    }

    private void onWebViewProgressChanged(int newProgress){

        if (LogMgr.isDebug())
            LogMgr.d(simpleTag(), "onWebViewProgressChanged progress = " + newProgress);

        switchViewOnProgressChanged(newProgress);

        //callback
        if(canCallbackWebviewListener())
            mWebViewLisn.onWebViewProgressChanged(newProgress);
    }

    /**
     * 回调完该方法后，webView会接着回调 onWebViewPageFinished()方法
     * @param errorCode
     * @param description
     * @param failingUrl
     */
    private void onWebViewReceivedError(int errorCode, String description, String failingUrl){

        if(LogMgr.isDebug())
            LogMgr.d(simpleTag(), "onWebViewReceivedError url = "+failingUrl+", errorCode = "+errorCode+", desc = "+description);

        mIsLoadUrlError = true;
        removeWebViewTimeOutMsg();

        //switchViewOnPageFinishedError();//不给错误回调，在finish回调中统一处理

        //callback
        if(canCallbackWebviewListener())
            mWebViewLisn.onWebViewReceivedError(errorCode, description, failingUrl);
    }

    private void handleWebViewTimeOutMessage(Message msg){

        if(isActivityFinishing())
            return;

        switch(msg.what){
            case MSG_WHAT_WEBVIEW_CUSTOM_TIMEOUT:
                onWebViewPageTimeout();
                break;
        }
    }

    /**
     * 超时的错误不通过onWebViewReceivedError()返回。
     */
    protected void onWebViewPageTimeout(){

        if(LogMgr.isDebug())
            LogMgr.d(simpleTag(), "onWebViewPageTimeout");

        mIsLoadUrlError = true;
        removeWebViewTimeOutMsg();
        mWebView.stopLoading();//停止加载
    }

    /**
     * 即使加载出错，WebView也会回调onPageFinished
     * @param url
     */
    private void onWebViewPageFinished(String url, boolean isLoadUrlError) {

        if(LogMgr.isDebug())
            LogMgr.d(simpleTag(), "onWebViewPageFinished url = "+url);

        removeWebViewTimeOutMsg();

        if(isLoadUrlError){

            onWebViewPageFinishedError(url);
        }else{

            onWebViewPageFinishedSuccess(url);
        }

        mIsLoadUrlError = false;
    }

    private void onWebViewPageFinishedSuccess(String url){

        if(isCookieStatusLoading()){

            //如果cookie正在加载中，只处理cookie
            handleCookieUrl(url);
        }else{

            //cookie已加载成功
            switchViewOnPageFinishedSuccess();

            if(mWebViewLisn != null){

                //callback
                mWebViewLisn.onWebViewPageFinished(url, false);

                //回调源码接口方法
                if (mIsGetHtmlSource)
                    mWebView.loadUrl("javascript:window.htmlSource.showSource(document.getElementsByTagName('html')[0].innerHTML);");
            }
        }
    }

    private void onWebViewPageFinishedError(String url){

        switchViewOnPageFinishedError();

        if(canCallbackWebviewListener())
            mWebViewLisn.onWebViewPageFinished(url, true);
    }

    private final class HtmlSourceObj {

        @JavascriptInterface// 千万不能去掉这个注解
        public void showSource(String html) {

            if(mWebViewLisn != null)
                mWebViewLisn.onWebViewHtmlSource(html);
        }
    }

    private void handleCookieUrl(String url){

        String cookieUrl = UrlUtil.getWebViewCookieUrl(JoyApplication.getUserToken());
        if(!cookieUrl.equals(url))
            return;

        CookieManager cookieManager = CookieManager.getInstance();
        String cookieInfo = cookieManager.getCookie(cookieUrl);
        if(TextUtil.isEmpty(cookieInfo)){

            //cookie没有种上，尝试两次
            if(mCookieUrlTryCount < 2){

                if(LogMgr.isDebug())
                    LogMgr.d(simpleTag(), "handleCookieUrl():setCooking, tryCount = "+mCookieUrlTryCount);

                mCookieUrlTryCount ++;
                mWebView.reload();//重新加载，此时的url是cookieurl。

            }else{

                if(LogMgr.isDebug())
                    LogMgr.d(simpleTag(), "handleCookieUrl():setCookieFailed, tryCount = "+mCookieUrlTryCount);

                setCookieStatusFailed();
                mCookieUrlTryCount = 0;
                loadUrl(mTempLoadUrl);//cookie设置3次还失败，不再种cookie，标记为失败，加载临时url
            }

        }else{

            if(LogMgr.isDebug())
                LogMgr.d(simpleTag(), "handleCookieUrl():setCookieSuccess, tryCount = "+mCookieUrlTryCount);

            setCookieStatusSuccess();//cookie已种上，标记成功
            mCookieUrlTryCount = 0;
            loadUrl(mTempLoadUrl);//加载临时url
        }
    }

    /*
     * webview 公共方法区-----------------------------------------------------------------------------
     */

    /**
     * 设置webview监听器
     * @param lisn
     */
    public void setWebViewListener(WebViewListener lisn){

        mWebViewLisn = lisn;
    }

    /**
     * 设置webviewTouch监听器
     * @param lisn
     */
    public void setWebViewOnTouchListener(View.OnTouchListener lisn){

        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.setOnTouchListener(lisn);
    }

    public void setWebViewOnScrollListener(BaseWebView.OnScrollListener lisn){

        mWebView.setOnScrollListener(lisn);
    }
    /**
     * 设置webview是否可返回源码
     * @param enable
     */
    public void setWebViewHtmlSourceEnable(boolean enable){

        if(enable){

            mWebView.addJavascriptInterface(new HtmlSourceObj(), "htmlSource");
        }else{

            mWebView.removeJavascriptInterface("htmlSource");
        }

        mIsGetHtmlSource = enable;
    }

    /**
     * 设置webview缓存模式
     * @param cacheMode webview定义的常量
     */
    public void setWebViewCacheMode(int cacheMode){

        mWebView.getSettings().setCacheMode(cacheMode);
    }

    /**
     * 设置webview是否可缩放
     * @param enabled
     */
    public void setWebViewBuiltInZoomControls(boolean enabled){

        mWebView.getSettings().setBuiltInZoomControls(enabled);
    }

    public void reloadUrlByLoginStateChanged(){

        setCookieStatusNone();

        if(TextUtil.isEmpty(mTempLoadUrl))
            loadUrl(mWebView.getUrl());
        else
            loadUrl(mTempLoadUrl);
    }

    public void reloadUrl(){

        mWebView.reload();
    }

    public String getUrl(){

        return mWebView.getUrl();
    }

    public void goForward(){

        mWebView.goForward();
    }

    public void goBack(){

        mWebView.goBack();
    }

    public boolean canGoForward(){

        return mWebView.canGoForward();
    }

    public boolean canGoBack(){

        return mWebView.canGoBack();
    }

    public WebBackForwardList copyBackForwardList(){

        return mWebView.copyBackForwardList();
    }

    public void loadUrl(String url){

        if(TextUtil.isEmpty(url))
            return;

        if(isCookieStatusLoaded()){//cookie已经加载过，直接加载新链接

            if(LogMgr.isDebug())
                LogMgr.d(simpleTag(), "loadUrl cookie loaded, url="+url);

            mWebView.loadUrl(url);
            mTempLoadUrl = null;

        }else if(isCookieStatusLoading()){//cookie正在加载中，则只临时保存要加载的链接，cookie种完后再加载该链接

            if(LogMgr.isDebug())
                LogMgr.d(simpleTag(), "loadUrl cookie loading, url="+url);

            mTempLoadUrl = url;

        }else if(isCookieStatusNone()){//cookie还未加载过

            if(LogMgr.isDebug())
                LogMgr.d(simpleTag(), "loadUrl cookie loadnone, user is login ="+JoyApplication.isLogin()+", url="+url);

            if(!JoyApplication.isLogin()){//用户未登录

                //直接加载url
                mWebView.loadUrl(url);
                mTempLoadUrl = null;
                return;
            }

//            //用户已登录，获取cookieUrl
            String cookieUrl = UrlUtil.getWebViewCookieUrl(JoyApplication.getUserToken());
            if(LogMgr.isDebug())
                LogMgr.d(simpleTag(), "loadUrl cookie loadnone cookieUrl = "+cookieUrl+", webview has cookie = "+webViewHasCookie(cookieUrl));

            /**
             webViewHasCookie(cookieUrl) 暂时去除该判断，每次都先加载一次cookie链接
            */
//            if(webViewHasCookie(cookieUrl)){
//
//                //已种好cookie，直接加载url，标记cookie种成功
//                setCookieStatusSuccess();
//                mWebView.loadUrl(url);
//                mTempLoadUrl = null;
//
//            }else{

                //没有cookie, webview加载种cookieurl，标记cookie正在加载
                mTempLoadUrl = url;
                mCookieUrlTryCount = 0;
                setCookieStatusLoading();
                mWebView.loadUrl(cookieUrl);
//            }
        }
    }

    /*
        webview 帮助方法区----------------------------------------------------------------------------
     */

    protected abstract void switchViewOnPageStarted();

    protected abstract void switchViewOnProgressChanged(int newProgress);

    protected abstract void switchViewOnPageFinishedSuccess();

    protected abstract void switchViewOnPageFinishedError();

    private void setCookieStatusNone(){

        mCookieStatus = COOKIE_STATUS_NONE;
        mCookieUrlTryCount = 0;
    }

    private void setCookieStatusLoading(){

        mCookieStatus = COOKIE_STATUS_LOADING;
    }

    private void setCookieStatusSuccess(){

        mCookieStatus = COOKIE_STATUS_SUCCESS;
    }

    private void setCookieStatusFailed(){

        mCookieStatus = COOKIE_STATUS_FAILED;
    }

    public boolean isCookieStatusNone(){

        return mCookieStatus == COOKIE_STATUS_NONE;
    }

    private boolean isCookieStatusLoaded(){

        return mCookieStatus == COOKIE_STATUS_SUCCESS || mCookieStatus == COOKIE_STATUS_FAILED;
    }

    public boolean isCookieStatusLoading(){

        return mCookieStatus == COOKIE_STATUS_LOADING;
    }

    private boolean webViewHasCookie(String cookieUrl){

        if(LogMgr.isDebug())
            LogMgr.d(simpleTag(), "webViewHasCookie cookie info = "+ CookieManager.getInstance().getCookie(cookieUrl));

        return !TextUtil.isEmpty(CookieManager.getInstance().getCookie(cookieUrl));
    }

    private void sendWebViewTimeOutMsg(){

        mHandler.removeMessages(MSG_WHAT_WEBVIEW_CUSTOM_TIMEOUT);
        mHandler.sendEmptyMessageDelayed(MSG_WHAT_WEBVIEW_CUSTOM_TIMEOUT, WEBVIEW_CUSTOM_TIME_OUT_MILLIS);
    }

    private void removeWebViewTimeOutMsg(){

        mHandler.removeMessages(MSG_WHAT_WEBVIEW_CUSTOM_TIMEOUT);
    }

    private boolean canCallbackWebviewListener(){

        //cooking状态不给回调
        return mWebViewLisn != null && mCookieStatus != COOKIE_STATUS_LOADING;
    }

    public static interface WebViewListener{

        public void onWebViewPageStarted(String url, Bitmap favicon);
        public void onWebViewReceiveTitle(String title);
        public void onWebViewProgressChanged(int newProgress);
        public void onWebViewReceivedError(int errorCode, String description, String failingUrl);
        public void onWebViewPageFinished(String url, boolean isUrlLoadError);
        public void onWebViewHtmlSource(String html);
        public boolean onWebViewShouldOverrideUrlLoading(String url);
    }
}
