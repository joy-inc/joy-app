package com.joy.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.joy.app.R;

/**
 * 线性布局构建成的listview
 *
 * create by xiaoyu.chen at 11/23/2015
 * */
public class LinearListView extends LinearLayout {

    private int mDivider;
    private int mDividerDrawableRid;
    private OnItemClickListener mOnItemClickLisn;

    public LinearListView(Context context) {
        super(context);
    }

    public LinearListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typeArray = context.obtainStyledAttributes(attrs,
                R.styleable.LinearListView);
        mDivider = typeArray.getDimensionPixelOffset(
                R.styleable.LinearListView_ll_divider, 0);
        mDividerDrawableRid = typeArray.getResourceId(
                R.styleable.LinearListView_ll_dividerDrawable, 0);
        typeArray.recycle();
    }

    public void setAdapter(BaseAdapter adapter) {

        if (getChildCount() != 0)
            removeAllViews();

        if (adapter == null)
            return;

        View convertView, dividerView = null;
        int size = adapter.getCount();
        for (int i = 0; i < size; i++) {

            convertView = adapter.getView(i, null, this);
            final int position = i;
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickLisn != null)
                        mOnItemClickLisn.onItemClick(position);
                }
            });

            addView(convertView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            if (i != size - 1 && mDivider != 0) {

                dividerView = new View(getContext());
                if (mDividerDrawableRid != 0) {
                    dividerView.setBackgroundResource(mDividerDrawableRid);
                }
                addView(dividerView, new LayoutParams(
                        LayoutParams.MATCH_PARENT, mDivider));
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener lisn) {
        mOnItemClickLisn = lisn;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
