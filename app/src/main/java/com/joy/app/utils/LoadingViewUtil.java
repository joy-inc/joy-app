package com.joy.app.utils;

import android.content.Context;

import com.joy.app.view.loadview.LoadingView;
import com.joy.library.utils.DimenCons;

/**
 * 用来产生loadingview的实例view
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-10
 */
public class LoadingViewUtil implements DimenCons {

    /**
     * 获取大尺寸的loading，适用于Activity页面的加载 LoadingView的高宽适合设置为80dp
     *
     * @param context
     * @return
     */
    public static LoadingView getLoadingViewBig(Context context) {

        LoadingView loadingView = new LoadingView(context, DP_1_PX * 40);
        //        loadingView.setAnimBgImage(R.drawable.ic_loading_yuan_gray);
        //        loadingView.setAnimForeImage(R.drawable.ic_loadiing_zhen);
        //        loadingView.setBackgroundResource(R.drawable.bg_qa_loading);
        return loadingView;
    }

    /**
     * 获取大尺寸的loading，适用于列表 loadmore LoadingView的高宽适合设置为30dp
     *
     * @param context
     * @return
     */
    public static LoadingView getListLoadMoreLoading(Context context) {

        LoadingView loadingView = new LoadingView(context, DP_1_PX * 20);
        //        loadingView.setAnimBgImage(R.drawable.ic_loading_yuan_white);
        //        loadingView.setAnimForeImage(R.drawable.ic_loadiing_zhen);
        return loadingView;
    }
}
