package com.joy.app.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.joy.app.R;
import com.joy.library.share.IShareInfo;
import com.joy.library.share.ShareHandler;
import com.joy.library.share.ShareInfo;
import com.joy.library.utils.DeviceUtil;
import com.joy.library.utils.ToastUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.listener.SocializeListeners;

/**
 * 分享的窗口界面
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-12
 */
public class ShareDialog extends AlertDialog implements View.OnClickListener {

    private IShareInfo mIShareInfo;
    private ShareHandler mShareHandle;

    public ShareDialog(Activity activity, IShareInfo ishareInfo) {

        super(activity, R.style.qa_ex_theme_dialog);
        mIShareInfo = ishareInfo;
        mShareHandle = new ShareHandler(activity, mSnsPostListener);
        initDialog();

    }

    /**
     * 初始化dialog的属性
     */
    private void initDialog() {
        setCanceledOnTouchOutside(true);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutInflater().inflate(R.layout.dialog_share, null), new ViewGroup.LayoutParams(DeviceUtil.getScreenWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        initContentView();

    }

    private void initContentView() {

        findViewById(R.id.tvWeibo).setOnClickListener(this);
        findViewById(R.id.tvWeixin).setOnClickListener(this);
        findViewById(R.id.tvFriend).setOnClickListener(this);
        findViewById(R.id.tvEmail).setOnClickListener(this);
        findViewById(R.id.tvQq).setOnClickListener(this);
        findViewById(R.id.tvQaZone).setOnClickListener(this);
        findViewById(R.id.tvsms).setOnClickListener(this);
        findViewById(R.id.tvShareMore).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (mIShareInfo == null || mShareHandle == null) {
            return;
        }
        ShareInfo info = null;
        SHARE_MEDIA type = null;
        switch (v.getId()) {
            case R.id.tvWeibo:
                type = SHARE_MEDIA.SINA;
                break;
            case R.id.tvWeixin:
                type = SHARE_MEDIA.WEIXIN;
                break;
            case R.id.tvFriend:
                type = SHARE_MEDIA.WEIXIN_CIRCLE;
                break;
            case R.id.tvEmail:
                type = SHARE_MEDIA.EMAIL;
                break;
            case R.id.tvQq:
                type = SHARE_MEDIA.QQ;
                break;
            case R.id.tvQaZone:
                type = SHARE_MEDIA.QZONE;
                break;
            case R.id.tvsms:
                type = SHARE_MEDIA.SMS;
                break;
            case R.id.tvShareMore:
                type = null;
                break;
        }
        info = mIShareInfo.getShareInfo(type);

        if (info != null) {
            mShareHandle.handleShare(info, type);
        }
    }

    private SocializeListeners.SnsPostListener mSnsPostListener = new SocializeListeners.SnsPostListener() {
        @Override
        public void onStart() {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity socializeEntity) {
            String showText = platform.toString();
            if (eCode == StatusCode.ST_CODE_SUCCESSED) {
                showText += "平台分享成功";
            } else {
                showText += "平台分享失败";
            }
            ToastUtil.showToast(showText);
            dismiss();
        }
    };
}
