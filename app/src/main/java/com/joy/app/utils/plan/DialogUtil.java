package com.joy.app.utils.plan;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.android.library.BaseApplication;
import com.android.library.ui.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.joy.app.R;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.PlanHtpUtil;

/**
 * @author litong  <br>
 * @Description <br>
 */
public class DialogUtil {

    BaseHttpUiActivity activity;

//    DialogPlus mDialog;

    FolderRequestListener listener;

    public DialogUtil(BaseHttpUiActivity activity, FolderRequestListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

//    public DialogPlus showDeleteFolderDialog(final String mFolderID) {
//
//        mDialog = DialogPlus.newDialog(activity)
//                .setContentHolder(new ViewHolder(R.layout.dialog_plan_del))
//                .setCancelable(true)
//                .setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(DialogPlus dialog, View view) {
//                        if (view.getId() == R.id.jtv_cancel) {
//                            mDialog.dismiss();
//                        } else if (view.getId() == R.id.jtv_del) {
//                            showAlertDialog(activity.getString(R.string.alert_plan_folder_delete), new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    if (which == DialogInterface.BUTTON_POSITIVE){
//                                        delFolder(mFolderID);
//                                    }
//                                    mDialog.dismiss();
//                                }
//                            });
//                        }
//                    }
//                })
//                .create();
//        mDialog.show();
//        return mDialog;
//    }

    private void delFolder(String mFolderID) {
        ObjectRequest<Object> req = PlanHtpUtil.getUserPlanFolderDeleteRequest(mFolderID, Object.class);
        if (listener != null)
            listener.onRequest(FolderRequestListener.dialog_category.delete, "Folder");
        req.setResponseListener(new ObjectResponse<Object>() {

//            @Override
//            public void onPre() {
//                if (listener != null)
//                    listener.onRequest(FolderRequestListener.dialog_category.delete, "Folder");
//            }

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

//    public DialogPlus showDeletePoiDialog(final String mFolderID, final String PoiId) {
//        View v = View.inflate(activity, R.layout.dialog_plan_del, null);
//        ((TextView) v.findViewById(R.id.jtv_del)).setText("删除景点");
//        mDialog = DialogPlus.newDialog(activity)
//                .setContentHolder(new ViewHolder(v))
//                .setCancelable(true)
//                .setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(DialogPlus dialog, View view) {
//                        if (view.getId() == R.id.jtv_del) {
//                            delPoiFromFolder(mFolderID, PoiId);
//                        }
//                        mDialog.dismiss();
//                    }
//                })
//                .create();
//        mDialog.show();
//        return mDialog;
//    }

    public void delPoiFromFolder(String mFolderID, String PoiId) {

        ObjectRequest<Object> req = PlanHtpUtil.getUserPlanDeleteRequest(mFolderID, PoiId, Object.class);
        if (listener != null)
            listener.onRequest(FolderRequestListener.dialog_category.delete, "Poi");
        req.setResponseListener(new ObjectResponse<Object>() {

//            @Override
//            public void onPre() {
//                if (listener != null)
//                    listener.onRequest(FolderRequestListener.dialog_category.delete, "Poi");
//            }

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

//    public DialogPlus showDeleteOrderDialog(final String OrderID,final boolean isProcess) {
//        View v = View.inflate(activity, R.layout.dialog_plan_del, null);
//        ((TextView) v.findViewById(R.id.jtv_del)).setText("删除订单");
//        mDialog = DialogPlus.newDialog(activity)
//                .setContentHolder(new ViewHolder(v))
//                .setCancelable(true)
//                .setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(DialogPlus dialog, View view) {
//                        if (view.getId() == R.id.jtv_cancel) {
//                            mDialog.dismiss();
//                        } else if (view.getId() == R.id.jtv_del) {
//                            if (isProcess){
//                                ToastUtil.showToast(R.string.toast_cannot_delete);
//                                mDialog.dismiss();
//
//                            }else{
//
//                                showAlertDialog(activity.getString(R.string.alert_order_delete), new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        if (which == DialogInterface.BUTTON_POSITIVE){
//                                            delOrder(OrderID);
//                                        }
//                                        mDialog.dismiss();
//                                    }
//                                });
//                            }
//                        }
//                    }
//                })
//                .create();
//        mDialog.show();
//        return mDialog;
//    }

    public void delOrder(String OrderID) {
        ObjectRequest<Object> req = OrderHtpUtil.getCancelOrderRequest(OrderID);
        if (listener != null)
            listener.onRequest(FolderRequestListener.dialog_category.delete, "Order");
        req.setResponseListener(new ObjectResponse<Object>() {

//            @Override
//            public void onPre() {
//                if (listener != null)
//                    listener.onRequest(FolderRequestListener.dialog_category.delete, "Order");
//            }

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
        if(mExitDialog==null) {
            mExitDialog = com.joy.library.dialog.DialogUtil.getOkCancelDialog(activity, R.string.confirm, R.string.cancel, content, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (alertListener != null) {
                        alertListener.onClick(dialog, which);
                    }
                    if (which == DialogInterface.BUTTON_NEGATIVE)
                        mExitDialog.dismiss();
                }
            });
        }
        mExitDialog.show();
    }

    private void addRequestNoCache(ObjectRequest<?> req) {
        req.setTag(req.getIdentifier());
        req.setShouldCache(false);
        BaseApplication.getRequestQueue().add(req);
    }
}
