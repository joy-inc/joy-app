package com.joy.app.activity.sample;

import com.android.library.httptask.ObjectRequest;
import com.android.library.injection.ActivityScope;
import com.android.library.presenter.fragment.BaseHttpRvPresenter;
import com.joy.app.bean.sample.Special;
import com.joy.app.utils.http.sample.TestHtpUtil;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Daisw on 16/6/18.
 */
@Module
public class MvpTestRvModule {

    private final MvpTestRvFragment mFragment;

    public MvpTestRvModule(MvpTestRvFragment fragment) {

        mFragment = fragment;
    }

    @Provides
    @ActivityScope
    MvpTestRvView provideMvpTestRvView() {

        return mFragment;
    }

    @Provides
    @ActivityScope
    BaseHttpRvPresenter<List<Special>, MvpTestRvView> provideBaseHttpRvPresenter2() {

        return new BaseHttpRvPresenter<List<Special>, MvpTestRvView>() {

            @Override
            protected ObjectRequest<List<Special>> getObjectRequest(String... params) {

                return ObjectRequest.get(TestHtpUtil.getSpecialListUrl(getPageIndex(), getPageLimit()), Special.class);
            }
        };
    }
}
