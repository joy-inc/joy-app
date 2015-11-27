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
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.facebook.common.references.CloseableReference;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.joy.app.R;
import com.joy.app.adapter.sample.CityDetailRvAdapter3;
import com.joy.app.bean.sample.CityDetail;
import com.joy.app.utils.http.sample.TestHtpUtil;
import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.CollectionUtil;
import com.android.library.view.observablescrollview.ObservableRecyclerView;
import com.android.library.view.observablescrollview.ObservableScrollViewCallbacks;
import com.android.library.view.observablescrollview.ScrollState;
import com.android.library.view.observablescrollview.ScrollUtils;
import com.android.library.view.systembar.SystemBarTintManager;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by KEVIN.DAI on 15/7/11.
 */
public class DetailTestActivity3 extends BaseHttpRvActivity<CityDetail> implements ObservableScrollViewCallbacks {

    private int mFlexibleHeight;
    private SystemBarTintManager mTintManager;
    private SimpleDraweeView mCoverSdvPhoto;
    private TextView mCoverTvTitle;

    public static void startActivity(Activity act, String... params) {

        if (act == null || params == null || params.length < 4)
            return;

        Intent intent = new Intent(act, DetailTestActivity3.class);
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

        Intent intent = new Intent(act, DetailTestActivity3.class);
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
        executeRefresh();
    }

    @Override
    protected void wrapContentView(FrameLayout rootView, View contentView) {

        LayoutParams photoLp = new LayoutParams(LayoutParams.MATCH_PARENT, 260 * DP_1_PX);
        rootView.addView(inflateLayout(R.layout.t_header_cover_photo3), photoLp);
        super.wrapContentView(rootView, contentView);
        LayoutParams titleLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rootView.addView(inflateLayout(R.layout.t_header_cover_title3), titleLp);
    }

    @Override
    protected void initData() {

        mFlexibleHeight = 260 * DP_1_PX;
    }

    @Override
    protected void initTitleView() {

        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintColor(R.color.black_trans54);

        setTitle(null);
        addTitleLeftBackView();
        getToolbarLp().topMargin = STATUS_BAR_HEIGHT;
        setTitleBgColorResId(R.color.black_trans54);
    }

    @Override
    protected void initContentView() {

        setSwipeRefreshEnable(false);// 设置下拉刷新不可用

        Intent it = getIntent();
        mCoverSdvPhoto = (SimpleDraweeView) findViewById(R.id.sdvPhoto);
        mCoverTvTitle = (TextView) findViewById(R.id.tvName);
        mCoverTvTitle.setText(it.getStringExtra("cnname") + "\n" + it.getStringExtra("enname"));
        hideView(mCoverTvTitle);
        Postprocessor processor = new BasePostprocessor() {

            @Override
            public CloseableReference<Bitmap> process(final Bitmap bitmap, PlatformBitmapFactory bitmapFactory) {

                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

                    @Override
                    public void onGenerated(Palette palette) {

                        if (palette != null) {

                            getRootView().setBackgroundColor(palette.getMutedColor(0X00000000));
                            mCoverSdvPhoto.setImageBitmap(bitmap);
                        }
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
                .newBuilderWithSource(Uri.parse(it.getStringExtra("photoUrl")))
                .setPostprocessor(processor)
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco
                .newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(mCoverSdvPhoto.getController())
                .build();
        mCoverSdvPhoto.setController(controller);

        setAdapter(new CityDetailRvAdapter3(this));
    }

    @Override
    protected RecyclerView getDefaultRecyclerView() {

        ObservableRecyclerView orv = (ObservableRecyclerView) inflateLayout(R.layout.lib_view_recycler_observable);
        orv.setScrollViewCallbacks(this);
        return orv;
    }

    @Override
    protected ObjectRequest<CityDetail> getObjectRequest() {

        return ObjectRequest.get(TestHtpUtil.getCityInfoUrl(getIntent().getStringExtra("cityId")), CityDetail.class);
    }

    @Override
    protected boolean invalidateContent(CityDetail datas) {

        if (datas == null || CollectionUtil.isEmpty(datas.getNew_trip()))
            return false;

        CityDetailRvAdapter3 adapter = (CityDetailRvAdapter3) getAdapter();
        adapter.setData(datas);
        adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        showToast(msg);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

        scrollY += STATUS_BAR_HEIGHT;

//        LogMgr.i("daisw", "~~" + scrollY);

        float minOverlayTransitionY = -mFlexibleHeight;
        float transY = ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0f);
        ViewHelper.setTranslationY(mCoverSdvPhoto, transY);

        // Translate title text
        if (mCoverTvTitle.getWidth() > 0 || mCoverTvTitle.getHeight() > 0) {

            float titleTranslationY = mFlexibleHeight / 2 - scrollY / 2;
            titleTranslationY = Math.max(STATUS_BAR_HEIGHT + (TITLE_BAR_HEIGHT - mCoverTvTitle.getHeight()) / 2, titleTranslationY);
            ViewHelper.setTranslationY(mCoverTvTitle, titleTranslationY);
            float titleTranslationX = SCREEN_WIDTH / 2 - mCoverTvTitle.getWidth() / 2 - scrollY / 2;
            titleTranslationX = Math.max(getToolbar().getContentInsetLeft() * 2 + getToolbar().getNavigationIcon().getMinimumWidth(), titleTranslationX);
            ViewHelper.setTranslationX(mCoverTvTitle, titleTranslationX);

            showView(mCoverTvTitle);
        }

        float flexibleRange = mFlexibleHeight;
        float fraction = ScrollUtils.getFloat(scrollY / flexibleRange, 0f, 1f);
        mTintManager.setStatusBarAlpha(fraction);
        getToolbar().getBackground().setAlpha((int) (fraction * 255));
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}