package com.joy.app.activity.sample;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.joy.app.R;
import com.joy.library.utils.DeviceUtil;

/**
 * Created by KEVIN.DAI on 15/7/13.
 */
public class FullScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_act_full_screen);
    }

    public static void startActivity(Activity act) {

        if (act == null)
            return;

        Intent intent = new Intent(act, FullScreenActivity.class);
        act.startActivity(intent);
    }

    /**
     * @param act
     * @param view The view which starts the transition
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startActivity(Activity act, View view) {

        if (act == null || view == null)
            return;

        Intent intent = new Intent(act, FullScreenActivity.class);

        if (DeviceUtil.isLollipopOrUpper()) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(act, view, view.getTransitionName());
            act.startActivity(intent, options.toBundle());
        } else {

            act.startActivity(intent);
        }
    }
}
