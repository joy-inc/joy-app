package com.joy.app.utils.http;

import com.android.library.httptask.ObjectRequest;
import com.joy.app.JoyApplication;
import com.joy.app.bean.User;
import com.joy.app.utils.http.BaseHtpUtil;

import java.util.Map;

/**
 * 用户相关的网络请求
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-12-03
 */
public class UserHtpUtil extends BaseHtpUtil {

    /**
     * 获取验证码
     *
     * @param phone
     * @return
     */
    public static Map<String, String> getCode(String phone) {

        Map<String, String> params = getBaseParams();
        params.put("mobile", phone);
        return params;
    }

    /**
     * 用户登录
     *
     * @param phone
     * @param code
     * @return
     */
    public static Map<String, String> userLogin(String phone, String code) {

        Map<String, String> params = getBaseParams();
        params.put("mobile", phone);
        params.put("mobile_code", code);
        return params;

    }

    /**
     * 登出
     * @param token
     * @return
     */
    public static Map<String,String>userLoginOut(String token){

        Map<String, String> params = getBaseParams();
        params.put("user_token", token);
        return params;
    }
}
