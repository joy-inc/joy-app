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
import com.joy.app.adapter.plan.UserPlanAdapter;
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

        ObjectRequest obj = ObjectRequest.get(PlanHttpUtil.getUserPlanListUrl(mFolderID), PlanFolder.class);
        if (BuildConfig.DEBUG) {
            ArrayList<PlanItem>list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {

                PlanItem folder = new PlanItem();
                folder.setCn_name("中文名" + i);
                folder.setEn_name("英文名" + i);
                folder.setPic_url("");
                folder.setPlan_id("id" + i);
                folder.setPoi_name("poi名字"+1);
                list.add(folder);
            }
            obj.setData(list);
        }
        return obj;

    }
}
