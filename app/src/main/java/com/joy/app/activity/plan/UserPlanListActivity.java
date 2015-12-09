package com.joy.app.activity.plan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TextUtil;
import com.joy.app.BuildConfig;
import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.app.activity.map.ListPoiMapActivity;
import com.joy.app.activity.poi.PoiDetailActivity;
import com.joy.app.adapter.plan.PlanListAdapter;
import com.joy.app.bean.plan.PlanFolder;
import com.joy.app.bean.plan.PlanItem;
import com.joy.app.utils.http.PlanHtpUtil;
import com.joy.app.utils.plan.FolderRequestListener;
import com.joy.app.utils.plan.PlanUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author litong  <br>
 * @Description 用户行程规划    <br>
 */
public class UserPlanListActivity extends BaseHttpRvActivity<List<PlanItem>> implements FolderRequestListener {


    private String mFolderID;
    private PlanUtil planUtil;

    public static void startActivityById(Activity act, String FolderID, String mFolderName, int code) {
        Intent intent = new Intent(act, UserPlanListActivity.class);
        intent.putExtra("FolderID", FolderID);
        intent.putExtra("FolderName", mFolderName);
        act.startActivityForResult(intent, code);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executeRefreshOnly();
    }

    @Override
    protected void initData() {

        mFolderID = getIntent().getStringExtra("FolderID");
    }

    @Override
    protected void initTitleView() {

        setTitle(null);
        addTitleLeftBackView();
        setTitle(getIntent().getStringExtra("FolderName"));

        addTitleRightView(R.drawable.ic_plan_more, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planUtil.showDeleteDialog(mFolderID);
            }
        });
        addTitleRightView(R.drawable.ic_plan_map, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getAdapter() == null || getAdapter().getData() == null)
                    return;
                ListPoiMapActivity.startActivityByPoiList(UserPlanListActivity.this, ((PlanListAdapter) getAdapter()).getContent());
            }
        });
    }


    @Override
    protected void initContentView() {

        PlanListAdapter adapter = new PlanListAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<PlanItem>() {

            @Override
            public void onItemViewClick(int position, View clickView, PlanItem planItem) {
                PoiDetailActivity.startActivity(UserPlanListActivity.this, planItem.getProduct_id());
            }
        });
        setAdapter(adapter);
        planUtil = new PlanUtil(this, this);
    }

    @Override
    public void onSuccess(dialog_category category, Object obj) {
        showToast("删除成功");
        setResult(Activity.RESULT_OK);
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

        ObjectRequest obj = PlanHtpUtil.getUserPlanListRequest(mFolderID, PlanItem.class);
        return obj;

    }
}
