package com.joy.app.view.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.android.library.utils.ViewUtil;
import com.joy.app.R;
import com.joy.library.share.IShareInfo;
import com.joy.library.share.ShareHandler;
import com.joy.library.share.ShareInfo;
import com.android.library.utils.DeviceUtil;
import com.android.library.utils.ToastUtil;
import com.android.library.widget.JDialog;
import com.joy.library.share.ShareType;
import com.joy.library.share.ShareWeiBoUtil;
import com.joy.library.share.ShareWeixinUtil;

/**
 * 分享的窗口界面
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-12
 */
public class ShareDialog extends JDialog implements View.OnClickListener {

    private IShareInfo mIShareInfo;
    private ShareHandler mShareHandle;

    public ShareDialog(Activity activity, IShareInfo ishareInfo) {

        super(activity);
        mIShareInfo = ishareInfo;
        mShareHandle = new ShareHandler(activity);
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

        if (ShareWeiBoUtil.hasSinaWeiboClient()) {
            findViewById(R.id.tvWeibo).setOnClickListener(this);
        } else {
            ViewUtil.goneView(findViewById(R.id.tvWeibo));
        }
        if (ShareWeixinUtil.hasWeChatClient()) {
            findViewById(R.id.tvWeixin).setOnClickListener(this);
            findViewById(R.id.tvFriend).setOnClickListener(this);
        } else {
            ViewUtil.goneView(findViewById(R.id.tvWeixin));
            ViewUtil.goneView(findViewById(R.id.tvFriend));
        }
//        findViewById(R.id.tvEmail).setOnClickListener(this);
//        findViewById(R.id.tvsms).setOnClickListener(this);
        findViewById(R.id.tvShareMore).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (mIShareInfo == null || mShareHandle == null) {
            return;
        }
        ShareInfo info = null;
        ShareType type = null;
        switch (v.getId()) {
            case R.id.tvWeibo:
                type = ShareType.SINA;
                break;
            case R.id.tvWeixin:
                type = ShareType.WEIXIN;
                break;
            case R.id.tvFriend:
                type = ShareType.WEIXIN_CIRCLE;
                break;
//            case R.id.tvEmail:
//                type = ShareType.EMAIL;
//                break;
//            case R.id.tvsms:
//                type = ShareType.SMS;
//                break;
            case R.id.tvShareMore:
                type = ShareType.MORE;
                break;
        }
        info = mIShareInfo.getShareInfo(type);

        if (info != null) {
            mShareHandle.handleShare(info, type);
        }
    }

    //    private SocializeListeners.SnsPostListener mSnsPostListener = new SocializeListeners.SnsPostListener() {
    //        @Override
    //        public void onStart() {
    //
    //        }
    //
    //        @Override
    //        public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity socializeEntity) {
    //            String showText = platform.toString();
    //            if (eCode == StatusCode.ST_CODE_SUCCESSED) {
    //                showText += "平台分享成功";
    //            } else {
    //                showText += "平台分享失败";
    //            }
    //            ToastUtil.showToast(showText);
    //            dismiss();
    //        }
    //    };
}
