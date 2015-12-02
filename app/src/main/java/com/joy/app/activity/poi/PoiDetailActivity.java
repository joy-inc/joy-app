package com.joy.app.activity.poi;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TextUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.view.ExBaseWidget;
import com.joy.app.BuildConfig;
import com.joy.app.R;
import com.joy.app.activity.map.SinglePoiMapActivity;
import com.joy.app.activity.map.StaticMapWidget;
import com.joy.app.bean.poi.CommentAll;
import com.joy.app.bean.poi.CommentItem;
import com.joy.app.bean.poi.CommentScores;
import com.joy.app.bean.sample.PoiDetail;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;

import java.util.ArrayList;

/**
 * 目的地详折扣情页(下订单的唯一入口)
 * <p/>
 * Created by xiaoyu.chen on 15/11/11.
 */
public class PoiDetailActivity extends BaseHttpUiActivity<PoiDetail> implements View.OnClickListener, ExBaseWidget.OnWidgetViewClickListener {

    private String mId;
    private String mPhotoUrl;
    private String mTopicId;
    private PoiDetail mPoiDetail;

    private TextView mAcbBook;
    private PoiDetailHeaderWidget mHeaderWidget;
    private PoiDetailHighWidget mHighWidget;
    private StaticMapWidget mMapWidget;
    private PoiDetailIntroduceWidget mIntroduceWidget;
    private PoiDetailCommentWidget mCommentWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_poi_detail);
        executeRefreshOnly();
        getCommentList();
    }

    @Override
    protected void initData() {

        mId = TextUtil.filterNull(getIntent().getStringExtra("id"));
        mPhotoUrl = TextUtil.filterNull(getIntent().getStringExtra("id"));
        mTopicId = TextUtil.filterNull(getIntent().getStringExtra("id"));
    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView();
    }

    @Override
    protected void initContentView() {

        mAcbBook = (TextView) findViewById(R.id.acbBook);
        mAcbBook.setOnClickListener(this);
        ViewUtil.goneView(mAcbBook);

        mHeaderWidget = new PoiDetailHeaderWidget(this);
        mHeaderWidget.setOnWidgetViewClickListener(this);
        RelativeLayout headerDiv = (RelativeLayout) findViewById(R.id.poiDetailHeaderDiv);
        headerDiv.addView(mHeaderWidget.getContentView(), new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mHighWidget = new PoiDetailHighWidget(this);
        RelativeLayout highDiv = (RelativeLayout) findViewById(R.id.poiDetailHighDiv);
        highDiv.addView(mHighWidget.getContentView(), new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mMapWidget = new StaticMapWidget(this);
        mMapWidget.setOnWidgetViewClickListener(this);
        RelativeLayout mapDiv = (RelativeLayout) findViewById(R.id.poiDetailMapDiv);
        mapDiv.addView(mMapWidget.getContentView(), new RelativeLayout.LayoutParams(SCREEN_WIDTH, SCREEN_WIDTH / 2));

        mIntroduceWidget = new PoiDetailIntroduceWidget(this);
        mIntroduceWidget.setOnWidgetViewClickListener(this);
        RelativeLayout introduceDiv = (RelativeLayout) findViewById(R.id.poiDetailIntroduceDiv);
        introduceDiv.addView(mIntroduceWidget.getContentView(), new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mCommentWidget = new PoiDetailCommentWidget(this);
        mCommentWidget.setOnWidgetViewClickListener(this);
        RelativeLayout commentDiv = (RelativeLayout) findViewById(R.id.poiDetailCommentDiv);
        commentDiv.addView(mCommentWidget.getContentView(), new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    @Override
    protected boolean invalidateContent(PoiDetail datas) {

        mPoiDetail = datas;

        mHeaderWidget.invalidate(mPoiDetail);
        mHighWidget.invalidate(mPoiDetail);

        mMapWidget.invalidate(R.drawable.ic_star_light_small);

        mMapWidget.setLocation(Double.parseDouble(mPoiDetail.getLat()),Double.parseDouble(mPoiDetail.getLon()));

        mIntroduceWidget.invalidate(mPoiDetail);

        if (mPoiDetail.getIs_book())
            ViewUtil.showView(mAcbBook);

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

            mCommentWidget.invalidate(data);

        }
        return true;
    }

    @Override
    protected ObjectRequest<PoiDetail> getObjectRequest() {

        ObjectRequest obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_PRODUCT_DETAIL, PoiDetail.class, OrderHtpUtil.getProductDetailUrl(mId));

        if (BuildConfig.DEBUG) {

            PoiDetail data = new PoiDetail();
            data.setComment_level("2.5");
            data.setComment_num("19");
            data.setDescription("07:00从酒店或集合地搭乘玻璃天窗全景豪华旅游巴士 开始米尔福德峡湾一日游。\n\n09:00沿着瓦卡蒂普湖穿过金斯顿到达蒂阿瑙, 这里拥有 令人窒息的风景，激动人心的河流，您将有时…");

            ArrayList<String> photos = new ArrayList<>();
            photos.add("http://pic.qyer.com/public/supplier/jd/2015/09/01/14410893435110/420x280");
            photos.add("http://pic.qyer.com/public/supplier/jd/2015/09/01/14410893435110/420x280");

            data.setLat("36.0655402");
            data.setLon("128.0650211");
            data.setPhotos(photos);
            ArrayList<String> list = new ArrayList<>();
            list.add("新西兰峡湾国家公园著名的峡湾，被誉为世界 第八大美景。");
            list.add("坐船欣赏倒映海中的山峰和从山崖泻下的瀑布，更有机会看到海豹。");
            list.add("乘顶棚是玻璃天窗的奢华长途客车，饱览94号公路上的湖光山色。");
            data.setHighlights(list);
            data.setIs_book("1");
            data.setPrice("700-800");
            data.setTitle("米尔福峡湾一日游(邮轮、自助、午餐、皮划艇)");

            obj.setData(data);
        }

        return obj;
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        super.onHttpFailed(tag, msg);
        showToast(msg);
    }

    private void getCommentList() {

        ObjectRequest obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_COMMENTS, CommentAll.class, OrderHtpUtil.getProductCommentListUrl(mId, 10, 1));

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

            mCommentWidget.invalidate(data);

        }

//        addRequestNoCache(obj);
//        obj.setResponseListener(new ObjectResponseListener() {
//            @Override
//            public void onSuccess(Object tag, Object o) {
//
//            }
//
//            @Override
//            public void onError(Object tag, String msg) {
//
//            }
//        });
    }


    @Override
    public void onWidgetViewClick(View v) {

        onClick(v);
    }

    @Override
    public void onClick(View view) {

        if (R.id.acbBook == view.getId()) {

            OrderBookActivity.startActivity(this, view,mPoiDetail.getProduct_id(), mPoiDetail.getPhotos().get(0), mPoiDetail.getTitle());
        } else if (R.id.btnAddToPlan == view.getId()) {

            showToast("加入旅行计划");
        } else if (R.id.poiDetailMapDiv == view.getId()) {

            startPoiMapWithSinglePoi();
        } else if (R.id.tvAllIntroduce == view.getId()) {

            showToast("查看全部简介");
        } else if (R.id.tvAllKnow == view.getId()) {

            showToast("查看全部购买须知");
        } else if (R.id.acbSeeAll == view.getId()) {

            startAllCommentActivity();
        } else if (R.id.rl_mapview == view.getId()) {

            startMapActivity();
        }
    }

    /**
     * 打开全部点评列表页
     */
    private void startAllCommentActivity() {

        showToast("open all comments activity");
    }
    /**
     * 打开地图页
     */
    private void startMapActivity() {

        SinglePoiMapActivity.startActivityByPoiDetail(this,mPoiDetail);
        showToast("open all comments activity");
    }

    private void startPoiMapWithSinglePoi() {

        if (mPoiDetail != null) {

            showToast("show map of poi");
        }
    }

    private void showPoiShareDialog() {

        if (mPoiDetail != null) {

            showToast("show share dialog");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_poi_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_share) {
            showPoiShareDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @param act
     * @param view The view which starts the transition
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startActivity(Activity act, View view, String photoUrl, String id, String topicId) {

        if (act == null || view == null)
            return;

        Intent intent = new Intent(act, PoiDetailActivity.class);
        intent.putExtra("photoUrl", photoUrl);
        intent.putExtra("id", id);
        intent.putExtra("topicId", topicId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(act, view, view.getTransitionName());
            act.startActivity(intent, options.toBundle());
        } else {

            act.startActivity(intent);
        }
    }

}
