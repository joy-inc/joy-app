package com.joy.app.activity.poi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TextUtil;
import com.joy.app.R;
import com.joy.app.adapter.poi.CommentRvAdapter;
import com.joy.app.bean.poi.CommentAll;
import com.joy.app.bean.poi.CommentItem;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;

import java.util.List;

/**
 * 全部点评页
 * Created by xiaoyu.chen on 15/11/27.
 */
public class CommentActivity extends BaseHttpRvActivity<CommentAll> {

    private String mId;
    private CommentScoresWidget mScoreWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        executeRefreshOnly();
    }

    @Override
    protected void initData() {

        mId = TextUtil.filterNull(getIntent().getStringExtra("id"));
    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView();
        addTitleMiddleView(R.string.comment_all);
    }

    @Override
    protected void initContentView() {

        setBackgroundResource(R.color.white);
        mScoreWidget = new CommentScoresWidget(this);
        setAdapter(new CommentRvAdapter());
        setLoadMoreDarkTheme();
    }

    @Override
    protected boolean invalidateContent(CommentAll commentAll) {

        addHeaderView(mScoreWidget.getContentView());
        mScoreWidget.invalidate(commentAll.getScores());
        return super.invalidateContent(commentAll);
    }

    @Override
    protected List<CommentItem> getListInvalidateContent(CommentAll commentAll) {

        return commentAll.getComments();
    }

    @Override
    protected ObjectRequest<CommentAll> getObjectRequest(int pageIndex, int pageLimit) {

        ObjectRequest obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_COMMENTS, CommentAll.class, OrderHtpUtil.getProductCommentListUrl(mId, pageLimit, pageIndex));

        return obj;
    }

    /**
     * @param act
     * @param id  product id
     */
    public static void startActivity(Activity act, String id) {

        if (act == null || TextUtil.isEmpty(id))
            return;

        Intent intent = new Intent(act, CommentActivity.class);
        intent.putExtra("id", id);
        act.startActivity(intent);
    }
}
