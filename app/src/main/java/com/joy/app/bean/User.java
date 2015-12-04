package com.joy.app.bean;

import com.android.library.utils.TextUtil;

/**
 * 用户登录信息
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-17
 */
public class User {

    private String user_id;//用户id
    private String mobile;//用户电话
    private String nickname;//用户昵称
    private long create_time;//创建时间
    private String token;//用户token

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = TextUtil.filterNull(user_id);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = TextUtil.filterNull(mobile);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = TextUtil.filterNull(nickname);
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = TextUtil.filterNull(token);
    }
}
