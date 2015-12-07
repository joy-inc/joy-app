package com.joy.app.utils.share;

import com.android.library.utils.DeviceUtil;
import com.android.library.utils.IOUtil;
import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.app.utils.StorageUtil;

import java.io.File;
import java.io.InputStream;

/**
 * 整理一些资源和工具
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-12-06
 */
public class ShareUtil {

    /**
     * 获取weibo的图片共享路径
     * @return
     */
    public static String getWeiboPaht(){
        File file = new File(StorageUtil.getQyerTempDir(), "share_img_for_weibo");
        if(file.exists()){
            return file.getAbsolutePath();
        }
        InputStream is = JoyApplication.getContext().getResources().openRawResource(R.raw.share_img_for_weibo);

        if (DeviceUtil.sdcardIsEnable() && IOUtil.copyFileFromRaw(is, file)){
            return file.getAbsolutePath();
        }
        return null;
    }
}
