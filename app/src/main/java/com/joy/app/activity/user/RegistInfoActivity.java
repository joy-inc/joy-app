package com.joy.app.activity.user;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;

/**
 * 用户完成的界面操作
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-30
 */
public class RegistInfoActivity  extends BaseHttpUiActivity<String> {

    @Override
    protected boolean invalidateContent(String s) {
        return false;
    }

    @Override
    protected ObjectRequest<String> getObjectRequest() {
        return null;
    }
}
