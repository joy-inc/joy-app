package com.joy.app.activity.sample;

import com.android.library.injection.ActivityScope;
import com.android.library.injection.module.ActivityModule;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Daisw on 16/6/18.
 */
@Module
public class TabTestModule extends ActivityModule {

    public TabTestModule(TabTestActivity activity) {

        super(activity);
    }

    @Provides
    @ActivityScope
    TabTestActivity provideTabTestActivity() {

        return (TabTestActivity) super.provideActivity();
    }
}
