package com.joy.app.activity.sample;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.joy.app.R;
import com.joy.library.activity.frame.BaseUiActivity;

/**
 * Created by KEVIN.DAI on 15/7/11.
 */
public class DetailTestActivity extends BaseUiActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_act_detail);
    }

    @Override
    protected void initTitleView() {

        setTitle("详情页");
        addTitleLeftBackView();
    }

    @Override
    protected void initContentView() {

        ImageView ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        Glide.with(this)
                .load(getIntent().getStringExtra("photoUrl"))
                .placeholder(R.color.transparent)
                .into(ivPhoto);

        findViewById(R.id.fabTest).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FullScreenActivity.startActivity(DetailTestActivity.this, v);
            }
        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    /**
     * @param act
     * @param view The view which starts the transition
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startActivity(Activity act, View view, String photoUrl) {

        if (act == null || view == null)
            return;

        Intent intent = new Intent(act, DetailTestActivity.class);
        intent.putExtra("photoUrl", photoUrl);

        if (isLollipopOrUpper()) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(act, view, view.getTransitionName());
            act.startActivity(intent, options.toBundle());
        } else {

            act.startActivity(intent);
        }
    }
}