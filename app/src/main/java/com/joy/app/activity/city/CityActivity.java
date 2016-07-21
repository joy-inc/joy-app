package com.joy.app.activity.city;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.library.ui.activity.BaseHttpRvActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.TextUtil;
import com.android.library.view.recyclerview.RecyclerAdapter;
import com.android.library.widget.FrescoImageView;
import com.android.library.widget.JTextView;
import com.joy.app.R;
import com.joy.app.activity.common.WebViewActivity;
import com.joy.app.adapter.city.CityRouteAdapter;
import com.joy.app.bean.city.City;
import com.joy.app.bean.city.CityRoute;
import com.joy.app.utils.http.CityHtpUtil;
import com.joy.app.utils.http.ReqFactory;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/12/5.
 */
public class CityActivity extends BaseHttpRvActivity<List<CityRoute>> implements View.OnClickListener {

    private String mPlaceId;
    private City mCity;
    private boolean isCityDetailReqFailed;

    public static void startActivity(Context act, String placeId) {

        if (TextUtil.isEmpty(placeId))
            return;

        Intent it = new Intent(act, CityActivity.class);
        it.putExtra("placeId", placeId);
        act.startActivity(it);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        executeCityDetail();// city detail
    }

    @Override
    protected void initData() {

        mPlaceId = getIntent().getStringExtra("placeId");
    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView();
    }

    @Override
    protected void initContentView() {

        setBackgroundResource(R.color.color_primary);
        setSwipeRefreshEnable(false);
        setAdapter(new CityRouteAdapter());
        setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {

                CityRoute cityRoute = (CityRoute) getAdapter().getItem(position);
                WebViewActivity.startActivityNoTitle(CityActivity.this, cityRoute.getRoute_url(), WebViewActivity.TYPE_CITY_RECMMMEND);
            }
        });
    }

    private void executeCityDetail() {

        ObjectRequest<City> cityReq = ReqFactory.newPost(CityHtpUtil.URL_POST_CITY, City.class, CityHtpUtil.getCityParams(mPlaceId));
        hideTipView();
        showLoading();
        cityReq.setResponseListener(new ObjectResponse<City>() {

//            @Override
//            public void onPre() {
//
//                hideTipView();
//                showLoading();
//            }

            @Override
            public void onSuccess(Object tag, City city) {

                onSuccessCallback(city);
                executeRefreshOnly();// recommend route
            }

            @Override
            public void onError(Object tag, String msg) {

                isCityDetailReqFailed = true;

                hideLoading();
                hideContent();
                showErrorTip();
            }
        });
        addRequestNoCache(cityReq);
    }

    @Override
    protected void onRetry() {

        if (isCityDetailReqFailed) {

            isCityDetailReqFailed = false;
            executeCityDetail();
        } else {

            super.onRetry();
        }
    }

    private void onSuccessCallback(City city) {// generate header view

        mCity = city;

        View headerView = inflateLayout(R.layout.view_city_header);
        FrescoImageView fivHeader = (FrescoImageView) headerView.findViewById(R.id.sdvPhoto);
        fivHeader.setImageURI(city.getPic_url());
        JTextView jtvCnName = (JTextView) headerView.findViewById(R.id.jtvCnName);
        JTextView jtvEnName = (JTextView) headerView.findViewById(R.id.jtvEnName);
        jtvCnName.setText(mCity.getCn_name());
        jtvEnName.setText(mCity.getEn_name());
        headerView.findViewById(R.id.jimTicket).setOnClickListener(this);
        headerView.findViewById(R.id.jimVisa).setOnClickListener(this);
        headerView.findViewById(R.id.jimAirpalne).setOnClickListener(this);
        headerView.findViewById(R.id.jimWifi).setOnClickListener(this);
        headerView.findViewById(R.id.jimPlay).setOnClickListener(this);
        headerView.findViewById(R.id.jimFood).setOnClickListener(this);
        headerView.findViewById(R.id.jimShop).setOnClickListener(this);
        headerView.findViewById(R.id.jimHotel).setOnClickListener(this);

        addHeaderView(headerView);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.jimTicket:

                if (mCity != null)
                    WebViewActivity.startActivityNoTitleShare(this, mCity.getTicket_url());
                break;
            case R.id.jimVisa:

                if (mCity != null)
                    WebViewActivity.startActivityNoTitleShare(this, mCity.getVisa_url());
                break;
            case R.id.jimAirpalne:

                if (mCity != null)
                    WebViewActivity.startActivityNoTitleShare(this, mCity.getTraffic_url());
                break;
            case R.id.jimWifi:

                if (mCity != null)
                    WebViewActivity.startActivityNoTitleShare(this, mCity.getWifi_url());
                break;

            case R.id.jimPlay:

                CityFunActivity.startActivity(this, mPlaceId, CityFunActivity.FunType.PLAY.getNetType());
                break;
            case R.id.jimHotel:

                if (mCity != null)
                    CityFunActivity.startActivity(this, mPlaceId, CityFunActivity.FunType.HOTEL.getNetType());
                break;
            case R.id.jimFood:

                CityFunActivity.startActivity(this, mPlaceId, CityFunActivity.FunType.FOOD.getNetType());
                break;
            case R.id.jimShop:

                CityFunActivity.startActivity(this, mPlaceId, CityFunActivity.FunType.SHOP.getNetType());
                break;
        }
    }

    @Override
    protected ObjectRequest<List<CityRoute>> getObjectRequest(int pageIndex, int pageLimit) {

        return ReqFactory.newPost(CityHtpUtil.URL_POST_CITY_ROUTE, CityRoute.class, CityHtpUtil.getCityRouteParams(mPlaceId, pageLimit, pageIndex));
    }

    @Override
    protected boolean invalidateContent(List<CityRoute> cityRoutes) {

        boolean ret = super.invalidateContent(cityRoutes);
        if (ret)
            showView(findViewById(R.id.llRouteTitle));
        return ret;
    }
}