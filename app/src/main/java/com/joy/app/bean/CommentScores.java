package com.joy.app.bean;

import com.android.library.utils.TextUtil;

/**
 * 商品评分
 * <p/>
 * Created by xiaoyu.chen on 15/11/20.
 */
public class CommentScores {

    private String comment_level = TextUtil.TEXT_EMPTY;
    private String comment_num = TextUtil.TEXT_EMPTY;
    private String five = TextUtil.TEXT_EMPTY;
    private String four = TextUtil.TEXT_EMPTY;
    private String three = TextUtil.TEXT_EMPTY;
    private String two = TextUtil.TEXT_EMPTY;
    private String one = TextUtil.TEXT_EMPTY;

    public String getComment_level() {
        return comment_level;
    }

    public void setComment_level(String comment_level) {
        this.comment_level = comment_level;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getFive() {
        return five;
    }

    public void setFive(String five) {
        this.five = five;
    }

    public String getFour() {
        return four;
    }

    public void setFour(String four) {
        this.four = four;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }
}
