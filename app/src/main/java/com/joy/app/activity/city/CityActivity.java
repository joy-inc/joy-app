package com.joy.app.activity.city;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TextUtil;
import com.android.library.view.recyclerview.RecyclerAdapter;
import com.android.library.widget.JTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.BuildConfig;
import com.joy.app.R;
import com.joy.app.activity.hotel.CityHotelListActivity;
import com.joy.app.adapter.city.CityAdapter;
import com.joy.app.bean.city.City;
import com.joy.app.utils.http.CityHtpUtil;
import com.joy.app.utils.http.ReqFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KEVIN.DAI on 15/12/5.
 */
public class CityActivity extends BaseHttpRvActivity<List<City>> implements View.OnClickListener {

    private String mPlaceId;

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
        executeRefreshOnly();
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

        setSwipeRefreshEnable(false);
        getRecyclerView().setBackgroundResource(R.color.color_primary);
        setAdapter(new CityAdapter());
        addHeaderView(generateHeaderView());
        setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {

                showToast("~~" + position);
            }
        });
    }

    private View generateHeaderView() {

        View headerView = inflateLayout(R.layout.view_city_header);
        SimpleDraweeView sdvHeader = (SimpleDraweeView) headerView.findViewById(R.id.sdvPhoto);
        sdvHeader.setImageURI(Uri.parse("http://pic.qyer.com/album/user/495/23/RUBQQBkGYw/index/300x200"));
        JTextView jtvName = (JTextView) headerView.findViewById(R.id.jtvName);
        jtvName.setText("京都\nKYOTO");
        headerView.findViewById(R.id.jimTicket).setOnClickListener(this);
        headerView.findViewById(R.id.jimVisa).setOnClickListener(this);
        headerView.findViewById(R.id.jimAirpalne).setOnClickListener(this);
        headerView.findViewById(R.id.jimWifi).setOnClickListener(this);
        headerView.findViewById(R.id.jimPlay).setOnClickListener(this);
        headerView.findViewById(R.id.jimFood).setOnClickListener(this);
        headerView.findViewById(R.id.jimShop).setOnClickListener(this);
        headerView.findViewById(R.id.jimHotel).setOnClickListener(this);
        return headerView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.jimTicket:

                showToast("ticket");
                break;
            case R.id.jimVisa:

                showToast("visa");
                break;
            case R.id.jimAirpalne:

                showToast("airplane");
                break;
            case R.id.jimWifi:

                showToast("wifi");
                break;

            case R.id.jimPlay:

                CityFunActivity.startActivity(this, mPlaceId, 1);
                break;
            case R.id.jimHotel:

//                CityFunActivity.startActivity(this, mPlaceId, 2);
                CityHotelListActivity.startActivity(this,"cityid","酒店名","fromkey");
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
    protected ObjectRequest<List<City>> getObjectRequest(int pageIndex, int pageLimit) {

        ObjectRequest<List<City>> req = ReqFactory.newPost(CityHtpUtil.URL_POST_CITY, City.class, CityHtpUtil.getCityParams(""));

        if (BuildConfig.DEBUG) {

            List<City> cities = new ArrayList<>(5);
            City city;
            for (int i = 0; i < 5; i++) {

                city = new City();
                city.setPic_url("http://pic2.qyer.com/album/1f0/87/1840431/index/680x400");
                city.setCn_name("京都");
                city.setEn_name("KYOTO");
                cities.add(city);
            }
            req.setData(cities);
        }

        return req;
    }
}