package com.joy.app.activity.user;

import android.os.Bundle;

import com.joy.library.activity.frame.BaseHttpLvActivity;
import com.joy.library.activity.frame.BaseHttpUiActivity;
import com.joy.library.httptask.frame.ObjectRequest;

/**
 * 用户登录,注册入口
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-17
 */
public class UserLoginActivity extends BaseHttpUiActivity<String> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean invalidateContent(String datas) {

        return true;
    }

    @Override
    protected ObjectRequest<String> getObjectRequest() {
        return null;
    }
}
