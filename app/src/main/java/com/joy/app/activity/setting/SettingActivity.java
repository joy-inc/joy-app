package com.joy.app.activity.setting;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.library.httptask.ObjectRequest;
import com.android.library.httptask.ObjectResponse;
import com.android.library.utils.TextUtil;
import com.android.library.utils.ToastUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.widget.JTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.BuildConfig;
import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.app.activity.user.UserLoginActivity;
import com.joy.app.eventbus.LoginStatusEvent;
import com.android.library.activity.BaseUiActivity;
import com.joy.app.utils.http.ReqFactory;
import com.joy.app.utils.http.sample.UserHttpUtil;
import com.umeng.update.UmengUpdateAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 设置中心
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-17
 */
public class SettingActivity extends BaseUiActivity implements View.OnClickListener {

    @Bind(R.id.sdvUserHead)
    ImageView mUserHead;

    @Bind(R.id.tvLoginInfo)
    TextView mLoginInfo;

    @Bind(R.id.tvName)
    TextView mUserName;

    @Bind(R.id.llLoginOut)
    View mLoinOut;

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
        setTitle(R.string.setting_title);

    }

    @Override
    protected void initContentView() {

        ButterKnife.bind(this);

        findViewById(R.id.llApp).setOnClickListener(this);
        findViewById(R.id.llRectangle).setOnClickListener(this);
        findViewById(R.id.llClean).setOnClickListener(this);
        findViewById(R.id.llAbout).setOnClickListener(this);
        findViewById(R.id.llLoginDiv).setOnClickListener(this);
        mUserHead.setOnClickListener(this);
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
            mUserName.setText(JoyApplication.getUser().getNickname());
            mUserHead.setImageResource(R.drawable.ic_joy_login);
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
            case R.id.sdvUserHead:
                openImageDialog();
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
     * 显示拍照的对话框
     */
    private void openImageDialog() {

    }

    /**
     * 推荐分享,打开推荐界面
     */
    private void rectangle() {

    }

    /**
     * 清除缓存
     */
    private void clean() {

    }

    /**
     * 打开关于界面
     */
    private void about() {

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
        //// TODO: 15/12/3 是否需要启动进度条
        ObjectRequest req = ReqFactory.newPost(UserHttpUtil.URL_USER_LOGIN_OUT, String.class, UserHttpUtil.userLoginOut(JoyApplication.getUserToken()));
        req.setData("");
        req.setResponseListener(new ObjectResponse() {

            @Override
            public void onSuccess(Object tag, Object o) {

                ToastUtil.showToast(R.string.login_login_out_success);
                JoyApplication.setUser(null);
                JoyApplication.getCommonPrefs().clearUser();
                EventBus.getDefault().post(new LoginStatusEvent(true, null));

            }

            @Override
            public void onError(Object tag, String msg) {
                super.onError(tag, msg);
                if (TextUtil.isEmpty(msg)) {
                    ToastUtil.showToast(R.string.request_error);
                } else {
                    ToastUtil.showToast(msg);
                }
            }
        });
        JoyApplication.getRequestQueue().add(req);
    }

    public static void startActivity(Context context) {

        context.startActivity(new Intent(context, SettingActivity.class));
    }
}
