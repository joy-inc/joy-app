package com.joy.app.utils.share;

import com.joy.app.R;
import com.joy.library.share.IShareInfo;
import com.joy.library.share.ShareInfo;
import com.joy.library.share.ShareType;

/**
 * app分享
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-12-07
 */
public class SettingShare implements IShareInfo {

    @Override
    public ShareInfo getShareInfo(ShareType platform) {

        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setContent("joy大法好，joy大法妙，joy大法呱呱叫，重点幸福啪啪叫，请给该员工颁发最靓员工奖！");
        shareInfo.setMediaResId(R.drawable.ic_pink_rectangle_logo);

        shareInfo.setMediaUrl(ShareUtil.getWeiboPaht());
        shareInfo.setTitle("joy大法好");
        shareInfo.setUrl("http://www.qyer.com");
        return shareInfo;
    }
}
