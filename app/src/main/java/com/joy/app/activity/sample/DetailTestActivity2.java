package com.joy.app.activity.sample;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.library.httptask.ObjectRequest;
import com.android.library.ui.activity.BaseHttpRvActivity;
import com.android.library.utils.CollectionUtil;
import com.android.library.view.observablescrollview.ObservableRecyclerView;
import com.android.library.view.observablescrollview.ObservableScrollViewCallbacks;
import com.android.library.view.observablescrollview.ScrollState;
import com.android.library.view.observablescrollview.ScrollUtils;
import com.android.library.view.systembar.SystemBarTintManager;
import com.android.library.widget.FrescoImageView;
import com.facebook.common.references.CloseableReference;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.joy.app.R;
import com.joy.app.adapter.sample.CityDetailRvAdapter;
import com.joy.app.bean.sample.CityDetail;
import com.joy.app.utils.http.sample.TestHtpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by KEVIN.DAI on 15/7/11.
 * Modified by KEVIN.DAI on 16/7/6.
 */
public class DetailTestActivity2 extends BaseHttpRvActivity<CityDetail> implements ObservableScrollViewCallbacks {

    private int mFlexibleHeight;
    private SystemBarTintManager mTintManager;

    public static void startActivity(Activity act, String... params) {

        if (act == null || params == null || params.length < 4)
            return;

        Intent intent = new Intent(act, DetailTestActivity2.class);
        intent.putExtra("cityId", params[0]);
        intent.putExtra("photoUrl", params[1]);
        intent.putExtra("cnname", params[2]);
        intent.putExtra("enname", params[3]);
        act.startActivity(intent);
    }

    /**
     * @param act
     * @param view The view which starts the transition
     */
    public static void startActivity(Activity act, View view, String... params) {

        if (act == null || view == null || params == null || params.length < 4)
            return;

        Intent intent = new Intent(act, DetailTestActivity2.class);
        intent.putExtra("cityId", params[0]);
        intent.putExtra("photoUrl", params[1]);
        intent.putExtra("cnname", params[2]);
        intent.putExtra("enname", params[3]);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(act, view, view.getTransitionName());
            act.startActivity(intent, options.toBundle());
        } else {

            act.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        executeRefreshOnly();
    }

    @Override
    protected void initData() {

        mFlexibleHeight = 260 * DP_1_PX;
    }

    @Override
    protected void initTitleView() {

        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintResource(R.color.black_trans54);
        mTintManager.setStatusBarAlpha(0);

        addTitleLeftBackView();
        setTitleBgColorResId(R.color.black_trans54);
        setTitleBarAlpha(0);
    }

    @Override
    protected void initContentView() {

        setSwipeRefreshEnable(false);// 设置下拉刷新不可用
        setAdapter(new CityDetailRvAdapter());
        addHeaderView(initHeaderCoverView());
        addFooterView(initFooterView());
    }

    @Override
    protected RecyclerView getDefaultRecyclerView() {

        ObservableRecyclerView orv = (ObservableRecyclerView) inflateLayout(R.layout.lib_view_recycler_observable);
        orv.setScrollViewCallbacks(this);
        return orv;
    }

    @Override
    protected ObjectRequest<CityDetail> getObjectRequest(int pageIndex, int pageLimit) {

        return ObjectRequest.get(TestHtpUtil.getCityInfoUrl(getIntent().getStringExtra("cityId")), CityDetail.class);
    }

    @Override
    protected boolean invalidateContent(CityDetail cityDetail) {

        addHeaderView(initHeaderSubView(cityDetail));
        return super.invalidateContent(cityDetail);
    }

    @Override
    protected List<?> getListInvalidateContent(CityDetail cityDetail) {

        return cityDetail.getNew_trip();
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

        float flexibleRange = mFlexibleHeight - STATUS_BAR_HEIGHT;
        float fraction = ScrollUtils.getFloat(scrollY / flexibleRange, 0f, 1f);
        mTintManager.setStatusBarAlpha(fraction);
        setTitleBarAlpha((int) (fraction * 255));
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    private View initHeaderCoverView() {

        View vCover = inflateLayout(R.layout.t_header_cover);

        String cnname = getIntent().getStringExtra("cnname");
        String enname = getIntent().getStringExtra("enname");
        ButterKnife.<TextView>findById(vCover, R.id.tvName).setText(cnname + "\n" + enname);

        FrescoImageView sdvPhoto = ButterKnife.<FrescoImageView>findById(vCover, R.id.sdvPhoto);
        Postprocessor processor = new BasePostprocessor() {

            @Override
            public CloseableReference<Bitmap> process(Bitmap bitmap, PlatformBitmapFactory bitmapFactory) {

                Palette.from(bitmap).generate(palette -> {

                    if (palette != null) {

                        getRootView().setBackgroundColor(palette.getMutedColor(0X00000000));
                        sdvPhoto.setImageBitmap(bitmap);
                    }
                });
                return null;
            }

            @Override
            public void process(Bitmap destBitmap, Bitmap sourceBitmap) {

//                super.process(destBitmap, sourceBitmap);
            }

            @Override
            public void process(Bitmap bitmap) {

//                super.process(bitmap);
            }
        };
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(getIntent().getStringExtra("photoUrl")))
                .setPostprocessor(processor)
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco
                .newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(sdvPhoto.getController())
                .build();
        sdvPhoto.setController(controller);
        return vCover;
    }

    private View initHeaderSubView(CityDetail cityDetail) {

        View vHeader = inflateLayout(R.layout.t_header_view_detail);
        ArrayList<String> photos = cityDetail.getPhotos();
        if (CollectionUtil.isNotEmpty(photos)) {

            if (photos.size() > 1)
                ButterKnife.<FrescoImageView>findById(vHeader, R.id.sdvSubPhoto1).setImageURI(photos.get(1));
            if (photos.size() > 2)
                ButterKnife.<FrescoImageView>findById(vHeader, R.id.sdvSubPhoto2).setImageURI(photos.get(2));
            if (photos.size() > 3)
                ButterKnife.<FrescoImageView>findById(vHeader, R.id.sdvSubPhoto3).setImageURI(photos.get(3));
            if (photos.size() > 4)
                ButterKnife.<FrescoImageView>findById(vHeader, R.id.sdvSubPhoto4).setImageURI(photos.get(4));
        }
        return vHeader;
    }

    private View initFooterView() {

        View view = new View(this);
        view.setMinimumHeight(DP_1_PX * 16);
        return view;
    }
}
