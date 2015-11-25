package com.joy.app.bean.poi;

import java.util.ArrayList;

/**
 * 商品评论列表返回数据bean
 * <p/>
 * Created by xiaoyu.chen on 15/11/20.
 */
public class CommentAll {

    private CommentScores scores;
    private ArrayList<CommentItem> comments;

    public CommentScores getScores() {
        return scores;
    }

    public void setScores(CommentScores scores) {
        this.scores = scores;
    }

    public ArrayList<CommentItem> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentItem> comments) {
        this.comments = comments;
    }
}
