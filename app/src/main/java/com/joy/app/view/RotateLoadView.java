package com.joy.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.joy.app.R;


/**
 * 中心不转,边旋转
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-12-16
 */
public class RotateLoadView extends RelativeLayout {

    private ImageView mCenterImageView;//中心的图形
    private ImageView mRotateImageView;//边边旋转的图
    private Animation mRotate;//旋转的动画

    public RotateLoadView(Context context) {
        super(context);
        initView();
        initAnim();

    }

    public RotateLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAnim();

    }

    public RotateLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAnim();

    }

    private void initView() {

        mCenterImageView = new ImageView(getContext());
        mRotateImageView = new ImageView(getContext());

        LayoutParams rotateImageParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rotateImageParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        rotateImageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        rotateImageParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rotateImageParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        addView(mRotateImageView, rotateImageParams);


        LayoutParams centerImageParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        centerImageParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        addView(mCenterImageView, centerImageParams);
        setImage(R.drawable.ic_loading_icon, R.drawable.ic_loading_rotate);
    }

    /**
     * 设置图片的属性
     *
     * @param centerIconResId
     * @param roateImageResId
     */
    private void setImage(int centerIconResId, int roateImageResId) {

        mRotateImageView.setImageResource(roateImageResId);
        mCenterImageView.setImageResource(centerIconResId);

    }

    private void initAnim() {

        mRotate = AnimationUtils.loadAnimation(this.getContext(), R.anim.anim_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        mRotate.setInterpolator(lin);
    }

    public void startAnim() {

        if (mRotateImageView != null && mRotateImageView.getAnimation() != null) {
            mRotateImageView.clearAnimation();
        }
        mRotateImageView.startAnimation(mRotate);
    }

    public void stopAnim() {

        if (mRotateImageView != null && mRotateImageView.getAnimation() != null) {
            mRotateImageView.clearAnimation();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mRotateImageView != null) {
            mRotateImageView.setImageBitmap(null);
            mRotateImageView.clearAnimation();
        }
        if (mCenterImageView != null)
            mCenterImageView.setImageBitmap(null);
        if (mRotate != null) {
            mRotate.cancel();
        }
    }
}
