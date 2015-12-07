package com.joy.app.activity.city;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TextUtil;
import com.android.library.view.recyclerview.RecyclerAdapter;
import com.joy.app.BuildConfig;
import com.joy.app.R;
import com.joy.app.adapter.city.CityFunAdapter;
import com.joy.app.bean.city.CityFun;
import com.joy.app.utils.http.CityHtpUtil;
import com.joy.app.utils.http.ReqFactory;

import java.util.ArrayList;
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

        ObjectRequest<CityFun> req = ReqFactory.newPost(CityHtpUtil.URL_POST_CITY_FUN, CityFun.class, CityHtpUtil.getCityFunParams(mPlaceId, mType, pageLimit, pageIndex));
        if (BuildConfig.DEBUG) {

            CityFun cityFun = new CityFun();
            cityFun.setDescription("总体描述...");
            CityFun.ListEntity entity;
            List<CityFun.ListEntity> entities = new ArrayList<>(5);
            for (int i = 0; i < 5; i++) {

                entity = new CityFun.ListEntity();
                entity.setPic_url("http://pic2.qyer.com/album/user/725/20/RktQQBoDYQ/index/300x200");
                entity.setRecommend("拉面是日本人生活中必不可少的一道美味，日本拉面非常讲究汤底，东京以酱油底、豚骨汤底最为常见。东京拉面的新流行。");
                entity.setTopic_name("浅草寺晴空塔与周边");
                entity.setEn_name("Sensoji Temple/SKYTREE");
                entities.add(entity);
            }
            cityFun.setList(entities);
            req.setData(cityFun);
        }
        return req;
    }
}