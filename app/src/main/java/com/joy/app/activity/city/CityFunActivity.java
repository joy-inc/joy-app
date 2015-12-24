package com.joy.app.activity.city;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TextUtil;
import com.android.library.view.recyclerview.RecyclerAdapter;
import com.android.library.widget.JTextView;
import com.joy.app.R;
import com.joy.app.activity.common.WebViewActivity;
import com.joy.app.adapter.city.CityFunAdapter;
import com.joy.app.bean.city.CityFun;
import com.joy.app.utils.http.CityHtpUtil;
import com.joy.app.utils.http.ReqFactory;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/12/5.
 */
public class CityFunActivity extends BaseHttpRvActivity<CityFun> {

    private String mPlaceId;
    private FunType mType;

    public static void startActivity(Context act, String placeId, int type) {

        if (TextUtil.isEmpty(placeId))
            return;

        Intent it = new Intent(act, CityFunActivity.class);
        it.putExtra("placeId", placeId);
        it.putExtra("type", type);
        act.startActivity(it);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        executeRefreshOnly();
    }

    @Override
    protected void initData() {

        mPlaceId = getIntent().getStringExtra("placeId");
        mType = FunType.getFunType(getIntent().getIntExtra("type", 0));
    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView();
        addTitleMiddleView(mType.getTitleResId());
    }

    @Override
    protected void initContentView() {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//            getToolbar().setElevation(0);
        setBackgroundResource(R.color.color_primary);
        setAdapter(new CityFunAdapter());
        setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {

                CityFun.ListEntity entity = (CityFun.ListEntity) getAdapter().getItem(position);

                WebViewActivity.startActivityNoTitle(CityFunActivity.this, entity.getTopic_url(), mType.getShareType());
            }
        });
    }

    @Override
    protected boolean invalidateContent(CityFun cityFun) {

        if (TextUtil.isNotEmpty(cityFun.getDesc()) && getHeaderViewsCount() == 0) {

            JTextView jtvDesc = (JTextView) inflateLayout(R.layout.view_cityfun_header);
            jtvDesc.setText(cityFun.getDesc());
            addHeaderView(jtvDesc);
        }

        return super.invalidateContent(cityFun);
    }

    @Override
    protected List<CityFun.ListEntity> getListInvalidateContent(CityFun cityFun) {

        return cityFun.getList();
    }

    @Override
    protected ObjectRequest<CityFun> getObjectRequest(int pageIndex, int pageLimit) {

        return ReqFactory.newPost(CityHtpUtil.URL_POST_CITY_FUN, CityFun.class, CityHtpUtil.getCityFunParams(mPlaceId, mType.getNetType(), pageLimit, pageIndex));
    }

    public enum FunType {

        PLAY(R.string.city_fun_play, WebViewActivity.TYPE_CITY_PLAY, 1),
        HOTEL(R.string.city_fun_hotel, WebViewActivity.TYPE_CITY_HOTEL, 2),
        FOOD(R.string.city_fun_food, WebViewActivity.TYPE_CITY_FOOD, 3),
        SHOP(R.string.city_fun_shop, WebViewActivity.TYPE_CITY_SHOP, 4);

        int mTitleResId = 0;
        int mShareType = 0;
        int mNetType = 0;

        FunType(int titleResId, int shareType, int netType) {
            mTitleResId = titleResId;
            mShareType = shareType;
            mNetType = netType;
        }

        public int getTitleResId() {
            return mTitleResId;
        }

        public int getShareType() {
            return mShareType;
        }

        public int getNetType() {
            return mNetType;
        }

        public static FunType getFunType(int netType) {

            if (netType == FunType.PLAY.getNetType()) {
                return FunType.PLAY;
            } else if (netType == FunType.HOTEL.getNetType()) {
                return FunType.HOTEL;
            } else if (netType == FunType.FOOD.getNetType()) {
                return FunType.FOOD;
            } else {
                return FunType.SHOP;
            }
        }
    }
}