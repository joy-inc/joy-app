package com.joy.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joy.app.R;
import com.joy.app.activity.user.UserLoginActivity;

/**
 * 提示登录,并且点解打开登录界面
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-12-10
 */
public class LoginTipView extends LinearLayout {

    public LoginTipView(final Context context, int titleResId, int subTitleResId) {
        super(context);
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.view_login_tip_view, this);
        TextView title = (TextView) view.findViewById(R.id.tvTitle);
        title.setText(titleResId);

        TextView sub = (TextView) view.findViewById(R.id.tvSub);
        sub.setText(subTitleResId);

        view.findViewById(R.id.tvLoginButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLoginActivity.startActivity(context);
            }
        });
    }


}
