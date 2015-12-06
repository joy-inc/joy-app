package com.joy.app.activity.plan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.LogMgr;
import com.android.library.utils.TextUtil;
import com.android.library.utils.ToastUtil;
import com.android.library.view.dialogplus.DialogPlus;
import com.android.library.view.dialogplus.ListHolder;
import com.android.library.view.dialogplus.OnClickListener;
import com.android.library.view.dialogplus.OnItemClickListener;
import com.android.library.view.dialogplus.ViewHolder;
import com.joy.app.BuildConfig;
import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.app.activity.map.ListPoiMapActivity;
import com.joy.app.activity.map.MapActivity;
import com.joy.app.activity.poi.PoiDetailActivity;
import com.joy.app.adapter.plan.PlanListAdapter;
import com.joy.app.adapter.sample.DialogListAdapter;
import com.joy.app.bean.plan.PlanFolder;
import com.joy.app.bean.plan.PlanItem;
import com.joy.app.utils.http.PlanHttpUtil;
import com.joy.app.utils.map.MapUtil;
import com.joy.app.utils.plan.FolderRequestListener;
import com.joy.app.utils.plan.PlanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author litong  <br>
 * @Description 用户行程规划    <br>
 */
public class UserPlanListActivity extends BaseHttpRvActivity<List<PlanItem>> implements FolderRequestListener {
    private String mFolderID;
    public static void startActivityById(Activity act ,String FolderID,String mFolderName){
        Intent intent = new Intent(act,UserPlanListActivity.class);
        intent.putExtra("FolderID",FolderID);
        intent.putExtra("mFolderName",mFolderName);
        act.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executeRefreshOnly();
    }

    @Override
    protected void initData() {

        mFolderID =  getIntent().getStringExtra("FolderID");
    }

    @Override
    protected void initTitleView() {
        setTitle(null);
        addTitleLeftBackView();
        addTitleMiddleView(((JoyApplication)getApplication()).getUserNameStr()+"的"+ TextUtil.filterEmpty(getIntent().getStringExtra("mFolderName"),"行程规划"));
        addTitleRightView(R.drawable.ic_plan_more, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planUtil.showDeleteDialog(mFolderID);
            }
        });
        addTitleRightView(R.drawable.ic_plan_map, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getAdapter() == null || getAdapter().getData() == null )return;
                ListPoiMapActivity.startActivityByPoiList(UserPlanListActivity.this,((PlanListAdapter)getAdapter()).getContent());
            }
        });
    }
    PlanUtil planUtil ;

    @Override
    protected void initContentView() {
        super.initContentView();
        PlanListAdapter adapter = new PlanListAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<PlanItem>() {

            @Override
            public void onItemViewClick(int position, View clickView, PlanItem planItem) {
                PoiDetailActivity.startActivity(UserPlanListActivity.this,planItem.getProduct_id());
            }
        });
        setAdapter(adapter);
        planUtil = new PlanUtil(this,this);
    }

    @Override
    public void onSuccess(dialog_category category, Object obj) {
        showToast("删除成功");
        finish();
    }

    @Override
    public void onfaild(dialog_category category, String msg) {

    }

    @Override
    protected List<?> getListInvalidateContent(List<PlanItem> planItems) {
        return super.getListInvalidateContent(planItems);
    }

    @Override
    protected ObjectRequest<List<PlanItem>> getObjectRequest(int pageIndex, int pageLimit) {

        ObjectRequest obj = PlanHttpUtil.getUserPlanListRequest(mFolderID, PlanFolder.class);
        if (BuildConfig.DEBUG) {
            ArrayList<PlanItem>list = new ArrayList<>();
            for (int i = 0; i < 8; i++) {

                PlanItem folder = new PlanItem();
                folder.setCn_name("中文名" + i);
                folder.setEn_name("英文名" + i);
                folder.setPic_url("http://pic.qyer.com/album/user/1294/6/QEtcRhoBYkw/index/680x400");
                folder.setBefore_day("2");
                folder.setPrice((12.34*i)+"");
                list.add(folder);
            }
            obj.setData(list);
        }
        return obj;

    }
}
