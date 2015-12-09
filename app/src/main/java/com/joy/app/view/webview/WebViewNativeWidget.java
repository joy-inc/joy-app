package com.joy.app.view.webview;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import com.joy.app.R;
import com.joy.app.utils.StorageUtil;
import com.android.library.utils.DeviceUtil;
import com.android.library.utils.DimenCons;
import com.android.library.utils.ToastUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.widget.JLoadingView;

/**
 * 该类实现了仿原生加载url的切换界面
 * @author yhb
 *         modify by Daisw at 2015/8/7 v6.6
 */
public class WebViewNativeWidget extends WebViewBaseWidget implements DimenCons, View.OnClickListener{

    private WebView mWebView;
    private ImageView mIvTip;
    private JLoadingView mLoadingView;
    private int mFailedImageResId;

    public WebViewNativeWidget(Activity activity){

        super(activity);
    }

    @Override
    protected View onInflateLayout(WebView webview) {

        //webview 默认隐藏
        mWebView = webview;
        setNativeWebViewAttr(mWebView);
        ViewUtil.hideView(mWebView);

        FrameLayout frameView = new FrameLayout(getActivity());

        // add transition animation
        LayoutTransition lt = new LayoutTransition();
        lt.setDuration(100);
        frameView.setLayoutTransition(lt);

        //create tip view
        mIvTip = new ImageView(getActivity());
        mIvTip.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mIvTip.setOnClickListener(this);
        ViewUtil.hideImageView(mIvTip);

        //设置网络错误提示图和为空图
        mFailedImageResId = R.drawable.ic_net_error;

        mLoadingView = JLoadingView.get(getActivity());
        mLoadingView.hide();//默认隐藏

        //add web view
        frameView.addView(webview, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //add tip view
        frameView.addView(mIvTip, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //add loading view
        frameView.addView(mLoadingView, JLoadingView.getLp());

        return frameView;
    }

    private void setNativeWebViewAttr(WebView webView){

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);//加速渲染
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        settings.setBuiltInZoomControls(true);//构建缩放控制，包含 setSupportZoom(true)和setDisplayZoomControls(true)
        settings.setSupportZoom(true);//支持缩放
        settings.setDisplayZoomControls(false);//是否显示缩放按钮

        // add Cache
        settings.setDomStorageEnabled(true);
        settings.setAppCacheMaxSize(1024 * 1024 * 8);// 设置缓冲大小，我设的是8M
        settings.setAppCachePath(StorageUtil.getWebViewCachePath());
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    @Override
    protected void switchViewOnPageStarted() {

        ViewUtil.hideView(mWebView);
        ViewUtil.hideImageView(mIvTip);
        mLoadingView.show();
    }

    @Override
    protected void switchViewOnProgressChanged(int newProgress) {

        //nothing to do
    }

    @Override
    protected void switchViewOnPageFinishedSuccess() {

        mLoadingView.hide();
        ViewUtil.hideImageView(mIvTip);
        ViewUtil.showView(mWebView);
    }

    @Override
    protected void switchViewOnPageFinishedError() {

        mLoadingView.hide();
        ViewUtil.hideView(mWebView);
        ViewUtil.showImageView(mIvTip, mFailedImageResId);
    }

    @Override
    public void onClick(View view) {

        if (DeviceUtil.isNetworkDisable()) {

            ToastUtil.showToast(R.string.toast_common_no_network);
        }else{

            reloadUrl();
        }
    }
}
