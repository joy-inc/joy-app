package com.joy.app.activity.poi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.MathUtil;
import com.android.library.utils.TextUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.view.ExBaseWidget;
import com.joy.app.R;
import com.joy.app.activity.map.SinglePoiMapActivity;
import com.joy.app.activity.map.StaticMapWidget;
import com.joy.app.bean.poi.CommentAll;
import com.joy.app.bean.sample.PoiDetail;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;

/**
 * 目的地详折扣情页(下订单的唯一入口)
 * <p/>
 * Created by xiaoyu.chen on 15/11/11.
 */
public class PoiDetailActivity extends BaseHttpUiActivity<PoiDetail> implements View.OnClickListener, ExBaseWidget.OnWidgetViewClickListener {

    private String mId;
    private String mPhotoUrl;
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
        mPhotoUrl = TextUtil.filterNull(getIntent().getStringExtra("photoUrl"));
    }

    @Override
    protected void initTitleView() {

        setTitle(null);
        addTitleLeftBackView();
        addTitleRightView(R.drawable.abc_ic_menu_share_mtrl_alpha, new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showPoiShareDialog();
            }
        });
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

        mMapWidget.setLocation(MathUtil.parseDouble(mPoiDetail.getLat(), 0), MathUtil.parseDouble(mPoiDetail.getLon(), 0), "我是地址");

        mIntroduceWidget.invalidate(mPoiDetail);

        if (mPoiDetail.getIs_book())
            ViewUtil.showView(mAcbBook);

        return true;
    }

    @Override
    protected ObjectRequest<PoiDetail> getObjectRequest() {

        ObjectRequest obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_PRODUCT_DETAIL, PoiDetail.class, OrderHtpUtil.getProductDetailUrl(mId));

        return obj;
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        super.onHttpFailed(tag, msg);
        showToast(msg);
    }

    private void getCommentList() {

        ObjectRequest<CommentAll> obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_COMMENTS, CommentAll.class, OrderHtpUtil.getProductCommentListUrl(mId, 3, 1));

        obj.setResponseListener(new ObjectResponse<CommentAll>() {

            @Override
            public void onSuccess(Object tag, CommentAll data) {

                mCommentWidget.invalidate(data);
            }
        });
        addRequestNoCache(obj);
    }


    @Override
    public void onWidgetViewClick(View v) {

        onClick(v);
    }

    @Override
    public void onClick(View view) {

        if (R.id.acbBook == view.getId()) {

            if (CollectionUtil.isNotEmpty(mPoiDetail.getPhotos()) && TextUtil.isEmpty(mPhotoUrl))
                mPhotoUrl = mPoiDetail.getPhotos().get(0);

            OrderBookActivity.startActivity(this, view, mPoiDetail.getProduct_id(), mPhotoUrl, mPoiDetail.getTitle());
        } else if (R.id.btnAddToPlan == view.getId()) {

            showToast("加入旅行计划");
        } else if (R.id.poiDetailMapDiv == view.getId()) {

            startMapActivity();
        } else if (R.id.tvAllIntroduce == view.getId()) {

            showToast("查看全部简介");
        } else if (R.id.tvAllKnow == view.getId()) {

            showToast("查看全部购买须知");
        } else if (R.id.acbSeeAll == view.getId()) {

            startAllCommentActivity();
        }
    }

    /**
     * 打开全部点评列表页
     */
    private void startAllCommentActivity() {

        CommentActivity.startActivity(this, mId);
    }

    /**
     * 打开地图页
     */
    private void startMapActivity() {

        if (mPoiDetail != null) {

            SinglePoiMapActivity.startActivityByPoiDetail(this, mPoiDetail);
            showToast("open all comments activity");
        }
    }

    private void showPoiShareDialog() {

        if (mPoiDetail != null) {

            showToast("show share dialog");
        }
    }

    public static void startActivity(Activity act, String id) {

        if (act == null || TextUtil.isEmpty(id))
            return;

        Intent intent = new Intent(act, PoiDetailActivity.class);
        intent.putExtra("id", id);
        act.startActivity(intent);
    }
}