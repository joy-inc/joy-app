package com.joy.app.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joy.app.R;

/**
 * 可伸缩的TextView
 */
public class ExpandTextView extends LinearLayout implements OnClickListener {
    public static final String TAG = "ExpandTextView";

    public static final int DEFAULT_TEXT_COLOR = 0XFF000000;
    public static final float DEFAULT_SPACE_MUTIL = 1.0f;
    public static final float DEFAULT_SPACE_ADD = 0.0f;
    public static final int DEFAULT_LINE_NUM = 3;
    public static final int DEFAULT_TEXT_SIZE = 12;
    public static final int DEFAULT_MARGIN_TOP = 20;
    public static final int DEFAULT_EXPAND_STYPE = 0;
    private static final long DEFAULT_ANIMATION_DURATION = 200;
    private static final String DEFAULT_TIP_EXPAND = "展开";
    private static final String DEFAULT_TIP_COLLAPSE = "收起";

    private long mDuration = DEFAULT_ANIMATION_DURATION;
    private float mSpaceAdd = DEFAULT_SPACE_ADD;
    private float mMutilSpace = DEFAULT_SPACE_MUTIL;

    private TextView mTextView;
    private AppCompatButton acButtonTip;
    private ImageView mImageView;
    private View mTipView;
    private String mCollapseText;
    private String mExpandText;
    /**
     * TextView字体的颜色
     */
    private int textColor;
    /**
     * TextView字体的大小
     */
    private float textSize;
    /**
     * TextView默认显示行数
     */
    private int maxLine;
    /**
     * TextView的文本内容
     */
    private String text;
    /**
     * ImageView使用的图片
     */
    private Drawable mIcon;
    /**
     * TextView所有的内容暂用的行数
     */
    private int contentLine = 0;
    /**
     * 是否展开
     */
    private boolean isExpand = false;
    private int mExpandStyle = DEFAULT_EXPAND_STYPE;

    public ExpandTextView(Context context) {
        super(context);
        init(null, 0);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        TypedArray array = this.getContext().obtainStyledAttributes(attrs, R.styleable.ExpandTextView, defStyleAttr, 0);
        textColor = array.getColor(R.styleable.ExpandTextView_textcolor, DEFAULT_TEXT_COLOR);
        textSize = array.getDimensionPixelOffset(R.styleable.ExpandTextView_textsize, dp2px(DEFAULT_TEXT_SIZE));
        maxLine = array.getInt(R.styleable.ExpandTextView_lines, DEFAULT_LINE_NUM);
        mIcon = array.getDrawable(R.styleable.ExpandTextView_expand_icon);
        text = array.getString(R.styleable.ExpandTextView_text);
        mExpandStyle = array.getInt(R.styleable.ExpandTextView_expandStyle, DEFAULT_EXPAND_STYPE);
        mCollapseText = array.getString(R.styleable.ExpandTextView_collapseText);
        mExpandText = array.getString(R.styleable.ExpandTextView_expandText);

        if (mIcon == null) {
            mIcon = this.getContext().getResources().getDrawable(R.drawable.ic_star_light_small);
        }
        if (mCollapseText == null) {
            mCollapseText = DEFAULT_TIP_COLLAPSE;
        }
        if (mExpandText == null) {
            mExpandText = DEFAULT_TIP_EXPAND;
        }
        array.recycle();
        initViewAttribute();
    }

    private void initViewAttribute() {

        mTextView = new TextView(this.getContext());
        //设置属性
        mTextView.setText(text);
        mTextView.setTextColor(textColor);
        mTextView.setLineSpacing(mSpaceAdd, mMutilSpace);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        int textHeight = mTextView.getLineHeight() * maxLine;
        LayoutParams mParams_txt = new LayoutParams(LayoutParams.MATCH_PARENT, textHeight);
        addView(mTextView, mParams_txt);

        mTipView = onCreateTipView(mExpandStyle);
        postLineStatus();
    }

    public View onCreateTipView(int showStyle) {

        if (showStyle == 1) {
            mImageView = new ImageView(this.getContext());
            mImageView.setImageDrawable(mIcon);
            LayoutParams mParams_img = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            mParams_img.topMargin = dp2px(DEFAULT_MARGIN_TOP);
            addView(mImageView, mParams_img);
            mImageView.setOnClickListener(this);
            return mImageView;

        } else if (showStyle == 2) {

            acButtonTip = new AppCompatButton(this.getContext());
            acButtonTip.setGravity(Gravity.CENTER);
            acButtonTip.setText(mExpandText);

            acButtonTip.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            acButtonTip.setTextColor(getResources().getColor(R.color.color_accent));
            acButtonTip.setBackgroundResource(R.drawable.selector_bg_rectangle_accent);
            acButtonTip.setOnClickListener(this);
            LayoutParams mParams_img = new LayoutParams(dp2px(160), dp2px(40));
            mParams_img.topMargin = dp2px(DEFAULT_MARGIN_TOP);
            addView(acButtonTip, mParams_img);
            acButtonTip.setOnClickListener(this);
            return acButtonTip;
        }
        return null;
    }

    public void setTextViewClickable(boolean clickable) {

        mTextView.setOnClickListener(clickable ? this : null);
    }

    private void postLineStatus() {

        this.post(new Runnable() {

            @Override
            public void run() {

                contentLine = mTextView.getLineCount();

                if (contentLine <= maxLine) {
                    mTipView.setVisibility(View.GONE);
                    LayoutParams mParam = (LayoutParams) mTextView.getLayoutParams();
                    mParam.height = LayoutParams.WRAP_CONTENT;
                    mTextView.setLayoutParams(mParam);
                    ExpandTextView.this.setOnClickListener(null);
                } else {
                    //默认是非展开模式，那么设置最大行为maxLine
                    mTextView.setMaxLines(maxLine);
                    mTextView.setEllipsize(TruncateAt.END);
                    mTipView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setText(CharSequence text) {

        mTextView.setText(text);
        postLineStatus();
    }

    public void setText(int resId) {

        mTextView.setText(resId);
        postLineStatus();
    }

    /**
     * dp单位和px单位的转化
     *
     * @param dp
     * @return
     */
    private int dp2px(int dp) {
        return (int) (this.getResources().getDisplayMetrics().density * dp + 0.5);
    }

    @Override
    public void onClick(View v) {

        flexibleHeight();
    }

    /**
     * 对TextView进行伸缩处理
     */
    private void flexibleHeight() {

        isExpand = !isExpand;
        int textHeight = 0;

        if (isExpand) {
            //如果是展开模式，那么取消最大行为maxLine的限制
            textHeight = contentLine * mTextView.getLineHeight();
            mTextView.setMaxLines(contentLine);
        } else {
            textHeight = mTextView.getLineHeight() * maxLine;
        }

        final LayoutParams mParam = (LayoutParams) mTextView.getLayoutParams();
        //TextView的平移动画
        ValueAnimator aniTvContent = ValueAnimator.ofInt(mTextView.getHeight(), textHeight);
        aniTvContent.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mParam.height = (Integer) animation.getAnimatedValue();
                mTextView.setLayoutParams(mParam);
            }
        });

        startExpandTipAni(aniTvContent);
    }

    private void startExpandTipAni(Animator aniTvContent) {

        if (mExpandStyle == 1) {

            float startDegree = 0.0f;
            float endDegree = 180.0f;
            if (isExpand) {
            } else {
                startDegree = 180.0f;
                endDegree = 0.0f;
            }

            //imageView的旋转动画
            ObjectAnimator animator_img = ObjectAnimator.ofFloat(mTipView, "rotation", startDegree, endDegree);

            AnimatorSet mAnimatorSets = new AnimatorSet();
            mAnimatorSets.setDuration(500);
            mAnimatorSets.play(animator_img).with(aniTvContent);
            mAnimatorSets.start();
            mAnimatorSets.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {

                    super.onAnimationEnd(animation);
                    //动画结束之后，如果是非展开模式，则设置最大行数为maxLine
                    if (!isExpand) {
                        mTextView.setMaxLines(maxLine);
                    }
                }

            });

        } else if (mExpandStyle == 2) {

            aniTvContent.setDuration(mDuration);
            aniTvContent.start();
            aniTvContent.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {

                    super.onAnimationEnd(animation);
                    //动画结束之后，如果是非展开模式，则设置最大行数为maxLine
                    if (!isExpand) {
                        mTextView.setMaxLines(maxLine);
                        acButtonTip.setText(mExpandText);
                    } else {
                        acButtonTip.setText(mCollapseText);
                    }
                }

            });
        }
    }


}
