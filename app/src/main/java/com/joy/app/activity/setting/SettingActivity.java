package com.joy.app.activity.setting;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.library.utils.ViewUtil;
import com.android.library.widget.JTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.BuildConfig;
import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.app.eventbus.LoginStatusEvent;
import com.android.library.activity.BaseUiActivity;
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
    SimpleDraweeView mUserHead;

    @Bind(R.id.sdUserBG)
    SimpleDraweeView mUserBG;

    @Bind(R.id.tvLoginInfo)
    TextView mLoginInfo;

    @Bind(R.id.tvName)
    TextView mUserName;

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
        View loginOut = findViewById(R.id.llLoginOut);
        loginOut.setOnClickListener(this);

        if (JoyApplication.isLogin()) {
            ViewUtil.showView(loginOut);
            ViewUtil.showView(mUserName);
            //            mUserName.setText(JoyApplication.geu);
            //// TODO: 15/11/30 确认好user对象
//            mUserHead.setImageURI(Uri.parse(JoyApplication.getUserToken()));
            ViewUtil.goneView(mLoginInfo);
        }
        JTextView version = (JTextView) findViewById(R.id.tvVersion);
        version.setText(getString(R.string.setting_version, BuildConfig.VERSION_NAME));


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
        //        EventBus.getDefault().post( new LoginStatusEvent("FirstEvent btn clicked"));
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

        }
    }

    /**
     * 登出
     */
    private void loginOut() {
        //// TODO: 15/11/25 登出成功后要改变按钮的状态
        ViewUtil.goneView(mUserName);
        ViewUtil.showView(mLoginInfo);
//        mUserHead.set//设置默认值
    }

    public static void startActivity(Context context) {

        context.startActivity(new Intent(context, SettingActivity.class));
    }
}
