package com.joy.app;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.joy.library.BaseApplication;

/**
 * Created by KEVIN.DAI on 15/7/8.
 */
public class JoyApplication extends BaseApplication {

    @Override
    public void onCreate() {

        super.onCreate();
        initApplication();
    }

    private void initApplication() {

        Fresco.initialize(getContext());
    }

    public static void releaseForExitApp() {

//        Fresco.shutDown();
        release();
    }
}