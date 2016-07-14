package com.joy.app.activity.sample;

import com.android.library.injection.ActivityScope;
import com.android.library.injection.component.ActivityComponent;
import com.android.library.injection.component.AppComponent;

import dagger.Component;

/**
 * Created by Daisw on 16/6/18.
 */
@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {
                TabTestModule.class,
                MvpTestModule.class,
                MvpTestRvModule.class
        }
)
public interface TabTestComponent extends ActivityComponent {

    void inject(MvpTestFragment fragment);

    void inject(MvpTestRvFragment fragment);
}
