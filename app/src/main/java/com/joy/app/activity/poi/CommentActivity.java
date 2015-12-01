package com.joy.app.activity.poi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TextUtil;
import com.joy.app.BuildConfig;
import com.joy.app.R;
import com.joy.app.adapter.poi.CommentRvAdapter;
import com.joy.app.adapter.sample.CityDetailRvAdapter3;
import com.joy.app.bean.poi.CommentAll;
import com.joy.app.bean.poi.CommentItem;
import com.joy.app.bean.poi.CommentScores;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu.chen on 15/11/27.
 */
public class CommentActivity extends BaseHttpRvActivity<CommentAll> {

    private String mId;

    private CommentRvAdapter mAdapter;

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

        setTitleText(R.string.comment_all);
//        setTitle(R.string.comment_all);
        addTitleLeftBackView();
    }

    @Override
    protected void initContentView() {

        mAdapter = new CommentRvAdapter(this, this);
        setAdapter(mAdapter);
        getRecyclerView().setBackgroundResource(R.color.white);
    }

    @Override
    protected boolean invalidateContent(CommentAll commentAll) {

        mAdapter.setScoreData(commentAll.getScores());

        return super.invalidateContent(commentAll);
    }

    @Override
    protected List<CommentItem> getListInvalidateContent(CommentAll commentAll) {

        return commentAll.getComments();
    }

    @Override
    protected ObjectRequest<CommentAll> getObjectRequest() {

        ObjectRequest obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_COMMENTS, CommentAll.class, OrderHtpUtil.getProductCommentListUrl(mId, 20, 1));// todo 有分页

        if (BuildConfig.DEBUG) {

            CommentAll data = new CommentAll();
            CommentScores scores = new CommentScores();
            scores.setComment_level("3.5");
            scores.setComment_num("22");
            scores.setFive("1");
            scores.setFour("2");
            scores.setThree("3");
            scores.setTwo("4");
            scores.setOne("5");

            data.setScores(scores);

            ArrayList<CommentItem> list = new ArrayList();

            for (int i = 0; i < 7; i++) {
                CommentItem item = new CommentItem();
                item.setComment_id(i + "");
                item.setComment("我是第 " + i + " 个来点评的诶！～");
                item.setComment_level("2.5");
                item.setComment_date("2015年11月19日");
                item.setComment_user("小" + i);

                list.add(item);
            }
            data.setComments(list);
            obj.setData(data);

        }

        return obj;
    }

    //    @Override
//    protected ObjectRequest<CommentAll> getObjectRequest(int pageIndex, int pageLimit) {
//
//        ObjectRequest obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_COMMENTS, CommentAll.class, OrderHtpUtil.getProductCommentListUrl(mId, pageLimit, pageIndex));
//
//        return obj;
//    }

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
