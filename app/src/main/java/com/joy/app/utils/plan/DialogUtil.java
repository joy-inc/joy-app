package com.joy.app.utils.plan;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.android.library.BaseApplication;
import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.view.dialogplus.DialogPlus;
import com.android.library.view.dialogplus.OnClickListener;
import com.android.library.view.dialogplus.ViewHolder;
import com.joy.app.R;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.PlanHtpUtil;

/**
 * @author litong  <br>
 * @Description <br>
 */
public class DialogUtil {

    BaseHttpUiActivity activity;

    DialogPlus dialog;

    FolderRequestListener listener;

    public DialogUtil(BaseHttpUiActivity activity, FolderRequestListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public DialogPlus showDeleteFolderDialog(final String mFolderID) {

        DialogPlus dialog = DialogPlus.newDialog(activity)
                .setContentHolder(new ViewHolder(R.layout.dialog_plan_del))
                .setCancelable(true)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        if (view.getId() == R.id.jtv_cancel) {
                        } else if (view.getId() == R.id.jtv_del) {
                            showAlertDialog(activity.getString(R.string.alert_plan_folder_delete), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == DialogInterface.BUTTON_POSITIVE){
                                        delFolder(mFolderID);
                                    }
                                }
                            });
                        }
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
        return dialog;
    }

    private void delFolder(String mFolderID) {
        ObjectRequest<Object> req = PlanHtpUtil.getUserPlanFolderDeleteRequest(mFolderID, Object.class);
        req.setResponseListener(new ObjectResponse<Object>() {

            @Override
            public void onPre() {
                if (listener != null)
                    listener.onRequest(FolderRequestListener.dialog_category.delete, "Folder");
            }

            @Override
            public void onSuccess(Object tag, Object object) {
                if (listener != null)
                    listener.onSuccess(FolderRequestListener.dialog_category.delete, "Folder");
            }

            @Override
            public void onError(Object tag, String msg) {
                super.onError(tag, msg);
                if (listener != null)
                    listener.onfaild(FolderRequestListener.dialog_category.delete, msg);
            }
        });

        addRequestNoCache(req);
    }

    public DialogPlus showDeletePoiDialog(final String mFolderID, final String PoiId) {
        View v = View.inflate(activity, R.layout.dialog_plan_del, null);
        ((TextView) v.findViewById(R.id.jtv_del)).setText("删除景点");
        DialogPlus dialog = DialogPlus.newDialog(activity)
                .setContentHolder(new ViewHolder(v))
                .setCancelable(true)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        if (view.getId() == R.id.jtv_del) {
                            delPoiFromFolder(mFolderID, PoiId);
                        }
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
        return dialog;
    }

    public void delPoiFromFolder(String mFolderID, String PoiId) {

        ObjectRequest<Object> req = PlanHtpUtil.getUserPlanDeleteRequest(mFolderID, PoiId, Object.class);
        req.setResponseListener(new ObjectResponse<Object>() {

            @Override
            public void onPre() {
                if (listener != null)
                    listener.onRequest(FolderRequestListener.dialog_category.delete, "Poi");
            }

            @Override
            public void onSuccess(Object tag, Object object) {
                if (listener != null)
                    listener.onSuccess(FolderRequestListener.dialog_category.delete, "Poi");
            }

            @Override
            public void onError(Object tag, String msg) {
                super.onError(tag, msg);
                if (listener != null)
                    listener.onfaild(FolderRequestListener.dialog_category.delete, msg);
            }
        });

        addRequestNoCache(req);
    }

    public DialogPlus showDeleteOrderDialog(final String OrderID) {
        View v = View.inflate(activity, R.layout.dialog_plan_del, null);
        ((TextView) v.findViewById(R.id.jtv_del)).setText("删除订单");
        DialogPlus dialog = DialogPlus.newDialog(activity)
                .setContentHolder(new ViewHolder(v))
                .setCancelable(true)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        if (view.getId() == R.id.jtv_cancel) {
                        } else if (view.getId() == R.id.jtv_del) {
                            delOrder(OrderID);
                        }
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
        return dialog;
    }

    public void delOrder(String OrderID) {
        ObjectRequest<Object> req = OrderHtpUtil.getCancelOrderRequest(OrderID);
        req.setResponseListener(new ObjectResponse<Object>() {

            @Override
            public void onPre() {
                if (listener != null)
                    listener.onRequest(FolderRequestListener.dialog_category.delete, "Order");
            }

            @Override
            public void onSuccess(Object tag, Object object) {
                if (listener != null)
                    listener.onSuccess(FolderRequestListener.dialog_category.delete, "Order");
            }

            @Override
            public void onError(Object tag, String msg) {
                super.onError(tag, msg);
                if (listener != null)
                    listener.onfaild(FolderRequestListener.dialog_category.delete, msg);
            }
        });

        addRequestNoCache(req);
    }
    AlertDialog mExitDialog;
    private void showAlertDialog(String content,final DialogInterface.OnClickListener alertListener) {
        mExitDialog = com.joy.library.dialog.DialogUtil.getOkCancelDialog(activity, R.string.confirm, R.string.cancel, content, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (alertListener != null) {
                    alertListener.onClick(dialog,which);
                }
                if (which == DialogInterface.BUTTON_NEGATIVE)
                    mExitDialog.dismiss();
            }
        });
        mExitDialog.show();
    }

    private void addRequestNoCache(ObjectRequest<?> req) {
        req.setTag(req.getIdentifier());
        req.setShouldCache(false);
        BaseApplication.getRequestQueue().add(req);
    }
}
