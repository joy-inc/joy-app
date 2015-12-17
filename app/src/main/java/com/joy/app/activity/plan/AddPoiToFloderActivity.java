package com.joy.app.activity.plan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.library.BaseApplication;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.DeviceUtil;
import com.android.library.utils.LogMgr;
import com.android.library.utils.TextUtil;
import com.android.library.utils.ToastUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.widget.JTextView;
import com.joy.app.R;
import com.joy.app.adapter.plan.PlanFolderAdapter;
import com.joy.app.bean.plan.PlanFolder;
import com.joy.app.eventbus.FolderEvent;
import com.joy.app.utils.http.PlanHtpUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * @author litong  <br>
 * @Description 添加进规划    <br>
 */
public class AddPoiToFloderActivity extends Activity {
    @Bind(R.id.jrv_list)
    RecyclerView jrvList;
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
    String FolderId;
    @Bind(R.id.v_shadow)
    View vShadow;

    public static void startActivity(Activity activity, String PoiId, String FolderId, int requestCode) {
        Intent intent = new Intent(activity, AddPoiToFloderActivity.class);
        intent.putExtra("PoiId", PoiId);
        intent.putExtra("FolderId", FolderId);
        activity.startActivityForResult(intent, requestCode);
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
        FolderId = getIntent().getStringExtra("FolderId");
    }

    boolean isCreateView = false;

    private void initContent() {
        int width = DeviceUtil.getScreenWidth();

        LinearLayout layout = (LinearLayout) findViewById(R.id.ll_content);
        layout.setLayoutParams(new FrameLayout.LayoutParams(width, FrameLayout.LayoutParams.MATCH_PARENT));
        jtvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCreateView) {
                    showCreateView();
                } else {
                    showLoading();
                    createFolder(edtName.getText().toString());
                    edtName.setText("");
                }
            }
        });
        edtName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                LogMgr.i("actionId:"+actionId);

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (TextUtil.isEmptyTrim(v.getText().toString()))
                        return false;
                    showLoading();
                    createFolder(edtName.getText().toString());
                    edtName.setText("");
                    return true;
                }
                return false;
            }
        });
        vShadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showLoading();
        getFolderData();
    }

    public void showCreateView() {
        isCreateView = true;
        jtvTitle.setText("创建旅行规划");
        ViewUtil.goneView(rlLoading);
        ViewUtil.goneView(jtvEmpty);
        ViewUtil.goneView(jrvList);
        ViewUtil.showView(rlCreate);
        jtvButton.setClickable(true);
        jtvButton.setTag("create");
        jtvButton.setText("确认创建");
        edtName.requestFocus();
        showInputWindow();
    }

    private void showEmpty() {
        isCreateView = false;
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
        isCreateView = false;
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
        isCreateView = false;
        jtvTitle.setText("我的旅行规划");
        ViewUtil.goneView(rlLoading);
        ViewUtil.goneView(jtvEmpty);
        ViewUtil.showView(jrvList);
        ViewUtil.goneView(rlCreate);

        if (adapter == null) {
            jrvList.setLayoutManager(new LinearLayoutManager(this));
            adapter = new PlanFolderAdapter();
            adapter.setName(FolderId);
            adapter.setOnItemViewClickListener(new OnItemViewClickListener<PlanFolder>() {
                @Override
                public void onItemViewClick(int position, View clickView, PlanFolder planFolder) {
                    if (planFolder.getFolder_name().equals(FolderId)) return;
                    showLoading();
                    addPoi(planFolder);
                }
            });
            jrvList.setAdapter(adapter);
        }
        if (!CollectionUtil.isEmpty(planFolders)) {
            if (adapter.getData() != null) {
                adapter.getData().clear();
            }
            adapter.setData(planFolders);
            adapter.notifyDataSetChanged();
        }
        jtvButton.setClickable(true);
        jtvButton.setTag("add");
        jtvButton.setText("创建新规划");
    }

    private void showCreateDialog() {

    }

    private void addPoi(final PlanFolder folde) {
        ObjectRequest<Object> req = PlanHtpUtil.getUserPlanAddRequest(PoiId, folde.getFolder_id(), Object.class);
        req.setResponseListener(new ObjectResponse<Object>() {
            @Override
            public void onSuccess(Object tag, Object object) {
                Intent intent = new Intent();
                intent.putExtra("name", folde.getFolder_name());
                intent.putExtra("id", folde.getFolder_id());
                EventBus.getDefault().post(new FolderEvent(FolderEvent.ADD_POI));
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
                EventBus.getDefault().post(new FolderEvent(FolderEvent.CREATE_FOLDER));
                getFolderData();
            }

            @Override
            public void onError(Object tag, String msg) {
                super.onError(tag, msg);
                if (adapter != null) {
                    showList(null);
                }else{
                    getFolderData();
                }
                ToastUtil.showToast(msg);
            }
        });

        addRequestNoCache(req);
    }

    /**
     * 隐藏输入框
     */
    public void hiddenInputWindow() {

        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtName.getWindowToken(), 0);

    }

    /**
     * 弹出输入框
     */
    public void showInputWindow() {

        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edtName, InputMethodManager.SHOW_IMPLICIT);
    }

    private void addRequestNoCache(ObjectRequest<?> req) {
        req.setTag(req.getIdentifier());
        req.setShouldCache(false);
        BaseApplication.getRequestQueue().add(req);
    }
}
