package com.joy.app.activity.sample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import com.android.library.ui.activity.BaseUiActivity;
import com.android.library.view.SpreadView;
import com.joy.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.library.view.SpreadView.STATE_EMPTY_FINISHED;
import static com.android.library.view.SpreadView.STATE_FILL_FINISHED;

/**
 * Created by Daisw on 16/7/16.
 */
public class SpreadTestActivity extends BaseUiActivity implements SpreadView.OnStateChangeListener {

    private static final String ARG_SPREAD_START_LOCATION = "spread_start_location";
    private static final int ANIM_DURATION_TOOLBAR = 300;

    @BindView(R.id.svBg)
    SpreadView mSvBg;

    public static void startActivity(Activity act, int[] startingLocation) {

        Intent intent = new Intent(act, SpreadTestActivity.class);
        intent.putExtra(ARG_SPREAD_START_LOCATION, startingLocation);
        act.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_act_spread);
        ButterKnife.bind(this);
        setupSpreadBackground(savedInstanceState);
    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView();
        getToolbar().setTranslationY(-getToolbarHeight() - STATUS_BAR_HEIGHT);
    }

    private void setupSpreadBackground(Bundle savedInstanceState) {

        mSvBg.setFillPaintColor(getResources().getColor(R.color.color_accent));
        mSvBg.setOnStateChangeListener(this);
        if (savedInstanceState == null) {

            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_SPREAD_START_LOCATION);
            mSvBg.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {

                    mSvBg.getViewTreeObserver().removeOnPreDrawListener(this);
                    mSvBg.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {

            mSvBg.setToFinishedFrame();
        }
    }

    @Override
    public void finish() {

        getToolbar().animate()
                .translationY(-getToolbarHeight() - STATUS_BAR_HEIGHT)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        mSvBg.startToLocation();
                    }
                });
    }

    @Override
    public void onStateChange(int state) {

        switch (state) {
            case STATE_FILL_FINISHED:

                getToolbar().animate()
                        .translationY(0)
                        .setDuration(ANIM_DURATION_TOOLBAR);
                break;
            case STATE_EMPTY_FINISHED:

                super.finish();
                break;
            default:
                break;
        }
    }
}
