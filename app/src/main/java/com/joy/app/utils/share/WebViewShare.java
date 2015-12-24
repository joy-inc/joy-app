package com.joy.app.utils.share;

import com.joy.app.R;
import com.joy.library.share.IShareInfo;
import com.joy.library.share.ShareInfo;
import com.joy.library.share.ShareType;

/**
 * webview的分享
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-12-05
 */
public class WebViewShare implements IShareInfo {

    private String mUrl;

    private int mType;
    private String mTitle;
    public void setInfo(int type, String url,String title) {
        mUrl = url;
        mType = type;
        mTitle=title;
    }

    @Override
    public ShareInfo getShareInfo(ShareType platform) {

        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setContent("要分享的url" + mUrl);
        shareInfo.setMediaResId(R.drawable.ic_logo_circle);

        shareInfo.setMediaUrl(ShareUtil.getWeiboPaht());
        shareInfo.setTitle("分享的");
        shareInfo.setUrl(mUrl);
        return shareInfo;
    }
}
