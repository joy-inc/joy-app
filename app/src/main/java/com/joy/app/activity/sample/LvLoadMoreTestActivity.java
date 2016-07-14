package com.joy.app.activity.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.library.ui.activity.BaseHttpLvActivity;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.widget.FrescoImageView;
import com.joy.app.R;
import com.joy.app.adapter.sample.LvLoadMoreAdapter;
import com.joy.app.bean.sample.Special;
import com.joy.app.utils.http.sample.TestHtpUtil;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/11/23.
 */
public class LvLoadMoreTestActivity extends BaseHttpLvActivity<List<Special>> {

    public static void startActivity(Activity act) {

        if (act != null)
            act.startActivity(new Intent(act, LvLoadMoreTestActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setPageLimit(10);
        executeCacheAndRefresh();
    }

    @Override
    protected void initTitleView() {

        setTitle("lv loadmore sample");

        View v = inflateLayout(R.layout.view_avatar);
        FrescoImageView sdvAvatar = (FrescoImageView) v.findViewById(R.id.sdvAvatar);
        sdvAvatar.setImageURI("http://static.qyer.com/data/avatar/000/66/51/28_avatar_big.jpg?v=1423838207");
        addTitleRightView(v, new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showToast("avatar");
            }
        });
    }

    @Override
    protected void initContentView() {

        LvLoadMoreAdapter adapter = new LvLoadMoreAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<Special>() {

            @Override
            public void onItemViewClick(int position, View clickView, Special special) {

                showToast(special.getTitle());
            }
        });
        setAdapter(adapter);
    }

    @Override
    protected ObjectRequest<List<Special>> getObjectRequest(int pageIndex, int pageLimit) {

        return ObjectRequest.get(TestHtpUtil.getSpecialListUrl(pageIndex, pageLimit), Special.class);
    }
}