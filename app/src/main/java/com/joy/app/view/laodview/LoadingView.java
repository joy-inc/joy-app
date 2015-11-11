package com.joy.app.view.laodview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * 统一的loadview,只做view的展现,没有问题,有动画
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-10
 */
public class LoadingView extends FrameLayout {

    private ImageView mIvFgIcon;//背景icon
    private RotateAnimation mFgRotateAnim;//前景icon旋转动画

    private int mIvFgIconResId;
    private int mIvBgIconResId;

    private ImageView mIvBgIcon;//前景icon
    private ScaleAnimation mBgSmallAnim;//背景icon缩小动画
    private ScaleAnimation mBgBigAnim;//背景icon放大动画

    private boolean mAnimRunning;

    public LoadingView(Context context, int iconSizePx) {

        super(context);
        initImageViews(iconSizePx);
        initAnimations();
    }

//    public LoadIngView(Context context, AttributeSet attrs){
//
//        super(context, attrs);
//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.QaLoadingView);
//        int iconSizePx = ta.getDimensionPixelSize(R.styleable.QaLoadingView_iconSize, -100);
//        mIvBgIconResId = ta.getResourceId(R.styleable.QaLoadingView_iconBg, 0);
//        mIvFgIconResId = ta.getResourceId(R.styleable.QaLoadingView_iconFg, 0);
//        ta.recycle();
//
//        initImageViews(iconSizePx);
//        initAnimations();
//    }

    private void initImageViews(int iconSizePx) {

        mIvBgIcon = new ImageView(getContext());
        mIvBgIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        int layoutSize = iconSizePx == -100 ? FrameLayout.LayoutParams.WRAP_CONTENT : iconSizePx;
        FrameLayout.LayoutParams fllp = new FrameLayout.LayoutParams(layoutSize, layoutSize);
        fllp.gravity = Gravity.CENTER;
        addView(mIvBgIcon, fllp);

        mIvFgIcon = new ImageView(getContext());
        mIvFgIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        fllp = new FrameLayout.LayoutParams(layoutSize, layoutSize);
        fllp.gravity = Gravity.CENTER;
        addView(mIvFgIcon, fllp);
    }

    private void initAnimations() {

        mBgSmallAnim = new ScaleAnimation(1f,0.9f,1f,0.9f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        mBgSmallAnim.setDuration(450);
        mBgSmallAnim.setFillAfter(true);
        mBgSmallAnim.setInterpolator(new BgDecelerateInterpolator());
        mBgSmallAnim.setAnimationListener(mBgSmallAnimListener);

        mBgBigAnim = new ScaleAnimation(0.9f,1f,0.9f,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        mBgBigAnim.setDuration(450);
        mBgBigAnim.setFillAfter(true);
        mBgBigAnim.setInterpolator(new BgDecelerateInterpolator());
        mBgBigAnim.setAnimationListener(mBgBigAnimListener);

        mFgRotateAnim = new RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        mFgRotateAnim.setInterpolator(new FgAnticipateInterpolator(1.5f));
        mFgRotateAnim.setDuration(900);
        mFgRotateAnim.setFillAfter(true);
        mFgRotateAnim.setAnimationListener(mFgAnimListener);
    }

    public void setAnimForeImage(int resId){

        mIvFgIconResId = resId;
    }

    public void setAnimBgImage(int resId){

        mIvBgIconResId = resId;
    }

    public void startLoadingAnim(long delayMillis){

        if(!mAnimRunning){

            if(mIvFgIconResId > 0)
                mIvFgIcon.setImageResource(mIvFgIconResId);

            if(mIvBgIconResId > 0)
                mIvBgIcon.setImageResource(mIvBgIconResId);

            removeCallbacks(mDelayStartAnimRunnable);

            if(delayMillis > 0){

                postDelayed(mDelayStartAnimRunnable, delayMillis);
            }else{

                mDelayStartAnimRunnable.run();
            }
            mAnimRunning = true;
        }
    }

    public void stopLoadingAnim(){

        if(mAnimRunning){

            removeCallbacks(mDelayStartAnimRunnable);
            mIvFgIcon.clearAnimation();
            mIvBgIcon.clearAnimation();

            mIvFgIcon.setImageDrawable(null);
            mIvBgIcon.setImageDrawable(null);
            mAnimRunning = false;
        }
    }

    @Override
    public void setVisibility(int visibility) {

        if(getVisibility() != visibility)
            super.setVisibility(visibility);

        if(visibility == VISIBLE){

            show(0);
        }else{

            stopLoadingAnim();
        }
    }

    public void show(long delayStartAnim){

        startLoadingAnim(delayStartAnim);
        if(getVisibility() != VISIBLE)
            setVisibility(VISIBLE);
    }

    public void hide(){

        if(getVisibility() != INVISIBLE)
            setVisibility(INVISIBLE);

        stopLoadingAnim();
    }

    public void gone() {

        if (getVisibility() != GONE)
            setVisibility(GONE);

        stopLoadingAnim();
    }

    public boolean isLoadingAnimRunning(){

        return mAnimRunning;
    }

    private Runnable mDelayStartAnimRunnable = new Runnable() {

        @Override
        public void run() {

            mIvFgIcon.startAnimation(mFgRotateAnim);
            mIvBgIcon.startAnimation(mBgSmallAnim);
        }
    };

    private Animation.AnimationListener mBgSmallAnimListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

            mIvBgIcon.startAnimation(mBgBigAnim);
        }
    };

    public boolean mBgBigAnimEnd;
    public Animation.AnimationListener mBgBigAnimListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

            mBgBigAnimEnd = true;
            if(mFgAnimEnd){

                mIvFgIcon.startAnimation(mFgRotateAnim);
                mIvBgIcon.startAnimation(mBgSmallAnim);
                mBgBigAnimEnd = false;
            }
        }
    };

    private boolean mFgAnimEnd = true;
    public Animation.AnimationListener mFgAnimListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

            mFgAnimEnd = true;
            if(mBgBigAnimEnd){

                mIvFgIcon.startAnimation(mFgRotateAnim);
                mIvBgIcon.startAnimation(mBgSmallAnim);
                mFgAnimEnd = false;
            }else{

                //nothing
            }
        }
    };

    /**
     * 前景icon动画插值器
     */
    private static class FgAnticipateInterpolator implements Interpolator {

        private final float mTension;

        public FgAnticipateInterpolator() {

            mTension = 2.0f;
        }

        public FgAnticipateInterpolator(float tension) {

            mTension = tension;
        }

        public float getInterpolation(float t) {

            if (t >= 0.0f && t < 0.5f)
                return (float)(t * t * (2 * t - 1));
            else
                return (float)(0.5f + 0.5f * Math.cos(2.0f * Math.PI * t));
        }
    }

    /**
     * 背景icon动画插值器
     */
    private static class BgDecelerateInterpolator implements Interpolator {

        public BgDecelerateInterpolator() {

        }

        public float getInterpolation(float input) {

            return (float)(1.0f - (1.0f - input) * (1.0f - input));
        }
    }
}
