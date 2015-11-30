package com.joy.app.activity.plan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.joy.app.BuildConfig;
import com.joy.app.adapter.plan.PlanListAdapter;
import com.joy.app.bean.plan.PlanFolder;
import com.joy.app.bean.plan.PlanItem;
import com.joy.app.utils.http.PlanHttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author litong  <br>
 * @Description 用户行程规划    <br>
 */
public class UserPlanListActivity extends BaseHttpRvActivity<List<PlanItem>> {
    private String mFolderID;

    public static void startActivityById(Activity act ,String FolderID){
        Intent intent = new Intent(act,UserPlanListActivity.class);
        intent.putExtra("FolderID",FolderID);
        act.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executeRefresh();
    }

    @Override
    protected void initData() {
        mFolderID =  getIntent().getStringExtra("FolderID");
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        PlanListAdapter adapter = new PlanListAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<PlanItem>() {

            @Override
            public void onItemViewClick(int position, View clickView, PlanItem planItem) {

            }
        });
        setAdapter(adapter);
    }

    @Override
    protected ObjectRequest<List<PlanItem>> getObjectRequest() {

        ObjectRequest obj = PlanHttpUtil.getUserPlanListRequest(mFolderID, PlanFolder.class);
        if (BuildConfig.DEBUG) {
            ArrayList<PlanItem>list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {

                PlanItem folder = new PlanItem();
                folder.setCn_name("中文名" + i);
                folder.setEn_name("英文名" + i);
                folder.setPic_url("http://pic.qyer.com/album/user/1294/6/QEtcRhoBYkw/index/680x400");
                folder.setBefore_day("2");
                folder.setPrice("123.34");
                folder.setPlan_id("id" + i);
                list.add(folder);
            }
            obj.setData(list);
        }
        return obj;

    }
}
