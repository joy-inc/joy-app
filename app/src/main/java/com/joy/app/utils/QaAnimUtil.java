package com.joy.app.utils;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * 提供动画工具类
 * @author yhb
 *
 */
public class QaAnimUtil {
	
	/**
	 * 获取统一的浮动view放大入场动画
	 * @return
	 */
	public static Animation getFloatViewScaleShowAnim(){
		
		ScaleAnimation anim = new ScaleAnimation(0f,1f,0f,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		anim.setInterpolator(new OvershootInterpolator());
		anim.setDuration(300);
		anim.setFillAfter(true);
		return anim;
	}

    /**
     * 获取统一的浮动view缩小出场动画
     * @return
     */
	public static Animation getFloatViewScaleHideAnim(){
		
		ScaleAnimation anim = new ScaleAnimation(1f,0f,1f,0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		anim.setInterpolator(new AccelerateInterpolator());
		anim.setDuration(300);
		anim.setFillAfter(true);
		return anim;
	}

	/**
	 * 获取统一的浮动view从底部滑入入场动画
	 * @return
	 */
	public static Animation getFloatViewBottomSlideInAnim(){

        TranslateAnimation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                                                         Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);
		anim.setInterpolator(new DecelerateInterpolator());//先快后慢
		anim.setDuration(300);
		anim.setFillAfter(true);
		return anim;
	}

    /**
     * 获取统一的浮动view从底部滑出出场动画
     * @return
     */
	public static Animation getFloatViewBottomSlideOutAnim(){

        TranslateAnimation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                                                         Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
		anim.setInterpolator(new DecelerateInterpolator());//先快后慢
		anim.setDuration(300);
		anim.setFillAfter(true);
		return anim;
	}
}
