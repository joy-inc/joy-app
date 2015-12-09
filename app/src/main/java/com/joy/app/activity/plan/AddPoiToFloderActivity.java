package com.joy.app.activity.plan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.library.BaseApplication;
import com.android.library.activity.BaseUiActivity;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.ToastUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.widget.JRecyclerView;
import com.android.library.widget.JTextView;
import com.joy.app.R;
import com.joy.app.adapter.plan.PlanFolderAdapter;
import com.joy.app.bean.plan.PlanFolder;
import com.joy.app.utils.http.PlanHtpUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description 添加进规划    <br>
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
    @Bind(R.id.jtv_title)
    JTextView jtvTitle;
    @Bind(R.id.edt_name)
    EditText edtName;
    @Bind(R.id.rl_create)
    LinearLayout rlCreate;
    @Bind(R.id.ll_dialog)
    LinearLayout llDialog;

    String PoiId;
    String FolderName;

    public static void startActivity(Activity activity, String PoiId) {
        Intent intent = new Intent(activity, AddPoiToFloderActivity.class);
        intent.putExtra("PoiId", PoiId);
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
                if (jtvButton.getTag().equals("create")){
                    showCreateView();
                }else{
                    showLoading();
                    createFolder(edtName.getText().toString());
                }
            }
        });
        getFolderData();
    }

    public void showCreateView(){
        jtvTitle.setText("创建旅行规划");
        ViewUtil.goneView(rlLoading);
        ViewUtil.goneView(jtvEmpty);
        ViewUtil.goneView(jrvList);
        ViewUtil.showView(rlCreate);
        jtvButton.setClickable(true);
        jtvButton.setTag("create");
        jtvButton.setText("确认创建");
    }

    private void showEmpty() {
        jtvTitle.setText("我的旅行规划");
        ViewUtil.goneView(rlLoading);
        ViewUtil.showView(jtvEmpty);
        ViewUtil.goneView(jrvList);
        ViewUtil.goneView(rlCreate);
        jtvButton.setClickable(true);
        jtvButton.setTag("add");
        jtvButton.setText("创建新规划");
    }
    private void showLoading() {
        jtvTitle.setText("我的旅行规划");
        ViewUtil.showView(rlLoading);
        ViewUtil.goneView(jtvEmpty);
        ViewUtil.goneView(jrvList);
        ViewUtil.goneView(rlCreate);
        jtvButton.setClickable(false);
        jtvButton.setTag("add");
        jtvButton.setText("创建新规划");
    }
    PlanFolderAdapter adapter;
    private void showList(List<PlanFolder> planFolders) {
        jtvTitle.setText("我的旅行规划");
        ViewUtil.goneView(rlLoading);
        ViewUtil.goneView(jtvEmpty);
        ViewUtil.showView(jrvList);
        ViewUtil.goneView(rlCreate);

        jrvList.setLayoutManager(new LinearLayoutManager(this));
        if (adapter != null){
            adapter = new PlanFolderAdapter();
            adapter.setName(PoiId);
            adapter.setOnItemViewClickListener(new OnItemViewClickListener<PlanFolder>() {
                @Override
                public void onItemViewClick(int position, View clickView, PlanFolder planFolder) {
                    if (planFolder.getFolder_name().equals(FolderName)) return;
                    showLoading();
                    addPoi(planFolder);
                }
            });
            jrvList.setAdapter(adapter);
        }
        if (!CollectionUtil.isEmpty(planFolders)){
            if(adapter.getData() != null){
                adapter.getData().clear();
            }
            adapter.setData(planFolders);
        }
        jtvButton.setClickable(true);
        jtvButton.setTag("add");
        jtvButton.setText("创建新规划");
    }

    private void showCreateDialog() {

    }

    private void addPoi(final PlanFolder folde) {
        ObjectRequest<List<PlanFolder>> req = PlanHtpUtil.getUserPlanAddRequest(PoiId, folde.getFolder_id(), PlanFolder.class);
        req.setResponseListener(new ObjectResponse<List<PlanFolder>>() {
            @Override
            public void onSuccess(Object tag, List<PlanFolder> planFolders) {
                Intent intent = new Intent();
                intent.putExtra("name", folde.getFolder_name());
                intent.putExtra("id", folde.getFolder_id());
                setResult(RESULT_OK, intent);
                finish();

            }

            @Override
            public void onError(Object tag, String msg) {
                super.onError(tag, msg);
                showList(null);
                ToastUtil.showToast(msg);
            }
        });

        addRequestNoCache(req);
    }

    private void getFolderData() {
        ObjectRequest<List<PlanFolder>> req = PlanHtpUtil.getUserPlanFolderRequest(PlanFolder.class, 200, 1);
        req.setResponseListener(new ObjectResponse<List<PlanFolder>>() {
            @Override
            public void onSuccess(Object tag, List<PlanFolder> planFolders) {
                if (CollectionUtil.isEmpty(planFolders)) {
                    showEmpty();
                } else {
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

    private void createFolder(String mName) {
        ObjectRequest<Object> req = PlanHtpUtil.getUserPlanFolderCreateRequest(mName, Object.class);
        req.setResponseListener(new ObjectResponse<Object>() {

            @Override
            public void onSuccess(Object tag, Object object) {
                getFolderData();
            }

            @Override
            public void onError(Object tag, String msg) {
                super.onError(tag, msg);
                if (adapter != null){
                    showList(null);
                }
                ToastUtil.showToast(msg);
            }
        });

        addRequestNoCache(req);
    }

    private void addRequestNoCache(ObjectRequest<?> req) {
        req.setTag(req.getIdentifier());
        req.setShouldCache(false);
        BaseApplication.getRequestQueue().add(req);
    }
}
