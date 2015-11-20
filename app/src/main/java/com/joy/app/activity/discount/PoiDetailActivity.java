package com.joy.app.activity.discount;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joy.app.BuildConfig;
import com.joy.app.R;
import com.joy.app.activity.map.StaticMapWidget;
import com.joy.app.bean.sample.PoiDetail;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.library.activity.frame.BaseHttpUiActivity;
import com.joy.library.httptask.frame.ObjectRequest;
import com.joy.library.utils.TextUtil;
import com.joy.library.utils.ViewUtil;
import com.joy.library.view.ExBaseWidget;

import java.util.ArrayList;

/**
 * 目的地详折扣情页(下订单的唯一入口)
 * <p/>
 * Created by xiaoyu.chen on 15/11/11.
 */
public class PoiDetailActivity extends BaseHttpUiActivity<PoiDetail> implements View.OnClickListener, ExBaseWidget.OnWidgetViewClickListener {

    private String mId;
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
        setContentView(R.layout.act_discount_poi_detail);
        executeRefresh();


    }

    @Override
    protected void initData() {

        mId = TextUtil.filterNull(getIntent().getStringExtra("id"));
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
        headerDiv.addView(mHeaderWidget.getContentView());

        mHighWidget = new PoiDetailHighWidget(this);
        RelativeLayout highDiv = (RelativeLayout) findViewById(R.id.poiDetailHighDiv);
        highDiv.addView(mHighWidget.getContentView());

        mMapWidget = new StaticMapWidget(this);
        mMapWidget.setOnWidgetViewClickListener(this);
        RelativeLayout mapDiv = (RelativeLayout) findViewById(R.id.poiDetailMapDiv);
        mapDiv.addView(mMapWidget.getContentView());

        mIntroduceWidget = new PoiDetailIntroduceWidget(this);
        mIntroduceWidget.setOnWidgetViewClickListener(this);
        RelativeLayout introduceDiv = (RelativeLayout) findViewById(R.id.poiDetailIntroduceDiv);
        introduceDiv.addView(mIntroduceWidget.getContentView());

        mCommentWidget = new PoiDetailCommentWidget(this);
        mCommentWidget.setOnWidgetViewClickListener(this);
        RelativeLayout commentDiv = (RelativeLayout) findViewById(R.id.poiDetailCommentDiv);
        commentDiv.addView(mCommentWidget.getContentView());
    }

    @Override
    protected boolean invalidateContent(PoiDetail datas) {

        mPoiDetail = datas;

        mHeaderWidget.invalidate(mPoiDetail);
        mHighWidget.invalidate(mPoiDetail);

//        mMapWidget.invalidate(R.drawable.ic_star_light_small, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        location.setLatitude(Double.parseDouble(mPoiDetail.getLat()));
        location.setLongitude(Double.parseDouble(mPoiDetail.getLon()));
        mMapWidget.setLocation(location);

        mIntroduceWidget.invalidate(mPoiDetail);

        ArrayList<String> comments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            comments.add("我是第 " + i + " 个来点评的诶！～");
        }
        mCommentWidget.invalidate(comments);

        if (mPoiDetail.getIs_book())
            ViewUtil.showView(mAcbBook);

        return true;
    }

    @Override
    protected ObjectRequest<PoiDetail> getObjectRequest() {

        ObjectRequest obj = new ObjectRequest(OrderHtpUtil.getPoiDiscountUrl("51"), PoiDetail.class);

        if (BuildConfig.DEBUG) {

            PoiDetail data = new PoiDetail();
            data.setComment_level("2.5");
            data.setComment_num("19");
            data.setDescription("07:00从酒店或集合地搭乘玻璃天窗全景豪华旅游巴士 开始米尔福德峡湾一日游。\n\n09:00沿着瓦卡蒂普湖穿过金斯顿到达蒂阿瑙, 这里拥有 令人窒息的风景，激动人心的河流，您将有时…");

            ArrayList<String> list = new ArrayList<>();
            ArrayList<String> photos = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                photos.add("http://pic.qyer.com/public/supplier/jd/2015/09/01/14410893435110/420x280");
                list.add("亮点A－" + i);
            }

            data.setLat("36.0655402");
            data.setLon("128.0650211");
            data.setPhotos(photos);
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


    }


    @Override
    public void onWidgetViewClick(View v) {

        onClick(v);
    }

    @Override
    public void onClick(View view) {

        if (R.id.acbBook == view.getId()) {

            showToast("立即预订");
        } else if (R.id.btnAddToPlan == view.getId()) {

            showToast("加入旅行计划");
        } else if (R.id.poiDetailMapDiv == view.getId()) {

            startPoiMapWithSinglePoi();
        } else if (R.id.tvAllIntroduce == view.getId()) {

            showToast("查看全部简介");
        } else if (R.id.tvAllKnow == view.getId()) {

            showToast("查看全部购买须知");
        } else if (R.id.rvCommentCountDiv == view.getId()) {

            showToast("查看全部点评");
        }
    }

    /**
     * 打开全部点评列表页
     */
    private void startAllCommentActivity() {

    }

    private void startPoiMapWithSinglePoi() {

        if (mPoiDetail != null) {

        }
    }

    private void showPoiShareDialog() {

        if (mPoiDetail != null) {


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
    public static void startActivity(Activity act, View view, String photoUrl, String id) {

        if (act == null || view == null)
            return;

        Intent intent = new Intent(act, PoiDetailActivity.class);
        intent.putExtra("photoUrl", photoUrl);
        intent.putExtra("id", id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(act, view, view.getTransitionName());
            act.startActivity(intent, options.toBundle());
        } else {

            act.startActivity(intent);
        }
    }

}
