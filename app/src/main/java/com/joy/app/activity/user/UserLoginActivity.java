package com.joy.app.activity.user;

import android.os.Bundle;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;

/**
 * 用户登录,注册入口
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-17
 */
public class UserLoginActivity extends BaseHttpUiActivity<String> {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean invalidateContent(String datas) {

        return false;
    }

    @Override
    protected ObjectRequest<String> getObjectRequest() {
        return null;
    }
}
