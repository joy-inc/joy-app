package com.joy.app.bean;

import com.android.library.utils.TextUtil;

/**
 * 商品评论详情(单条评论)
 * <p/>
 * Created by xiaoyu.chen on 15/11/20.
 */
public class CommentItem {

    private String comment_id = TextUtil.TEXT_EMPTY;
    private String comment_level = TextUtil.TEXT_EMPTY;
    private String comment = TextUtil.TEXT_EMPTY;
    private String comment_user = TextUtil.TEXT_EMPTY;
    private String comment_date = TextUtil.TEXT_EMPTY;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_level() {
        return comment_level;
    }

    public void setComment_level(String comment_level) {
        this.comment_level = comment_level;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment_user() {
        return comment_user;
    }

    public void setComment_user(String comment_user) {
        this.comment_user = comment_user;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }
}
