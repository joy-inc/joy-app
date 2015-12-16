package com.joy.app.activity.setting;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.TextUtil;
import com.android.library.utils.ToastUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.widget.JTextView;
import com.joy.app.BuildConfig;
import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.app.activity.common.WebViewActivity;
import com.joy.app.activity.user.UserLoginActivity;
import com.joy.app.eventbus.LoginStatusEvent;
import com.joy.app.utils.http.ReqFactory;
import com.joy.app.utils.http.UserHtpUtil;
import com.joy.app.utils.share.SettingShare;
import com.joy.app.view.dialog.ShareDialog;
import com.umeng.update.UmengUpdateAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 设置中心
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-17
 */
public class SettingActivity extends BaseHttpUiActivity<String> implements View.OnClickListener {

    @Bind(R.id.sdvUserHead)
    ImageView mUserHead;

    @Bind(R.id.tvLoginInfo)
    TextView mLoginInfo;

    @Bind(R.id.tvName)
    TextView mUserName;

    @Bind(R.id.llLoginOut)
    View mLoinOut;

    private SettingShare mShare;

    private ShareDialog mShareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_setting);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {

        EventBus.getDefault().register(this);
    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView();
        addTitleMiddleView(R.string.setting_title);
    }

    @Override
    protected void initContentView() {

        ButterKnife.bind(this);

        findViewById(R.id.llApp).setOnClickListener(this);
        findViewById(R.id.llRectangle).setOnClickListener(this);
        findViewById(R.id.llClean).setOnClickListener(this);
        findViewById(R.id.llAbout).setOnClickListener(this);
        findViewById(R.id.llLoginDiv).setOnClickListener(this);
        mLoinOut.setOnClickListener(this);
        handleUserLogin();
        JTextView version = (JTextView) findViewById(R.id.tvVersion);
        version.setText(getString(R.string.setting_version, BuildConfig.VERSION_NAME));


    }


    /**
     * 处理用户登录没有登录的状态
     */
    private void handleUserLogin() {

        if (JoyApplication.isLogin()) {
            ViewUtil.showView(mLoinOut);
            ViewUtil.showView(mUserName);
            mUserName.setText(JoyApplication.getNickname());
            mUserHead.setImageResource(R.drawable.ic_logo_circle);
            ViewUtil.goneView(mLoginInfo);
        } else {
            ViewUtil.goneView(mLoinOut);
            ViewUtil.goneView(mUserName);
            ViewUtil.showView(mLoginInfo);
            mUserHead.setImageResource(R.drawable.ic_user_def_head);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llApp:
                openAppMarket();
                break;
            case R.id.llRectangle:
                rectangle();
                break;
            case R.id.llClean:
                clean();
                break;
            case R.id.llAbout:
                about();
                break;

            case R.id.llLoginDiv:
                showLoginActivity();
                break;
            case R.id.llUpdate:

                UmengUpdateAgent.forceUpdate(SettingActivity.this);
                break;
            case R.id.llLoginOut:
                loginOut();
                break;
        }
    }

    /**
     * 登录的回掉
     *
     * @param event
     */
    public void onEventMainThread(LoginStatusEvent event) {
        handleUserLogin();
    }

    /**
     * 打开市场
     */
    private void openAppMarket() {
        try {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + getApplicationInfo().packageName));
            intent.setComponent(new ComponentName("android", "com.android.internal.app.ResolverActivity"));
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 推荐分享,打开推荐界面
     */
    private void rectangle() {

        if (mShare == null) {
            mShare = new SettingShare();
        }
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(this, mShare);

        }
        mShareDialog.show();
    }

    /**
     * 清除缓存
     */
    private void clean() {

        ToastUtil.showToast(R.string.setting_clean_success);
    }

    /**
     * 打开关于界面
     */
    private void about() {

        WebViewActivity.startActivity(this, "http://www.qyer.com", getString(R.string.setting_about), WebViewActivity.TYPE_ABOUT);
    }

    /**
     * 打开登录界面
     */
    private void showLoginActivity() {

        if (!JoyApplication.isLogin()) {
            UserLoginActivity.startActivity(this);
        }
    }

    /**
     * 登出
     */
    private void loginOut() {

        //网络请求,发广播
        if (BuildConfig.DEBUG && ("41dd399909650830414ae7b0276d8dc2ae777fa2d552c5a4dd93fcd0040bce1e".equals(JoyApplication.getUserToken())
                || "e4f6ed7c3acb5bbcd17f62f82a0effb22bc3c1b319b50f825a95dc3891af0aeb".equals(JoyApplication.getUserToken()))){
            ToastUtil.showToast("退毛线啊退,再退后台要吵着跳楼了");
            return;
        }
        ObjectRequest req = ReqFactory.newPost(UserHtpUtil.URL_USER_LOGIN_OUT, String.class, UserHtpUtil.userLoginOut(JoyApplication.getUserToken()));
        req.setResponseListener(new ObjectResponse() {

            @Override
            public void onPre() {
                showLoading();
            }

            @Override
            public void onSuccess(Object tag, Object o) {

                ToastUtil.showToast(R.string.login_login_out_success);
                JoyApplication.setUser(null);
                JoyApplication.getCommonPrefs().clearUser();
                EventBus.getDefault().post(new LoginStatusEvent(true, null));
                hideLoading();
            }

            @Override
            public void onError(Object tag, String msg) {
                if (TextUtil.isEmpty(msg)) {
                    ToastUtil.showToast(R.string.request_error);
                } else {
                    ToastUtil.showToast(msg);
                }
                hideLoading();

            }
        });
        addRequestNoCache(req);
    }

    public static void startActivity(Context context) {

        context.startActivity(new Intent(context, SettingActivity.class));
    }

    @Override
    protected boolean invalidateContent(String s) {
        return false;
    }

    @Override
    protected ObjectRequest<String> getObjectRequest() {
        return null;
    }
}
