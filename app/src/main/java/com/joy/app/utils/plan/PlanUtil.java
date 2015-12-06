package com.joy.app.utils.plan;

import android.view.View;

import com.android.library.BaseApplication;
import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.view.dialogplus.DialogPlus;
import com.android.library.view.dialogplus.OnClickListener;
import com.android.library.view.dialogplus.ViewHolder;
import com.joy.app.R;
import com.joy.app.utils.http.PlanHttpUtil;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class PlanUtil  {

    BaseHttpUiActivity activity;

    DialogPlus dialog;

    FolderRequestListener listener;

    public PlanUtil(BaseHttpUiActivity activity, FolderRequestListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public DialogPlus showDeleteDialog(final String mFolderID ){

        DialogPlus dialog = DialogPlus.newDialog(activity)
                .setContentHolder(new ViewHolder(R.layout.dialog_plan_del))
                .setCancelable(true)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        if (view.getId() == R.id.jtv_cancel){
                            dialog.dismiss();
                        }else if(view.getId() == R.id.jtv_del){
                            delFolder(mFolderID);
                        }
                    }
                })
                .create();
        dialog.show();
        return dialog;
    }

    private void delFolder(String mFolderID){
        ObjectRequest<Object> req = PlanHttpUtil.getUserPlanFolderDeleteRequest(mFolderID, Object.class);
        req.setResponseListener(new ObjectResponse<Object>() {

            @Override
            public void onSuccess(Object tag, Object object) {
                if (listener != null)
                    listener.onSuccess(FolderRequestListener.dialog_category.delete,null);
            }

            @Override
            public void onError(Object tag, String msg) {
                super.onError(tag, msg);
                if (listener != null)
                    listener.onfaild(FolderRequestListener.dialog_category.delete,msg);
            }
        });

        addRequestNoCache(req);
    }
    private void createFolder(String mName){
        ObjectRequest<Object> req = PlanHttpUtil.getUserPlanFolderCreateRequest(mName, Object.class);
        req.setResponseListener(new ObjectResponse<Object>() {

            @Override
            public void onSuccess(Object tag, Object object) {
                if (listener != null)
                    listener.onSuccess(FolderRequestListener.dialog_category.delete,null);
            }

            @Override
            public void onError(Object tag, String msg) {
                super.onError(tag, msg);
                if (listener != null)
                    listener.onfaild(FolderRequestListener.dialog_category.delete,msg);
            }
        });

        addRequestNoCache(req);
    }

    public void setListener(FolderRequestListener listener) {
        this.listener = listener;
    }

    private void addRequestNoCache(ObjectRequest<?> req){
        req.setTag( req.getIdentifier());
        req.setShouldCache(false);
        BaseApplication.getRequestQueue().add(req);
    }
}
