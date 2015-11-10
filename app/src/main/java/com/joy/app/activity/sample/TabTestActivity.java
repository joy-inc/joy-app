package com.joy.app.activity.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.library.activity.frame.BaseUiFragment;
import com.joy.library.adapter.frame.ExFragmentPagerAdapter;
import com.joy.library.utils.ToastUtil;
import com.joy.library.view.ExViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KEVIN.DAI on 15/11/10.
 */
public class TabTestActivity extends AppCompatActivity {

    private ExViewPager mViewPager;
    private ExFragmentPagerAdapter mPagerAdapter;

    public static void startActivity(Activity act) {

        if (act != null)
            act.startActivity(new Intent(act, TabTestActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_act_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ExViewPager) findViewById(R.id.viewpager);
        List<BaseUiFragment> fragments = new ArrayList<>();
        fragments.add(ListTestFragment.instantiate(this).setLableText("目的地"));
        fragments.add(ListTestFragment.instantiate(this).setLableText("旅行规划"));
        fragments.add(ListTestFragment.instantiate(this).setLableText("订单"));
        initFragments(fragments, false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void initFragments(List<? extends BaseUiFragment> fragments, boolean destoryEnable) {

        mPagerAdapter = new ExFragmentPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setFragmentItemDestoryEnable(destoryEnable);
        mPagerAdapter.setFragments(fragments);
        mViewPager.setAdapter(mPagerAdapter);
    }

    private long mLastPressedTime;

    @Override
    public void onBackPressed() {

        long currentPressedTime = System.currentTimeMillis();
        if (currentPressedTime - mLastPressedTime > 2000) {

            mLastPressedTime = currentPressedTime;
            showToast(R.string.toast_exit_tip);
        } else {

            super.onBackPressed();
            JoyApplication.releaseForExitApp();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    protected void showToast(String text) {

        ToastUtil.showToast(text);
    }

    protected void showToast(int resId) {

        showToast(getString(resId));
    }

    protected void showToast(int resId, Object... formatArgs) {

        showToast(getString(resId, formatArgs));
    }
}