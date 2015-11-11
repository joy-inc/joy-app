package com.joy.app.view.webview;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.joy.app.R;
import com.joy.app.utils.LoadingViewUtil;
import com.joy.app.utils.StorageUtil;
import com.joy.app.view.loadview.LoadingView;
import com.joy.library.utils.DeviceUtil;
import com.joy.library.utils.DimenCons;
import com.joy.library.utils.ToastUtil;
import com.joy.library.utils.ViewUtil;

/**
 * 该类实现了仿原生加载url的切换界面
 * @author yhb
 *         modify by Daisw at 2015/8/7 v6.6
 */
public class WebViewNativeWidget extends WebViewBaseWidget implements DimenCons, View.OnClickListener{

    private WebView mWebView;
    private ImageView mIvTip;
    private LoadingView mLoadingView;
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

        //add transition animation
        LayoutTransition lt = new LayoutTransition();
        lt.setDuration(100);
        frameView.setLayoutTransition(lt);

        //create tip view
        mIvTip = new ImageView(getActivity());
        mIvTip.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mIvTip.setOnClickListener(this);
        ViewUtil.hideImageView(mIvTip);

        //设置网络错误提示图和为空图
        mFailedImageResId = R.mipmap.ic_net_error;

        mLoadingView = LoadingViewUtil.getLoadingViewBig(getActivity());
        mLoadingView.hide();//默认隐藏

        //add web view
        frameView.addView(webview, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        //add tip view
        frameView.addView(mIvTip, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        //add loading view
        frameView.addView(mLoadingView, new FrameLayout.LayoutParams(DP_1_PX*80, DP_1_PX*80, Gravity.CENTER));

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
        mLoadingView.show(200);
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
