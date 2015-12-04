package com.joy.app.activity.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.view.dialogplus.DialogPlus;
import com.android.library.view.dialogplus.ListHolder;
import com.android.library.view.dialogplus.OnItemClickListener;
import com.android.library.view.recyclerview.RecyclerAdapter;
import com.joy.app.R;
import com.joy.app.adapter.sample.DialogListAdapter;
import com.joy.app.adapter.sample.RvLoadMoreAdapter;
import com.joy.app.bean.sample.Special;
import com.joy.app.utils.http.sample.TestHtpUtil;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/12/2.
 */
public class RvLoadMoreTestActivity extends BaseHttpRvActivity<List<Special>> {

    public static void startActivity(Activity act) {

        if (act != null)
            act.startActivity(new Intent(act, RvLoadMoreTestActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setPageLimit(10);
        executeCacheAndRefresh();
    }

    @Override
    protected void initContentView() {

        setAdapter(new RvLoadMoreAdapter());
        setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {

                DialogPlus dialog = DialogPlus.newDialog(RvLoadMoreTestActivity.this)
                        .setContentHolder(new ListHolder())
                        .setHeader(R.layout.t_tv_dialog)
                        .setFooter(R.layout.t_btn_dialog)
                        .setCancelable(true)
                        .setAdapter(new DialogListAdapter())
                        .setOnItemClickListener(new OnItemClickListener() {

                            @Override
                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {

                                showToast("~~" + position);
                            }
                        })
                        .create();
                dialog.show();
            }
        });
    }

    @Override
    protected ObjectRequest<List<Special>> getObjectRequest(int pageIndex, int pageLimit) {

        return ObjectRequest.get(TestHtpUtil.getSpecialListUrl(pageIndex, pageLimit), Special.class);
    }
}