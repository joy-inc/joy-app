package com.joy.app.activity.sample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.android.library.activity.BaseHttpLvActivity;
import com.android.library.httptask.ObjectRequest;
import com.facebook.drawee.view.SimpleDraweeView;
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

        View v = inflateLayout(R.layout.view_avatar);
        SimpleDraweeView sdvAvatar = (SimpleDraweeView) v.findViewById(R.id.sdvAvatar);
        sdvAvatar.setImageURI(Uri.parse("http://static.qyer.com/data/avatar/000/66/51/28_avatar_big.jpg?v=1423838207"));
        addTitleRightView(v, new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initContentView() {

        setAdapter(new LvLoadMoreAdapter());
    }

    @Override
    protected ObjectRequest<List<Special>> getObjectRequest(int pageIndex, int pageLimit) {

        return new ObjectRequest(TestHtpUtil.getSpecialListUrl(pageIndex, pageLimit), Special.class);
    }
}