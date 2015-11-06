package com.joy.app.activity.sample;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.joy.app.R;
import com.joy.library.activity.frame.BaseHttpLvActivity;
import com.joy.app.adapter.sample.CityAdapter;
import com.joy.app.bean.sample.HotCityItem;
import com.joy.app.httptask.sample.TestHtpUtil;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/8.
 */
public class ListTestActivity extends BaseHttpLvActivity<List<HotCityItem>> {

    @Override
    public void finish() {

        removeRequestFromQueue(0);
        super.finish();
    }

    @Override
    protected void initData() {

        addRequest2QueueHasCache(TestHtpUtil.getTestUrl(), 0, HotCityItem.class);
    }

    @Override
    protected void initTitleView() {

        setTitleText("列表页");
    }

    @Override
    protected void initContentView() {

        setAdapter(new CityAdapter());
        setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                View v = view.findViewById(R.id.sdvPhoto);
                String url = ((CityAdapter) getAdapter()).getItem(position).getPhoto();
                DetailTestActivity.startActivity(ListTestActivity.this, v, url);
            }
        });
    }

    @Override
    protected void onHttpFailed(String msg) {

//        super.onHttpFailed(msg);
        showToast("~~" + msg);
    }

    public static void startActivity(Activity act) {

        if (act == null)
            return;

        Intent intent = new Intent(act, ListTestActivity.class);
        act.startActivity(intent);
    }
}
