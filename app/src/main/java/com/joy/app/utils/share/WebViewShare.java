package com.joy.app.utils.share;

import com.android.library.utils.LogMgr;
import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.library.share.IShareInfo;
import com.joy.library.share.ShareInfo;
import com.joy.library.share.ShareType;

import java.io.File;

/**
 * webview的分享
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-12-05
 */
public class WebViewShare implements IShareInfo {

    String url;

    public void setInfo(String url) {
        this.url = url;
    }

    @Override
    public ShareInfo getShareInfo(ShareType platform) {
        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setContent("要分享的url" + url);
        shareInfo.setMediaResId(R.drawable.ic_joy_login);

        shareInfo.setMediaUrl(ShareUtil.getWeiboPaht());
        shareInfo.setTitle("分享的");
        shareInfo.setUrl(url);
        return shareInfo;
    }
}
