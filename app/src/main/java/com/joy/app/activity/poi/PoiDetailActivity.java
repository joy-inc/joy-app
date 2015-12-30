package com.joy.app.activity.poi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.TextUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.view.ExBaseWidget;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.app.activity.common.WebViewActivity;
import com.joy.app.activity.map.SinglePoiMapActivity;
import com.joy.app.activity.plan.AddPoiToFloderActivity;
import com.joy.app.activity.user.UserLoginActivity;
import com.joy.app.bean.poi.CommentAll;
import com.joy.app.bean.sample.PoiDetail;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;

/**
 * 目的地折扣详情页(下订单的唯一入口)
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
    private RelativeLayout mMapDiv;
    private SimpleDraweeView mSdvMap;
    private TextView mTvAddress;
    private PoiDetailIntroduceWidget mIntroduceWidget;
    private PoiDetailCommentWidget mCommentWidget;

    private final int REQ_ADD_POI = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_poi_detail);
        executeRefreshOnly();
        getCommentList();
    }

    @Override
    protected void onResume() {

        super.onResume();
        mHeaderWidget.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
        mHeaderWidget.onPause();
    }

    @Override
    protected void initData() {

        mId = TextUtil.filterNull(getIntent().getStringExtra("id"));
        mPhotoUrl = TextUtil.filterNull(getIntent().getStringExtra("photoUrl"));
    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView(R.drawable.ic_back_gray);
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

        mMapDiv = (RelativeLayout) findViewById(R.id.poiDetailMapDiv);
        mSdvMap = (SimpleDraweeView) findViewById(R.id.sdvMap);
        mTvAddress = (TextView) findViewById(R.id.tvAddress);

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
    protected boolean invalidateContent(PoiDetail data) {

        mPoiDetail = data;

        mHeaderWidget.invalidate(mPoiDetail);
        mHeaderWidget.onResume(); // 打开图片自动滚动
        mHighWidget.invalidate(mPoiDetail);

        if (TextUtil.isNotEmpty(data.getLat()) && TextUtil.isNotEmpty(data.getLon()) && !"0".equals(data.getLat()) && !"0".equals(data.getLon()) && TextUtil.isNotEmpty(data.getMap()))
            invalidateMap(data);

        mIntroduceWidget.invalidate(mPoiDetail);

        if (mPoiDetail.getIs_book())
            ViewUtil.showView(mAcbBook);

        return true;
    }

    private void invalidateMap(PoiDetail data) {

        mSdvMap.setImageURI(Uri.parse(data.getMap()));
        if (TextUtil.isNotEmpty(data.getAddress())) {
            mTvAddress.setText(data.getAddress());
            ViewUtil.showView(mTvAddress);
        }

        ViewUtil.showView(mMapDiv);
    }

    @Override
    protected ObjectRequest<PoiDetail> getObjectRequest() {

        ObjectRequest obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_PRODUCT_DETAIL, PoiDetail.class, OrderHtpUtil.getProductDetailUrl(mId));

        return obj;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (REQ_ADD_POI == requestCode && resultCode == RESULT_OK) {
            executeRefreshOnly();
        }
    }

    private void getCommentList() {

        ObjectRequest<CommentAll> obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_COMMENTS, CommentAll.class, OrderHtpUtil.getProductCommentListUrl(mId, 5, 1));

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

            if (JoyApplication.isLogin()) {

                if (CollectionUtil.isNotEmpty(mPoiDetail.getPhotos()) && TextUtil.isEmpty(mPhotoUrl))
                    mPhotoUrl = mPoiDetail.getPhotos().get(0);

                OrderBookActivity.startActivity(this, view, mPoiDetail.getProduct_id(), mPhotoUrl, mPoiDetail.getTitle());
            } else {

                UserLoginActivity.startActivity(this);
            }
        } else if (R.id.llAddPlanDiv == view.getId()) {

            if (TextUtil.isEmpty(mPoiDetail.getFolder_id())) {

                AddPoiToFloderActivity.startActivity(this, mId, mPoiDetail.getFolder_id(), REQ_ADD_POI);
            } else {//已经收藏了

            }

        } else if (R.id.poiDetailMapDiv == view.getId()) {

            startMapActivity();
        } else if (R.id.tvAllKnow == view.getId()) {

            WebViewActivity.startActivityNoShare(PoiDetailActivity.this, mPoiDetail.getPurchase_info(), getString(R.string.need_know));
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
        }
    }

    public static void startActivity(Context act, String id) {

        if (act == null || TextUtil.isEmpty(id))
            return;

        Intent intent = new Intent(act, PoiDetailActivity.class);
        intent.putExtra("id", id);
        act.startActivity(intent);
    }
}