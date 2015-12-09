package com.joy.app.activity.city;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.TextUtil;
import com.android.library.view.recyclerview.RecyclerAdapter;
import com.android.library.widget.JTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.activity.common.WebViewActivity;
import com.joy.app.activity.hotel.CityHotelListActivity;
import com.joy.app.adapter.city.CityAdapter;
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

    public static void startActivity(Activity act, String placeId) {

        if (TextUtil.isEmpty(placeId))
            return;

        Intent it = new Intent(act, CityActivity.class);
        it.putExtra("placeId", placeId);
        act.startActivity(it);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        executeRefreshOnly();// recommend route
        executeCityDetail();// city detail
    }

    @Override
    protected void initData() {

        mPlaceId = getIntent().getStringExtra("placeId");
    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView();
        // todo add mapview
    }

    @Override
    protected void initContentView() {

        setSwipeRefreshEnable(false);
        getRecyclerView().setBackgroundResource(R.color.color_primary);
        setAdapter(new CityAdapter());
        setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {

                showToast("~~" + position);
            }
        });
    }

    private void executeCityDetail() {

        ObjectRequest<City> cityReq = ReqFactory.newPost(CityHtpUtil.URL_POST_CITY, City.class, CityHtpUtil.getCityParams(mPlaceId));
        cityReq.setResponseListener(new ObjectResponse<City>() {

            @Override
            public void onSuccess(Object tag, City city) {

                onSuccessCallback(city);
            }
        });
        addRequestNoCache(cityReq);
    }

    private void onSuccessCallback(City city) {// generate header view

        mCity = city;

        View headerView = inflateLayout(R.layout.view_city_header);
        SimpleDraweeView sdvHeader = (SimpleDraweeView) headerView.findViewById(R.id.sdvPhoto);
        if (TextUtil.isNotEmpty(city.getPic_url()))
            sdvHeader.setImageURI(Uri.parse(city.getPic_url()));
        JTextView jtvName = (JTextView) headerView.findViewById(R.id.jtvName);
        jtvName.setText(mCity.getCn_name() + "\n" + mCity.getEn_name());
        headerView.findViewById(R.id.jimTicket).setOnClickListener(this);
        headerView.findViewById(R.id.jimVisa).setOnClickListener(this);
        headerView.findViewById(R.id.jimAirpalne).setOnClickListener(this);
        headerView.findViewById(R.id.jimWifi).setOnClickListener(this);
        headerView.findViewById(R.id.jimPlay).setOnClickListener(this);
        headerView.findViewById(R.id.jimFood).setOnClickListener(this);
        headerView.findViewById(R.id.jimShop).setOnClickListener(this);
        headerView.findViewById(R.id.jimHotel).setOnClickListener(this);

        addHeaderView(headerView);
        showContentView();// todo
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.jimTicket:

                if (mCity != null)
                    WebViewActivity.startActivity(this, mCity.getTicket_url(), "");
                break;
            case R.id.jimVisa:

                if (mCity != null)
                    WebViewActivity.startActivity(this, mCity.getVisa_url(), "");
                break;
            case R.id.jimAirpalne:

                if (mCity != null)
                    WebViewActivity.startActivity(this, mCity.getTraffic_url(), "");
                break;
            case R.id.jimWifi:

                if (mCity != null)
                    WebViewActivity.startActivity(this, mCity.getWifi_url(), "");
                break;

            case R.id.jimPlay:

                CityFunActivity.startActivity(this, mPlaceId, 1);
                break;
            case R.id.jimHotel:

                if (mCity != null)
                    CityHotelListActivity.startActivity(this, mCity.getCity_id(), mCity.getCn_name(), "fromkey");
                break;
            case R.id.jimFood:

                CityFunActivity.startActivity(this, mPlaceId, 3);
                break;
            case R.id.jimShop:

                CityFunActivity.startActivity(this, mPlaceId, 4);
                break;
        }
    }

    @Override
    protected ObjectRequest<List<CityRoute>> getObjectRequest(int pageIndex, int pageLimit) {

        return ReqFactory.newPost(CityHtpUtil.URL_POST_CITY_ROUTE, CityRoute.class, CityHtpUtil.getCityRouteParams(mPlaceId, pageLimit, pageIndex));
    }

    @Override
    protected void onHttpFailed(Object tag, String msg) {

        showToast("CityActivity error: " + msg);
    }

    @Override
    protected void showNoContentTip() {
    }
}