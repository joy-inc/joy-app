package com.joy.app.activity.plan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.library.BaseApplication;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.view.dialogplus.DialogPlus;
import com.android.library.widget.JRecyclerView;
import com.android.library.widget.JTextView;
import com.joy.app.R;
import com.joy.app.adapter.plan.PlanFolderAdapter;
import com.joy.app.bean.plan.PlanFolder;
import com.joy.app.utils.http.PlanHtpUtil;
import com.joy.app.utils.plan.FolderRequestListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class AddPoiToFloderActivity extends Activity {
    @Bind(R.id.jrv_list)
    JRecyclerView jrvList;
    @Bind(R.id.jtv_empty)
    JTextView jtvEmpty;
    @Bind(R.id.rl_loading)
    RelativeLayout rlLoading;
    @Bind(R.id.jtv_button)
    JTextView jtvButton;

    String PoiId;

    public static void startActivity(Activity activity,String PoiId){
        Intent intent = new Intent(activity,AddPoiToFloderActivity.class);
        intent.putExtra("PoiId",PoiId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plan_add_poi);
        ButterKnife.bind(this);
        initData();
        initContent();
    }


    private void initData() {
        PoiId = getIntent().getStringExtra("PoiId");
    }
    private void initContent() {
        jtvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        getFolderData();
    }

    private void showEmpty(){
        ViewUtil.goneView(rlLoading);
        ViewUtil.showView(jtvEmpty);
        ViewUtil.goneView(jrvList);

    }
    private void showList(List<PlanFolder> planFolders){
        ViewUtil.goneView(rlLoading);
        ViewUtil.goneView(jtvEmpty);
        ViewUtil.showView(jrvList);

        jrvList.setLayoutManager(new LinearLayoutManager(this));
        PlanFolderAdapter adapter= new PlanFolderAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<PlanFolder>() {
            @Override
            public void onItemViewClick(int position, View clickView, PlanFolder planFolder) {

            }
        });
        adapter.setData(planFolders);
        jrvList.setAdapter(adapter);
    }

    private void showDialog(){

    }

    private void getFolderData() {
        ObjectRequest<List<PlanFolder>> req = PlanHtpUtil.getUserPlanFolderRequest(PlanFolder.class,200,1);
        req.setResponseListener(new ObjectResponse<List<PlanFolder>>() {
            @Override
            public void onSuccess(Object tag, List<PlanFolder> planFolders) {
                if(CollectionUtil.isEmpty(planFolders)){
                    showEmpty();
                }else{
                    showList(planFolders);
                }
            }

            @Override
            public void onError(Object tag, String msg) {
                super.onError(tag, msg);
                showEmpty();
            }
        });

        addRequestNoCache(req);
    }

    private void createFolder(String mName){
        ObjectRequest<Object> req = PlanHtpUtil.getUserPlanFolderCreateRequest(mName, Object.class);
        req.setResponseListener(new ObjectResponse<Object>() {

            @Override
            public void onSuccess(Object tag, Object object) {

            }

            @Override
            public void onError(Object tag, String msg) {
                super.onError(tag, msg);

            }
        });

        addRequestNoCache(req);
    }

    private void addRequestNoCache(ObjectRequest<?> req){
        req.setTag( req.getIdentifier());
        req.setShouldCache(false);
        BaseApplication.getRequestQueue().add(req);
    }
}
