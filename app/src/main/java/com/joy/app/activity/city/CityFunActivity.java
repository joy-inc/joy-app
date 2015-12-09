package com.joy.app.activity.city;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TextUtil;
import com.android.library.view.recyclerview.RecyclerAdapter;
import com.joy.app.R;
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
    private int mType;

    public static void startActivity(Activity act, String placeId, int type) {

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
        mType = getIntent().getIntExtra("type", 0);
    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView();
        addTitleMiddleView(getResources().getStringArray(R.array.city_fun)[mType - 1]);
    }

    @Override
    protected void initContentView() {

        setAdapter(new CityFunAdapter());
        setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {

                showToast("~~" + position);
            }
        });
    }

    @Override
    protected List<?> getListInvalidateContent(CityFun cityFun) {

        return cityFun.getList();
    }

    @Override
    protected ObjectRequest<CityFun> getObjectRequest(int pageIndex, int pageLimit) {

        return ReqFactory.newPost(CityHtpUtil.URL_POST_CITY_FUN, CityFun.class, CityHtpUtil.getCityFunParams(mPlaceId, mType, pageLimit, pageIndex));
    }
}