package com.joy.app.activity.sample;

import com.android.library.injection.ActivityScope;
import com.android.library.presenter.fragment.BaseHttpRvPresenter;
import com.joy.app.bean.sample.Special;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Daisw on 16/6/8.
 */
@ActivityScope
public class MvpTestRvPresenter {

    @Inject
    MvpTestRvView mTestRvView;

    @Inject
    BaseHttpRvPresenter<List<Special>, MvpTestRvView> mLauncher;

    @Inject
    MvpTestRvPresenter() {

    }

    @Inject
    void disposeLifecycle() {

        mTestRvView.lifecycle()
                .doOnSubscribe(() -> mLauncher.attachView(mTestRvView))
                .filter(e -> e == FragmentEvent.DESTROY_VIEW)
                .subscribe(e -> mLauncher.detachView());
    }

    public void launchRefreshOnly() {

        mLauncher.setPageLimit(10);
        mLauncher.launchRefreshOnly();
    }
}
