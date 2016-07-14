package com.joy.app.activity.sample;

import com.android.library.injection.ActivityScope;
import com.android.library.presenter.fragment.RequestLauncherImpl;
import com.joy.app.bean.sample.CityDetail;
import com.trello.rxlifecycle.FragmentEvent;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by KEVIN.DAI on 16/1/23.
 */
@ActivityScope
public class MvpTestPresenter {

    @Inject
    MvpTestView mTestView;

    @Inject
    RequestLauncherImpl<CityDetail, MvpTestView> mLauncher;

    @Inject
    MvpTestPresenter() {

    }

    @Inject
    void disposeLifecycle() {

        mTestView.lifecycle()
                .doOnSubscribe(() -> mLauncher.attachView(mTestView))
                .filter(e -> e == FragmentEvent.DESTROY_VIEW)
                .subscribe(e -> mLauncher.detachView());
    }

    public void launchRefreshOnly(String... params) {

        Observable<CityDetail> observable = mLauncher.launchRefreshOnly(params);
        observable
                .doOnSubscribe(mLauncher::doOnFirst)
                .filter(mLauncher::filterNull)
                .compose(mTestView.<CityDetail>bindToLifecycle())
                .subscribe(
                        cd -> {
                            mTestView.setCoverImg(cd.getPhotos().get(0));
                            mTestView.setCoverTitle(cd.getChinesename() + "\n" + cd.getEnglishname());
                            mLauncher.onNext(cd);
                        },
                        mLauncher::onError);
    }
}
