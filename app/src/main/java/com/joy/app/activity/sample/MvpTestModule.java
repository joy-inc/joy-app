package com.joy.app.activity.sample;

import com.android.library.httptask.ObjectRequest;
import com.android.library.injection.ActivityScope;
import com.android.library.presenter.fragment.RequestLauncherImpl;
import com.joy.app.bean.sample.CityDetail;
import com.joy.app.utils.http.ReqFactory;
import com.joy.app.utils.http.sample.TestHtpUtil;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Daisw on 16/6/18.
 */
@Module
public class MvpTestModule {

    private final MvpTestFragment mFragment;

    public MvpTestModule(MvpTestFragment fragment) {

        mFragment = fragment;
    }

    @Provides
    @ActivityScope
    MvpTestView provideMvpTestView() {

        return mFragment;
    }

    @Provides
    @ActivityScope
    RequestLauncherImpl<CityDetail, MvpTestView> provideRequestLauncherImpl2() {

        return new RequestLauncherImpl<CityDetail, MvpTestView>() {

            @Override
            protected ObjectRequest<CityDetail> getObjectRequest(String... params) {

                return ReqFactory.newGet(TestHtpUtil.getCityInfoUrl(params[0]), CityDetail.class);
            }
        };
    }
}
