package com.joy.app.eventbus;

import com.joy.app.bean.User;

/**
 * 登录状态的通知,登入登出
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-17
 */
public class LoginStatusEvent {


    //true  登入 false 登出
    private boolean isLogin = true;
    private User user;

    public LoginStatusEvent(boolean loginStatus,User user){
        this.isLogin=loginStatus;
        this.user=user;
    }
    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
